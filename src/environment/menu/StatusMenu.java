package environment.menu;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class StatusMenu {
	private static final Font MENU_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 40);
	private Canvas background;
	private Canvas title;
	private Map<String, Canvas> statusText = new HashMap<String, Canvas>();
	private Map<String, Canvas> statusIcon = new HashMap<String, Canvas>();
	private Map<String, Boolean> canSelect = new HashMap<String, Boolean>();
	private Canvas back;
	protected static int pointer = 0;
	private static int gap = Main.SCREEN_SIZE * 3 / 4 / 10;

	public StatusMenu() {
		this.background = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.title = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.back = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.statusText.put("VIT", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusText.put("ATK", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusText.put("DEF", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusText.put("ACC", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusText.put("EVA", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusText.put("DEX", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));

		this.statusIcon.put("VIT", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("ATK", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("DEF", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("ACC", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("EVA", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("DEX", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));

		this.canSelect.put("VIT", false);
		this.canSelect.put("ATK", false);
		this.canSelect.put("DEF", false);
		this.canSelect.put("ACC", false);
		this.canSelect.put("EVA", false);
		this.canSelect.put("DEX", false);

		GraphicsContext gc = background.getGraphicsContext2D();
		gc.setFill(Color.WHEAT);
		gc.fillRect(Main.SCREEN_SIZE / 8, Main.SCREEN_SIZE / 8, Main.SCREEN_SIZE * 3 / 4, Main.SCREEN_SIZE * 3 / 4);

		gc = title.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(), 50));
		gc.fillText("PAUSED", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 4 + gap);

	}
}
