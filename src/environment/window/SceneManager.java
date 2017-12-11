package environment.window;

import environment.DeadScene;
import environment.GameHandler;
import environment.GameScene;
import environment.StatusBar;
import environment.menu.MainMenu;
import environment.menu.PauseMenu;
import environment.menu.PausedHandler;
import environment.menu.StatusHandler;
import environment.menu.StatusMenu;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import environment.window.*;

public final class SceneManager {

	private static Stage primaryStage;
	private static MainMenu mainMenuCanvas  = new environment.menu.MainMenu();
	private static PauseMenu pausedMenu = new PauseMenu();
	private static StatusMenu statusMenu = new StatusMenu();
	private static DeadScene deadScene = new DeadScene();
	private static Scene mainMenuScene = new Scene(mainMenuCanvas);
	public static Timeline pauseTimer;
	public static Pane allPane;


	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.show();
	}

	public static void gotoMainMenu() {
		// TODO Fill Code
		GameScene.stopMusic();
		primaryStage.setScene(mainMenuScene);
		MainMenu.playMusic();
		mainMenuCanvas .requestFocus();
		StatusBar.groundSmash.canUse=false;
		StatusBar.groundSmash.draw();
	}
	public static void openPausedMenu() {
		// TODO Fill Code
		allPane.getChildren().add(pausedMenu);
		GameHandler.stopTimer();
		
		PausedHandler.start();

		primaryStage.getScene().setOnKeyPressed(event -> PausedHandler.keyPressed(event));
		primaryStage.getScene().setOnKeyReleased(event -> PausedHandler.keyReleased(event));
	}
	public static void closePausedMenu() {
		// TODO Fill Code
		allPane.getChildren().remove(pausedMenu);
		GameHandler.playTimer();
		primaryStage.getScene().setOnKeyPressed(event -> GameHandler.keyPressed(event));
		primaryStage.getScene().setOnKeyReleased(event -> GameHandler.keyReleased(event));
		PausedHandler.stopTimer();
	
	}

	public static void openStatusMenu() {
		// TODO Fill Code
		StatusMenu.open();
		allPane.getChildren().add(statusMenu);
		PausedHandler.stopTimer();
		StatusHandler.start();
		primaryStage.getScene().setOnKeyPressed(event -> StatusHandler.keyPressed(event));
		primaryStage.getScene().setOnKeyReleased(event -> StatusHandler.keyReleased(event));
	}
	public static void closeStatusMenu() {
		// TODO Fill Code
		allPane.getChildren().remove(statusMenu);
		PausedHandler.playTimer();
		primaryStage.getScene().setOnKeyPressed(event -> PausedHandler.keyPressed(event));
		primaryStage.getScene().setOnKeyReleased(event -> PausedHandler.keyReleased(event));
		StatusHandler.stopTimer();
	
	}
	
	public static void gotoGameScene() {
		GameScene.getInstance().reset();
		gotoStage1();
	}
	
	public static void gotoStage1() {
		GameScene.getInstance().createStage1();
		putAllPane();
	}

	
	public static void gotoStage2() {
		GameScene.getInstance().createStage2();
		putAllPane();
	}
	

	public static void gotoStage3() {
		GameScene.getInstance().createStage3();
		putAllPane();
	}
	public static void putAllPane() {
		allPane = new Pane();
		VBox stage = new VBox();
		Pane gamePane = new Pane();
		gamePane.getChildren().add(GameScene.getInstance().getTileGroup());
		gamePane.getChildren().add(GameScene.getInstance().getStatusBarGroup());
		gamePane.getChildren().add(GameScene.getInstance().getEntityGroup());
		gamePane.getChildren().add(GameScene.getInstance().getNamePane());
		gamePane.getChildren().add(GameScene.getInstance().getEffectGroup());
		stage.getChildren().addAll(gamePane,StatusBar.getInstance());
		allPane.getChildren().add(stage);
		Scene scene = new Scene(allPane,Main.SCREEN_SIZE,Main.SCREEN_SIZE);
		
		scene.setOnKeyPressed(event -> GameHandler.keyPressed(event));
		scene.setOnKeyReleased(event -> GameHandler.keyReleased(event));
		// set handler
		GameHandler.startGame();
		MainMenu.stopMusic();
		GameScene.playMusic();
		
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> {
			GameHandler.stopTimer();
			GameScene.stopMusic();
		});
		
	}
	
	
	public static void setStage(Stage primaryStage) {
		SceneManager.primaryStage = primaryStage;
		
	}

	public static void playerDead() {
		// TODO Auto-generated method stub
		allPane.getChildren().add(deadScene);
		deadScene.requestFocus();
		GameHandler.stopTimer();
	}

}
