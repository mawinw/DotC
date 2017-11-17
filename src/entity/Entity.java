package entity;
import javafx.scene.canvas.Canvas;
import utility.Pair;
import utility.Side;
public abstract class Entity {
	protected Canvas canvas;
	protected String name;
	protected int maxHp;
	protected int Hp;
	protected int atk;
	protected int def;
	protected double acc;
	protected double eva;
	protected double criRate;
	protected Pair position;
	protected Side side;
	protected boolean isDead;
	protected boolean canMove;
	protected boolean canAttack;
	protected int picLength;
	
	
	public Entity(String name, int maxHp, int attack, int defense, 
			double accuracy, double evasion,double criticalRate, Pair position) {
		this.canvas=new Canvas();
		this.name = name;
		this.maxHp = maxHp;
		this.atk = attack;
		this.def = defense;
		this.acc = accuracy;
		this.eva = evasion;
		this.criRate = criticalRate;
		this.position = position;
		this.canAttack=true;
		this.canMove=true;
		this.isDead=false;
		draw();
	}

	public abstract void draw();
	
	public abstract void move(int moveX,int moveY);
	
	public abstract void attack(Entity entity);
	
	public boolean isCollied(Entity entity) {
		//x1y1 x2y2 equal ? true false
		if(this.position == entity.position) {return true;}
		else return false;
	}
	
	public abstract void takeDamage(double dmg);
	
	public void die() {
		isDead=true;
		canvas.setOpacity(0);
	}

	public double defRate() {
		return def/(def+100);
	}
	
	
	
	
	
	
	
	
	

	
	
	
	
}
