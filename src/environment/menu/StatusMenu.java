package environment.menu;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import main.Main;

public class StatusMenu {
	private Canvas background;
	private Canvas title;
	private Map<String, Canvas> statusText = new HashMap<String, Canvas>();
	private Map<String, Canvas> statusIcon = new HashMap<String, Canvas>();
	
	public StatusMenu() {
		this.background = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.title = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
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
		
	}
}
