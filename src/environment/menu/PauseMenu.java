package environment.menu;

import java.util.ArrayList;

import environment.Map;
import environment.window.SceneManager;
import exception.EmptyNameException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.GameHandler;
import main.Main;

public class PauseMenu extends Pane {
	private static final Font MENU_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 40);
	private Canvas background;
	private Canvas title;
	private Canvas status;
	private Canvas resume;
	private Canvas mainMenu;
	private static Canvas selectedFrame;
	private ArrayList<Canvas> menu = new ArrayList<>();

	protected static int pointer = 0;
	private static int gap = Main.SCREEN_SIZE / 2 / 5;
	private static boolean isCompleted=true;

	public PauseMenu() {
		this.background = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.title = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.status = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.resume = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.mainMenu = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.selectedFrame = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);

		GraphicsContext gc = background.getGraphicsContext2D();
		gc.setFill(Color.WHEAT);
		gc.fillRect(Main.SCREEN_SIZE / 4, Main.SCREEN_SIZE / 4, Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 2);

		gc = title.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(), 50));
		gc.fillText("PAUSED", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 4 + gap);

		gc = resume.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(MENU_FONT);
		gc.fillText("Resume", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 4 + gap * 2);

		gc = status.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(MENU_FONT);
		gc.fillText("Status", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 4 + gap * 3);

		gc = mainMenu.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(MENU_FONT);
		gc.fillText("Main Menu", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 4 + gap * 4);

		menu.add(resume);
		menu.add(status);
		menu.add(mainMenu);
		drawSelectedFrame();
		this.getChildren().addAll(background, title, resume, status, mainMenu,selectedFrame);
		this.requestFocus();
	}

	

	private static void drawSelectedFrame() {
		isCompleted=false;
		Timeline timer = new Timeline(new KeyFrame(new Duration(4000 / Main.FPS), e -> {
			GraphicsContext gc = selectedFrame.getGraphicsContext2D();
			gc.clearRect(0, 0, Main.SCREEN_SIZE, Main.SCREEN_SIZE);
			gc.setStroke(Color.RED);
			gc.setLineWidth(5);
			gc.strokeRect(Main.SCREEN_SIZE * 5 /14, Main.SCREEN_SIZE / 4 + gap * (pointer+2-0.5), Main.SCREEN_SIZE * 2 /7, gap);
		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e->{
			isCompleted=true;
		});
		}

	public static void action() {
		// TODO Auto-generated method stub
		if (pointer == 0) {
			
			SceneManager.closePausedMenu();
		} else if (pointer == 1) {
			SceneManager.openStatusMenu();
		} else if (pointer == 2) {
			SceneManager.gotoMainMenu();
			GameHandler.stopTimer();
			SceneManager.pauseTimer.stop();
			Map.getInstance().reset();
			pointer=0;
			drawSelectedFrame();
		}
	}

	public static void moveSelected(boolean b) {
		// TODO Auto-generated method stub
		if(!isCompleted) return;
		if (b) {
			pointer = (pointer + 2) % 3;
			drawSelectedFrame();
		} else{
			pointer = (pointer + 1) % 3;
			drawSelectedFrame();
		}
	}
}
