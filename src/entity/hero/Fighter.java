package entity.hero;

import entity.Entity;
import entity.monster.Monster;
import entity.property.HpBar;
import environment.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
	

	private final static int maxSmashImage=8;
	private static final Image[] smashImages = new Image[maxSmashImage];
	static {
		for (int i = 1; i <= maxSmashImage; ++i) {
			smashImages[i - 1] = new Image("images/effect/smash (" + i + ").png");
		}
	}
	private static int currentSmashAnimation = 0;

	

	public Fighter(Pair pos) {
		super(pos);
		System.out.println("x");
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
		currentSmashAnimation=0;
		Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(50), attack -> {
			drawSmashAnimation();
			currentSmashAnimation ++;
		}));
		attackTimeline.setCycleCount(7);
		attackTimeline.play();
		

	}
	
	private void drawSmashAnimation() {
		GraphicsContext gc = this.atkCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH*Map.TILE_SIZE, Map.HEIGHT*Map.TILE_SIZE);
		if (currentSmashAnimation == 0) {
			attackDirection = faceDirection;
		}
		double playerX=position.first;
		double playerY=position.second;
		int tileSize=Map.TILE_SIZE;

		if (currentSmashAnimation <= (maxSmashImage-1)) {
				gc.clearRect((playerX - 1) * tileSize, (playerY-1) * tileSize,
						(picWidth+2) * tileSize, (picHeight+2) * tileSize);
				gc.drawImage(smashImages[currentSmashAnimation], (playerX - 1) * tileSize,
						(playerY-1) * tileSize, (picWidth+2) * tileSize, (picHeight+2) * tileSize);
		}

	}
	

	protected void drawSkillDirection() {
		System.out.println("x");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);
		
		int tileSize = Map.TILE_SIZE;
		double playerX = position.first;
		double playerY = position.second;
		
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