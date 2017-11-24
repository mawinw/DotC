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
import utility.Direction;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class Monster extends Entity {
	public static final int VISIBLE_RANGE = 3;
	
	private static final int DEFAULT_MAX_HP = 200;
	private static final int DEFAULT_ATK = 30;
	private static final int DEFAULT_DEF = 10;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	public static final int EXP_GAIN =40;
	
	protected Pair areaPosition; //ref from top left 

	public Monster(Pair pos) {
		super("Slime",DEFAULT_MAX_HP,DEFAULT_ATK,DEFAULT_DEF,DEFAULT_ACC,DEFAULT_EVA,DEFAULT_CRI_RATE,pos);
		this.side=Side.MONSTER;
		//this.areaPosition=pos;
		this.areaPosition = new Pair(pos.first, pos.second);
	//	System.out.println(this.position.first+" "+this.position.second);
		picHeight=50;
		picWidth=50;

		faceDirection=Direction.LEFT;
		draw();
		// don't forget to initial picture size and first time position
	}
	
	public void draw() {
		GraphicsContext 	gc=this.canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);
		gc.setFill(Color.YELLOW);
		gc.fillOval(position.first*Map.TILE_SIZE, position.second*Map.TILE_SIZE, picWidth, picHeight);
		drawDirection();
//		gc.setFill(Color.CORAL);
//		gc.setTextAlign(TextAlignment.CENTER);
//		gc.setTextBaseline(VPos.CENTER);
//		gc.fillText("" + this.Hp, position.first*Map.TILE_SIZE + picWidth/2, position.second*Map.TILE_SIZE + picHeight/2);
		if(isDead) return;
		Map.statusBarGroup.getChildren().remove(hpBar.getCanvas());
		
		hpBar= new HpBar(this);
		Map.statusBarGroup.getChildren().add(hpBar.getCanvas());
//		System.out.println(Map.statusBarGroup.getChildren().contains(hpBar.getCanvas()));

	}
	
	private void drawDirection() {
		GraphicsContext gc =canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(4);
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
	
	public void move(double moveX,double moveY) {
		if(Map.getBoard(position.add(new Pair(moveX,moveY))).entity != null) {
			if (moveX == 0 || moveY == 0) {
				changeDirection(moveX, moveY);
				return;
			} else if (Map.getBoard(position.add(new Pair(moveX,0))).entity == null) {
				moveY = 0;
			} else if (Map.getBoard(position.add(new Pair(0,moveY))).entity == null) {
				moveX = 0;
			} else {
				return;
			}
		}
		double x = moveX;
		double y = moveY;
		Map.setBoard(position,TileType.NONE, null);
		Map.setBoard(position.add(new Pair(x,y)),TileType.MONSTER, this);
		changeDirection(x, y);
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			position.first += x / Main.FPS * 10;
			position.second += y / Main.FPS * 10;
			draw();
		}));
		timer.setCycleCount(Main.FPS / 10);
		timer.play();
		timer.setOnFinished(e -> {
		//	isActionFinished=true;
			
		});
	}
	
	private void changeDirection(double x, double y) {
		if(x>0) faceDirection=Direction.RIGHT;
		else if(x<0) faceDirection=Direction.LEFT;
		else if(y>0) faceDirection=Direction.DOWN;
		else if(y<0) faceDirection=Direction.UP;
		draw();
	}
	
	public void randomMove() {
		double moveX = new Random().nextInt(3) - 1;
		double moveY = new Random().nextInt(3) - 1;
	//	System.out.println(areaPosition.first+" "+areaWidth+" "+areaHeight+" "+areaPosition.second);
		if(0<=position.first+moveX&& position.first+moveX<Map.WIDTH &&
		   0<=position.second+moveY&& position.second+moveY<Map.HEIGHT 
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
		
		
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000), e -> {
			double atkDmg=calculateDamage(entity);
			entity.takeDamage(atkDmg);
			entity.draw();
			
		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e ->{
			Timeline wait = new Timeline(new KeyFrame(Duration.millis(100), f -> {}));
			wait.setCycleCount(1);
			wait.play();
		});
		System.out.println(entity.getHp());
	}
	
	public void takeDamage(double dmg) {
	//	System.out.println(Hp+" "+dmg);
		if(Hp<=dmg) {die();}
		else {
			Hp-=dmg;
		}
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
	
}
