package environment;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class StatusBar extends Pane {
	private static StatusBar instance;

	public static StatusBar getInstance() {
		if (instance == null) {
			instance = new StatusBar();
		}

		return instance;
	}

	private static final Font BAR_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 20);
	private Canvas background;
	private static Canvas expBar;
	private static Canvas expBarText;
	private static double expMaxWidth;
	private static double expWidth;

	public StatusBar() {
		this.setPrefSize(Main.SCREEN_SIZE, GameScene.TILE_SIZE * 2);
		this.background = new Canvas(Main.SCREEN_SIZE, GameScene.TILE_SIZE * 2);
		this.expBar = new Canvas(Main.SCREEN_SIZE, GameScene.TILE_SIZE * 2);
		this.expBarText = new Canvas(Main.SCREEN_SIZE, GameScene.TILE_SIZE * 2);
		expMaxWidth = 50 * 5;

		GraphicsContext gc = background.getGraphicsContext2D();
		gc.setFill(Color.BEIGE);
		gc.fillRect(0, 0, Main.SCREEN_SIZE, GameScene.TILE_SIZE * 2);

		drawExpBar();

		this.getChildren().addAll(background, expBar, expBarText);
	}

	public static void drawExpBar() {
		GraphicsContext gc = expBar.getGraphicsContext2D();
		gc.clearRect(0, 0, Main.SCREEN_SIZE, GameScene.TILE_SIZE * 2);
		gc.setFill(Color.GREENYELLOW);
		gc.setStroke(Color.GREEN);
		gc.setLineWidth(5);
		gc.strokeRect(GameScene.TILE_SIZE * 2 - 2.5, GameScene.TILE_SIZE - 15 - 2.5, expMaxWidth + 5, 30 + 5);
		double exp = GameScene.getInstance().getHero().getExp();
		double expRate = GameScene.getInstance().getHero().EXP_RATE[GameScene.getInstance().getHero().getLv()];
		System.out.println(exp + " " + expRate);
		expWidth = (exp / expRate) * expMaxWidth;
		gc.fillRect(GameScene.TILE_SIZE * 2, GameScene.TILE_SIZE - 15, expWidth, 30);

		gc = expBarText.getGraphicsContext2D();
		gc.clearRect(0, 0, Main.SCREEN_SIZE, GameScene.TILE_SIZE * 2);
		gc.setFill(Color.BLACK);
		gc.setFont(BAR_FONT);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText((int) exp + " / " + (int) expRate, GameScene.TILE_SIZE * 2 + expMaxWidth + 10, GameScene.TILE_SIZE);
		gc.setTextAlign(TextAlignment.RIGHT);
		gc.fillText("EXP ", GameScene.TILE_SIZE * 2 - 15, GameScene.TILE_SIZE);
	
	}

}
