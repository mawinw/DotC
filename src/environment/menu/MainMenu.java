package environment.menu;

import java.awt.Event;
import java.util.Random;

import javax.sound.sampled.FloatControl;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Main;
import environment.window.*;
import exception.DeleteNullException;
import exception.EmptyNameException;
import exception.LongNameException;
import exception.NonEnglishCharacterException;
import exception.UnsupportedCharacterException;

public class MainMenu extends Pane {

	private static final Font TITLE_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/future-timesplitters/Future TimeSplitters.otf"), 100);
	private static final Font MENU_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 40);

	private Canvas background;
	private static Canvas text;
	public static String name;
	private static final Image[] backgroundImages = new Image[5];
	static {
		backgroundImages[0] = new Image("background/main_0.jpg");
		backgroundImages[1] = new WritableImage(backgroundImages[0].getPixelReader(),0,0,541,407);
		backgroundImages[2] = new WritableImage(backgroundImages[0].getPixelReader(),0,407+5,541,407);
		backgroundImages[3] = new WritableImage(backgroundImages[0].getPixelReader(),0,814+10,541,407);
	}
	private static Media mainMenuMusicFile = new Media(
			ClassLoader.getSystemResource("sound/bgm01_intro.mp3").toString());
	static MediaPlayer mainMenuMusic = new MediaPlayer(mainMenuMusicFile);

	public MainMenu() {
		name="";
		background = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		text = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.getChildren().addAll(background, text);
		GraphicsContext gc = background.getGraphicsContext2D();
		Random randomBg = new Random();
		int rn = randomBg.nextInt(3);
		gc.drawImage(backgroundImages[rn+1],0, 0, Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFill(Color.WHITE);
		gc.setFont(TITLE_FONT);
		gc.fillText("Defends of the Crystal", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE * 2 / 10);
		drawName();
		this.addKeyEventHandler();
	}

	private void addKeyEventHandler() {
		// TODO Fill Cod
		this.setOnKeyPressed(e -> {
			try {
				if(e.getCode() == KeyCode.ENTER) {
					if(!name.isEmpty()) {
						moveToGameScene();
					}
					else {
						throw new EmptyNameException();
					}
				} else if (e.getCode() == KeyCode.ESCAPE) {
					Platform.exit();
				} else {
					addCharacter(e);
				}
				clearErrorMessage();
			} catch(Exception f) {
				setErrorMessage(f.getMessage());
				//f.printStackTrace();
			}
			
		});
	}

	public void setErrorMessage(String error) {
		clearErrorMessage();

		GraphicsContext gc = text.getGraphicsContext2D();
		gc.setFont(Font.font("Monospace", 20));
		gc.setFill(Color.FIREBRICK);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText(error, Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE * 7 / 10);
	}

	public void clearErrorMessage() {
		GraphicsContext gc = text.getGraphicsContext2D();
		gc.clearRect(0, Main.SCREEN_SIZE * 7 / 10, Main.SCREEN_SIZE, Main.SCREEN_SIZE * 3 / 10);
	}

	public static void resetName() {
		name = "";
		drawName();
	}

	public static void drawName() {
		GraphicsContext gc = text.getGraphicsContext2D();

		gc.clearRect(Main.SCREEN_SIZE / 5, Main.SCREEN_SIZE / 7.5 * 4, 450, 100);

		gc.setFont(Font.font(MENU_FONT.getName(), 30));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.BASELINE);
		gc.setFill(Color.WHITE);
		gc.fillText("Please Enter Your Name", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 2);

		gc.setFill(Color.gray(0.925));
		gc.fillRoundRect((Main.SCREEN_SIZE - 450) / 2, Main.SCREEN_SIZE / 7.5 * 4, 450, Main.SCREEN_SIZE / 7.5, 20, 20);

		gc.setFont(Font.font(MENU_FONT.getName(), 40));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFill(Color.BLACK);
		gc.fillText(name, Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE * 3 / 5);
	}

	public void addCharacter(KeyEvent input) throws Exception {
		
		if(input.getCode() == KeyCode.BACK_SPACE) {
			if(name.length() == 0) {
				throw new DeleteNullException();
			} else {
				name = name.substring(0, name.length() - 1);
			}
		} else if(input.getText().length() == 1 && !input.getCode().isWhitespaceKey()) {
			if(name.length() >= 10) {
				throw new LongNameException();
			} else {
				if (input.getText().codePointAt(0) <= 255) {
					name += input.getText();
				}
				else {
					throw new NonEnglishCharacterException(input.getText());
				}
			}
		} else {
			if(input.getText().length() >= 1) {
				throw new UnsupportedCharacterException(input.getText());
			} else {
				throw new UnsupportedCharacterException(input.getCode().getName());
			}
		}
		
		drawName();
	}
	
	public static void moveToGameScene() {
		SceneManager.gotoGameScene();
		resetName();
	}
	public static void playMusic() {
		mainMenuMusic.play();		
		Timeline fadeIn = new Timeline(
				new KeyFrame(Duration.millis(5000), new KeyValue(mainMenuMusic.volumeProperty(), 1)));
    fadeIn.play();
	}
	public static void stopMusic() {
		Timeline fadeOut = new Timeline(
				new KeyFrame(Duration.millis(3000), new KeyValue(mainMenuMusic.volumeProperty(), 0)));
    fadeOut.play();
    fadeOut.setOnFinished(finish -> {
    	mainMenuMusic.stop();
    });
	}
}