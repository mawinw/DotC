package main;

import java.util.HashSet;

import entity.Entity;
import environment.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utility.ActionResult;
import utility.ActionType;
import utility.Direction;
import utility.Tile;
import utility.TileType;

public class Handler {
	// set all event
	private static HashSet<KeyCode> activeKey = new HashSet<KeyCode>();
	private static boolean autoshoot = false;
	private static boolean skillUpgradable = false;

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

		
		if (Map.getNovice().isMoveFinished()) {
			
			if (activeKey.contains(KeyCode.UP)) {
				Map.getNovice().setFaceDirection(Direction.UP);
				Map.getNovice().draw();
				if (Map.getNovice().getPosition().second > 0 && !Map
						.getBoard()[(int) Map.getNovice().getPosition().first][(int) (Map.getNovice().getPosition().second
								- 1)].hasEntity()) {
					Map.getNovice().move(0, -1);
				}
				activeKey.remove(KeyCode.UP);
			}
	
			if (activeKey.contains(KeyCode.DOWN)) {
				Map.getNovice().setFaceDirection(Direction.DOWN);
				Map.getNovice().draw();
				if (Map.getNovice().getPosition().second <= Map.HEIGHT - 2 && !Map
						.getBoard()[(int) Map.getNovice().getPosition().first][(int) (Map.getNovice().getPosition().second
								+ 1)].hasEntity()) {
					Map.getNovice().move(0, 1);
				}
				activeKey.remove(KeyCode.DOWN);
			}
	
			if (activeKey.contains(KeyCode.LEFT)) {
				Map.getNovice().setFaceDirection(Direction.LEFT);
				Map.getNovice().draw();
				if (Map.getNovice().getPosition().first > 0 && !Map.getBoard()[(int) Map.getNovice().getPosition().first
						- 1][(int) (Map.getNovice().getPosition().second)].hasEntity()) {
					Map.getNovice().move(-1, 0);
				}
				activeKey.remove(KeyCode.LEFT);
			}
	
			if (activeKey.contains(KeyCode.RIGHT)) {
				Map.getNovice().setFaceDirection(Direction.RIGHT);
				Map.getNovice().draw();
				if (Map.getNovice().getPosition().first <= Map.WIDTH - 2
						&& !Map.getBoard()[(int) Map.getNovice().getPosition().first
								+ 1][(int) (Map.getNovice().getPosition().second)].hasEntity()) {
					Map.getNovice().move(1, 0);
				}
				activeKey.remove(KeyCode.RIGHT);
			}
			System.out.println(Map.getBoard()[(int) Map.getNovice().getPosition().first][(int) Map.getNovice().getPosition().second].getTileType());
		
		}

		// if run then goto out of map

	}

	public static void playerAttact() {
		if (activeKey.contains(KeyCode.Z)) {
			if ((int) Map.getNovice().getPosition().first + 1 < Map.WIDTH) {
				if (Map.getBoard()[(int) Map.getNovice().getPosition().first
						+ 1][(int) (Map.getNovice().getPosition().second)].getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.RIGHT) {

					Map.getNovice().attack(Map.getBoard()[(int) Map.getNovice().getPosition().first
							+ 1][(int) (Map.getNovice().getPosition().second)].entity);
				}
			}

			if ((int) Map.getNovice().getPosition().first - 1 > 0) {
				if (Map.getBoard()[(int) Map.getNovice().getPosition().first
						- 1][(int) (Map.getNovice().getPosition().second)].getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.LEFT) {

					Map.getNovice().attack(Map.getBoard()[(int) Map.getNovice().getPosition().first
							- 1][(int) (Map.getNovice().getPosition().second)].entity);
				}
			}

			if ((int) Map.getNovice().getPosition().second - 1 > 0) {
				if (Map.getBoard()[(int) Map.getNovice()
						.getPosition().first][(int) (Map.getNovice().getPosition().second) - 1]
								.getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.UP) {

					Map.getNovice().attack(Map.getBoard()[(int) Map.getNovice()
							.getPosition().first][(int) (Map.getNovice().getPosition().second) - 1].entity);
				}
			}

			if ((int) Map.getNovice().getPosition().second + 1 < Map.HEIGHT) {
				if (Map.getBoard()[(int) Map.getNovice()
						.getPosition().first][(int) (Map.getNovice().getPosition().second) + 1]
								.getTileType() == TileType.MONSTER
						&& Map.getNovice().getFaceDirection() == Direction.DOWN) {

					Map.getNovice().attack(Map.getBoard()[(int) Map.getNovice()
							.getPosition().first][(int) (Map.getNovice().getPosition().second) + 1].entity);
				}
			}

			System.out.println((int) Map.getNovice().getPosition().first + " " + (int)Map.getNovice().getPosition().second);
			activeKey.remove(KeyCode.Z);

		}

	}

	public static void checkStatus() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (Map.getBoard()[i][j].entity != null) {
					if (Map.getBoard()[i][j].entity.getIsDead()) {
						Map.setBoard(i, j, TileType.NONE, null);
					}
				}
			}
		}
	}

	public static void update() {
		movePlayer();
		playerAttact();
		checkStatus();
	}

	private static ActionResult tryMove(Entity entity, int newX, int newY) {
		// Map.getBoard()[newX][newY].hasEntity()
		if (newX < 0 && newY < 0 && newX >= Map.WIDTH && newY >= Map.HEIGHT) {
			return new ActionResult(ActionType.NONE);
		} else
			return new ActionResult(ActionType.MOVE);

	}

}
