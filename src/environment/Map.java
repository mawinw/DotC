package environment;

import java.util.List;

import entity.Entity;
import entity.hero.Novice;
import entity.monster.Slime;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import utility.ActionResult;
import utility.ActionType;
import utility.Pair;
import utility.Tile;
import utility.TileType;

public class Map {
	private Canvas canvas;

	public static final int TILE_SIZE = 50;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;

	private static Tile[][] board = new Tile[WIDTH][HEIGHT];

	private static Group tileGroup = new Group();
	private static Group entityGroup = new Group();
	private static Novice novice;
	private static Pair heroPosition;

	public static Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y, null);

				tile.setTileType(TileType.NONE);
				board[x][y] = tile;
				tileGroup.getChildren().add(tile);
			}
		}
		/* */
		// add monsters below
		Entity slime1 = createDefaultEntity("Slime");
		Entity slime2 = createDefaultEntity("Slime");
		Entity slime3 = createDefaultEntity("Slime");
		Entity slime4 = createDefaultEntity("Slime");
		board[2][4].setTileType(TileType.MONSTER); board[2][4].setEntity(slime1);
		slime1.setPosition(2, 4);
		slime1.draw();
		board[4][4].setTileType(TileType.MONSTER); board[4][4].setEntity(slime2);
		slime2.setPosition(4, 4);
		slime2.draw();
		board[7][1].setTileType(TileType.MONSTER); board[7][1].setEntity(slime3);
		slime3.setPosition(7, 1);
		slime3.draw();
		board[7][7].setTileType(TileType.MONSTER); board[7][7].setEntity(slime4);
		slime4.setPosition(7, 7);
		slime4.draw();
		entityGroup.getChildren().addAll(slime1.getCanvas(), slime2.getCanvas(), slime3.getCanvas(),
				slime4.getCanvas());

		// add monsters above

		// add hero below
		novice = (Novice) createDefaultEntity("Novice");
		board[1][4].setTileType(TileType.HERO); board[1][4].setEntity(novice);
		novice.setPosition(1, 4);
		novice.draw();
		heroPosition = novice.getPosition();
		entityGroup.getChildren().add(novice.getCanvas());
		// add hero above
		root.getChildren().addAll(tileGroup, entityGroup);
		return root;
	}

	public static Group getEntityGroup() {
		return entityGroup;
	}

	public static Pair getHeroPosition() {
		return heroPosition;
	}

	public static Tile[][] getBoard() {
		return board;
	}
	
	public static Tile getBoard(int x, int y) {
		return board[x][y];
	}

	public static void setBoard(int x, int y, TileType tileType, Entity entity) {
		Tile tile = new Tile((x + y) % 2 == 0, x, y, entity);
		
		tile.setTileType(tileType);
		System.out.println(x + " " +y);
		board[x][y] = tile;
		
	}

	private ActionResult tryAction(ActionType type, int clickX, int clickY) {
		switch (type) {
		case ATTACK:
			break;
		case SKILL:
			break;
		case MOVE:
			break;
		default:
			return new ActionResult(ActionType.ATTACK);
		}
		return new ActionResult(ActionType.ATTACK);
		// confused
	}

	private static Entity createDefaultEntity(String entityType) {
		switch (entityType) {
		case "Slime":
			return new Slime();
		case "Novice":
			return new Novice();
		}
		return null;
	}

	private int toBoard(double pixel) {
		return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
	}

	public static Novice getNovice() {
		return novice;
	}

}
