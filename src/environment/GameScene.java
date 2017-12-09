package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.Entity;
import entity.hero.Fighter;
import entity.hero.Novice;
import entity.monster.Monster;
import entity.monster.Slime;
import entity.monster.SlimeKing;
import environment.menu.MainMenu;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utility.ActionResult;
import utility.ActionType;
import utility.Pair;
import utility.Tile;
import utility.TileType;

public class GameScene extends Pane {
	private static GameScene instance;
	public static final int TILE_SIZE = 50;
	public static final int WIDTH = 14;
	public static final int HEIGHT = 12;

	private static Tile[][] board;

	private static Group tileGroup;
	private static Group entityGroup;
	public static Group statusBarGroup;
	public static Group effectGroup;
	private static Pane namePane;
	private static Fighter hero;
	private static Pair heroPosition;
	private static String heroName;
	private static ArrayList<Monster> monsterList;
	private static Canvas BG;
	private static Image bgImage = new Image("background/BG_01.png");

	public GameScene() {
		tileGroup = new Group();
		entityGroup = new Group();
		effectGroup = new Group();
		statusBarGroup = new Group();
		this.getChildren().clear();
		namePane = new Pane();
		monsterList = new ArrayList<>();
		board = new Tile[WIDTH][HEIGHT];
		BG = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

		this.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

		GraphicsContext MapGc = BG.getGraphicsContext2D();
		MapGc.clearRect(0, 0, 500, 500);
		MapGc.drawImage(bgImage, 0, 0, 700, 700);
		tileGroup.getChildren().add(BG);
		
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y, null);

				tile.setTileType(TileType.NONE);
				board[x][y] = tile;
				tileGroup.getChildren().add(tile);
			}
		}

		Slime slime6 = new Slime(new Pair(4, 1));
		createDefaultEntity(slime6, "Slime", slime6.getPosition());
		SlimeKing king = new SlimeKing(new Pair(5, 5));
		createDefaultEntity(king, "SlimeKing", king.getPosition());
		hero = new Fighter(MainMenu.name, new Pair(1, 4));
		heroName = hero.getName();
		createDefaultEntity(hero, "Novice", hero.getPosition());
		namePane.getChildren().add(hero.getNameCanvas());


		this.getChildren().addAll(tileGroup, namePane,statusBarGroup,entityGroup,effectGroup);

	}

	public static Group getTileGroup() {
		return tileGroup;
	}

	public static Group getStatusBarGroup() {
		return statusBarGroup;
	}
	public static Group getEffectGroup() {
		return effectGroup;
	}

	public static Pane getNamePane() {
		return namePane;
	}

	public static Canvas getBG() {
		return BG;
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

	private static void createDefaultEntity(Entity entity, String entityType, Pair position) {
		switch (entityType) {
		case "Slime":
			monsterList.add((Monster) entity);
			board[(int) position.first][(int) position.second].setTileType(TileType.MONSTER);
			board[(int) position.first][(int) position.second].setEntity(entity);
			entityGroup.getChildren().add(entity.getCanvas());
			effectGroup.getChildren().add(entity.getAtkCanvas());
			entity.draw();
			return;
		case "SlimeKing":
			monsterList.add((SlimeKing) entity);
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					board[(int) position.first + i][(int) position.second + j].setTileType(TileType.MONSTER);
					board[(int) position.first + i][(int) position.second + j].setEntity(entity);
				}
			}
			entityGroup.getChildren().add(entity.getCanvas());
			effectGroup.getChildren().add(entity.getAtkCanvas());
			entity.draw();
			return;
		case "Novice":
			board[(int) position.first][(int) position.second].setTileType(TileType.HERO);
			board[(int) position.first][(int) position.second].setEntity(hero);
			heroPosition = hero.getPosition();
			entityGroup.getChildren().add(hero.getLevelUpCanvas());
			entityGroup.getChildren().add(entity.getCanvas());
			effectGroup.getChildren().add(entity.getAtkCanvas());
			entity.draw();
			return;
		}
	}

	private int toBoard(double pixel) {
		return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
	}

	public static Novice getHero() {
		return hero;
	}

	public static ArrayList<Monster> getMonsterList() {
		return monsterList;
	}

	public static GameScene getInstance() {
		if (instance == null) {
			instance = new GameScene();
		}

		return instance;
	}

	public void reset() {
		tileGroup = new Group();
		entityGroup = new Group();
		effectGroup = new Group();
		System.out.println("resetto");
		statusBarGroup = new Group();
		namePane = new Pane();
		monsterList = new ArrayList<>();
		board = new Tile[WIDTH][HEIGHT];
		BG = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		this.getChildren().clear();

		this.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);


		GraphicsContext MapGc = BG.getGraphicsContext2D();
		MapGc.clearRect(0, 0, 500, 500);
		MapGc.drawImage(bgImage, 0, 0, 700, 700);
		tileGroup.getChildren().add(BG);
		
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				Tile tile = new Tile((x + y) % 2 == 0, x, y, null);

				tile.setTileType(TileType.NONE);
				board[x][y] = tile;
				tileGroup.getChildren().add(tile);
			}
		}

		Slime slime6 = new Slime(new Pair(4, 1));
		createDefaultEntity(slime6, "Slime", slime6.getPosition());
		SlimeKing king = new SlimeKing(new Pair(5, 5));
		createDefaultEntity(king, "SlimeKing", king.getPosition());

		hero = new Fighter(MainMenu.name, new Pair(1, 4));
		heroName = hero.getName();
		createDefaultEntity(hero, "Novice", hero.getPosition());
		namePane.getChildren().add(hero.getNameCanvas());

		this.getChildren().addAll(tileGroup, statusBarGroup,namePane,entityGroup,effectGroup);

	}
}