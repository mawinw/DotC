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
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
	private static final Font NAMEFONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("resources/font/arcadeclassic/ARCADECLASSIC.ttf"), 10);
	
	protected Timeline timer;
	protected int lv;
	protected int exp;
	protected Canvas nameCanvas;

	
	private static final int maxAttackImage = 5;
	private static final Image[] attackImages = new Image[maxAttackImage];
	static {
		for (int i = 1; i <= maxAttackImage; ++i) {
			attackImages[i - 1] = new Image("images/effect/hit5 (" + i + ").png");
		}
	}
	private static int currentAttackAnimation = 7;

	private static int currentAnimation = 0;
	
	
	
	
	private final static int maxSmashImage=8;
	private static final Image[] smashImages = new Image[maxSmashImage];
	static {
		for (int i = 1; i <= maxSmashImage; ++i) {
			smashImages[i - 1] = new Image("images/effect/smash (" + i + ").png");
		}
	}
	private static int currentSmashAnimation = 9;


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
		this.nameCanvas = new Canvas(Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);

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
		this.nameCanvas = new Canvas(Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);
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
		drawNameAndLv();

	}
	
	public void drawNameAndLv() {
		GraphicsContext gc = nameCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, Map.WIDTH * Map.TILE_SIZE, Map.HEIGHT * Map.TILE_SIZE);
		gc.setTextBaseline(VPos.BOTTOM);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(NAMEFONT);
		gc.setFill(Color.GRAY);
		gc.fillText(name+" lv. "+lv, (position.first+0.5) * Map.TILE_SIZE, position.second * Map.TILE_SIZE - 10);
		
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
		
		//currentAttackAnimation = 0;
		currentSmashAnimation=0; //
		
		Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(50), attack -> { //was 150
			//drawAttackAnimation();
			//currentAttackAnimation ++;
			drawSmashAnimation();
			currentSmashAnimation++;
		}));
		//attackTimeline.setCycleCount(6);
		attackTimeline.setCycleCount(9);
		attackTimeline.play();

		timer = new Timeline(new KeyFrame(new Duration(750), e -> {
			double atkDmg = calculateDamage(entity);
			entity.takeDamage(atkDmg);
			entity.draw();
			if (entity.getIsDead()) {
				exp += ((Monster)entity).getExpGain();
				System.out.println(exp);
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

		if (currentAttackAnimation <= maxAttackImage) {
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
				gc.clearRect((playerX-0.15) * tSize, (playerY + 1) * tSize,
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

		if (currentAttackAnimation == maxAttackImage+1) {
			if (attackDirection == Direction.RIGHT) {
				gc.clearRect((playerX + 1) * tSize, (playerY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.clearRect((playerX-0.3 - 1) * tSize, (playerY-0.15) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.clearRect((playerX-0.15) * tSize, (playerY+ 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			} else if (attackDirection == Direction.UP) {
				gc.clearRect((playerX-0.15) * tSize, (playerY-0.3 - 1) * tSize,
						(picWidth+0.3) * tSize, (picHeight+0.3) * tSize);
			}
		}

	}
	
	private void drawSmashAnimation() {
		GraphicsContext gc = this.canvas.getGraphicsContext2D();
		if (currentSmashAnimation == 0) {
			attackDirection = faceDirection;
		}
		double playerX=position.first;
		double playerY=position.second;
		int tileSize=Map.TILE_SIZE;

		if (currentSmashAnimation <= (maxSmashImage)) {
				gc.clearRect((playerX - 1) * tileSize, (playerY-1) * tileSize,
						(picWidth+2) * tileSize, (picHeight+2) * tileSize);
				gc.drawImage(smashImages[currentSmashAnimation], (playerX - 1) * tileSize,
						(playerY-1) * tileSize, (picWidth+2) * tileSize, (picHeight+2) * tileSize);
		}

		if (currentSmashAnimation == maxSmashImage+1) {
			gc.clearRect((playerX - 1) * tileSize, (playerY-1) * tileSize,
					(picWidth) * tileSize, (picHeight) * tileSize);
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
		if (EXP_RATE[lv] < exp) {
			lv++;
			drawNameAndLv();
		}
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

	public Canvas getNameCanvas() {
		return nameCanvas;
	}

}