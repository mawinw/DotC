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

public abstract class Monster extends Entity {

	public static final int VISIBLE_RANGE = 3;

	private static final int DEFAULT_MAX_HP = 100;
	private static final int DEFAULT_ATK = 20;
	private static final int DEFAULT_DEF = 10;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 0.00;
	private static final double DEFAULT_CRI_RATE = 0;
	
	private static final int EXP_GAIN = 20;



	private static int currentAnimation = 0;
	private final static int maxImage = 6;
	
	public int getExpGain() {
		return EXP_GAIN;
	}

	private static Image[] images;

	private static int currentAttackAnimation=0;
	private static final int maxAttackImage = 4;
	private static Image[] attackImages;
	
	protected Pair areaPosition; // ref from top left

	public Monster(String name, int maxHp, int attack, int defense, double accuracy, double evasion,
			double criticalRate, Pair pos) {
		super(name, maxHp, attack, defense, accuracy, evasion, criticalRate, pos);
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
		gc.clearRect(0, 0, Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);

		currentAnimation %= 6;
		if (lastLRFaceDirection == Direction.RIGHT) {
			gc.drawImage(images[currentAnimation], position.first * Map.TILE_SIZE - Map.TILE_SIZE * 0.25,
					position.second * Map.TILE_SIZE - Map.TILE_SIZE * 0.25,
					picWidth * Map.TILE_SIZE + Map.TILE_SIZE * 0.25, picHeight * Map.TILE_SIZE + Map.TILE_SIZE * 0.25);
		} else if (lastLRFaceDirection == Direction.LEFT) {
			gc.drawImage(images[currentAnimation],
					position.first * Map.TILE_SIZE - Map.TILE_SIZE * 0.25 + picWidth * Map.TILE_SIZE
							+ Map.TILE_SIZE * 0.5,
					position.second * Map.TILE_SIZE - Map.TILE_SIZE * 0.25,
					-picWidth * Map.TILE_SIZE - Map.TILE_SIZE * 0.25, picHeight * Map.TILE_SIZE + Map.TILE_SIZE * 0.25);
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
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		if (currentAttackAnimation == 0) {
			attackDirection = faceDirection;
		}
		double monsterX=position.first;
		double monsterY=position.second;
		int tSize=Map.TILE_SIZE;

		if (currentAttackAnimation <= (maxAttackImage-1)) {
			if (attackDirection == Direction.RIGHT) {
				gc.clearRect((monsterX + 1) * tSize, (monsterY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15 + 1) * tSize,
						(monsterY-0.15) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.clearRect((monsterX-0.3 - 1) * tSize, (monsterY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15 - 1) * tSize,
						(monsterY-0.15) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.clearRect((monsterX-0.15) * tSize, (monsterY+0.3 + 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15) * tSize,
						(monsterY-0.15 + 1) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.UP) {
				gc.clearRect((monsterX-0.3) * tSize, (monsterY-0.3 - 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
				gc.drawImage(attackImages[currentAttackAnimation], (monsterX-0.15) * tSize,
						(monsterY-0.15 - 1) * tSize, (picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			}
		}

		if (currentAttackAnimation == maxAttackImage) {
			if (attackDirection == Direction.RIGHT) {
				gc.clearRect((monsterX + 1) * tSize, (monsterY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.clearRect((monsterX-0.3 - 1) * tSize, (monsterY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.clearRect((monsterX-0.15) * tSize, (monsterY + 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.UP) {
				gc.clearRect((monsterX-0.15) * tSize, (monsterY-0.3 - 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			}
		}

	}
	
	
	protected void drawDirection() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLUE);
		gc.setLineWidth(4);
		if (faceDirection == Direction.RIGHT) {
			gc.strokeRect((position.first + picWidth) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE, Map.TILE_SIZE,
					picHeight * Map.TILE_SIZE);
		} else if (faceDirection == Direction.LEFT) {
			gc.strokeRect((position.first - 1) * Map.TILE_SIZE, (position.second) * Map.TILE_SIZE, Map.TILE_SIZE,
					picHeight * Map.TILE_SIZE);
		} else if (faceDirection == Direction.DOWN) {
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second + picHeight) * Map.TILE_SIZE,
					picWidth * Map.TILE_SIZE, Map.TILE_SIZE);
		} else if (faceDirection == Direction.UP) {
			gc.strokeRect((position.first) * Map.TILE_SIZE, (position.second - 1) * Map.TILE_SIZE,
					picWidth * Map.TILE_SIZE, Map.TILE_SIZE);
		}

	}

	public void move(double moveX, double moveY) {

		if (!checkMove(moveX, moveY)) {
			return;
		}
		double x = moveX;
		double y = moveY;
		for (int i = 0; i < picWidth; i++) {
			for (int j = 0; j < picHeight; j++) {
				Map.setBoard(position.add(new Pair(i, j)), TileType.NONE, null);
			}
		}
		for (int i = 0; i < picWidth; i++) {
			for (int j = 0; j < picHeight; j++) {
				Map.setBoard(position.add(new Pair(x + i, y + j)), TileType.MONSTER, this);
				// System.out.println(position.add(new Pair(x+i,y+j)).first+" "+position.add(new
				// Pair(x+i,y+j)).second);
			}
		}
		// System.out.println();
		changeDirection(x, y);
		if (x < 0 && lastLRFaceDirection == Direction.RIGHT) {
			lastLRFaceDirection = Direction.LEFT;
		}
		if (x > 0 && lastLRFaceDirection == Direction.LEFT) {
			lastLRFaceDirection = Direction.RIGHT;
		}
		Timeline timer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			position.first += x / Main.FPS * 10;
			position.second += y / Main.FPS * 10;
			draw();
		}));
		timer.setCycleCount(Main.FPS / 10);
		timer.play();
		timer.setOnFinished(e -> {
			// isActionFinished=true;

		});
	}

	protected boolean checkMove(double moveX, double moveY) {
		// System.out.println(moveX+" "+moveY);
		if (moveX == 1) {
			for (int i = 0; i < picHeight; i++) {
				// System.out.println((moveX+picWidth-1)+" "+i);
				if (Map.getBoard(position.add(new Pair(moveX + picWidth - 1, i))).getEntity() != null) {
					changeDirection(1, 0);
					return false;
				}
			}
		}

		if (moveX == -1) {
			for (int i = 0; i < picHeight; i++) {
				if (Map.getBoard(position.add(new Pair(moveX, i))).getEntity() != null) {
					changeDirection(-1, 0);
					return false;
				}
			}
		}

		if (moveY == 1) {
			for (int i = 0; i < picWidth; i++) {
				if (Map.getBoard(position.add(new Pair(i, moveY + picHeight - 1))).getEntity() != null) {
					changeDirection(0, 1);
					return false;
				}
			}
		}

		if (moveY == -1) {
			for (int i = 0; i < picWidth; i++) {
				if (Map.getBoard(position.add(new Pair(i, moveY))).getEntity() != null) {
					changeDirection(0, -1);
					return false;
				}
			}
		}
		return true;
	}

	protected void changeDirection(double x, double y) {
		if (x > 0)
			faceDirection = Direction.RIGHT;
		else if (x < 0)
			faceDirection = Direction.LEFT;
		else if (y > 0)
			faceDirection = Direction.DOWN;
		else if (y < 0)
			faceDirection = Direction.UP;
		draw();
	}

	public void randomMove() {
		int moveRandom = new Random().nextInt(4);
		double moveX = 0, moveY = 0;
		switch (moveRandom) {
		case 0:
			moveX = 1;
			moveY = 0;
			break;
		case 1:
			moveX = -1;
			moveY = 0;
			break;
		case 2:
			moveX = 0;
			moveY = 1;
			break;
		case 3:
			moveX = 0;
			moveY = -1;
			break;
		}
		// System.out.println(areaPosition.first+" "+areaWidth+" "+areaHeight+"
		// "+areaPosition.second);
		if (0 <= position.first + moveX && position.first + moveX < Map.WIDTH && 0 <= position.second + moveY
				&& position.second + moveY < Map.HEIGHT) {
			if (Map.getBoard(position.add(new Pair(moveX, moveY))).getEntity() == null)
				move(moveX, moveY);
		}
	}

	public void moveToPlayer() {
		double moveX = Map.getHeroPosition().first - position.first;
		double moveY = Map.getHeroPosition().second - position.second;
		// System.out.println(" "+moveX+" "+moveY);
		for (int i = 1; i < picWidth; i++) {
			if (moveX > 0) {
				moveX = Math.min(moveX, moveX - 1);

			} else if (moveX < 0) {
				moveX = Math.max(moveX, moveX - 1);
			}
		}
		for (int i = 1; i < picHeight; i++) {
			if (moveY > 0) {
				moveY = Math.min(moveY, moveY - 1);
			} else if (moveY < 0) {
				moveY = Math.max(moveY, moveY - 1);
			}
		}
		if (Math.abs(moveX) > Math.abs(moveY)) {
			if (moveX > 0)
				moveX = 1;
			else if (moveX < 0)
				moveX = -1;
			moveY = 0;
		} else {
			if (moveY > 0)
				moveY = 1;
			else if (moveY < 0)
				moveY = -1;
			moveX = 0;
		}
		// System.out.println(moveX+" "+moveY);
		move(moveX, moveY);
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
		timer.setOnFinished(e -> {
			Timeline wait = new Timeline(new KeyFrame(Duration.millis(100), f -> {
			}));
			wait.setCycleCount(1);
			wait.play();
			entity.setMoveFinished(true);
		});
	}

	public void takeDamage(double dmg) {
		// System.out.println(Hp+" "+dmg);
		if (Hp <= dmg) {
			die();
		} else {
			Hp -= dmg;
		}
	}

	private double calculateDamage(Entity entity) {
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

}
