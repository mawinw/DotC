package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Entity;
import entity.hero.Novice;
import entity.monster.Monster;
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
	public static final int WIDTH = 20;
	public static final int HEIGHT = 10;

	private static Tile[][] board = new Tile[WIDTH][HEIGHT];

	private static Group tileGroup = new Group();
	private static Group entityGroup = new Group();
	public static Group statusBarGroup = new Group();
	private static Novice novice;
	private static Pair heroPosition;
	private static ArrayList<Monster> monsterList = new ArrayList<>();

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
		Monster slime1 = (Monster) createDefaultEntity("Slime", new Pair(2, 4));
		Monster slime2 = (Monster) createDefaultEntity("Slime", new Pair(4, 4));
		Monster slime3 = (Monster) createDefaultEntity("Slime", new Pair(7, 1));
		Monster slime4 = (Monster) createDefaultEntity("Slime", new Pair(7, 7));
		board[2][4].setTileType(TileType.MONSTER);
		board[2][4].setEntity(slime1);
		board[4][4].setTileType(TileType.MONSTER);
		board[4][4].setEntity(slime2);
		board[7][1].setTileType(TileType.MONSTER);
		board[7][1].setEntity(slime3);
		board[7][7].setTileType(TileType.MONSTER);
		board[7][7].setEntity(slime4);
//		statusBarGroup.getChildren().add(slime1.getHpBar().getCanvas());
		entityGroup.getChildren().addAll(slime1.getCanvas()
				, slime2.getCanvas(), slime3.getCanvas(),
				slime4.getCanvas());
		monsterList.add(slime1);
		monsterList.add(slime2);
		monsterList.add(slime3);
		monsterList.add(slime4);
		
		Monster slime5 = (Monster) createDefaultEntity("Slime", new Pair(10,5));
		monsterList.add(slime5);
		board[10][5].setTileType(TileType.MONSTER);
		board[10][5].setEntity(slime5);
		entityGroup.getChildren().add(slime5.getCanvas());
		Monster slime6 = (Monster) createDefaultEntity("Slime", new Pair(12,7));
		monsterList.add(slime6);
		board[12][7].setTileType(TileType.MONSTER);
		board[12][7].setEntity(slime6);
		entityGroup.getChildren().add(slime6.getCanvas());
		
		
		// random n th  slime
//		for(int i=0;i<5;i++) {
//			int x= new Random().nextInt(Map.WIDTH); 
//			int y= new Random().nextInt(Map.HEIGHT);
//			if(Map.getBoard()[x][y].getTileType()!=null) { i--; continue;}
//			Monster slime = (Monster) createDefaultEntity("Slime", new Pair(x,y));
//			monsterList.add(slime);
//			board[x][y].setTileType(TileType.MONSTER);
//			board[x][y].setEntity(slime);
//			entityGroup.getChildren().add(slime.getCanvas());
//		}

		// System.out.println(slime1.getPosition().first+"
		// "+slime1.getPosition().second);
		// add monsters above

		// add hero below
		novice = (Novice) createDefaultEntity("Novice", new Pair(1, 4));
		board[1][4].setTileType(TileType.HERO);
		board[1][4].setEntity(novice);
		heroPosition = novice.getPosition();
	//	statusBarGroup.getChildren().add(novice.getHpBar().getCanvas());
		entityGroup.getChildren().add(novice.getCanvas());
		
		// add hero above
		root.getChildren().addAll(tileGroup, entityGroup, statusBarGroup);
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

	public static Tile getBoard(Pair position) {
		return board[(int) position.first][(int) position.second];
	}

	public static void setBoard(Pair xy, TileType tileType, Entity entity) {
		int x = (int) xy.first;
		int y = (int) xy.second;
		Tile tile = new Tile((x + y) % 2 == 0, x, y, entity);

		tile.setTileType(tileType);
		// System.out.println(x + " " +y);
		// System.out.println(x + "." + y + "." + tileType);
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

	private static Entity createDefaultEntity(String entityType, Pair position) {
		switch (entityType) {
		case "Slime":
			return new Monster(position);
		case "Novice":
			return new Novice(position);
		}
		return null;
	}

	private int toBoard(double pixel) {
		return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
	}

	public static Novice getNovice() {
		return novice;
	}

	public static ArrayList<Monster> getMonsterList() {
		return monsterList;
	}
}
