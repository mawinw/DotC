package environment.menu;

import java.util.HashMap;
import java.util.Map;

import entity.hero.Novice;
import environment.GameHandler;
import environment.GameScene;
import environment.window.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Main;

public class StatusMenu extends Pane {
	private static final Font MENU_FONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 40);
	private Canvas background;
	private Canvas title;
	private static Map<String, Canvas> statusText = new HashMap<String, Canvas>();
	private static Map<String, Canvas> statusIcon = new HashMap<String, Canvas>();
	private Map<String, Boolean> canSelect = new HashMap<String, Boolean>();
	private static Map<String, Double> status = new HashMap<String, Double>();
	private Canvas back;
	private static Canvas selectedFrame;
	private static Novice hero =GameScene.getInstance().getHero();
	
	protected static int pointer = 0;
	private static int gap = Main.SCREEN_SIZE * 3 / 4 / 10;
	private static boolean isCompleted=true;
	
	public StatusMenu() {
		this.background = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.title = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.back = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		this.selectedFrame = new Canvas(Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		
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

		updateStatus();

		GraphicsContext gc = background.getGraphicsContext2D();
		gc.setFill(Color.WHEAT);
		gc.fillRect(Main.SCREEN_SIZE / 8, Main.SCREEN_SIZE / 8, Main.SCREEN_SIZE * 3 / 4, Main.SCREEN_SIZE * 3 / 4);

		gc = title.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(new Font(MENU_FONT.getName(), 50));
		gc.fillText("STATUS", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 8 + gap);

		drawAllStatus();

		drawAllIcon();

		gc = back.getGraphicsContext2D();
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFont(MENU_FONT);
		gc.fillText("BACK", Main.SCREEN_SIZE / 2, Main.SCREEN_SIZE / 8 + gap * 9);
		if(hero.statusPoint==0) pointer =5;
		drawSelectedFrame();
		this.getChildren().addAll(background, title);
		for (Map.Entry m : statusText.entrySet()) {
			this.getChildren().add((Node) m.getValue());
			
		}
		for (Map.Entry m : statusIcon.entrySet()) {
			this.getChildren().add((Node) m.getValue());
			
		}
		this.getChildren().addAll(back,selectedFrame);

	}
	private static void updateStatus() {
		status.put("VIT", GameScene.getInstance().getHero().getMaxHp());
		status.put("ATK", (double) GameScene.getInstance().getHero().getAtk());
		status.put("DEF", (double) GameScene.getInstance().getHero().getDef());
		status.put("EVA", GameScene.getInstance().getHero().getEva());
		status.put("DEX", GameScene.getInstance().getHero().getDex());
	}
	private static void drawAllStatus() {
		drawStatus("VIT", statusText.get("VIT"), 3);
		drawStatus("ATK", statusText.get("ATK"), 4);
		drawStatus("DEF", statusText.get("DEF"), 5);
		drawStatus("EVA", statusText.get("EVA"), 6);
		drawStatus("DEX", statusText.get("DEX"), 7);
	}
	private static void drawStatus(String s, Canvas statusCanvas, int i) {
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
	
	private static void drawAllIcon() {
		drawIcon("VIT", statusIcon.get("VIT"), 3);
		drawIcon("ATK", statusIcon.get("ATK"), 4);
		drawIcon("DEF", statusIcon.get("DEF"), 5);
		drawIcon("EVA", statusIcon.get("EVA"), 6);
		drawIcon("DEX", statusIcon.get("DEX"), 7);
	}
	private static void drawIcon(String s, Canvas iconCanvas, int i) {
		GraphicsContext gc = iconCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Main.SCREEN_SIZE, Main.SCREEN_SIZE);
		if(hero.statusPoint==0) gc.setFill(Color.BLACK);
		else gc.setFill(Color.RED);
		gc.fillRect(Main.SCREEN_SIZE * 2 / 3 -15, Main.SCREEN_SIZE / 8 + gap * i -15, 30, 30);
	}
	
	private static void drawSelectedFrame() {
		isCompleted=false;
		Timeline timer = new Timeline(new KeyFrame(new Duration(4000 / Main.FPS), e -> {
			GraphicsContext gc = selectedFrame.getGraphicsContext2D();
			gc.clearRect(0, 0, Main.SCREEN_SIZE, Main.SCREEN_SIZE);
			if(pointer==5) {
				gc.setStroke(Color.RED);
				gc.setLineWidth(5);
				gc.strokeRect(Main.SCREEN_SIZE * 5 /14, Main.SCREEN_SIZE / 8 + gap * (9-0.5), Main.SCREEN_SIZE * 2 /7, gap);
			}
			else {
				gc.setStroke(Color.WHITE);
				gc.setLineWidth(5);
				gc.strokeRect(Main.SCREEN_SIZE * 2 / 3 -15, Main.SCREEN_SIZE / 8 + gap * (pointer+3) -15, 30, 30);
				
			}
		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e->{
			isCompleted=true;
		});
		}

	public static void action() {
		// TODO Auto-generated method stub
		if(!isCompleted) return;
		if(hero.statusPoint==0) {
			close();
			return;
		}
		isCompleted=false;
		Timeline timer = new Timeline(new KeyFrame(new Duration(100), e -> {
			if (pointer == 0) {
				hero.statusPoint--;
				hero.setMaxHp(hero.getMaxHp()+50);
			} else if (pointer == 1) {
				hero.statusPoint--;
				hero.setAtk(hero.getAtk()+10);
			} else if (pointer == 2) {
				hero.statusPoint--;
				hero.setDef(hero.getDef()+10);
			} else if (pointer == 3) {
				hero.statusPoint--;
				hero.setEva(hero.getEva()+5);
			} else if (pointer == 4) {
				hero.statusPoint--;
				hero.setDex(hero.getDex()+5);
			} else if (pointer == 5) {
				close();
			}
			if(hero.statusPoint==0) {
				pointer=5;
				drawSelectedFrame();
			}
			
			updateStatus();
			drawAllStatus();
			drawAllIcon();
		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e->{
			isCompleted=true;
		});
	}
	public static void close() {
		
		SceneManager.closeStatusMenu();
	}
	
	public static void open() {
		hero = GameScene.getInstance().getHero();
		if(hero.statusPoint==0) pointer=5;
		else		pointer=0;
		drawSelectedFrame();
		drawAllStatus();
		drawAllIcon();
	}

	public static void moveSelected(boolean b) {
		// TODO Auto-generated method stub
		if(!isCompleted||hero.statusPoint==0) return;
		System.out.println(hero.statusPoint);

		if (b) {
			pointer = (pointer + 5) % 6;
			drawSelectedFrame();
		} else{
			pointer = (pointer + 1) % 6;
			drawSelectedFrame();
		}
	}
	
}
