package environment.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class PauseMenu extends Pane {
	private static final Font MENU_FONT = Font
			.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 40);
	private Canvas background;
	private Canvas title;
	private Canvas status;
	private Canvas resume;
	private Canvas mainMenu;
	private int gap = Main.SCREEN_SIZE / 2 / 5;

	public PauseMenu() {
		this.background = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.title = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.status = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.resume = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.mainMenu = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);

		GraphicsContext gc = background.getGraphicsContext2D();
		gc.setFill(Color.WHEAT);
		gc.fillRect(Main.SCREEN_SIZE / 4, Main.SCREEN_SIZE / 4, Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 2);

		gc = title.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(),50));
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

		this.getChildren().addAll(background,title,resume,status,mainMenu);
	}

}
