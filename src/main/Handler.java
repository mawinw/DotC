package main;

import java.util.HashSet;

import entity.Entity;
import entity.monster.Monster;
import environment.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utility.ActionResult;
import utility.ActionType;
import utility.Direction;
import utility.Pair;
import utility.Tile;
import utility.TileType;

public class Handler {
	// set all event
	private static HashSet<KeyCode> activeKey = new HashSet<KeyCode>();

	private static int tick = 0;
	private static boolean isPaused;

	public static void keyPressed(KeyEvent event) {
		// if (activeKey.contains(event.getCode()))
		// return;
		if (event.getCode() == KeyCode.SPACE) {
			activeKey.add(KeyCode.SPACE);
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
		if (event.getCode() == KeyCode.SPACE) {
			activeKey.remove(KeyCode.SPACE);
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

		Pair playerPosition = Map.getHero().getPosition();

		Direction faceDirection = Map.getHero().getFaceDirection();

		if (activeKey.contains(KeyCode.UP)) {
			Map.getHero().setFaceDirection(Direction.UP);
			if (Map.getHero().isMoveFinished()) {
				if (playerPosition.second > 0 && !Map.getBoard(playerPosition.add(new Pair(0, -1))).hasEntity()
						&& faceDirection == Direction.UP) {
					Map.getHero().move(0, -1);
				}
			}
			Map.getHero().draw();
			activeKey.remove(KeyCode.UP);
		}

		if (activeKey.contains(KeyCode.DOWN)) {
			Map.getHero().setFaceDirection(Direction.DOWN);
			if (Map.getHero().isMoveFinished()) {
				if (playerPosition.second <= Map.HEIGHT - 2
						&& !Map.getBoard(playerPosition.add(new Pair(0, 1))).hasEntity()
						&& faceDirection == Direction.DOWN) {
					Map.getHero().move(0, 1);
				}
			}
			Map.getHero().draw();
			activeKey.remove(KeyCode.DOWN);
		}

		if (activeKey.contains(KeyCode.LEFT)) {
			Map.getHero().setFaceDirection(Direction.LEFT);
			if (Map.getHero().isMoveFinished()) {
				if (playerPosition.first > 0 && !Map.getBoard(playerPosition.add(new Pair(-1, 0))).hasEntity()
						&& faceDirection == Direction.LEFT) {
					Map.getHero().move(-1, 0);
				}
			}
			Map.getHero().draw();
			activeKey.remove(KeyCode.LEFT);
		}

		if (activeKey.contains(KeyCode.RIGHT)) {
			Map.getHero().setFaceDirection(Direction.RIGHT);
			if (Map.getHero().isMoveFinished()) {
				if (playerPosition.first <= Map.WIDTH - 2
						&& !Map.getBoard(playerPosition.add(new Pair(1, 0))).hasEntity()
						&& faceDirection == Direction.RIGHT) {
					Map.getHero().move(1, 0);
				}
			}
			Map.getHero().draw();
			activeKey.remove(KeyCode.RIGHT);
		}

	}

	public static void playerAttack() {

		if (activeKey.contains(KeyCode.Z) && Map.getHero().isMoveFinished() == true
				&& Map.getHero().isAttackFinished()) {
			Map.getHero().normalAttack();
			activeKey.remove(KeyCode.Z);

		}

		else if (activeKey.contains(KeyCode.X) && Map.getHero().isMoveFinished() == true
				&& Map.getHero().isAttackFinished()) {
			Map.getHero().groundSmash();
		}

	}

	public static void checkStatus() {
		for (Monster entity : Map.getMonsterList()) {
			if (entity.getIsDead()) {
				for (int i = 0; i < entity.getPicWidth(); i++) {
					for (int j = 0; j < entity.getPicHeight(); j++) {
						Map.setBoard(entity.getPosition().add(new Pair(i, j)), TileType.NONE, null);
					}
				}
			}
		}
		if (Map.getHero().getIsDead()) {
			Map.setBoard(Map.getHeroPosition(), TileType.NONE, null);
		}
	}

	public static void moveMonster() {
		if (tick % (Main.FPS * 2) != 0)
			return;
		for (Monster monster : Map.getMonsterList()) {
			//System.out.println(monster.isMoveFinished());
				if (Math.abs(Map.getHeroPosition().first - monster.getPosition().first) <= Monster.VISIBLE_RANGE && Math
						.abs(Map.getHeroPosition().second - monster.getPosition().second) <= Monster.VISIBLE_RANGE) {
					monster.moveToPlayer();
				} else
					monster.randomMove();
			
		}

	}

	public static void checkPause() {
		if (activeKey.contains(KeyCode.SPACE)) {
			isPaused = !isPaused;
			activeKey.remove(KeyCode.SPACE);
		}
	}

	public void setOpacity(int x) {

	}

	public static void monsterAttack() {
		if (tick % (Main.FPS * 2) != 0)
			return;
		for (Monster monster : Map.getMonsterList()) {
			if (monster.getFaceDirection() == Direction.UP && (int) monster.getPosition().second - 1 > 0) {
				for (int i = 0; i < monster.getPicWidth(); i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(i, -1))).getTileType() == TileType.HERO) {
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(i, -1))).getEntity());
					}
				}

			}
			if (monster.getFaceDirection() == Direction.DOWN && (int) monster.getPosition().second + 1 < Map.HEIGHT) {
				for (int i = 0; i < monster.getPicWidth(); i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight())))
							.getTileType() == TileType.HERO) {
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight())))
								.getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.LEFT && (int) monster.getPosition().first - 1 > 0) {
				for (int i = 0; i < monster.getPicHeight(); i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(-1, i))).getTileType() == TileType.HERO) {
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(-1, i))).getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.RIGHT && (int) monster.getPosition().first + 1 < Map.WIDTH) {
				for (int i = 0; i < monster.getPicHeight(); i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i)))
							.getTileType() == TileType.HERO) {
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i)))
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
		if(tick%(Main.FPS/10)==0) {
			for(Monster Monster: Map.getMonsterList()) {
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
