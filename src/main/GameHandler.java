package main;

import java.util.HashSet;

import entity.Entity;
import entity.hero.Fighter;
import entity.hero.Novice;
import entity.monster.Monster;
import environment.Map;
import environment.window.SceneManager;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

		
		Pair playerPosition = Map.getInstance().getHeroPosition();

		Direction faceDirection = Map.getInstance().getHero().getFaceDirection();

		if (activeKey.contains(KeyCode.UP)) {
			Map.getInstance().getHero().setFaceDirection(Direction.UP);
			if (Map.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.second > 0 && !Map.getInstance().getBoard(playerPosition.add(new Pair(0, -1))).hasEntity()
						&& faceDirection == Direction.UP) {
					Map.getInstance().getHero().move(0, -1);
				}
			}
			Map.getInstance().getHero().draw();
			activeKey.remove(KeyCode.UP);
		}

		if (activeKey.contains(KeyCode.DOWN)) {
			Map.getInstance().getHero().setFaceDirection(Direction.DOWN);
			if (Map.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.second <= Map.getInstance().HEIGHT - 2
						&& !Map.getInstance().getBoard(playerPosition.add(new Pair(0, 1))).hasEntity()
						&& faceDirection == Direction.DOWN) {
					Map.getInstance().getHero().move(0, 1);
				}
			}
			Map.getInstance().getHero().draw();
			activeKey.remove(KeyCode.DOWN);
		}

		if (activeKey.contains(KeyCode.LEFT)) {
			Map.getInstance().getHero().setFaceDirection(Direction.LEFT);
			if (Map.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.first > 0 && !Map.getInstance().getBoard(playerPosition.add(new Pair(-1, 0))).hasEntity()
						&& faceDirection == Direction.LEFT) {
					Map.getInstance().getHero().move(-1, 0);
				}
			}
			Map.getInstance().getHero().draw();
			activeKey.remove(KeyCode.LEFT);
		}

		if (activeKey.contains(KeyCode.RIGHT)) {
			Map.getInstance().getHero().setFaceDirection(Direction.RIGHT);
			if (Map.getInstance().getHero().isMoveFinished()) {
				if (playerPosition.first <= Map.getInstance().WIDTH - 2
						&& !Map.getInstance().getBoard(playerPosition.add(new Pair(1, 0))).hasEntity()
						&& faceDirection == Direction.RIGHT) {
					Map.getInstance().getHero().move(1, 0);
				}
			}
			Map.getInstance().getHero().draw();
			activeKey.remove(KeyCode.RIGHT);
		}

	}

	public static void playerAttack() {

		if (activeKey.contains(KeyCode.Z) && Map.getInstance().getHero().isMoveFinished() == true
				&& Map.getInstance().getHero().isAttackFinished()) {
			Map.getInstance().getHero().normalAttack();
			activeKey.remove(KeyCode.Z);

		}

		else if (activeKey.contains(KeyCode.X) && Map.getInstance().getHero().isMoveFinished() == true
				&& Map.getInstance().getHero().isAttackFinished()) {
			((Fighter) Map.getInstance().getHero()).groundSmash();
		}

	}

	public static void checkStatus() {
		for (Monster entity : Map.getInstance().getMonsterList()) {
			if (entity.getIsDead()) {
				for (int i = 0; i < entity.getPicWidth(); i++) {
					for (int j = 0; j < entity.getPicHeight(); j++) {
						Map.getInstance().setBoard(entity.getPosition().add(new Pair(i, j)), TileType.NONE, null);
					}
				}
			}
		}
		Map.getInstance().getMonsterList().removeIf(m -> m.getIsDead());
		if (Map.getInstance().getHero().getIsDead()) {
			Map.getInstance().setBoard(Map.getInstance().getHeroPosition(), TileType.NONE, null);
		}
	}

	public static void moveMonster() {
		if (tick % (Main.FPS * 2) != 0)
			return;
		for (Monster monster : Map.getInstance().getMonsterList()) {
			// System.out.println(monster.isMoveFinished());
			if (Math.abs(Map.getInstance().getHeroPosition().first - monster.getPosition().first) <= Monster.VISIBLE_RANGE
					&& Math.abs(Map.getInstance().getHeroPosition().second - monster.getPosition().second) <= Monster.VISIBLE_RANGE) {
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

	public void setOpacity(int x) {

	}

	public static void monsterAttack() {
		if (tick % (Main.FPS * 2) != 0)
			return;
		for (Monster monster : Map.getInstance().getMonsterList()) {
			if (monster.getFaceDirection() == Direction.UP && (int) monster.getPosition().second - 1 > 0) {
				for (int i = 0; i < monster.getPicWidth(); i++) {
					if (Map.getInstance().getBoard(monster.getPosition().add(new Pair(i, -1))).getTileType() == TileType.HERO) {
						monster.attack(Map.getInstance().getBoard(monster.getPosition().add(new Pair(i, -1))).getEntity());
					}
				}

			}
			if (monster.getFaceDirection() == Direction.DOWN && (int) monster.getPosition().second + 1 < Map.getInstance().HEIGHT) {
				for (int i = 0; i < monster.getPicWidth(); i++) {
					if (Map.getInstance().getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight())))
							.getTileType() == TileType.HERO) {
						monster.attack(Map.getInstance().getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight())))
								.getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.LEFT && (int) monster.getPosition().first - 1 > 0) {
				for (int i = 0; i < monster.getPicHeight(); i++) {
					if (Map.getInstance().getBoard(monster.getPosition().add(new Pair(-1, i))).getTileType() == TileType.HERO) {
						monster.attack(Map.getInstance().getBoard(monster.getPosition().add(new Pair(-1, i))).getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.RIGHT && (int) monster.getPosition().first + 1 < Map.getInstance().WIDTH) {
				for (int i = 0; i < monster.getPicHeight(); i++) {
					if (Map.getInstance().getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i)))
							.getTileType() == TileType.HERO) {
						monster.attack(Map.getInstance().getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i)))
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
			for (Monster Monster : Map.getInstance().getMonsterList()) {
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

}
