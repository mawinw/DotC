package environment;

import environment.window.SceneManager;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class DeadScene extends Pane {
	private static final Font MENU_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 40);
	private Canvas background;
	private Canvas title;
	private Canvas description;
	private static final Image BG = new Image("background/pauseBG.png");
	private static int gap = Main.SCREEN_SIZE / 5 ;

	public DeadScene() {
		this.background = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.title = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.description = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);

		GraphicsContext gc = background.getGraphicsContext2D();
		gc.drawImage(BG, Main.SCREEN_SIZE / 5, Main.SCREEN_SIZE / 5 - 20, Main.SCREEN_SIZE * 3 / 5,
				Main.SCREEN_SIZE * 3 / 5 + 40);

		gc = title.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(), 50));
		gc.fillText("YOU ARE DEAD.", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 5 + gap);

		gc = description.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(), 30));
		gc.fillText("Please Enter to New Game", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 5 + gap * 2);
	
		this.getChildren().addAll(background,title,description);
		this.addKeyEventHandler();
	}
	
	private void addKeyEventHandler() {
		//TODO Fill Cod
		setOnKeyPressed(e ->{
			if(e.getCode()==KeyCode.ENTER) {
				SceneManager.gotoMainMenu();
			}
			
		});
	}
}
