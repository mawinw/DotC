package entity.hero;

import java.util.Random;

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
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 30;
	private static final int[] EXP_RATE = { 0, 100, 200, 350, 550, 750, 1000, 1300, 1650, 2100, 2500 };

	protected int lv;
	protected int exp;
	protected boolean isActionFinished;
	

	public Novice(Pair pos) {
		super("Novice", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE,
				pos);
	//	System.out.println(this.position.first+" "+this.position.second);
		this.lv = 1;
		this.exp = 0;
		this.side = Side.HERO;
		picHeight = 50;
		picWidth = 50;
		this.faceDirection = Direction.RIGHT;
		this.isActionFinished=true;
		// don't forget to initial picture size and first time position
	}

	public Novice(String name,Pair pos) {
		super(name, DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE,
				pos);
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
		isActionFinished=false;
		Map.setBoard(position,TileType.NONE, null);
		Map.setBoard(position.add(new Pair(moveX,moveY)),TileType.MONSTER, this);
		
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			position.first += moveX / Main.FPS * 10;
			position.second += moveY / Main.FPS * 10;
			draw();
		}));
		timer.setCycleCount(Main.FPS / 10);
		timer.play();
		timer.setOnFinished(e -> {
			isActionFinished=true;
		});
	}

	public void attack(Entity entity) {
		isActionFinished=false;
		
		
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000), e -> {
			double atkDmg=calculateDamage(entity);
			entity.takeDamage(atkDmg);
			entity.draw();
		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e ->{
			isActionFinished=true;
		});
		
		
	}

	private double calculateDamage(Entity entity) {
		Random rn = new Random();
		int atkSuccess = rn.nextInt(100);
		int criSuccess = rn.nextInt(100);
		//System.out.println(atkSuccess+" "+criSuccess+" "+(this.atk - entity.getDef()));
		if(this.acc-entity.getEva()>atkSuccess) {
			if(this.atk>entity.getDef()) {
				if(this.criRate>criSuccess)
					return 2*(this.atk - entity.getDef());		
				else
					return this.atk - entity.getDef();
			}
			else
				return 1;
			}
		else return 0;
	}
	
	public void takeDamage(double dmg) {
		if (Hp <= dmg) {
			die();
		} else {
			Hp -= dmg;
		}
	}

	public boolean isActionFinished() {
		return isActionFinished;
	}

	public void setActionFinished(boolean isActionFinished) {
		this.isActionFinished = isActionFinished;
	}

	

}
