package entity.monster;

import entity.property.HpBar;
import environment.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.Main;
import utility.Direction;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class SlimeKing extends Monster {
	public static final int VISIBLE_RANGE = 5;

	private static final int DEFAULT_MAX_HP = 500;
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	public static final int EXP_GAIN = 40;

	public SlimeKing(Pair pos) {
		super(pos);
		setValue("SlimeKing", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
		this.side=Side.MONSTER;
		this.areaPosition = new Pair(pos.first, pos.second);
		picHeight = 2;
		picWidth = 3;
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
}
