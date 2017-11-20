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
import environment.Map;

import java.util.LinkedList;

public class Main extends Application {
	
	public static final int SCREEN_SIZE = 750;
	private Pane allPane;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		allPane = new Pane();
		
		//put all pane
		
		Scene scene = new Scene(Map.createContent());
		
		//set handler
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("DotC: Defense of the Crystal");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
