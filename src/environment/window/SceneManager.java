package environment.window;

import environment.Map;
import environment.menu.MainMenu;
import environment.menu.PauseMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.GameHandler;
import main.Main;
import environment.window.*;

public final class SceneManager {

	private static Stage primaryStage;
	private static MainMenu mainMenuCanvas  = new environment.menu.MainMenu();
	private static PauseMenu pausedMenu = new PauseMenu();
	private static Scene mainMenuScene = new Scene(mainMenuCanvas);

	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.show();
	}

	public static void gotoMainMenu() {
		// TODO Fill Code
		
		primaryStage.setScene(mainMenuScene);
		mainMenuCanvas .requestFocus();
	}
	public static void openPausedMenu() {
		// TODO Fill Code
		Map.getInstance().getChildren().add(pausedMenu);
		
	}
	public static void closePausedMenu() {
		// TODO Fill Code
		Map.getInstance().getChildren().remove(pausedMenu);
		
	}

	
	public static void gotoGameScene() {
		Pane allPane = new Pane();

		// put all pane

		Scene scene = new Scene(Map.getInstance());
		GameHandler.setPaused(false);
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			GameHandler.update();
		}));
		timer.setCycleCount(Animation.INDEFINITE);
		timer.play();
		scene.setOnKeyPressed(event -> GameHandler.keyPressed(event));
		scene.setOnKeyReleased(event -> GameHandler.keyReleased(event));
		// set handler

		primaryStage.setScene(scene);
	}

	public static void setStage(Stage primaryStage) {
		SceneManager.primaryStage = primaryStage;
		
	}
}
