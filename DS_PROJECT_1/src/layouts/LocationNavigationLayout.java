package layouts;

import data.District;
import data.Location;
import data.Martyr;
import dataholder.DataHolder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class LocationNavigationLayout extends TabLayout {

	private Label locationNameL, totalMartyrsL, totalMalesL, totalFemalesL, avgAgesL, youngestMartyrL, oldestMartyrL;
	private Button nextBtn;

	
	public LocationNavigationLayout(DataHolder dataHolder) {
		super("Location navigation", dataHolder);
	}

	@Override
	protected Pane createLayout() {
		locationNameL = new Label("Location Name");
		locationNameL.getStyleClass().add("title");
		totalMartyrsL = new Label("martyrs");
		totalMalesL = new Label("males");
		totalFemalesL = new Label("females");
		avgAgesL = new Label("avg");
		youngestMartyrL = new Label("youngest");
		oldestMartyrL = new Label("oldest");

		Label matrysL = new Label("Total Martyrs: "), malesL = new Label("Total Males: "),
				femalesL = new Label("Total Females: "), agesL = new Label("Average ages: "),
				youngestL = new Label("Youngest Martyr: "), oldestL = new Label("Oldest Martyr: ");

		nextBtn = new Button("-->");
		
		nextBtn.setOnAction(e -> {
			if (getDataHolder().getDistricts().length() == 0 
					/*getDataHolder().getCurrentDistrict().getLocations().length() == 0*/) 
				return;
			getDataHolder().nextLocation();
			fillLayoutWithData();
		});

		GridPane midLayout = new GridPane(15, 15);
		midLayout.setAlignment(Pos.CENTER);

		midLayout.add(matrysL, 0, 0);
		midLayout.add(totalMartyrsL, 1, 0);
		midLayout.add(malesL, 0, 1);
		midLayout.add(totalMalesL, 1, 1);
		midLayout.add(femalesL, 0, 2);
		midLayout.add(totalFemalesL, 1, 2);
		midLayout.add(agesL, 0, 3);
		midLayout.add(avgAgesL, 1, 3);
		midLayout.add(youngestL, 0, 4);
		midLayout.add(youngestMartyrL, 1, 4);
		midLayout.add(oldestL, 0, 5);
		midLayout.add(oldestMartyrL, 1, 5);

		BorderPane layout = new BorderPane();
		layout.setCenter(midLayout);
		layout.setTop(locationNameL);
		layout.setBottom(nextBtn);

		layout.setPadding(new Insets(15));
		BorderPane.setAlignment(nextBtn, Pos.CENTER);
		BorderPane.setAlignment(locationNameL, Pos.CENTER);
		
		fillLayoutWithData();

		return layout;
	}
	
	private void fillLayoutWithData() {  // O(L + D)
		 // O(D)
		if (getDataHolder().getDistricts().length() == 0) { // no districts at all
			locationNameL.setText(" Data is Empty ");
			totalMartyrsL.setText("");
			totalMalesL.setText("");
			totalFemalesL.setText("");
			avgAgesL.setText("");
			youngestMartyrL.setText("");
			oldestMartyrL.setText("");
			return;  
		}
		District district = getDataHolder().getCurrentDistrict();
		 // O(L)
		if (district.getLocations().length() == 0) {  // no locations for this district
			locationNameL.setText(" Loc is Empty ");
			totalMartyrsL.setText("");
			totalMalesL.setText("");
			totalFemalesL.setText("");
			avgAgesL.setText("");
			youngestMartyrL.setText("");
			oldestMartyrL.setText("");
			return;
		}
		Location firstLoc = getDataHolder().getCurrentLocation();
		locationNameL.setText(firstLoc.getName());
		totalMartyrsL.setText(firstLoc.getStat().getTotalMartyrs()+"");
		totalMalesL.setText(firstLoc.getStat().getTotalMales()+"");
		totalFemalesL.setText(firstLoc.getStat().getTotalFemales()+"");
		avgAgesL.setText(firstLoc.getStat().getAvgAges()+"");
		Martyr youngest = firstLoc.getStat().getYoungest(),
				oldest = firstLoc.getStat().getOldest();
		youngestMartyrL.setText((youngest == null) ? "null" : youngest.getName() + "  with age  " + youngest.getAge());
		oldestMartyrL.setText((oldest == null) ? "null" : oldest.getName() + "  with age  " + oldest.getAge());
	}

	@Override
	public void updateContent() { // O(N)
		fillLayoutWithData();
	}


}
