package entity;
import environment.Map;
import javafx.scene.canvas.Canvas;
import utility.Direction;
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
	protected int picWidth;
	protected int picHeight;
	protected Direction faceDirection;
	
	
	
	public Entity(String name, int maxHp, int attack, int defense, 
			double accuracy, double evasion,double criticalRate, Pair position) {
		this.canvas=new Canvas(Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);
		this.name = name;
		this.maxHp = maxHp;
		Hp=maxHp;
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
	
	public abstract void move(double moveX,double moveY);
	
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

	public boolean getIsDead() {
		return isDead;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void setPosition(int x,int y) {
		position=new Pair(x,y);
	}
	public Pair getPosition() {
		return position;
	}
	
	
	
	

	
	
	
	
}
