package entity.monster;

import java.util.Random;

import entity.Entity;
import entity.property.HpBar;
import environment.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Main;
import utility.Direction;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class Slime extends Monster {
	public static final int VISIBLE_RANGE = 5;

	private static final int DEFAULT_MAX_HP = 20;
	private static final int DEFAULT_ATK = 80;
	private static final int DEFAULT_DEF = 10;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int EXP_GAIN = 400;

	@Override
	public int getExpGain() {
		return EXP_GAIN;
	}


	private int currentAnimation = 0;
	private final static int maxImage = 6;
	private static final Image[] images = new Image[maxImage];
	static {
		for (int i = 1; i <= maxImage; ++i) {
			images[i - 1] = new Image("images/monster/slimer (" + i + ").png");
		}
	}

	private int currentAttackAnimation=0;
	private static final int maxAttackImage = 4;
	private static final Image[] attackImages = new Image[maxAttackImage];
	static {
		for (int i = 1; i <= maxAttackImage; ++i) {
			attackImages[i - 1] = new Image("images/effect/hit2 (" + i + ").png");
		}
	}

	double monsterX=position.first;
	double monsterY=position.second;
	int tileSize=Map.TILE_SIZE;
	
	
	private double oldStartX=0;
	private double oldStartY=0;
	private double oldSizeX=Map.WIDTH * tileSize;
	private double oldSizeY=Map.HEIGHT * tileSize;
	
	
	
	
	protected Pair areaPosition; // ref from top left

	public Slime(Pair pos) {
		super("Slime", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
		if (this instanceof SlimeKing)
			return;
		this.side = Side.MONSTER;
		this.areaPosition = new Pair(pos.first, pos.second);
		picHeight = 1;
		picWidth = 1;
		faceDirection = Direction.LEFT;
		lastLRFaceDirection = Direction.LEFT;
		// draw();
		// don't forget to initial picture size and first time position
	}

	
	public void draw() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.clearRect(0, 0, Map.WIDTH*tileSize, Map.HEIGHT*tileSize);
		currentAnimation %= 6;
		if (lastLRFaceDirection == Direction.RIGHT) {
			gc.drawImage(images[currentAnimation], 
					oldStartX=(position.first-0.5) * Map.TILE_SIZE,
					oldStartY=(position.second-0.5) * Map.TILE_SIZE,
					oldSizeX=(picWidth+0.5) * Map.TILE_SIZE, 
					oldSizeY=(picHeight+0.5) * Map.TILE_SIZE);
		} else if (lastLRFaceDirection == Direction.LEFT) {
			gc.drawImage(images[currentAnimation],
					oldStartX=(position.first) * Map.TILE_SIZE + picWidth * Map.TILE_SIZE+ Map.TILE_SIZE * 0.5,
					oldStartY=(position.second-0.5) * Map.TILE_SIZE,
					oldSizeX=(-picWidth-0.5) * Map.TILE_SIZE, 
					oldSizeY=(picHeight+0.5) * Map.TILE_SIZE);
		}

		drawDirection();
		if (isDead)
			return;
		Map.statusBarGroup.getChildren().remove(hpBar.getCanvas());

		hpBar = new HpBar(this);
		hpBar.draw();
		Map.statusBarGroup.getChildren().add(hpBar.getCanvas());

	}
	
	public void updateAnimation() {
		currentAnimation++;
		draw();
	}	
	public void updateAttackAnimation() {
		currentAttackAnimation++;
		drawAttackAnimation();
	}

	public void drawAttackAnimation() {
		GraphicsContext gc = this.atkCanvas.getGraphicsContext2D();
		if (currentAttackAnimation == 0) {
			attackDirection = faceDirection;
		}
		double monsterX=position.first;
		double monsterY=position.second;
		int tileSize=Map.TILE_SIZE;

		gc.clearRect(0, 0, Map.WIDTH*tileSize, Map.HEIGHT*tileSize);

		if (currentAttackAnimation <= (maxAttackImage-1)) {
			if (attackDirection == Direction.RIGHT) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15 + 1) * tileSize,
						(monsterY-0.15) * tileSize, (picWidth+0.3) * tileSize, (picHeight+0.3) * tileSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15 - 1) * tileSize,
						(monsterY-0.15) * tileSize, (picWidth+0.3) * tileSize, (picHeight+0.3) * tileSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15) * tileSize,
						(monsterY-0.15 + 1) * tileSize, (picWidth+0.3) * tileSize, (picHeight+0.3) * tileSize);
			} else if (attackDirection == Direction.UP) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15) * tileSize,
						(monsterY-0.15 - 1) * tileSize, (picWidth+0.3) * tileSize, (picHeight+0.3) * tileSize);
			}
		}
	}
	
	


	
}
