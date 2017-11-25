package entity.hero;

import environment.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Direction;
import utility.Pair;

public class Fighter extends Novice {
	private static final int DEFAULT_MAX_HP = 500;
	private static final int DEFAULT_ATK = 100;
	private static final int DEFAULT_DEF = 30;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 30.00;
	private static final double DEFAULT_CRI_RATE = 40;

	public Fighter(Pair pos) {
		super(pos);
		setValue("Fighter", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
	}
	
	public Fighter(String name,Pair pos) {
		super(name,pos);
		setValue(name, DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
	}
	
	
	
	
	private void drawDirection() {
		GraphicsContext gc =canvas.getGraphicsContext2D();
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);
		if(faceDirection==Direction.RIGHT) {	
			gc.strokeRect((position.first + 1) * Map.TILE_SIZE, (position.second-1) * Map.TILE_SIZE, picWidth* Map.TILE_SIZE, picHeight* Map.TILE_SIZE);
			gc.strokeRect((position.first + 1) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE, picWidth* Map.TILE_SIZE, picHeight* Map.TILE_SIZE);
			gc.strokeRect((position.first + 1) * Map.TILE_SIZE, (position.second+1) * Map.TILE_SIZE, picWidth* Map.TILE_SIZE, picHeight* Map.TILE_SIZE);
		}
		else if(faceDirection==Direction.LEFT) {	
			gc.strokeRect((position.first - 1) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE, picWidth* Map.TILE_SIZE, picHeight* Map.TILE_SIZE);
		}
		else if(faceDirection==Direction.DOWN) {		
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second + 1) * Map.TILE_SIZE, picWidth* Map.TILE_SIZE, picHeight* Map.TILE_SIZE);
		}
		else if(faceDirection==Direction.UP) {	
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second - 1) * Map.TILE_SIZE, picWidth* Map.TILE_SIZE, picHeight* Map.TILE_SIZE);
		}

	}
	
	
	
}
