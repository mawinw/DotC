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
	public static Timeline pauseTimer;
	public static Pane allPane;

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
		allPane.getChildren().add(pausedMenu);
		GameHandler.stopTimer();
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
		allPane.getChildren().remove(pausedMenu);
		GameHandler.playTimer();
		primaryStage.getScene().setOnKeyPressed(event -> GameHandler.keyPressed(event));
		primaryStage.getScene().setOnKeyReleased(event -> GameHandler.keyReleased(event));
		pauseTimer.stop();
	
	}

	
	public static void gotoGameScene() {

		// put all pane
		System.out.println("x");
		allPane = new Pane();
		allPane.getChildren().add(Map.getInstance().getTileGroup());
		allPane.getChildren().add(Map.getInstance().getStatusBarGroup());
		allPane.getChildren().add(Map.getInstance().getEntityGroup());
		allPane.getChildren().add(Map.getInstance().getNamePane());
		
		Scene scene = new Scene(allPane,Main.SCREEN_SIZE,Main.SCREEN_SIZE);
		System.out.println("y");
		
		scene.setOnKeyPressed(event -> GameHandler.keyPressed(event));
		scene.setOnKeyReleased(event -> GameHandler.keyReleased(event));
		// set handler
		GameHandler.startGame();
		
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> {
			GameHandler.stopTimer();
			
		});
	}

	public static void setStage(Stage primaryStage) {
		SceneManager.primaryStage = primaryStage;
		
	}
}
