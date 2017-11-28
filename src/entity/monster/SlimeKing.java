package entity.monster;

import entity.property.HpBar;
import environment.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.Main;
import utility.Direction;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class SlimeKing extends Slime {
	public static final int VISIBLE_RANGE = 5;

	private static final int DEFAULT_MAX_HP = 500;
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	public static final int EXP_GAIN = 40;

	private static final Image[] images = new Image[6];
	static {
		for (int i = 1; i <= 6; ++i) {
			images[i - 1] = new Image("images/monster/slimer (" + i + ").png");
		}
	}
	private static int currentAnimation = 0;

	public SlimeKing(Pair pos) {
		super(pos);
		setValue("SlimeKing", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE,
				pos);
		this.side = Side.MONSTER;
		this.areaPosition = new Pair(pos.first, pos.second);
		picHeight = 2;
		picWidth = 2;
		lastLRFaceDirection = Direction.LEFT;
		// TODO Auto-generated constructor stub
	}

	public void draw() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);

		currentAnimation %= 6;
		if (lastLRFaceDirection == Direction.RIGHT) {
			gc.drawImage(images[currentAnimation], position.first * Map.TILE_SIZE - Map.TILE_SIZE * 0.5,
					position.second * Map.TILE_SIZE - Map.TILE_SIZE * 0.5,
					picWidth * Map.TILE_SIZE + Map.TILE_SIZE * 0.5, picHeight * Map.TILE_SIZE + Map.TILE_SIZE * 0.5);
		} else if (lastLRFaceDirection == Direction.LEFT) {
			gc.drawImage(images[currentAnimation],
					position.first * Map.TILE_SIZE - Map.TILE_SIZE * 0.5 + picWidth * Map.TILE_SIZE + Map.TILE_SIZE,
					position.second * Map.TILE_SIZE - Map.TILE_SIZE * 0.5,
					-picWidth * Map.TILE_SIZE - Map.TILE_SIZE * 0.5, picHeight * Map.TILE_SIZE + Map.TILE_SIZE * 0.5);
		}
		drawDirection();

		if (isDead)
			return;
		Map.statusBarGroup.getChildren().remove(hpBar.getCanvas());

		hpBar = new HpBar(this);
		hpBar.draw();
		Map.statusBarGroup.getChildren().add(hpBar.getCanvas());
		// System.out.println(Map.statusBarGroup.getChildren().contains(hpBar.getCanvas()));

	}

	public void updateAnimation() {
		currentAnimation++;
		draw();
	}

}
