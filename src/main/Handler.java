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

	}

	private static void movePlayer() {

		Pair playerPosition = Map.getNovice().getPosition();

		Direction faceDirection = Map.getNovice().getFaceDirection();

		if (Map.getNovice().isMoveFinished()) {

			if (activeKey.contains(KeyCode.UP)) {
				// System.out.println(!Map.getBoard(playerPosition.add(new
				// Pair(0,-1))).hasEntity());
				if (playerPosition.second > 0 && !Map.getBoard(playerPosition.add(new Pair(0, -1))).hasEntity()
						&& faceDirection == Direction.UP) {
					// System.out.println(playerPosition.first+" "+playerPosition.second);
					Map.getNovice().move(0, -1);
				}

				Map.getNovice().setFaceDirection(Direction.UP);
				activeKey.remove(KeyCode.UP);
				Map.getNovice().draw();
			}

			if (activeKey.contains(KeyCode.DOWN)) {
				if (playerPosition.second <= Map.HEIGHT - 2
						&& !Map.getBoard(playerPosition.add(new Pair(0, 1))).hasEntity()
						&& faceDirection == Direction.DOWN) {
					Map.getNovice().move(0, 1);
				}
				Map.getNovice().setFaceDirection(Direction.DOWN);
				Map.getNovice().draw();
				activeKey.remove(KeyCode.DOWN);
			}

			if (activeKey.contains(KeyCode.LEFT)) {
				if (playerPosition.first > 0 && !Map.getBoard(playerPosition.add(new Pair(-1, 0))).hasEntity()
						&& faceDirection == Direction.LEFT) {
					Map.getNovice().move(-1, 0);
				}
				Map.getNovice().setFaceDirection(Direction.LEFT);
				Map.getNovice().draw();
				activeKey.remove(KeyCode.LEFT);
			}

			if (activeKey.contains(KeyCode.RIGHT)) {
				if (playerPosition.first <= Map.WIDTH - 2
						&& !Map.getBoard(playerPosition.add(new Pair(1, 0))).hasEntity()
						&& faceDirection == Direction.RIGHT) {
					Map.getNovice().move(1, 0);
				}
				Map.getNovice().setFaceDirection(Direction.RIGHT);
				Map.getNovice().draw();
				activeKey.remove(KeyCode.RIGHT);
			}
			// System.out.println(Map.getBoard()[(int)
			// Map.getNovice().getPosition().first][(int)
			// Map.getNovice().getPosition().second].getTileType());

		}

		// if run then goto out of map

	}

	public static void playerAttack() {
		if (activeKey.contains(KeyCode.Z) && Map.getNovice().isMoveFinished() == true&&Map.getNovice().isAttackFinished()) {
			if ((int) Map.getNovice().getPosition().first + 1 < Map.WIDTH) {
			//	System.out.println(Map.getBoard()[(int) Map.getNovice().getPosition().first
			//			+ 1][(int) (Map.getNovice().getPosition().second)].getTileType());
				if (Map.getBoard()[(int) Map.getNovice().getPosition().first
						+ 1][(int) (Map.getNovice().getPosition().second)].getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.RIGHT) {

					Map.getNovice().attack(Map.getBoard()[(int) Map.getNovice().getPosition().first
							+ 1][(int) (Map.getNovice().getPosition().second)].getEntity());
				}
			}

			if ((int) Map.getNovice().getPosition().first - 1 > 0) {
				if (Map.getBoard()[(int) Map.getNovice().getPosition().first
						- 1][(int) (Map.getNovice().getPosition().second)].getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.LEFT) {

					Map.getNovice().attack(Map.getBoard()[(int) Map.getNovice().getPosition().first
							- 1][(int) (Map.getNovice().getPosition().second)].getEntity());
				}
			}

			if ((int) Map.getNovice().getPosition().second - 1 > 0) {
				if (Map.getBoard()[(int) Map.getNovice()
						.getPosition().first][(int) (Map.getNovice().getPosition().second) - 1]
								.getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.UP) {

					Map.getNovice()
							.attack(Map.getBoard()[(int) Map.getNovice()
									.getPosition().first][(int) (Map.getNovice().getPosition().second) - 1]
											.getEntity());
				}
			}

			if ((int) Map.getNovice().getPosition().second + 1 < Map.HEIGHT) {
				if (Map.getBoard()[(int) Map.getNovice()
						.getPosition().first][(int) (Map.getNovice().getPosition().second) + 1]
								.getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.DOWN) {

					Map.getNovice()
							.attack(Map.getBoard()[(int) Map.getNovice()
									.getPosition().first][(int) (Map.getNovice().getPosition().second) + 1]
											.getEntity());
				}
			}

			// System.out.println((int) Map.getNovice().getPosition().first + " " +
			// (int)Map.getNovice().getPosition().second);
			activeKey.remove(KeyCode.Z);

		}

	}

	public static void checkStatus() {
		for (Monster entity:Map.getMonsterList()) {
					if (entity.getIsDead()) {
						Map.setBoard(entity.getPosition(), TileType.NONE, null);
					}
		}
		if(Map.getNovice().getIsDead()) {
			Map.setBoard(Map.getHeroPosition(), TileType.NONE, null);
		}
	}

	public static void moveMonster() {
		if (tick % (Main.FPS * 2) != 0)
			return;
		for (Monster monster : Map.getMonsterList()) {
			if (Math.abs(Map.getHeroPosition().first - monster.getPosition().first) <= Monster.VISIBLE_RANGE
					&& Math.abs(Map.getHeroPosition().second - monster.getPosition().second) <= Monster.VISIBLE_RANGE) {
			//	System.out.println(Math.abs(Map.getHeroPosition().first - monster.getPosition().first)+" "+Math.abs(Map.getHeroPosition().second - monster.getPosition().second));
				monster.moveToPlayer();
			}
			else
				monster.randomMove();
		}
		// new but bug
//		boolean isNearPlayer = false;
//		for (Monster monster : Map.getMonsterList()) {
//			for (int i = 0; i < monster.getPicWidth(); i++) {
//				for (int j = 0; j < monster.getPicHeight(); j++) {
//					if (Math.abs(
//							Map.getHeroPosition().first - (monster.getPosition().first + i)) <= Monster.VISIBLE_RANGE
//							&& Math.abs(Map.getHeroPosition().second
//									- (monster.getPosition().second + j)) <= Monster.VISIBLE_RANGE) {
//							monster.moveToPlayer();
//							isNearPlayer = true;
//							break;
//						
//					}
//				}
//			}
//			if (isNearPlayer == false);
//				monster.randomMove();

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
		if (tick % (Main.FPS *2) != 0)
			return;
		for (Monster monster : Map.getMonsterList()) {
			if (monster.getFaceDirection() == Direction.UP && (int) monster.getPosition().second - 1 > 0) {
				for(int i=0;i<monster.getPicWidth();i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(i, -1))).getTileType() == TileType.HERO) {
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(i, -1))).getEntity());
					}
				}
				
			}
			if (monster.getFaceDirection() == Direction.DOWN && (int) monster.getPosition().second + 1 < Map.HEIGHT) {
				for(int i=0;i<monster.getPicWidth();i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight()))).getTileType() == TileType.HERO) {		
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(i, monster.getPicHeight()))).getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.LEFT && (int) monster.getPosition().first - 1 > 0) {
				for(int i=0;i<monster.getPicHeight();i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(-1, i))).getTileType() == TileType.HERO) {
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(-1, i))).getEntity());
					}
				}
			}
			if (monster.getFaceDirection() == Direction.RIGHT && (int) monster.getPosition().first + 1 < Map.WIDTH) {
				for(int i=0;i<monster.getPicHeight();i++) {
					if (Map.getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i))).getTileType() == TileType.HERO) {
						monster.attack(Map.getBoard(monster.getPosition().add(new Pair(monster.getPicWidth(), i))).getEntity());
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
