package environment.menu;

import java.awt.RenderingHints.Key;
import java.util.HashSet;

import environment.GameHandler;
import environment.window.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import main.Main;

public class PausedHandler {
	private static HashSet<KeyCode> activeKey = new HashSet<KeyCode>();
	private static Timeline pauseTimer;
	
	public static void keyPressed(KeyEvent event) {
		// if (activeKey.contains(event.getCode()))
		// return;
		if (event.getCode() == KeyCode.ENTER) {
			activeKey.add(KeyCode.ENTER);
		}

		if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
			activeKey.add(KeyCode.UP);
		}

		if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
			activeKey.add(KeyCode.DOWN);
		}

		if (event.getCode() == KeyCode.Z) {
			activeKey.add(KeyCode.Z);

		}
		if (event.getCode() == KeyCode.X) {
			activeKey.add(KeyCode.X);

		}

	}

	public static void keyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			activeKey.remove(KeyCode.ENTER);
		}

		if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
			activeKey.remove(KeyCode.UP);
		}

		if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
			activeKey.remove(KeyCode.DOWN);
		}

		if (event.getCode() == KeyCode.Z) {
			activeKey.remove(KeyCode.Z);

		}
		if (event.getCode() == KeyCode.X) {
			activeKey.remove(KeyCode.X);

		}

	}
	
	public static void update() {
		moveSelected();
		action();
	}

	private static void action() {
		// TODO Auto-generated method stub
		if (activeKey.contains(KeyCode.Z)||activeKey.contains(KeyCode.ENTER)) {
			PauseMenu.action();
		}
		
	}

	private static void moveSelected() {
		// TODO Auto-generated method stub
		if(activeKey.contains(KeyCode.UP)) {
			PauseMenu.moveSelected(true);
		}
		else if(activeKey.contains(KeyCode.DOWN)) {
			PauseMenu.moveSelected(false);
		}
	}
	
	public static void start() {
		// TODO Auto-generated method stub
		activeKey.clear();
		pauseTimer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			PausedHandler.update();
		}));
		pauseTimer.setCycleCount(Animation.INDEFINITE);
		pauseTimer.play();
	}
	
	public static void stopTimer() {
		// TODO Auto-generated method stub
		pauseTimer.stop();
	}

	public static void playTimer() {
		// TODO Auto-generated method stub
		pauseTimer.play();
	}
	
	
	
}
