package entity.monster;

import entity.Entity;
import environment.Map;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import utility.Pair;
import utility.Side;

public class Slime extends Entity {
	private static final int DEFAULT_MAX_HP = 100;
	private static final int DEFAULT_ATK = 10;
	private static final int DEFAULT_DEF = 10;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int EXP_GAIN =40;
	
	public Slime() {
		super("Slime",DEFAULT_MAX_HP,DEFAULT_ATK,DEFAULT_DEF,DEFAULT_ACC,DEFAULT_EVA,DEFAULT_CRI_RATE,new Pair(1,1));
		this.side=Side.MONSTER;
		picHeight=50;
		picWidth=50;
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
	}
	
	public void move(double moveX,double moveY) {
		position.first+=moveX*Map.TILE_SIZE;
		position.second+=moveY*Map.TILE_SIZE;
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
