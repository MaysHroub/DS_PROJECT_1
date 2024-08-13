package temp;

import dataholder_pack.DataHolder;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AllScenes {

}

abstract class TabLayout {
	
	private Pane layout;
	private String name;
	private DataHolder dataHolder;
	
	protected TabLayout(String name, DataHolder dataHolder) {
		this.name = name;
		this.dataHolder = dataHolder;
		layout = createLayout();
	}

	protected abstract Pane createLayout();
	
	public abstract void updateContent();

	public String getName() {
		return name;
	}

	public Pane getLayout() {
		return layout;
	}

	public DataHolder getDataHolder() {
		return dataHolder;
	}
	
}

abstract class TabScene {
	
	private Scene scene;
	private SceneID id;
	private SceneManager manager;
	private TabLayout[] layouts;
	
	public TabScene(SceneManager manager, SceneID id, int width, int height, TabLayout... layouts) {
		this.id = id;
		this.manager = manager;
		this.layouts = layouts;
		scene = createScene(width, height);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
	}
	
	public Scene getScene() {
		return scene;
	}

	public SceneID getId() {
		return id;
	}
	
	protected abstract Scene createScene(int width, int height);
	
	public void updateLayouts() {
		for (int i = 0; i < layouts.length; i++) 
			layouts[i].updateContent();
	}

	public TabLayout[] getLayouts() {
		return layouts;
	}

	public SceneManager getManager() {
		return manager;
	}
	
}

class SceneManager {
	
	private TabScene[] scenes;
	private Stage primaryStage;
	
	public SceneManager() {}
	
	public SceneManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public void setScenes(TabScene... scenes) {
		this.scenes = scenes;
	}
	
	public void switchTo(SceneID id) {
		for (int i = 0; i < scenes.length; i++) 
			if (scenes[i].getId() == id) {
				primaryStage.setScene(scenes[i].getScene());
				scenes[i].updateLayouts();
				System.out.println("switching to " + id);
				break;
			}
	}
	
	public Scene getSceneByID(SceneID id) {
		for (int i = 0; i < scenes.length; i++) 
			if (scenes[i].getId() == id) 
				return scenes[i].getScene();
		return null;
	}

}


enum SceneID {
	DISTRICT,
	LOCATION,
	MARTYR
}

class MartyrScene extends TabScene {

	public MartyrScene(SceneManager manager, SceneID id, int width, int height, TabLayout... layouts) {
		super(manager, id, width, height, layouts);
	}

	@Override
	protected Scene createScene(int width, int height) {
		TabPane tabPane = new TabPane();
		TabLayout[] layouts = getLayouts();

		for (int i = 0; i < layouts.length; i++) {
			Tab tab = new Tab(layouts[i].getName());
			tab.setContent(layouts[i].getLayout());
			tab.setClosable(false);
			tabPane.getTabs().add(tab);
		}
		tabPane.setSide(Side.BOTTOM);

		MenuItem distItem = new MenuItem("Districts screen");
		MenuItem locItem = new MenuItem("Locations screen");
		distItem.setOnAction(e -> {
			getManager().switchTo(SceneID.DISTRICT);
		});
		locItem.setOnAction(e -> {
			getManager().switchTo(SceneID.LOCATION);
		});
		Menu menu = new Menu("Go to");
		menu.getItems().addAll(distItem, locItem);
		MenuBar menuBar = new MenuBar(menu);
		
		BorderPane bp = new BorderPane();
		bp.setTop(menuBar);
		bp.setCenter(tabPane);
		
		return new Scene(bp, width, height);
	}


}

class LocationScene extends TabScene {

	public LocationScene(SceneManager manager, SceneID id, int width, int height, TabLayout... layouts) {
		super(manager, id, width, height, layouts);
	}

	@Override
	protected Scene createScene(int width, int height) {
		TabPane tabPane = new TabPane();
		TabLayout[] layouts = getLayouts();
		
		for (int i = 0; i < layouts.length; i++) {
			Tab tab = new Tab(layouts[i].getName());
			tab.setContent(layouts[i].getLayout());
			tab.setClosable(false);
			tabPane.getTabs().add(tab);
		}
		tabPane.setSide(Side.BOTTOM);

		MenuItem distItem = new MenuItem("Districts screen");
		MenuItem martyrItem = new MenuItem("Martyrs screen");
		distItem.setOnAction(e -> {
			getManager().switchTo(SceneID.DISTRICT);
		});
		martyrItem.setOnAction(e -> {
			getManager().switchTo(SceneID.MARTYR);
		});
		Menu menu = new Menu("Go to");
		menu.getItems().addAll(distItem, martyrItem);
		MenuBar menuBar = new MenuBar(menu);
		
		BorderPane bp = new BorderPane();
		bp.setTop(menuBar);
		bp.setCenter(tabPane);
		
		return new Scene(bp, width, height);
	}


}

class DistrictScene extends TabScene {

	public DistrictScene(SceneManager manager, SceneID id, int width, int height, TabLayout... layouts) {
		super(manager, id, width, height, layouts);
	}

	@Override
	protected Scene createScene(int width, int height) {
		TabPane tabPane = new TabPane();
		TabLayout[] layouts = getLayouts();
		
		for (int i = 0; i < layouts.length; i++) {
			Tab tab = new Tab(layouts[i].getName());
			tab.setContent(layouts[i].getLayout());
			tab.setClosable(false);
			tabPane.getTabs().add(tab);
		}
		tabPane.setSide(Side.BOTTOM);

		MenuItem locItem = new MenuItem("Locations screen");
		locItem.setOnAction(e -> {
			getManager().switchTo(SceneID.LOCATION);
		});
		Menu menu = new Menu("Go to");
		menu.getItems().add(locItem);
		MenuBar menuBar = new MenuBar(menu);
		
		BorderPane bp = new BorderPane();
		bp.setTop(menuBar);
		bp.setCenter(tabPane);
		
		return new Scene(bp, width, height);
	}


}