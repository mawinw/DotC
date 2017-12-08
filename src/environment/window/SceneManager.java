package environment.window;

import environment.Map;
import environment.menu.MainMenu;
import environment.menu.PauseMenu;
import environment.menu.PausedHandler;
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
	public static Timeline gameTimer;
	public static Timeline pauseTimer;

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
		gameTimer.stop();
		pauseTimer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			PausedHandler.update();
		}));
		pauseTimer.setCycleCount(Animation.INDEFINITE);
		pauseTimer.play();

		primaryStage.getScene().setOnKeyPressed(event -> PausedHandler.keyPressed(event));
		primaryStage.getScene().setOnKeyReleased(event -> PausedHandler.keyReleased(event));
	}
	public static void closePausedMenu() {
		// TODO Fill Code
		Map.getInstance().getChildren().remove(pausedMenu);
		gameTimer.play();
		primaryStage.getScene().setOnKeyPressed(event -> GameHandler.keyPressed(event));
		primaryStage.getScene().setOnKeyReleased(event -> GameHandler.keyReleased(event));
		pauseTimer.stop();
	
	}

	
	public static void gotoGameScene() {

		// put all pane

		Scene scene = new Scene(Map.getInstance());
		GameHandler.setPaused(false);
		gameTimer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			GameHandler.update();
		}));
		gameTimer.setCycleCount(Animation.INDEFINITE);
		gameTimer.play();
		scene.setOnKeyPressed(event -> GameHandler.keyPressed(event));
		scene.setOnKeyReleased(event -> GameHandler.keyReleased(event));
		// set handler

		primaryStage.setScene(scene);
	}

	public static void setStage(Stage primaryStage) {
		SceneManager.primaryStage = primaryStage;
		
	}
}
