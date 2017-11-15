package hero;

import entity.Entity;
import utility.Pair;
import utility.Side;

public class Novice extends Entity {

	private static final int DEFAULT_MAX_HP = 200;
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int[] EXP_RATE = {0,100,200,350,550,750,1000,1300,1650,2100,2500};
	
	
	
	protected int lv;
	protected int exp;
	
	
	public Novice(String name) {
		super(name,DEFAULT_MAX_HP,DEFAULT_ATK,DEFAULT_DEF,DEFAULT_ACC,DEFAULT_EVA,DEFAULT_CRI_RATE,new Pair(1,1));
		this.lv=1;
		this.exp=0;
		this.side=Side.HERO;
		// TODO Auto-generated constructor stub
	}
	public 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
