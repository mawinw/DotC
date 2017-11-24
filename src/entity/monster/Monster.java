package entity.monster;

import java.util.Random;

import entity.Entity;
import entity.property.HpBar;
import environment.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Main;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class Monster extends Entity {
	public static final int VISIBLE_RANGE = 3;
	
	private static final int DEFAULT_MAX_HP = 500;
	private static final int DEFAULT_ATK = 10;
	private static final int DEFAULT_DEF = 10;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int EXP_GAIN =40;
	
	protected Pair areaPosition; //ref from top left 
	protected int areaWidth;
	protected int areaHeight;
	public Monster(Pair pos) {
		super("Slime",DEFAULT_MAX_HP,DEFAULT_ATK,DEFAULT_DEF,DEFAULT_ACC,DEFAULT_EVA,DEFAULT_CRI_RATE,pos);
		this.side=Side.MONSTER;
		//this.areaPosition=pos;
		this.areaPosition = new Pair(pos.first, pos.second);
	//	System.out.println(this.position.first+" "+this.position.second);
		picHeight=50;
		picWidth=50;
		areaHeight=3;
		areaWidth=3;
		draw();
		// don't forget to initial picture size and first time position
	}
	
	public void draw() {
		GraphicsContext 	gc=this.canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);
		gc.setFill(Color.YELLOW);
		gc.fillOval(position.first*Map.TILE_SIZE, position.second*Map.TILE_SIZE, picWidth, picHeight);
		gc.setFill(Color.CORAL);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText("" + this.Hp, position.first*Map.TILE_SIZE + picWidth/2, position.second*Map.TILE_SIZE + picHeight/2);
		Map.statusBarGroup.getChildren().remove(hpBar.getCanvas());
		
		hpBar= new HpBar(this);
		Map.statusBarGroup.getChildren().add(hpBar.getCanvas());
		System.out.println(Map.statusBarGroup.getChildren().contains(hpBar.getCanvas()));

	}
	
	public void move(double moveX,double moveY) {
		if(Map.getBoard(position.add(new Pair(moveX,moveY))).entity != null)
			return;
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
		//	isActionFinished=true;
			
		});
	}
	
	public void randomMove() {
		double moveX = new Random().nextInt(3) - 1;
		double moveY = new Random().nextInt(3) - 1;
	//	System.out.println(areaPosition.first+" "+areaWidth+" "+areaHeight+" "+areaPosition.second);
		if(areaPosition.first<=position.first+moveX&& position.first+moveX<areaPosition.first+areaWidth &&
		   areaPosition.second<=position.second+moveY&& position.second+moveY<areaPosition.second+areaHeight 
				) {
			if(Map.getBoard(position.add(new Pair(moveX,moveY))).entity == null)
				move(moveX, moveY);
		}
	}
	
	public void moveToPlayer() {
		double moveX = Map.getHeroPosition().first - position.first;
		if (moveX > 0)
			moveX = 1;
		else if (moveX < 0)
			moveX = -1;
		
		double moveY = Map.getHeroPosition().second - position.second;
		if (moveY > 0)
			moveY = 1;
		else if (moveY < 0)
			moveY = -1;
		
		move(moveX, moveY);
	}
	
	public void attack(Entity entity) {
		double atkDmg=this.atk*(1-entity.defRate());
		entity.takeDamage(atkDmg); //it is the opponent entity to take damage naja
		entity.draw();
	}
	
	public void takeDamage(double dmg) {
	//	System.out.println(Hp+" "+dmg);
		if(Hp<=dmg) {die();}
		else {
			Hp-=dmg;
		}
	}
	
	
	
}
