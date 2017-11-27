package entity;
import entity.property.HpBar;
import environment.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Direction;
import utility.Pair;
import utility.Side;
public abstract class Entity {
	protected Canvas canvas;
	protected String name;
	protected double maxHp;
	protected double Hp;
	protected int atk;
	protected int def;
	protected double acc;
	protected double eva;
	protected double criRate;
	protected  Pair position;
	protected Side side;
	protected boolean isDead;
	protected boolean canMove;
	protected boolean canAttack;
	protected int picWidth;
	protected int picHeight;
	protected Direction faceDirection;
	protected Direction lastLRFaceDirection;
	protected HpBar hpBar;
	protected boolean isAttackFinished;
	protected boolean isMoveFinished;
	
	
	
	public Entity(String name, int maxHp, int attack, int defense, 
			double accuracy, double evasion,double criticalRate, Pair position) {
//		System.out.println(position.first+" "+position.second);
		
		setValue(name,maxHp,attack,defense,accuracy,evasion,criticalRate,position);
	}

	public abstract void draw();
	
	protected abstract void drawDirection() ;
	
	public abstract void move(double moveX,double moveY);
	
	public abstract void attack(Entity entity);
	
	public boolean isCollied(Entity entity) {
		//x1y1 x2y2 equal ? true false
		if(this.position == entity.position) {return true;}
		else return false;
	}
	
	public abstract void takeDamage(double dmg);
	
	public void die() {
		GraphicsContext gc=canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 200, 200);
		Map.getEntityGroup().getChildren().remove(canvas);
		isDead=true;
		Hp=0;
		hpBar.die();		
	}
	
	protected void setValue(String name, int maxHp, int attack, int defense, 
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
		hpBar= new HpBar(this);
		Map.statusBarGroup.getChildren().add(hpBar.getCanvas());
		//draw();
	}

	public void setFaceDirection(Direction faceDirection) {
		this.faceDirection = faceDirection;
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

	public String getName() {
		return name;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public double getHp() {
		return Hp;
	}

	public int getAtk() {
		return atk;
	}

	public int getDef() {
		return def;
	}

	public double getAcc() {
		return acc;
	}

	public double getEva() {
		return eva;
	}

	public double getCriRate() {
		return criRate;
	}

	public Side getSide() {
		return side;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public boolean isCanAttack() {
		return canAttack;
	}

	public int getPicWidth() {
		return picWidth;
	}

	public int getPicHeight() {
		return picHeight;
	}

	public Direction getFaceDirection() {
		return faceDirection;
	}
	
	public HpBar getHpBar() {
		return hpBar;
	}

	public boolean isAttackFinished() {
		return isAttackFinished;
	}

	public void setAttackFinished(boolean isAttackFinished) {
		this.isAttackFinished = isAttackFinished;
	}

	public boolean isMoveFinished() {
		return isMoveFinished;
	}

	public void setMoveFinished(boolean isMoveFinished) {
		this.isMoveFinished = isMoveFinished;
	}

	

	

	
	
	
	
}
