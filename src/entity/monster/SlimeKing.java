package entity.monster;

import entity.Entity;
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

	private static final int DEFAULT_MAX_HP = 1250;
	private static final int DEFAULT_ATK = 90;
	@Override
	public int getExpGain() {
		return EXP_GAIN;
	}

	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 80.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	private static final int EXP_GAIN = 250;

	
	private static final Image[] images = new Image[6];
	static {
		for (int i = 1; i <= 6; ++i) {
			images[i - 1] = new Image("images/monster/slimer (" + i + ").png");
		}
	}
	private int currentAnimation = 0;
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
		double monsterX=position.first;
		double monsterY=position.second;
		int tileSize=Map.TILE_SIZE;
		
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH*tileSize, Map.HEIGHT*tileSize);

		currentAnimation %= 6;
		if (lastLRFaceDirection == Direction.RIGHT) {
			gc.drawImage(images[currentAnimation], 
					oldStartX=monsterX * tileSize - tileSize * 0.9,
					oldStartY=monsterY * tileSize - tileSize * 0.9,
					oldSizeX=picWidth * tileSize + tileSize * 0.9,
					oldSizeY=picHeight * tileSize + tileSize * 0.9);
		} else if (lastLRFaceDirection == Direction.LEFT) {
			gc.drawImage(images[currentAnimation],
					oldStartX=monsterX * tileSize - tileSize * 0 + picWidth * tileSize + tileSize,
					oldStartY=monsterY * tileSize - tileSize * 0.9,
					oldSizeX=-picWidth * tileSize - tileSize * 0.9,
					oldSizeY=picHeight * tileSize + tileSize * 0.9);
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
	public void drawAttackAnimation() {
		GraphicsContext gc = this.atkCanvas.getGraphicsContext2D();
		double monsterX=position.first;
		double monsterY=position.second;
		int tileSize=Map.TILE_SIZE;
		if (currentAttackAnimation == 0) {
			attackDirection = faceDirection;
		}
		gc.clearRect(0, 0, Map.WIDTH*tileSize, Map.HEIGHT*tileSize);

		if (currentAttackAnimation <= (maxAttackImage-1)) {
			if (attackDirection == Direction.RIGHT) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15 + 2) * tileSize,
						(monsterY-0.15-0.5) * tileSize, (picWidth+0.3) * tileSize/2, (picHeight+0.3+1) * tileSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15 - 1) * tileSize,
						(monsterY-0.15-0.5) * tileSize, (picWidth+0.3) * tileSize/2, (picHeight+0.3+1) * tileSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15) * tileSize,
						(monsterY-0.15 + 2) * tileSize, (picWidth+0.3) * tileSize, (picHeight+0.3) * tileSize/2);
			} else if (attackDirection == Direction.UP) {
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15) * tileSize,
						(monsterY-0.15 - 1) * tileSize, (picWidth+0.3) * tileSize, (picHeight+0.3-1) * tileSize);
			}
		}

	}
	public void attack(Entity entity) {
		entity.setMoveFinished(false);
		
		currentAttackAnimation=0;
		double monsterX=position.first;
		double monsterY=position.second;
		int tileSize=Map.TILE_SIZE;
		Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(150), attack -> {
			drawAttackAnimation();
			currentAttackAnimation ++;
		}));
		attackTimeline.setCycleCount(6);
		attackTimeline.play();
		
		
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000), e -> {
			double atkDmg = calculateDamage(entity);
			entity.takeDamage(atkDmg);
			entity.draw();

		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e -> {
			Timeline wait = new Timeline(new KeyFrame(Duration.millis(100), f -> {
			}));
			wait.setCycleCount(1);
			wait.play();
			entity.setMoveFinished(true);
		});
	}


	public void updateAnimation() {
		currentAnimation++;
		draw();
	}

}
