package entity.hero;

import entity.Entity;
import entity.monster.Monster;
import entity.property.HpBar;
import environment.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utility.Direction;
import utility.Pair;
import utility.TileType;

public class Fighter extends Novice {
	private static final int DEFAULT_MAX_HP = 500;
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 30;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 30.00;
	private static final double DEFAULT_CRI_RATE = 40;

	public Fighter(Pair pos) {
		super(pos);
		setValue("Fighter", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
	}

	public Fighter(String name, Pair pos) {
		super(name, pos);
		setValue(name, DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
	}

	public void groundSmash() {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (position.first + i - 1 >= 0 && position.first + i + 1 < Map.WIDTH && position.second + j - 1 >= 0
						&& position.second + j + 1 < Map.HEIGHT) {
					if (Map.getBoard()[(int) position.first + i][(int) (position.second) + j]
							.getTileType() == TileType.MONSTER) {
						attack(Map.getBoard()[(int) position.first + i][(int) position.second + j].getEntity());
					}
				}
			}
		}

	}

	protected void drawSkillDirection() {
		System.out.println("x");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);
		
		int tileSize = Map.TILE_SIZE;
		double playerX = position.first;
		double playerY = playerY;
		
		if (faceDirection == Direction.RIGHT) {
			gc.strokeRect((playerX + 1) * tileSize, (playerY - 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX + 1) * tileSize, (playerY) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX + 1) * tileSize, (playerY + 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
		} else if (faceDirection == Direction.LEFT) {
			gc.strokeRect((playerX - 1) * tileSize, (playerY - 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX - 1) * tileSize, (playerY) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX - 1) * tileSize, (playerY + 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
		} else if (faceDirection == Direction.DOWN) {
			gc.strokeRect((playerX - 1) * tileSize, (playerY + 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX) * tileSize, (playerY + 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX + 1) * tileSize, (playerY + 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
		} else if (faceDirection == Direction.UP) {
			gc.strokeRect((playerX - 1) * tileSize, (playerY - 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX) * tileSize, (playerY - 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
			gc.strokeRect((playerX + 1) * tileSize, (playerY - 1) * tileSize,
					picWidth * tileSize, picHeight * tileSize);
		}

	}

}