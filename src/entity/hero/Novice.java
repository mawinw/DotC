package entity.hero;

import java.awt.Graphics2D;
import java.util.Random;

import entity.Entity;
import entity.monster.Monster;
import entity.monster.SlimeKing;
import entity.property.HpBar;
import environment.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Handler;
import main.Main;
import utility.Direction;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class Novice extends Entity {

	private static final int DEFAULT_MAX_HP = 200;
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 30;
	private static final int[] EXP_RATE = { 0, 100, 200, 350, 550, 750, 1000, 1300, 1650, 2100, 2500 };

	protected Timeline timer;
	protected int lv;
	protected int exp;

	private static final Image[] attackImages = new Image[5];
	static {
		for (int i = 1; i <= 5; ++i) {
			attackImages[i - 1] = new Image("images/effect/hit5 (" + i + ").png");
		}
	}
	private static int currentAttackAnimation = 7;

	private static int currentAnimation = 0;

	public Novice(Pair pos) {
		super("Novice", DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
		// System.out.println(this.position.first+" "+this.position.second);
		this.lv = 1;
		this.exp = 0;
		this.side = Side.HERO;
		picHeight = 1;
		picWidth = 1;
		this.faceDirection = Direction.RIGHT;
		this.isAttackFinished = true;
		this.isMoveFinished = true;
		// don't forget to initial picture size and first time position
	}

	public Novice(String name, Pair pos) {
		super(name, DEFAULT_MAX_HP, DEFAULT_ATK, DEFAULT_DEF, DEFAULT_ACC, DEFAULT_EVA, DEFAULT_CRI_RATE, pos);
		this.lv = 1;
		this.exp = 0;
		this.side = Side.HERO;
		picHeight = 1;
		picWidth = 1;
		this.faceDirection = Direction.RIGHT;
		this.isAttackFinished = true;
		this.isMoveFinished = true;
		// don't forget to initial picture size and first time position
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);
		gc.setFill(Color.AQUA);
		gc.fillOval(position.first * Map.TILE_SIZE, position.second * Map.TILE_SIZE, picWidth * Map.TILE_SIZE,
				picHeight * Map.TILE_SIZE);
		drawDirection();
		// System.out.println(position.first+" "+position.second);
		if (isDead)
			return;
		Map.statusBarGroup.getChildren().remove(hpBar.getCanvas());
		hpBar = new HpBar(this);
		hpBar.draw();
		Map.statusBarGroup.getChildren().add(hpBar.getCanvas());
	}

	protected void drawDirection() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);
		if (faceDirection == Direction.RIGHT) {
			gc.strokeRect((position.first + 1) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE,
					picWidth * Map.TILE_SIZE, picHeight * Map.TILE_SIZE);
		} else if (faceDirection == Direction.LEFT) {
			gc.strokeRect((position.first - 1) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE,
					picWidth * Map.TILE_SIZE, picHeight * Map.TILE_SIZE);
		} else if (faceDirection == Direction.DOWN) {
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second + 1) * Map.TILE_SIZE,
					picWidth * Map.TILE_SIZE, picHeight * Map.TILE_SIZE);
		} else if (faceDirection == Direction.UP) {
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second - 1) * Map.TILE_SIZE,
					picWidth * Map.TILE_SIZE, picHeight * Map.TILE_SIZE);
		}

	}

	public void move(double moveX, double moveY) {
		isMoveFinished = false;
		Map.setBoard(position, TileType.NONE, null);
		Map.setBoard(position.add(new Pair(moveX, moveY)), TileType.HERO, this);

		Timeline timer2 = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			position.first += moveX / Main.FPS * 10;
			position.second += moveY / Main.FPS * 10;
			draw();
		}));
		timer2.setCycleCount(Main.FPS / 10);
		timer2.play();
		timer2.setOnFinished(e -> {
			isMoveFinished = true;
		});
	}

	public void attack(Entity entity) {
		isMoveFinished = false;
		isAttackFinished = false;
		entity.setMoveFinished(false);
		currentAttackAnimation = 0;
		
		Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(150), attack -> {
			drawAttackAnimation();
			currentAttackAnimation ++;
		}));
		attackTimeline.setCycleCount(6);
		attackTimeline.play();

		timer = new Timeline(new KeyFrame(new Duration(750), e -> {
			double atkDmg = calculateDamage(entity);
			entity.takeDamage(atkDmg);
			entity.draw();
			if (entity.getIsDead()) {
				exp += Monster.EXP_GAIN;
				checkLevelUp();
				// System.out.println(lv+" "+exp);
			}
		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e -> {
			Timeline wait = new Timeline(new KeyFrame(Duration.millis(100), f -> {
			}));
			wait.setCycleCount(1);
			wait.play();
			wait.setOnFinished(f -> {
				isMoveFinished = true;
				isAttackFinished = true;
				entity.setMoveFinished(false);
			});
		});
	}

	public void drawAttackAnimation() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		if (currentAttackAnimation == 0) {
			attackDirection = faceDirection;
		}
		double playerX=position.first;
		double playerY=position.second;
		int tSize=Map.TILE_SIZE;

		if (currentAttackAnimation <= 4) {
			if (attackDirection == Direction.RIGHT) {
				gc.clearRect((playerX + 1) * tSize, (playerY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (playerX-0.15 + 1) * tSize,
						(playerY-0.15) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.clearRect((playerX-0.3 - 1) * tSize, (playerY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (playerX-0.15 - 1) * tSize,
						(playerY-0.15) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.clearRect((playerX-0.15) * tSize, (playerY+0.3 + 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (playerX-0.15) * tSize,
						(playerY-0.15 + 1) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.UP) {
				gc.clearRect((playerX-0.3) * tSize, (playerY-0.3 - 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (playerX-0.15) * tSize,
						(playerY-0.15 - 1) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			}
		}

		if (currentAttackAnimation == 5) {
			if (attackDirection == Direction.RIGHT) {
				gc.clearRect((playerX + 1) * tSize, (playerY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.clearRect((playerX-0.3 - 1) * tSize, (playerY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.clearRect((playerX-0.15) * tSize, (playerY + 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.UP) {
				gc.clearRect((playerX-0.15) * tSize, (playerY-0.3 - 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			}
		}

	}

	public void normalAttack() {
		if ((int) position.first + 1 < Map.WIDTH) {
			if (Map.getBoard()[(int) position.first + 1][(int) (position.second)].getTileType() == TileType.MONSTER
					&& faceDirection == Direction.RIGHT) {
				attack(Map.getBoard()[(int) position.first + 1][(int) position.second].getEntity());
			}
		}

		if ((int) position.first - 1 > 0) {
			if (Map.getBoard()[(int) position.first - 1][(int) (position.second)].getTileType() == TileType.MONSTER
					&& faceDirection == Direction.LEFT) {
				attack(Map.getBoard()[(int) position.first - 1][(int) (position.second)].getEntity());
			}
		}

		if ((int) position.second - 1 > 0) {
			if (Map.getBoard()[(int) position.first][(int) (position.second) - 1].getTileType() == TileType.MONSTER
					&& faceDirection == Direction.UP) {
				attack(Map.getBoard()[(int) position.first][(int) (position.second) - 1].getEntity());
			}
		}

		if ((int) position.second + 1 < Map.HEIGHT) {
			if (Map.getBoard()[(int) Map.getHero().getPosition().first][(int) (position.second) + 1]
					.getTileType() == TileType.MONSTER && faceDirection == Direction.DOWN) {
				attack(Map.getBoard()[(int) Map.getHero().getPosition().first][(int) (position.second) + 1]
						.getEntity());
			}
		}
	}

	protected void checkLevelUp() {
		if (EXP_RATE[lv] < exp)
			lv++;
	}

	protected double calculateDamage(Entity entity) {
		Random rn = new Random();
		int atkSuccess = rn.nextInt(100);
		int criSuccess = rn.nextInt(100);
		// System.out.println(atkSuccess+" "+criSuccess+" "+(this.atk -
		// entity.getDef()));
		if (this.acc - entity.getEva() > atkSuccess) {
			if (this.atk > entity.getDef()) {
				if (this.criRate > criSuccess)
					return 2 * (this.atk - entity.getDef());
				else
					return this.atk - entity.getDef();
			} else
				return 1;
		} else
			return 0;
	}

	public void takeDamage(double dmg) {
		if (Hp <= dmg) {
			die();
		} else {
			Hp -= dmg;
		}
	}

	public void updateAnimation() {
		// TODO Auto-generated method stub
		// draw();
//		currentAttackAnimation++;
//		currentAttackAnimation %= 5;
//		drawAttackAnimation();
	}

}