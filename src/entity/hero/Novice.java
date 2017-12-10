package entity.hero;

import java.awt.Graphics2D;
import java.util.Random;

import entity.Entity;
import entity.monster.Monster;
import entity.monster.SlimeKing;
import entity.property.Attackable;
import entity.property.HpBar;
import entity.property.Moveable;
import environment.GameHandler;
import environment.GameScene;
import environment.StatusBar;
import exception.AttackDeadEntityException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.Main;
import utility.Direction;
import utility.Pair;
import utility.Side;
import utility.TileType;

public class Novice extends Entity implements Attackable, Moveable {

	private static final int DEFAULT_MAX_HP = 200;
	private static final int DEFAULT_ATK = 50;
	private static final int DEFAULT_DEF = 20;
	private static final double DEFAULT_ACC = 100.00;
	private static final double DEFAULT_EVA = 10.00;
	private static final double DEFAULT_CRI_RATE = 20;
	public static final int[] EXP_RATE = { 0, 100, 200, 350, 550, 750, 1000, 1300, 1650, 2100, 2500 };
	private static final Font NAMEFONT = Font.loadFont(ClassLoader.getSystemResourceAsStream("font/ferrum.otf"), 15);

	protected Timeline timer;
	protected int lv;
	protected int exp;
	protected Canvas nameCanvas;
	public int statusPoint;

	private static final int maxAttackImage = 5;
	private static final Image[] attackImages = new Image[maxAttackImage];
	static {
		for (int i = 1; i <= maxAttackImage; ++i) {
			attackImages[i - 1] = new Image("images/effect/hit5 (" + i + ").png");
		}
	}
	private static int currentAttackAnimation = 7;

	private static int currentAnimation = 0;

	private static final Image[] characterImages = new Image[5];
	static {
		characterImages[0] = new Image("images/hero/Novice_dlru_t.png");
		for (int i = 1; i <= 4; i++) {
			characterImages[i] = new WritableImage(characterImages[0].getPixelReader(), 0, (i - 1) * 48, 46, 48);
		}
	}

	protected Canvas levelUpCanvas = new Canvas(GameScene.WIDTH*GameScene.TILE_SIZE,GameScene.HEIGHT*GameScene.TILE_SIZE);
	private static final int maxLevelUpImage = 21;
	private static final Image[] levelUpImages = new Image[maxLevelUpImage];
	static {
		for (int i = 1; i <= maxLevelUpImage; i++) {
			levelUpImages[i - 1] = new Image("images/effect/levelup (" + i + ").png");
		}
	}
	private static int currentLevelUpAnimation = 0;
	
	
	private static final int maxhealingImage = 20;
	private static final Image[] healingImages = new Image[maxhealingImage];
	static {
		for (int i = 1; i <= maxhealingImage; i++) {
			healingImages[i - 1] = new Image("images/effect/heal/healing (" + i + ").png");
		}
	}
	private static int currentHealingAnimation = 0;
	

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
		this.nameCanvas = new Canvas(GameScene.WIDTH * GameScene.TILE_SIZE, GameScene.HEIGHT * GameScene.TILE_SIZE);
		this.statusPoint = 0;
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
		this.nameCanvas = new Canvas(GameScene.WIDTH * GameScene.TILE_SIZE, GameScene.HEIGHT * GameScene.TILE_SIZE);
		this.statusPoint = 0;
		// don't forget to initial picture size and first time position
	}

	public void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, GameScene.WIDTH * GameScene.TILE_SIZE, GameScene.HEIGHT * GameScene.TILE_SIZE);

		if (faceDirection == Direction.RIGHT) {
			gc.drawImage(characterImages[3], position.first * GameScene.TILE_SIZE, position.second * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		} else if (faceDirection == Direction.LEFT) {
			gc.drawImage(characterImages[2], position.first * GameScene.TILE_SIZE, position.second * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		} else if (faceDirection == Direction.DOWN) {
			gc.drawImage(characterImages[1], position.first * GameScene.TILE_SIZE, position.second * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		} else if (faceDirection == Direction.UP) {
			gc.drawImage(characterImages[4], position.first * GameScene.TILE_SIZE, position.second * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		}

		drawDirection();
		// System.out.println(position.first+" "+position.second);
		if (isDead)
			return;
		GameScene.statusBarGroup.getChildren().remove(hpBar.getCanvas());
		hpBar = new HpBar(this);
		hpBar.draw();
		GameScene.statusBarGroup.getChildren().add(hpBar.getCanvas());
		drawNameAndLv();
	}

	public void drawNameAndLv() {
		GraphicsContext gc = nameCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, GameScene.WIDTH * GameScene.TILE_SIZE, GameScene.HEIGHT * GameScene.TILE_SIZE);
		gc.setFill(Color.WHITE);
		String msg = name + "  (lv  " + lv + ")";
		final Text text = new Text(msg);
		text.setFont(NAMEFONT);
		final double width = text.getLayoutBounds().getWidth();
		final double height = text.getLayoutBounds().getHeight();
		gc.fillRect((position.first + 0.5) * GameScene.TILE_SIZE - (width / 2 + 5),
				(position.second) * GameScene.TILE_SIZE - (height + 15), width + 10, height + 10);
		gc.setTextBaseline(VPos.BOTTOM);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(NAMEFONT);
		gc.setFill(Color.BLUE);
		gc.fillText(name + "  (lv  " + lv + ")", (position.first + 0.5) * GameScene.TILE_SIZE,
				position.second * GameScene.TILE_SIZE - 10);

	}

	protected void drawDirection() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);
		if (faceDirection == Direction.RIGHT) {
			gc.strokeRect((position.first + 1) * GameScene.TILE_SIZE, (position.second) * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		} else if (faceDirection == Direction.LEFT) {
			gc.strokeRect((position.first - 1) * GameScene.TILE_SIZE, (position.second) * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		} else if (faceDirection == Direction.DOWN) {
			gc.strokeRect((position.first) * GameScene.TILE_SIZE, (position.second + 1) * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		} else if (faceDirection == Direction.UP) {
			gc.strokeRect((position.first) * GameScene.TILE_SIZE, (position.second - 1) * GameScene.TILE_SIZE,
					picWidth * GameScene.TILE_SIZE, picHeight * GameScene.TILE_SIZE);
		}

	}

	public void move(double moveX, double moveY) {
		GameScene.statusBarGroup.getChildren().clear();
		isMoveFinished = false;
		GameScene.setBoard(position, TileType.NONE, null);
		GameScene.setBoard(position.add(new Pair(moveX, moveY)), TileType.HERO, this);

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
		if(entity.getIsDead()) {
			try {
				throw new AttackDeadEntityException();
			} catch (AttackDeadEntityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
		}
		isMoveFinished = false;
		isAttackFinished = false;
		entity.setMoveFinished(false);
		
		currentAttackAnimation = 0;
		Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(150), attack -> {
			drawAttackAnimation();
			currentAttackAnimation++;
		}));
		attackTimeline.setCycleCount(6);
		attackTimeline.play();

		timer = new Timeline(new KeyFrame(new Duration(750), e -> {
			double atkDmg = calculateDamage(entity);
			entity.takeDamage(atkDmg);
			entity.draw();
			if (entity.getIsDead()) {
				exp += ((Monster) entity).getExpGain();
				checkLevelUp();
				StatusBar.drawExpBar();
				// System.out.println(lv+" "+exp);
			}
		}));
		timer.setCycleCount(1);
		timer.play();
		timer.setOnFinished(e -> {
			Timeline wait = new Timeline(new KeyFrame(Duration.millis(2000), f -> {
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
		GraphicsContext gc = this.atkCanvas.getGraphicsContext2D();
		if (currentAttackAnimation == 0) {
			attackDirection = faceDirection;
		}
		double playerX = position.first;
		double playerY = position.second;
		int tileSize = GameScene.TILE_SIZE;

		gc.clearRect(0, 0, GameScene.WIDTH * tileSize, GameScene.HEIGHT * tileSize);

		if (currentAttackAnimation <= maxAttackImage - 1) {
			if (attackDirection == Direction.RIGHT) {
				gc.drawImage(attackImages[currentAttackAnimation], (playerX - 0.15 + 1) * tileSize,
						(playerY - 0.15) * tileSize, (picWidth + 0.3) * tileSize, (picHeight + 0.3) * tileSize);
			} else if (attackDirection == Direction.LEFT) {
				gc.drawImage(attackImages[currentAttackAnimation], (playerX - 0.15 - 1) * tileSize,
						(playerY - 0.15) * tileSize, (picWidth + 0.3) * tileSize, (picHeight + 0.3) * tileSize);
			} else if (attackDirection == Direction.DOWN) {
				gc.drawImage(attackImages[currentAttackAnimation], (playerX - 0.15) * tileSize,
						(playerY - 0.15 + 1) * tileSize, (picWidth + 0.3) * tileSize, (picHeight + 0.3) * tileSize);
			} else if (attackDirection == Direction.UP) {
				gc.drawImage(attackImages[currentAttackAnimation], (playerX - 0.15) * tileSize,
						(playerY - 0.15 - 1) * tileSize, (picWidth + 0.3) * tileSize, (picHeight + 0.3) * tileSize);
			}
		}

	}

	public void normalAttack() {
		if ((int) position.first + 1 < GameScene.WIDTH) {
			if (GameScene.getBoard()[(int) position.first + 1][(int) (position.second)].getTileType() == TileType.MONSTER
					&& faceDirection == Direction.RIGHT) {
				attack(GameScene.getBoard()[(int) position.first + 1][(int) position.second].getEntity());
			}
		}

		if ((int) position.first - 1 > 0) {
			if (GameScene.getBoard()[(int) position.first - 1][(int) (position.second)].getTileType() == TileType.MONSTER
					&& faceDirection == Direction.LEFT) {
				attack(GameScene.getBoard()[(int) position.first - 1][(int) (position.second)].getEntity());
			}
		}

		if ((int) position.second - 1 > 0) {
			if (GameScene.getBoard()[(int) position.first][(int) (position.second) - 1].getTileType() == TileType.MONSTER
					&& faceDirection == Direction.UP) {
				attack(GameScene.getBoard()[(int) position.first][(int) (position.second) - 1].getEntity());
			}
		}

		if ((int) position.second + 1 < GameScene.HEIGHT) {
			if (GameScene.getBoard()[(int) GameScene.getHero().getPosition().first][(int) (position.second) + 1]
					.getTileType() == TileType.MONSTER && faceDirection == Direction.DOWN) {
				attack(GameScene.getBoard()[(int) GameScene.getHero().getPosition().first][(int) (position.second) + 1]
						.getEntity());
			}
		}
	}

	protected void checkLevelUp() {
		while (EXP_RATE[lv] <= exp) {
			lv++;
			statusPoint += 2;
			drawNameAndLv();
			hp=maxHp;
			currentLevelUpAnimation = 0;
			Timeline attackTimeline = new Timeline(new KeyFrame(Duration.millis(45*1.5), attack -> {
				drawLevelUpAnimation();
				currentLevelUpAnimation++;
			}));
			attackTimeline.setCycleCount(22);
			attackTimeline.play();

		}
	if(lv>=5&& !(this instanceof Fighter)) {
		GameScene.getInstance().classChange();
	
		}
	
	}

	public void drawLevelUpAnimation() {
		GraphicsContext gc = this.levelUpCanvas.getGraphicsContext2D();
		double playerX = position.first;
		double playerY = position.second;
		int tileSize = GameScene.TILE_SIZE;

		gc.clearRect(0, 0, GameScene.WIDTH * tileSize, GameScene.HEIGHT * tileSize);

		if (currentLevelUpAnimation <= maxLevelUpImage - 1) {
			gc.drawImage(levelUpImages[currentLevelUpAnimation], 
					(playerX - 1.5) * tileSize,
					(playerY - 1.5) * tileSize, 
					(picWidth + 3) * tileSize, 
					(picHeight + 2) * tileSize);
		}
	}
	public void cleanLevelUpCanvas() {
		int tileSize = GameScene.TILE_SIZE;
		GraphicsContext gc = this.levelUpCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, GameScene.WIDTH * tileSize, GameScene.HEIGHT * tileSize);
	}

	public double calculateDamage(Entity entity) {
		Random rn = new Random();
		int atkSuccess = rn.nextInt(100);
		int criSuccess = rn.nextInt(100);
		// System.out.println(atkSuccess+" "+criSuccess+" "+(this.atk -
		// entity.getDef()));
		if (this.acc - entity.getEva() > atkSuccess) {
			if (this.atk > entity.getDef()) {
				if (this.dex > criSuccess)
					return 2 * (this.atk - entity.getDef());
				else
					return this.atk - entity.getDef();
			} else
				return 1;
		} else
			return 0;
	}
	
	public void drawHealingAnimation() {
		GraphicsContext gc = this.levelUpCanvas.getGraphicsContext2D();
		double playerX = position.first;
		double playerY = position.second;
		int tileSize = GameScene.TILE_SIZE;

		gc.clearRect(0, 0, GameScene.WIDTH * tileSize, GameScene.HEIGHT * tileSize);

		if (currentHealingAnimation <= maxhealingImage - 1) {
			gc.drawImage(healingImages[currentHealingAnimation], 
					(playerX - 1.5) * tileSize,
					(playerY - 1.5) * tileSize, 
					(picWidth + 3) * tileSize, 
					(picHeight + 2) * tileSize);
		}

	}

	public void takeDamage(double dmg) {
		if (hp <= dmg) {
			die();
		} else {
			hp -= dmg;
		}
	}

	public void updateAnimation() {
		// TODO Auto-generated method stub
		// draw();
		// currentAttackAnimation++;
		// currentAttackAnimation %= 5;
		// drawAttackAnimation();
	}

	public Canvas getNameCanvas() {
		return nameCanvas;
	}

	public Canvas getLevelUpCanvas() {
		return levelUpCanvas;
	}

	public int getExp() {
		return exp;
	}

	public int getLv() {
		return lv;
	}
	
	@Override
	public void die() {
		GameScene.getEntityGroup().getChildren().remove(canvas);
		GameScene.getNamePane().getChildren().remove(nameCanvas);
		isDead = true;
		hp = 0;
		hpBar.die();
	}
	
	public void heal() {
		Timeline healTimeline = new Timeline(new KeyFrame(Duration.millis(800), attack -> {
			hp+=maxHp*0.05;
			if(hp>maxHp) hp=maxHp;
		}));
		healTimeline.setCycleCount(5);
		healTimeline.play();

		currentHealingAnimation=0;
		Timeline animationTimeline = new Timeline(new KeyFrame(Duration.millis(80), attack -> {
			drawHealingAnimation();
			currentHealingAnimation++;
			currentHealingAnimation%=20;
			System.out.println("do");
		}));
		animationTimeline.setOnFinished(finish -> {
			cleanLevelUpCanvas();
		});
		animationTimeline.setCycleCount(50);
		animationTimeline.play();
	}

}