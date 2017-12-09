package environment.menu;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.Main;

public class StatusMenu extends Pane {
	private static final Font MENU_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 40);
	private Canvas background;
	private Canvas title;
	private Map<String, Canvas> statusText = new HashMap<String, Canvas>();
	private Map<String, Canvas> statusIcon = new HashMap<String, Canvas>();
	private Map<String, Boolean> canSelect = new HashMap<String, Boolean>();
	private Map<String, Double> status = new HashMap<String, Double>();
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
		this.statusText.put("EVA", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusText.put("DEX", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));

		this.statusIcon.put("VIT", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("ATK", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("DEF", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("EVA", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));
		this.statusIcon.put("DEX", new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE));

		this.canSelect.put("VIT", false);
		this.canSelect.put("ATK", false);
		this.canSelect.put("DEF", false);
		this.canSelect.put("EVA", false);
		this.canSelect.put("DEX", false);

		this.status.put("VIT", environment.Map.getInstance().getHero().getMaxHp());
		this.status.put("ATK", (double) environment.Map.getInstance().getHero().getAtk());
		this.status.put("DEF", (double) environment.Map.getInstance().getHero().getDef());
		this.status.put("EVA", environment.Map.getInstance().getHero().getEva());
		this.status.put("DEX", environment.Map.getInstance().getHero().getDex());

		GraphicsContext gc = background.getGraphicsContext2D();
		gc.setFill(Color.WHEAT);
		gc.fillRect(Main.SCREEN_SIZE / 8, Main.SCREEN_SIZE / 8, Main.SCREEN_SIZE * 3 / 4, Main.SCREEN_SIZE * 3 / 4);

		gc = title.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(), 50));
		gc.fillText("STATUS", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 8 + gap);

		drawStatus("VIT", statusText.get("VIT"), 3);
		drawStatus("ATK", statusText.get("ATK"), 4);
		drawStatus("DEF", statusText.get("DEF"), 5);
		drawStatus("EVA", statusText.get("EVA"), 6);
		drawStatus("DEX", statusText.get("DEX"), 7);

		gc = back.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(MENU_FONT);
		gc.fillText("BACK", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 8 + gap * 9);

		this.getChildren().addAll(background, title);
		for (Map.Entry m : statusText.entrySet()) {
			this.getChildren().add((Node) m.getValue());
		}
		this.getChildren().add(back);

	}

	private void drawStatus(String s, Canvas statusCanvas, int i) {
		GraphicsContext gc = statusCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(), 30));

		gc.fillText(s, Main.SCREEN_SIZE / 3, Main.SCREEN_SIZE / 8 + gap * i);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText(":", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 8 + gap * i);
		gc.setTextAlign(TextAlignment.RIGHT);
		String value = "" + (status.get(s).intValue());
		gc.fillText(value, Main.SCREEN_SIZE * 6 / 10, Main.SCREEN_SIZE / 8 + gap * i);

	}
}
