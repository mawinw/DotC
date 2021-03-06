package entity.monster;

import java.util.Random;

import entity.Entity;
import entity.property.HpBar;
import environment.GameScene;
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

	private static final int DEFAULT_MAX_HP = 100;
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 10;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int EXP_GAIN = 400;

	@Override
	public int getExpGain() {
		return EXP_GAIN;
	}

	private Random rn = new Random();
	private int rng = rn.nextInt(6);
	private int currentAnimation = rng;
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
	private boolean isAlreadyDead;

	double monsterX=position.first;
	double monsterY=position.second;
	int tileSize=GameScene.TILE_SIZE;
	
	
	private double oldStartX=0;
	private double oldStartY=0;
	private double oldSizeX=GameScene.WIDTH * tileSize;
	private double oldSizeY=GameScene.HEIGHT * tileSize;
	
	
	
	
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
		isAlreadyDead=false;
		// draw();
		// don't forget to initial picture size and first time position
	}

	
	public void draw() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		
		gc.clearRect(0, 0, GameScene.WIDTH*tileSize, GameScene.HEIGHT*tileSize);
		currentAnimation %= 6;
		if (lastLRFaceDirection == Direction.RIGHT) {
			gc.drawImage(images[currentAnimation], 
					oldStartX=(position.first-0.5) * GameScene.TILE_SIZE,
					oldStartY=(position.second-0.5) * GameScene.TILE_SIZE,
					oldSizeX=(picWidth+0.5) * GameScene.TILE_SIZE, 
					oldSizeY=(picHeight+0.5) * GameScene.TILE_SIZE);
		} else if (lastLRFaceDirection == Direction.LEFT) {
			gc.drawImage(images[currentAnimation],
					oldStartX=(position.first) * GameScene.TILE_SIZE + picWidth * GameScene.TILE_SIZE+ GameScene.TILE_SIZE * 0.5,
					oldStartY=(position.second-0.5) * GameScene.TILE_SIZE,
					oldSizeX=(-picWidth-0.5) * GameScene.TILE_SIZE, 
					oldSizeY=(picHeight+0.5) * GameScene.TILE_SIZE);
		}

		drawDirection();
		if (isDead)
			return;
		GameScene.statusBarGroup.getChildren().remove(hpBar.getCanvas());

		hpBar = new HpBar(this);
		hpBar.draw();
		GameScene.statusBarGroup.getChildren().add(hpBar.getCanvas());

	}
	
	public void updateAnimation() {
		currentAnimation++;
		draw();
	}	
	

	public void attack(Entity entity) {
		entity.setMoveFinished(false);

		Timeline timer = new Timeline(new KeyFrame(new Duration(1000), e -> {
			double atkDmg = calculateDamage(entity);
			entity.takeDamage(atkDmg);
			entity.draw();

		}));
		timer.setCycleCount(1);
		timer.play();
		

		currentAttackAnimation=0;
		Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(150), attack -> {
			drawAttackAnimation();
			currentAttackAnimation ++;
		}));
		attackTimeline.setCycleCount(6);
		attackTimeline.play();
		
		
		
		
		timer.setOnFinished(e -> {
			Timeline wait = new Timeline(new KeyFrame(Duration.millis(100), f -> {
			}));
			wait.setCycleCount(1);
			wait.play();
			entity.setMoveFinished(true);
		});
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
		int tileSize=GameScene.TILE_SIZE;

		gc.clearRect(0, 0, GameScene.WIDTH*tileSize, GameScene.HEIGHT*tileSize);

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
	
	public void die() {
		if(!isAlreadyDead) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		GameScene.getEntityGroup().getChildren().remove(canvas);
		isDead = true;
		hp = 0;
		hpBar.die();
		GameScene.decreaseMonsterCount();
		isAlreadyDead=true;
		}
	}


	
}
