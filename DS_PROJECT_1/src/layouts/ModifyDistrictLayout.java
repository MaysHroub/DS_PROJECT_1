package layouts;

import data.*;
import dataholder.DataHolder;
import doublylinkedlist.DNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import linkedlist.LinkedList;

public class ModifyDistrictLayout extends TabLayout {
	
	private TextField insertTF, newNameTF, locNameTF;
	private Label statusL;
	private Button insertBtn, deleteBtn, updateBtn;
	private ComboBox<District> districtsCB;
	
	
	public ModifyDistrictLayout(DataHolder dataHolder) {
		super("Modify District", dataHolder);
	}

	@Override
	protected Pane createLayout() {
		Label enterNameL = new Label("Enter District Name: "),
				enterFirstLocL = new Label("Enter the name of the first location: "),
				enterNewL = new Label("Enter new name for selected district: "),
				selectL = new Label("Select a district: ");
		
		statusL = new Label("status");
		
		insertTF = new TextField();
		newNameTF = new TextField();
		locNameTF = new TextField();
		
		insertBtn = new Button("Insert");
		deleteBtn = new Button("Delete selected district");
		updateBtn = new Button("Update");
		
		insertBtn.setOnAction(e -> insertDistrict());
		deleteBtn.setOnAction(e -> deleteDistrict());
		updateBtn.setOnAction(e -> updateDistrict());
		
		districtsCB = new ComboBox<>();
		fillDistrictCB();
		
		GridPane layout = new GridPane(15, 15);
		layout.setPadding(new Insets(20));
		layout.setAlignment(Pos.CENTER);
		
		layout.add(enterNameL, 0, 0);
		layout.add(insertTF, 1, 0);
		layout.add(insertBtn, 2, 0);
		
		layout.add(enterFirstLocL, 0, 1);
		layout.add(locNameTF, 1, 1);
		
		layout.add(selectL, 0, 2);
		layout.add(districtsCB, 1, 2);
		
		layout.add(deleteBtn, 0, 3);
		
		layout.add(enterNewL, 0, 4);
		layout.add(newNameTF, 1, 4);
		layout.add(updateBtn, 2, 4);
		
		layout.add(statusL, 1, 5);
		
		return layout;
	}

	private void fillDistrictCB() { // O(D)
		DNode<District> curr = getDataHolder().getDistricts().getHead();
		while (curr != null) {
			districtsCB.getItems().add(curr.getData());
			curr = curr.getNext();
		}
	}

	private void updateDistrict() { // O(D)
		if (newNameTF.getText() == null || newNameTF.getText().equals("")) {
			statusL.setText("Text field is empty");
			return;
		}
		if (districtsCB.getValue() == null) {
			statusL.setText("No district is selected");
			return;
		}
		District newNameDist = getDataHolder().getDistricts().find(new District(newNameTF.getText())); 
		if (newNameDist != null) {
			statusL.setText("District name is already taken");
			return;
		}
		
		DNode<District> deletedNode = getDataHolder().getDistricts().delete(districtsCB.getValue()); // O(D)
		districtsCB.getItems().remove(deletedNode.getData()); // O(D)
		deletedNode.getData().setName(newNameTF.getText());
		getDataHolder().getDistricts().insert(deletedNode.getData()); // O(D)
		districtsCB.getItems().add(deletedNode.getData()); // constant
		statusL.setText("District name is updated");
	}

	private void deleteDistrict() { // O(D)
		District selectedDistrict = districtsCB.getValue();
		if (selectedDistrict == null) {
			statusL.setText("No district is selected");
			return;
		}
		getDataHolder().getDistricts().delete(selectedDistrict); // O(D)
		districtsCB.getItems().remove(selectedDistrict); // O(D)
		statusL.setText("District  " + selectedDistrict + "  is deleted");
	}

	private void insertDistrict() { // O(D + L)
		if (insertTF.getText() == null || insertTF.getText().equals("") ||
				locNameTF.getText() == null || locNameTF.getText().equals("")) {
			statusL.setText("Text field is empty");
			return;
		}
		LinkedList<Location> locations = new LinkedList<>();
		Location location = new Location(locNameTF.getText());
		LocationStat locStat = new LocationStat(location);
		location.setStat(locStat);
		locations.insert(location); // O(L)
		District district = new District(insertTF.getText(), locations);
		DistrictStat stat = new DistrictStat(district);
		district.setStat(stat);
		if (getDataHolder().getDistricts().find(district) != null) {
			statusL.setText("District name already exists");
			return;
		}
		getDataHolder().getDistricts().insert(district); // O(D)
		districtsCB.getItems().add(district); // constant
		statusL.setText("District  " + insertTF.getText() + "  is inserted");
	}

	@Override
	public void updateContent() { /*do nothing*/ }
	
}