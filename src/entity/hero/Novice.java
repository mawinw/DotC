package entity.hero;

import entity.Entity;
import environment.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Handler;
import main.Main;
import utility.Direction;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class Novice extends Entity {

	private static final int DEFAULT_MAX_HP = 200;
	private static final int DEFAULT_ATK = 10;
	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int[] EXP_RATE = { 0, 100, 200, 350, 550, 750, 1000, 1300, 1650, 2100, 2500 };

	protected int lv;
	protected int exp;
	protected boolean isMoveFinished;

	public Novice() {
		super("Novice", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE,
				new Pair(1, 1));
		this.lv = 1;
		this.exp = 0;
		this.side = Side.HERO;
		picHeight = 50;
		picWidth = 50;
		this.faceDirection = Direction.RIGHT;
		this.isMoveFinished=true;
		// don't forget to initial picture size and first time position
	}

	public Novice(String name) {
		super(name, DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE,
				new Pair(1, 1));
		this.lv = 1;
		this.exp = 0;
		this.side = Side.HERO;
		this.faceDirection = Direction.RIGHT;
		// don't forget to initial picture size and first time position
	}

	public void draw() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);
		gc.setFill(Color.AQUA);
		gc.fillOval(position.first * Map.TILE_SIZE, position.second * Map.TILE_SIZE, picWidth, picHeight);
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);
		if(faceDirection==Direction.RIGHT) {	
			gc.strokeRect((position.first + 1) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE, picWidth, picHeight);
		}
		else if(faceDirection==Direction.LEFT) {	
			gc.strokeRect((position.first - 1) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE, picWidth, picHeight);
		}
		else if(faceDirection==Direction.DOWN) {		
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second + 1) * Map.TILE_SIZE, picWidth, picHeight);
		}
		else if(faceDirection==Direction.UP) {	
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second - 1) * Map.TILE_SIZE, picWidth, picHeight);
		}

	}

	public void move(double moveX, double moveY) {
		isMoveFinished=false;
		Map.setBoard((int) position.first, (int) position.second,
				TileType.NONE, null);
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			position.first += moveX / Main.FPS * 10;
			position.second += moveY / Main.FPS * 10;
			draw();
		}));
		timer.setCycleCount(Main.FPS / 10);
		timer.play();
		timer.setOnFinished(e -> {
			isMoveFinished=true;
		});
		Map.setBoard((int) Map.getNovice().getPosition().first, (int) Map.getNovice().getPosition().second,
				TileType.HERO, this);
	}

	public void attack(Entity entity) {
		double atkDmg = this.atk * (1 - entity.defRate());
		// System.out.println(atkDmg);
		entity.takeDamage(atkDmg);
		entity.draw();
	}

	public void takeDamage(double dmg) {
		if (Hp <= dmg) {
			die();
		} else {
			Hp -= dmg;
		}
	}

	public boolean isMoveFinished() {
		return isMoveFinished;
	}

	public void setMoveFinished(boolean isMoveFinished) {
		this.isMoveFinished = isMoveFinished;
	}
	

}
