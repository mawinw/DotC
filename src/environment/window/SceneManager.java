package environment.window;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class SceneManager {

	private static Stage primaryStage;
	private static Canvas mainMenuCanvas = new environment.menu.MainMenu();
	private static Scene mainMenuScene = new Scene(new Pane(mainMenuCanvas));
	public static final int SCENE_WIDTH = 800;
	public static final int SCENE_HEIGHT = 600;

	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.show();
	}

	public static void gotoMainMenu() {
		// TODO Fill Code

		primaryStage.setScene(mainMenuScene);
		mainMenuCanvas.requestFocus();
	}

	public static void gotoSceneOf(Canvas canvas) {
		// TODO Fill Code
		primaryStage.setScene(new Scene(new Pane(canvas)));
		canvas.requestFocus();
	}
}
