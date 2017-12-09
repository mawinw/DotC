package environment.menu;

import java.util.HashSet;

import environment.window.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import main.Main;

public class StatusHandler {
	private static HashSet<KeyCode> activeKey = new HashSet<KeyCode>();
	private static Timeline statusTimer;

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
		if (activeKey.contains(KeyCode.Z) || activeKey.contains(KeyCode.ENTER)) {
			StatusMenu.action();
		}
		else if(activeKey.contains(KeyCode.X)) {
			SceneManager.closeStatusMenu();
			
		}

	}

	private static void moveSelected() {
		// TODO Auto-generated method stub
		
		if (activeKey.contains(KeyCode.UP)) {
			StatusMenu.moveSelected(true);
		} else if (activeKey.contains(KeyCode.DOWN)) {
			StatusMenu.moveSelected(false);
		}
	}

	public static void start() {
		// TODO Auto-generated method stub
		activeKey.clear();
		statusTimer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			StatusHandler.update();
		}));
		statusTimer.setCycleCount(Animation.INDEFINITE);
		statusTimer.play();
	}

	public static void stopTimer() {
		// TODO Auto-generated method stub
		statusTimer.stop();
	}

	public static void playTimer() {
		// TODO Auto-generated method stub
		statusTimer.play();
	}

}
