module DS_PROJECT_1 {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
