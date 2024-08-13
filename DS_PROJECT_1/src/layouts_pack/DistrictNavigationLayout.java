package layouts_pack;

import java.util.Date;

import data_pack.District;
import dataholder_pack.DataHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class DistrictNavigationLayout extends TabLayout {

	private Label districtNameL, totalMartyrsL, totalMalesL, totalFemalesL,
				avgAgesL, mostFreqDateL, totalForDateL;
	private Button nextBtn, prevBtn, calcMartyrsOfDateBtn;
	private ComboBox<Integer> yearCB, monthCB, dayCB;
	
	public DistrictNavigationLayout(DataHolder dataHolder) {
		super("District Navigation", dataHolder);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Pane createLayout() {
		districtNameL = new Label("District Name");
		districtNameL.getStyleClass().add("title");
		totalMartyrsL = new Label("martyrs");
		totalMalesL = new Label("males");
		totalFemalesL = new Label("females");
		avgAgesL = new Label("avg");
		mostFreqDateL = new Label("date");
		totalForDateL = new Label("total for date");
		Label enterDateL = new Label("Enter the date: "),
				totalMartyrsDateL = new Label("Total martyrs for the selected date: "),
				matrysL = new Label("Total Martyrs: "),
				malesL = new Label("Total Males: "),
				femalesL = new Label("Total Females: "),
				agesL = new Label("Average ages: "),
				freqDateL = new Label("Most occuring date: ");
		
		
		nextBtn = new Button("-->");
		prevBtn = new Button("<--");
		calcMartyrsOfDateBtn = new Button("Calculate");
		
		nextBtn.setOnAction(e -> {
			getDataHolder().nextDistrict();
			fillLayoutWithData();
			yearCB.getSelectionModel().clearSelection();
			monthCB.getSelectionModel().clearSelection();
			dayCB.getSelectionModel().clearSelection();
			totalForDateL.setText(0 + "");
		});
		prevBtn.setOnAction(e -> {
			getDataHolder().prevDistrict();
			fillLayoutWithData();
			yearCB.getSelectionModel().clearSelection();
			monthCB.getSelectionModel().clearSelection();
			dayCB.getSelectionModel().clearSelection();
			totalForDateL.setText(0 + "");
		});
		calcMartyrsOfDateBtn.setOnAction(e -> {
			if (isAnyEmpty(yearCB, monthCB, dayCB)) 
				totalForDateL.setText("Date is not fully specified");
			else if (getDataHolder().getDistricts().length() != 0)
				totalForDateL.setText(
						getDataHolder().
						getCurrentDistrict().
						getStat().
						calcTotalMartyrsInDate(
								new Date(yearCB.getValue()-1900, monthCB.getValue()-1, dayCB.getValue()))+"");
		});
		
		yearCB = new ComboBox<>();
		monthCB = new ComboBox<>();
		dayCB = new ComboBox<>();
		
		fillDateCB();
		
		GridPane midLayout = new GridPane(20, 10);
		midLayout.setAlignment(Pos.CENTER);
		
		midLayout.add(matrysL, 0, 0);
		midLayout.add(totalMartyrsL, 1, 0);
		midLayout.add(malesL, 0, 1);
		midLayout.add(totalMalesL, 1, 1);
		midLayout.add(femalesL, 0, 2);
		midLayout.add(totalFemalesL, 1, 2);
		midLayout.add(agesL, 0, 3);
		midLayout.add(avgAgesL, 1, 3);
		midLayout.add(freqDateL, 0, 4);
		midLayout.add(mostFreqDateL, 1, 4);
		
		GridPane dateGP = new GridPane(10, 10);
		dateGP.setAlignment(Pos.CENTER);
		dateGP.setPadding(new Insets(15, 0, 15, 0));
		dateGP.add(new Label("Year"), 0, 0);
		dateGP.add(new Label("Month"), 1, 0);
		dateGP.add(new Label("Day"), 2, 0);
		dateGP.add(yearCB, 0, 1);
		dateGP.add(monthCB, 1, 1);
		dateGP.add(dayCB, 2, 1);
		
		midLayout.add(enterDateL, 0, 5);
		midLayout.add(dateGP, 1, 5);
		
		midLayout.add(totalMartyrsDateL, 0, 6);
		midLayout.add(totalForDateL, 1, 6);
		midLayout.add(calcMartyrsOfDateBtn, 2, 6);
		
		BorderPane layout = new BorderPane();
		layout.setCenter(midLayout);
		layout.setTop(districtNameL);
		
		HBox navButtons = new HBox(15, prevBtn, nextBtn);
		navButtons.setAlignment(Pos.CENTER);
		layout.setBottom(navButtons);
		
		layout.setPadding(new Insets(15));
		BorderPane.setAlignment(prevBtn, Pos.CENTER);
		BorderPane.setAlignment(nextBtn, Pos.CENTER);
		BorderPane.setAlignment(districtNameL, Pos.CENTER);
		
		fillLayoutWithData();
		
		return layout;
	}
	
	private void fillLayoutWithData() { 
		District district = getDataHolder().getCurrentDistrict();
		if (getDataHolder().getDistricts().length() == 0) {
			districtNameL.setText(" Empty ");
			totalMartyrsL.setText("");
			totalMalesL.setText("");
			totalFemalesL.setText("");
			avgAgesL.setText("");
			mostFreqDateL.setText("");
			return;  
		}
		districtNameL.setText(district.getName());
		totalMartyrsL.setText(district.getStat().getTotalMartyrs()+"");
		totalMalesL.setText(district.getStat().getTotalMales()+"");
		totalFemalesL.setText(district.getStat().getTotalFemales()+"");
		avgAgesL.setText(district.getStat().getAvgAges()+"");
		mostFreqDateL.setText(district.getStat().getMostFreqDate()+"");
	}
	
	private void fillDateCB() { 
		for (int i = 1; i <= 31; i++)  
			dayCB.getItems().add(i); 
		
		for (int i = 1; i <= 12; i++)
			monthCB.getItems().add(i);
		
		for (int i = 1970; i <= 2024; i++) 
			yearCB.getItems().add(i);
	}
	
	private boolean isAnyEmpty(ComboBox<?>... boxs) {
		for (ComboBox<?> box : boxs) 
			if (box.getValue() == null) 
				return true;
		return false;
	}

	@Override
	public void updateContent() {
		fillLayoutWithData();
	}

}



















