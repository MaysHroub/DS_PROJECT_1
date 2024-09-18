package layouts;

import data.*;
import dataholder.DataHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import linkedlist.Node;

public class ModifyLocationLayout extends TabLayout {

	private Label statusL;
	private Button insertBtn, deleteBtn, updateBtn;
	private TextField insertTF, updateTF;
	private ComboBox<Location> locationsCB;
	
	
	public ModifyLocationLayout(DataHolder dataHolder) {
		super("Modify Locations", dataHolder);
	}

	
	@Override
	protected Pane createLayout() {
		Label enterName = new Label("Enter the location name: "),
				selectL = new Label("Select a location: "),
				enterNewNameL = new Label("Enter the new location name: ");
		statusL = new Label("status");
		
		insertBtn = new Button("Insert");
		deleteBtn = new Button("Delete selected location");
		updateBtn = new Button("Update");
		
		insertBtn.setOnAction(e -> insertLocation());
		deleteBtn.setOnAction(e -> deleteLocation());
		updateBtn.setOnAction(e -> updateLocation());
		
		insertTF = new TextField();
		updateTF = new TextField();
		
		locationsCB = new ComboBox<>();
		fillLocationsCB();
		
		GridPane layout = new GridPane(15, 15);
		layout.setPadding(new Insets(15));
		layout.setAlignment(Pos.CENTER);
		
		layout.add(enterName, 0, 0);
		layout.add(insertTF, 1, 0);
		layout.add(insertBtn, 2, 0);
		layout.add(selectL, 0, 2);
		layout.add(locationsCB, 1, 2);
		layout.add(deleteBtn, 0, 3);
		layout.add(enterNewNameL, 0, 4);
		layout.add(updateTF, 1, 4);
		layout.add(updateBtn, 2, 4);
		layout.add(statusL, 1, 5);
		
		return layout;
	}


	private void updateLocation() {  // O(L)
		if (updateTF.getText() == null || updateTF.getText().equals("")) {
			statusL.setText("Text field is empty");
			return;
		}
		if (locationsCB.getValue() == null) {
			statusL.setText("No location is selected");
			return;
		}
		if (getDataHolder().getCurrentDistrict().getLocations().find(new Location(updateTF.getText())) != null) {
			statusL.setText("Location name is already taken");
			return;
		}
		Node<Location> deletedNode = getDataHolder().getCurrentDistrict().getLocations().delete(locationsCB.getValue()); // O(L)
		locationsCB.getItems().remove(deletedNode.getData()); // O(L)
		deletedNode.getData().setName(updateTF.getText());
		getDataHolder().getCurrentDistrict().getLocations().insert(deletedNode.getData()); // O(L)
		locationsCB.getItems().add(deletedNode.getData()); // constant
		statusL.setText("Location name is updated");
	}


	private void deleteLocation() { // O(N)
		Location selectedLocation = locationsCB.getValue();
		if (selectedLocation == null) {
			statusL.setText("No location is selected");
			return;
		}
		getDataHolder().getCurrentDistrict().getLocations().delete(selectedLocation); // O(L)
		locationsCB.getItems().remove(selectedLocation); // O(L)
		getDataHolder().getCurrentDistrict().getStat().updateStats(); // O(N)
		
		statusL.setText("Location  " + selectedLocation + "  is deleted");
	}

	private void insertLocation() {  // O(L)
		if (getDataHolder().getDistricts().length() == 0) 
			return;
		if (insertTF.getText() == null || insertTF.getText().equals("")) {
			statusL.setText("Text field is empty");
			return;
		}
		Location location = new Location(insertTF.getText());
		LocationStat stat = new LocationStat(location);
		location.setStat(stat);
		if (getDataHolder().getCurrentDistrict().getLocations().find(location) != null) { // O(L)
			statusL.setText("Location name already exists");
			return;
		}
		getDataHolder().getCurrentDistrict().getLocations().insert(location); // O(L)
		locationsCB.getItems().add(location); // constant
		statusL.setText("Location  " + insertTF.getText() + "  is inserted");
	}

	@Override
	public void updateContent() { // O(L)
		// update the combobox
		if (getDataHolder().getDistricts().length() == 0) {
			locationsCB.getItems().clear();
			return;
		}
		locationsCB.getItems().clear();
		fillLocationsCB(); // O(L)
		statusL.setText("");
	}

	private void fillLocationsCB() { // O(L)
		Node<Location> curr = getDataHolder().getCurrentDistrict().getLocations().getHead();
		while (curr != null) { // O(L)
			locationsCB.getItems().add(curr.getData());
			curr = curr.getNext();
		}
	}

}















