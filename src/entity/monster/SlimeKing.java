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
		picWidth = 2;
		// TODO Auto-generated constructor stub
	}

//	public void move(double moveX,double moveY) {
//		boolean checkX=false;
//		boolean checkY=false;
//		for(int i=0;i<picWidth;i++) {
//			for(int j=0;j<picHeight;j++) {
//				if(Map.getBoard(position.add(new Pair(moveX+i,moveY+j))).getEntity() != null) {
//					if (moveX == 0 || moveY == 0) {
//						changeDirection(moveX, moveY);
//						return;
//					} else if (Map.getBoard(position.add(new Pair(moveX+i,0))).getEntity() == null) {
//						checkX=true;
//					} else if (Map.getBoard(position.add(new Pair(0,moveY+j))).getEntity() == null) {
//						checkY=true;
//					} else {
//						return;
//					}
//				}
//			}
//		}
//		if(checkX) moveY=0;
//		if(checkY) moveX=0;
//		
//		double x = moveX;
//		double y = moveY;
//		for(int i=0;i<picWidth;i++) {
//			for(int j=0;j<picHeight;j++) {
//				Map.setBoard(position.add(new Pair(i,j)),TileType.NONE, null);
//			}
//		}
//		for(int i=0;i<picWidth;i++) {
//			for(int j=0;j<picHeight;j++) {
//				Map.setBoard(position.add(new Pair(x+i,y+j)),TileType.MONSTER, this);
//				System.out.println(position.add(new Pair(x+i,y+j)).first+" "+position.add(new Pair(x+i,y+j)).second);
//			}
//		}
//		System.out.println();
//		changeDirection(x, y);
//		Timeline timer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
//			position.first += x / Main.FPS * 10;
//			position.second += y / Main.FPS * 10;
//			draw();
//		}));
//		timer.setCycleCount(Main.FPS / 10);
//		timer.play();
//		timer.setOnFinished(e -> {
//		//	isActionFinished=true;
//			
//		});
//	}

}
