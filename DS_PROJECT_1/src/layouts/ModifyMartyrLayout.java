package layouts;

import java.util.ArrayList;
import java.util.Date;

import data.*;
import dataholder.DataHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import linkedlist.Node;

public class ModifyMartyrLayout extends TabLayout {

	private Label statusL;
	private Button insertBtn, deleteBtn, updateBtn, searchBtn;
	private TextField nameTF, updateTF, searchTF;
	private ComboBox<Martyr> martyrCB;
	private ComboBox<Integer> ageCB, yearCB, monthCB, dayCB;
	private ComboBox<Character> genderCB;
	private TextArea searchTA;


	public ModifyMartyrLayout(DataHolder dataHolder) {
		super("Modify Martyrs", dataHolder);
	}

	@Override
	protected Pane createLayout() {
		Label enterName = new Label("Enter the martyr's name: "),
				selectAge = new Label("Select the martyr's age: "),
				selectGender = new Label("Select the martyr's gender: "),
				selectDate = new Label("Select the martyr's event date: "),
				selectL = new Label("Select a martyr: "),
				enterNewNameL = new Label("Enter a new name for the selected martyr: "),
				searchByNameL = new Label("Enter the martyr's name (or part of it) : ");
		statusL = new Label("status");

		insertBtn = new Button("Insert");
		deleteBtn = new Button("Delete selected martyr");
		updateBtn = new Button("Update Name");
		searchBtn = new Button("Search");
		
		insertBtn.setOnAction(e -> insertMartyr());
		deleteBtn.setOnAction(e -> deleteMartyr());
		updateBtn.setOnAction(e -> updateMartyrName());
		searchBtn.setOnAction(e -> searchMartyr());

		nameTF = new TextField();
		updateTF = new TextField();
		searchTF = new TextField();

		martyrCB = new ComboBox<>();
		fillMartyrsCB();
		ageCB = new ComboBox<>();
		fillAgeCB();
		dayCB = new ComboBox<>();
		monthCB = new ComboBox<>();
		yearCB = new ComboBox<>();
		fillDateCB();
		genderCB = new ComboBox<>();
		genderCB.getItems().addAll('F', 'M');
		
		searchTA = new TextArea();
		searchTA.setEditable(false);
		searchTA.setPrefColumnCount(25);
		
		GridPane dateGP = new GridPane(10, 10);
		dateGP.setAlignment(Pos.CENTER);
		dateGP.setPadding(new Insets(10, 0, 20, 0));
		dateGP.add(new Label("Year"), 0, 0);
		dateGP.add(new Label("Month"), 1, 0);
		dateGP.add(new Label("Day"), 2, 0);
		dateGP.add(yearCB, 0, 1);
		dateGP.add(monthCB, 1, 1);
		dateGP.add(dayCB, 2, 1);
		
		GridPane gp = new GridPane(15, 15);
		gp.setAlignment(Pos.CENTER);

		gp.add(enterName, 0, 0);
		gp.add(nameTF, 1, 0);
		gp.add(insertBtn, 2, 0);
		gp.add(selectAge, 0, 1);
		gp.add(ageCB, 1, 1);
		gp.add(selectGender, 0, 2);
		gp.add(genderCB, 1, 2);
		gp.add(selectDate, 0, 3);
		gp.add(dateGP, 1, 3);
		
		gp.add(selectL, 0, 4);
		gp.add(martyrCB, 1, 4);
		gp.add(deleteBtn, 0, 5);
		gp.add(enterNewNameL, 0, 6);
		gp.add(updateTF, 1, 6);
		gp.add(updateBtn, 2, 6);
		gp.add(searchByNameL, 0, 7);
		gp.add(searchTF, 1, 7);
		gp.add(searchBtn, 2, 7);
		gp.add(statusL, 1, 8);
		
		searchTA.maxHeightProperty().bind(gp.heightProperty().divide(1.5));
		
		HBox layout = new HBox(20, gp, searchTA);
		layout.setPadding(new Insets(10));
		layout.setAlignment(Pos.CENTER);
		return layout;
	}

	private void searchMartyr() {  // O(N) [due to length()] + O(MS)
		if (getDataHolder().getDistricts().length() == 0 ||
				getDataHolder().getCurrentDistrict().getLocations().length() == 0) 
			return;
		
		if (searchTF.getText() == null || searchTF.getText().equals("")) {
			statusL.setText("Text field is empty");
			return;
		}
		Node<Martyr> curr = getDataHolder().getCurrentLocation().getMartyrs().getHead();
		// O(MS)
		ArrayList<Martyr> allMatches = new ArrayList<>();
		while (curr != null) {  // O(M)
			if (curr.getData().getName().toLowerCase().contains(searchTF.getText().toLowerCase()))  // O(S)
				allMatches.add(curr.getData());
			curr = curr.getNext();
		}
		StringBuilder strbld = new StringBuilder();
		System.out.println(allMatches.size());
		for (int i = 0; i < allMatches.size(); i++) {
			System.out.println(allMatches.get(i).getInfo());
			strbld.append(allMatches.get(i).getInfo());
			strbld.append("\n---------------------------\n");
		}
		searchTA.setText(strbld.toString());
	}

	private void updateMartyrName() {  // O(M)
		if (updateTF.getText() == null || updateTF.getText().equals("")) {
			statusL.setText("Text field is empty");
			return;
		}
		if (martyrCB.getValue() == null) {
			statusL.setText("No martyr is selected");
			return;
		}
		Node<Martyr> deletedNode = getDataHolder().getCurrentLocation().getMartyrs().delete(martyrCB.getValue());  // O(M)
		martyrCB.getItems().remove(deletedNode.getData());  // O(M)
		deletedNode.getData().setName(updateTF.getText());
		getDataHolder().getCurrentLocation().getMartyrs().insert(deletedNode.getData());  // O(M)
		martyrCB.getItems().add(deletedNode.getData());  // constant
		statusL.setText("Martyr name is updated");
	}

	private void deleteMartyr() {  // O(N)
		Martyr selectedMartyr = martyrCB.getValue();
		if (selectedMartyr == null) {
			statusL.setText("No martyr is selected");
			return;
		}
		// since the linked list deletes based on compareTo method
		// so it deletes martyrs based on the age 
		// for the case that there are more than 1 martyr with the same age, first match is deleted
		getDataHolder().getCurrentLocation().getMartyrs().deleteByEquals(selectedMartyr); // O(M)
		martyrCB.getItems().remove(selectedMartyr);  // O(M)
		statusL.setText("Martyr  " + selectedMartyr + "  is deleted");
		updateStats();  // O(N)
	}

	private void insertMartyr() {  // O(N) due to length()
		if (getDataHolder().getDistricts().length() == 0 ||
				getDataHolder().getCurrentDistrict().getLocations().length() == 0) 
			return;
		
		if (isAnyEmpty(nameTF, ageCB, yearCB, monthCB, dayCB, genderCB)) {
			statusL.setText("Martyr is not inserted. No enough information.");
			return;
		}
		String name = nameTF.getText();
		int age = ageCB.getValue();
		@SuppressWarnings("deprecation")
		Date date = new Date(yearCB.getValue() - 1900, monthCB.getValue() - 1, dayCB.getValue());
		char gender = genderCB.getValue();
		Martyr martyr = new Martyr(name, age, gender, date,
				getDataHolder().getCurrentDistrict(), getDataHolder().getCurrentLocation());
		getDataHolder().getCurrentLocation().getMartyrs().insert(martyr);  // O(M)
		martyrCB.getItems().add(martyr);
		statusL.setText("Martyr  " + name + "  is inserted");
		nameTF.setText("");
		ageCB.getSelectionModel().clearSelection();
		yearCB.getSelectionModel().clearSelection();
		monthCB.getSelectionModel().clearSelection();
		dayCB.getSelectionModel().clearSelection();
		genderCB.getSelectionModel().clearSelection();
		updateStats();
	}
	
	private void updateStats() {  // O(M) + O(N) -> O(N)
		// update the location first because the district depends on it
		getDataHolder().getCurrentLocation().getStat().updateStats();
		getDataHolder().getCurrentDistrict().getStat().updateStats();
	}

	@Override
	public void updateContent() {  // O(N) due to length()
		if (getDataHolder().getDistricts().length() == 0 ||
				getDataHolder().getCurrentDistrict().getLocations().length() == 0) {
			martyrCB.getItems().clear();
			return;
		}
		// update the combobox
		martyrCB.getItems().clear();
		fillMartyrsCB();  // O(M)
		statusL.setText("");
	}
	
	private void fillMartyrsCB() {  // O(M)
		Node<Martyr> curr = getDataHolder().getCurrentLocation().getMartyrs().getHead();
		while (curr != null) {
			martyrCB.getItems().add(curr.getData());
			curr = curr.getNext();
		}
	}
	
	private void fillDateCB() { 
		for (int i = 1; i <= 31; i++)  
			dayCB.getItems().add(i); 
		
		for (int i = 1; i <= 12; i++)
			monthCB.getItems().add(i);
		
		for (int i = 1970; i <= 2024; i++) 
			yearCB.getItems().add(i);
	}
	
	private void fillAgeCB() {
		for (int i = 0; i <= 150; i++) 
			ageCB.getItems().add(i); 
	} 
	
	private boolean isAnyEmpty(javafx.scene.Node... nodes) {
		for (javafx.scene.Node node : nodes) 
			if (node instanceof TextField) {
				if (((TextField) node).getText() == null || ((TextField) node).getText().equals(""))
					return true;
			}
			else if (node instanceof ComboBox) {
				if (((ComboBox<?>) node).getValue() == null) 
					return true;
			}
		return false;
	}

}
