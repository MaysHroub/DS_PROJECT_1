package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import data.*;
import dataholder.DataHolder;
import doublylinkedlist.DoublyLinkedList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import layouts.DistrictNavigationLayout;
import layouts.LocationNavigationLayout;
import layouts.ModifyDistrictLayout;
import layouts.ModifyLocationLayout;
import layouts.ModifyMartyrLayout;
import scenes.DistrictScene;
import scenes.LocationScene;
import scenes.MartyrScene;
import scenes.SceneID;
import scenes.SceneManager;

public class AppLauncher extends Application {

	final int WIDTH = 1050, HEIGHT = 550;

	@Override
	public void start(Stage primaryStage) {

		Button loadBtn = new Button("Load data");
		
		loadBtn.setStyle("-fx-font-size: 13;"
				+ "	-fx-font-weight: bold;"
				+ "	-fx-font-family: 'Cambria';"
				+ "	-fx-background-color: #fca503;"
				+ "	-fx-text-fill: white;");
		loadBtn.setOnAction(e -> {
			DoublyLinkedList<District> districts = loadData(primaryStage);
			if (districts == null)
				return;
			initPorgram(districts, primaryStage);
		});
		
		primaryStage.setScene(new Scene(new StackPane(loadBtn), WIDTH, HEIGHT));
		primaryStage.show();
		primaryStage.setAlwaysOnTop(true);
	}

	private void initPorgram(DoublyLinkedList<District> districts, Stage primaryStage) {
		// create the data holder
		DataHolder dataHolder = new DataHolder(districts);

		// create the tab layouts
		ModifyDistrictLayout modifyDist = new ModifyDistrictLayout(dataHolder);
		DistrictNavigationLayout distNav = new DistrictNavigationLayout(dataHolder);

		ModifyLocationLayout modifyLoc = new ModifyLocationLayout(dataHolder);
		LocationNavigationLayout locNav = new LocationNavigationLayout(dataHolder);

		ModifyMartyrLayout modifyMartyr = new ModifyMartyrLayout(dataHolder);

		// create the scene manager
		SceneManager manager = new SceneManager(primaryStage);

		// create the scenes
		DistrictScene districtScene = new DistrictScene(manager, SceneID.DISTRICT, WIDTH, HEIGHT, modifyDist, distNav);
		LocationScene locationScene = new LocationScene(manager, SceneID.LOCATION, WIDTH, HEIGHT, locNav, modifyLoc);
		MartyrScene martyrScene = new MartyrScene(manager, SceneID.MARTYR, WIDTH, HEIGHT, modifyMartyr);

		// set scenes to the manager
		manager.setScenes(districtScene, locationScene, martyrScene);

		manager.switchTo(SceneID.DISTRICT);
	}

	DoublyLinkedList<District> loadData(Stage stage) {
		DoublyLinkedList<District> districts = new DoublyLinkedList<>();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a file");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv files", "*.csv"));
		fileChooser.setInitialDirectory(new File("/C:/Users/ismae/Downloads"));
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile == null)
			return null;
		try (Scanner in = new Scanner(new FileInputStream(selectedFile))) {
			in.nextLine();
			while (in.hasNext()) {
				String line = in.nextLine();

				Martyr martyr = Martyr.constructMartyr(line);
				if (martyr == null)
					continue;
				District district = martyr.getDistrict();
				District districtInList = districts.find(district);
				if (districtInList == null) {
					districtInList = district;
					districts.insert(districtInList);
					DistrictStat stat = new DistrictStat(districtInList);
					districtInList.setStat(stat);
				}
				martyr.setDistrict(districtInList);
				Location location = martyr.getLocation();
				Location locationInList = districtInList.getLocations().find(location);
				if (locationInList == null) {
					locationInList = location;
					districtInList.getLocations().insert(locationInList);
					LocationStat stat = new LocationStat(locationInList);
					locationInList.setStat(stat);
				}
				martyr.setLocation(locationInList);
				locationInList.getMartyrs().insert(martyr);
			}

		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return districts;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
