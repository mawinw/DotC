package environment.window;

import environment.Map;
import environment.menu.MainMenu;
import environment.menu.PauseMenu;
import environment.menu.PausedHandler;
import environment.menu.StatusMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.GameHandler;
import main.Main;
import environment.window.*;

public final class SceneManager {

	private static Stage primaryStage;
	private static MainMenu mainMenuCanvas  = new environment.menu.MainMenu();
	private static PauseMenu pausedMenu = new PauseMenu();
	private static StatusMenu statusMenu = new StatusMenu();
	private static Scene mainMenuScene = new Scene(mainMenuCanvas);
	public static Timeline pauseTimer;
	public static Pane allPane;
	
	private static AudioClip stageMusic = new AudioClip("file:resources/sound/bgm03_stage.mp3");


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

	public static void openStatusMenu() {
		// TODO Fill Code
		allPane.getChildren().add(statusMenu);
//		GameHandler.stopTimer();
//		pauseTimer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
//			PausedHandler.update();
//		}));
//		pauseTimer.setCycleCount(Animation.INDEFINITE);
//		pauseTimer.play();
//
//		primaryStage.getScene().setOnKeyPressed(event -> PausedHandler.keyPressed(event));
//		primaryStage.getScene().setOnKeyReleased(event -> PausedHandler.keyReleased(event));
	}
	public static void closeStatusMenu() {
		// TODO Fill Code
		allPane.getChildren().remove(pausedMenu);
//		GameHandler.playTimer();
//		primaryStage.getScene().setOnKeyPressed(event -> GameHandler.keyPressed(event));
//		primaryStage.getScene().setOnKeyReleased(event -> GameHandler.keyReleased(event));
//		pauseTimer.stop();
	
	}

	
	public static void gotoGameScene() {

		// put all pane
		Map.getInstance().reset();
		allPane = new Pane();
		allPane.getChildren().add(Map.getInstance().getTileGroup());
		allPane.getChildren().add(Map.getInstance().getStatusBarGroup());
		allPane.getChildren().add(Map.getInstance().getEntityGroup());
		allPane.getChildren().add(Map.getInstance().getNamePane());
		allPane.getChildren().add(Map.getInstance().getEffectGroup());
		
		Scene scene = new Scene(allPane,Main.SCREEN_SIZE,Main.SCREEN_SIZE);
		
		scene.setOnKeyPressed(event -> GameHandler.keyPressed(event));
		scene.setOnKeyReleased(event -> GameHandler.keyReleased(event));
		// set handler
		GameHandler.startGame();
		stageMusic.play();
		
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> {
			GameHandler.stopTimer();

			stageMusic.stop();
		});
	}

	public static void setStage(Stage primaryStage) {
		SceneManager.primaryStage = primaryStage;
		
	}
}
