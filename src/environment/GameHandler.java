package environment;

import java.util.HashSet;

import entity.Entity;
import entity.hero.Fighter;
import entity.hero.Novice;
import entity.monster.Monster;
import environment.window.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import main.Main;
import utility.ActionResult;
import utility.ActionType;
import utility.Direction;
import utility.Pair;
import utility.Tile;
import utility.TileType;

public class GameHandler {
	// set all event
	private static HashSet<KeyCode> activeKey = new HashSet<KeyCode>();

	private static int tick = 0;
	private static boolean isPaused;

	private static Timeline gameTimer;

	public static void keyPressed(KeyEvent event) {
		// if (activeKey.contains(event.getCode()))
		// return;
		if (event.getCode() == KeyCode.ENTER) {
			activeKey.add(KeyCode.ENTER);
		}

		if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
			activeKey.add(KeyCode.UP);
		}

		if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
			activeKey.add(KeyCode.DOWN);
		}

		if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
			activeKey.add(KeyCode.LEFT);
		}

		if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
			activeKey.add(KeyCode.RIGHT);
		}
		if (event.getCode() == KeyCode.Z) {
			activeKey.add(KeyCode.Z);

		}
		if (event.getCode() == KeyCode.X) {
			activeKey.add(KeyCode.X);

		}

	}

	public static void keyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			activeKey.remove(KeyCode.ENTER);
		}

		if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
			activeKey.remove(KeyCode.UP);
		}

		if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
			activeKey.remove(KeyCode.DOWN);
		}

		if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
			activeKey.remove(KeyCode.LEFT);
		}

		if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
			activeKey.remove(KeyCode.RIGHT);
		}
		if (event.getCode() == KeyCode.Z) {
			activeKey.remove(KeyCode.Z);

		}
		if (event.getCode() == KeyCode.X) {
			activeKey.remove(KeyCode.X);

		}

	}

	private static void movePlayer() {

		
		Pair playerPosition = GameScene.getInstance().getHeroPosition();

		Direction faceDirection = GameScene.getInstance().getHero().getFaceDirection();

		if (activeKey.contains(KeyCode.UP)) {
			GameScene.getInstance().getHero().setFaceDirection(Direction.UP);
			if (GameScene.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.second > 0 && !GameScene.getInstance().getBoard(playerPosition.add(new Pair(0, -1))).hasEntity()
						&& faceDirection == Direction.UP) {
					GameScene.getInstance().getHero().move(0, -1);
				}
			}
			GameScene.getInstance().getHero().draw();
			activeKey.remove(KeyCode.UP);
		}

		if (activeKey.contains(KeyCode.DOWN)) {
			GameScene.getInstance().getHero().setFaceDirection(Direction.DOWN);
			if (GameScene.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.second <= GameScene.getInstance().HEIGHT - 2
						&& !GameScene.getInstance().getBoard(playerPosition.add(new Pair(0, 1))).hasEntity()
						&& faceDirection == Direction.DOWN) {
					GameScene.getInstance().getHero().move(0, 1);
				}
			}
			GameScene.getInstance().getHero().draw();
			activeKey.remove(KeyCode.DOWN);
		}

		if (activeKey.contains(KeyCode.LEFT)) {
			GameScene.getInstance().getHero().setFaceDirection(Direction.LEFT);
			if (GameScene.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.first > 0 && !GameScene.getInstance().getBoard(playerPosition.add(new Pair(-1, 0))).hasEntity()
						&& faceDirection == Direction.LEFT) {
					GameScene.getInstance().getHero().move(-1, 0);
				}
			}
			GameScene.getInstance().getHero().draw();
			activeKey.remove(KeyCode.LEFT);
		}

		if (activeKey.contains(KeyCode.RIGHT)) {
			GameScene.getInstance().getHero().setFaceDirection(Direction.RIGHT);
			if (GameScene.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.first <= GameScene.getInstance().WIDTH - 2
						&& !GameScene.getInstance().getBoard(playerPosition.add(new Pair(1, 0))).hasEntity()
						&& faceDirection == Direction.RIGHT) {
					GameScene.getInstance().getHero().move(1, 0);
				}
			}
			GameScene.getInstance().getHero().draw();
			activeKey.remove(KeyCode.RIGHT);
		}

	}

	public static void playerAttack() {

		if (activeKey.contains(KeyCode.Z) && GameScene.getInstance().getHero().isMoveFinished() == true
				&& GameScene.getInstance().getHero().isAttackFinished()) {
			GameScene.getInstance().getHero().normalAttack();
			activeKey.remove(KeyCode.Z);

		}

		else if (activeKey.contains(KeyCode.X) && GameScene.getInstance().getHero().isMoveFinished() == true
				&& GameScene.getInstance().getHero().isAttackFinished()) {
			((Fighter) GameScene.getInstance().getHero()).groundSmash();
		}

	}

	public static void checkStatus() {
		for (Monster entity : GameScene.getInstance().getMonsterList()) {
			if (entity.getIsDead()) {
				for (int i = 0; i < entity.getPicWidth(); i++) {
					for (int j = 0; j < entity.getPicHeight(); j++) {
						GameScene.getInstance().setBoard(entity.getPosition().add(new Pair(i, j)), TileType.NONE, null);
					}
				}
			}
		}
		GameScene.getInstance().getMonsterList().removeIf(m -> m.getIsDead());
		if (GameScene.getInstance().getHero().getIsDead()) {
			GameScene.getInstance().setBoard(GameScene.getInstance().getHeroPosition(), TileType.NONE, null);
		}
	}

	public static void moveMonster() {
		if (tick % (Main.FPS * 2) != 0)
			return;
		for (Monster monster : GameScene.getInstance().getMonsterList()) {
			// System.out.println(monster.isMoveFinished());
			if (Math.abs(GameScene.getInstance().getHeroPosition().first - monster.getPosition().first) <= Monster.VISIBLE_RANGE
					&& Math.abs(GameScene.getInstance().getHeroPosition().second - monster.getPosition().second) <= Monster.VISIBLE_RANGE) {
				monster.moveToPlayer();
			} else
				monster.randomMove();
		}

	}

	public static void checkPause() {
		if (activeKey.contains(KeyCode.ENTER)) {
			activeKey.remove(KeyCode.ENTER);
//			if(!isPaused) {
//				isPaused=!isPaused;
				SceneManager.openPausedMenu();
//			}
//			else {
//				isPaused=!isPaused;
//				SceneManager.closePausedMenu();
//			}
			
			
		}
	}


	public static void monsterAttack() {
		if (tick % (Main.FPS * 2) != 0)
			return;
		for (Monster monster : GameScene.getInstance().getMonsterList()) {
			if (monster.getFaceDirection() == Direction.UP && (int) monster.getPosition().second - 1 > 0) {
				for (int i = 0; i < monster.getPicWidth(); i++) {
					if (GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(i, -1))).getTileType() == TileType.HERO) {
						monster.attack(GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(i, -1))).getEntity());
					}
				}

			}
			if (monster.getFaceDirection() == Direction.DOWN && (int) monster.getPosition().second + 1 < GameScene.getInstance().HEIGHT) {
				for (int i = 0; i < monster.getPicWidth(); i++) {
					if (GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight())))
							.getTileType() == TileType.HERO) {
						monster.attack(GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight())))
								.getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.LEFT && (int) monster.getPosition().first - 1 > 0) {
				for (int i = 0; i < monster.getPicHeight(); i++) {
					if (GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(-1, i))).getTileType() == TileType.HERO) {
						monster.attack(GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(-1, i))).getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.RIGHT && (int) monster.getPosition().first + 1 < GameScene.getInstance().WIDTH) {
				for (int i = 0; i < monster.getPicHeight(); i++) {
					if (GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i)))
							.getTileType() == TileType.HERO) {
						monster.attack(GameScene.getInstance().getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i)))
								.getEntity());
					}
				}
			}
		}
	}

	public static void update() {
		if (!isPaused) {

			movePlayer();
			playerAttack();
			moveMonster();
			monsterAttack();
			checkStatus();
			animateAll();
			tick++;
		}
		checkPause();

	}

	public static void animateAll() {
		if (tick % (Main.FPS / 12) == 0) {
			for (Monster Monster : GameScene.getInstance().getMonsterList()) {
				Monster.updateAnimation();
			}
		}
	}

	public static boolean isPaused() {
		return isPaused;
	}

	public static void setPaused(boolean isPause) {
		isPaused = isPause;
	}

	public static void startGame() {
		// TODO Auto-generated method stub
		activeKey.clear();
		GameHandler.setPaused(false);
		gameTimer = new Timeline(new KeyFrame(new Duration(1000 / Main.FPS), e -> {
			GameHandler.update();
		}));
		gameTimer.setCycleCount(Animation.INDEFINITE);
		gameTimer.play();
	}

	public static void stopTimer() {
		// TODO Auto-generated method stub
		gameTimer.stop();
	}

	public static void playTimer() {
		// TODO Auto-generated method stub
		gameTimer.play();
	}

}
