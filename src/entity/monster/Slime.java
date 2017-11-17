package entity.monster;

import entity.Entity;
import environment.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Pair;
import utility.Side;

public class Slime extends Entity {
	private static final int DEFAULT_MAX_HP = 50;
	private static final int DEFAULT_ATK = 10;
	private static final int DEFAULT_DEF = 10;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int EXP_GAIN =40;
	
	public Slime() {
		super("Slime",DEFAULT_MAX_HP,DEFAULT_ATK,DEFAULT_DEF,DEFAULT_ACC,DEFAULT_EVA,DEFAULT_CRI_RATE,new Pair(1,1));
		this.side=Side.MONSTER;
		// อย่าลืม initial ขนาดรูปมานะ
		// แก้ตำแหน่งเริ่มด้วย
	}
	
	public void draw() {
		GraphicsContext 	gc=this.canvas.getGraphicsContext2D();
		gc.setFill(Color.YELLOW);
		gc.fillRect(position.first-picWidth/2, position.second-picHeight/2, picWidth, picHeight);
		}
	
	public void move(int moveX,int moveY) {
		position.first+=moveX*Map.BLOCK_SIZE;
		position.second+=moveY*Map.BLOCK_SIZE;
		
	}
	
	public void attack(Entity entity) {
		double atkDmg=this.atk*(1-entity.defRate());
		takeDamage(atkDmg);
	}
	public void takeDamage(double dmg) {
		if(Hp<=dmg) {this.isDead=true;}
		else {
			Hp-=dmg;
		}
	}
	
	
}
