package main;

import javafx.application.Application;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import environment.GameScene;
import environment.window.SceneManager;

import java.util.LinkedList;

public class Main extends Application {

	public static final int SCREEN_SIZE = 700;
	public static final int FPS = 40;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		Scene scene = SceneManager.gotoMainMenu();
//		primaryStage.setScene(scene);
		SceneManager.setStage(primaryStage);
		SceneManager.gotoMainMenu();
		primaryStage.setTitle("DotC: Defense of the Crystal");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

}
