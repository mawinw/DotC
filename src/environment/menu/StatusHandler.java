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
	private static boolean isActionFinished=true;

	public static void keyPressed(KeyEvent event) {
		// if (activeKey.contains(event.getCode()))
		// return;
		if ((event.getCode() == KeyCode.ENTER||event.getCode() == KeyCode.Z)) {
			activeKey.add(KeyCode.ENTER);
			
		}

		if (event.getCode() == KeyCode.UP ) {
			activeKey.add(KeyCode.UP);
		}

		if (event.getCode() == KeyCode.DOWN) {
			activeKey.add(KeyCode.DOWN);
		}
		
		if (event.getCode() == KeyCode.X) {
			activeKey.add(KeyCode.X);

		}

	}

	public static void keyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER||event.getCode() == KeyCode.Z) {
			activeKey.remove(KeyCode.ENTER);
			isActionFinished=true;
		}

		if (event.getCode() == KeyCode.UP) {
			activeKey.remove(KeyCode.UP);
		}

		if (event.getCode() == KeyCode.DOWN) {
			activeKey.remove(KeyCode.DOWN);
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
		if (activeKey.contains(KeyCode.ENTER)&&isActionFinished) {
			StatusMenu.action();
			isActionFinished=false;
			
		}
		else if(activeKey.contains(KeyCode.X)) {
			StatusMenu.close();
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
