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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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
	private static Novice hero;
	private static ArrayList<Monster> monsterList;
	private static Canvas BG;
	private static Image bgImage = new Image("background/BG_01.png");

	private static Media stageMusicFile = new Media(
			ClassLoader.getSystemResource("sound/bgm03_stage.mp3").toString());
	static MediaPlayer stageMusic = new MediaPlayer(stageMusicFile);
	private static boolean isMusicPlaying=false;
	
	private static boolean isStageFinished=false;
	private static int monsterCount = 0;
	private static boolean isHeroDead=false;
	private static int currentStage=0;

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
		MapGc.clearRect(0, 0, 700, 700);
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

		createLv1HeroAt(8, 2);

		this.getChildren().addAll(tileGroup, namePane,statusBarGroup,entityGroup,effectGroup);

	}

	public static void classChange() {
		namePane.getChildren().remove(hero.getNameCanvas());
		entityGroup.getChildren().remove(hero.getLevelUpCanvas());
		entityGroup.getChildren().remove(hero.getCanvas());
		effectGroup.getChildren().remove(hero.getAtkCanvas());
		statusBarGroup.getChildren().clear();
		hero = new Fighter(hero);
//		Novice newHero = new Fighter(hero);
		
//		GameScene.hero = newHero;
		namePane.getChildren().add(hero.getNameCanvas());
		entityGroup.getChildren().add(hero.getLevelUpCanvas());
		entityGroup.getChildren().add(hero.getCanvas());
		effectGroup.getChildren().add(hero.getAtkCanvas());
		board[(int) hero.getPosition().first][(int) hero.getPosition().second].setEntity(hero);
		hero.draw();
		statusBarGroup.getChildren().clear();
		StatusBar.groundSmash.canUse=true;
		StatusBar.groundSmash.draw();
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
		clearScreen();
		drawBG();
		createLv1HeroAt(1, 4);
		this.getChildren().addAll(tileGroup, statusBarGroup,namePane,entityGroup,effectGroup);
	}
	public void createStage1() {
		clearScreen();
		drawBG();
		createSlimeAt(7,1);
		createLv1HeroAt(1, 4);
		this.getChildren().addAll(tileGroup, statusBarGroup,namePane,entityGroup,effectGroup);
		currentStage=1;
	}	
	public void createStage2() {
		clearScreen();
		drawBG();
		createSlimeAt(7,1);
		createSlimeAt(7,10);
		createProgressedHeroAt(1, 8);
		this.getChildren().addAll(tileGroup, statusBarGroup,namePane,entityGroup,effectGroup);
		currentStage=2;
	}
	public void createStage3() {
		clearScreen();
		drawBG();
		createSlimeAt(1,1);
		createSlimeAt(4,1);
		createSlimeAt(7,11);
		createSlimeKingAt(2,7);
		createProgressedHeroAt(10, 4);
		this.getChildren().addAll(tileGroup, statusBarGroup,namePane,entityGroup,effectGroup);
	}
	
	private void clearScreen() {

		tileGroup = new Group();
		entityGroup = new Group();
		effectGroup = new Group();
		statusBarGroup = new Group();
		namePane = new Pane();
		monsterList = new ArrayList<>();
		board = new Tile[WIDTH][HEIGHT];
		BG = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		this.getChildren().clear();
		hero.cleanAtkCanvas();

	}
	
	
	private void drawBG() {
		this.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		GraphicsContext MapGc = BG.getGraphicsContext2D();
		MapGc.clearRect(0, 0, 700, 700);
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
	}
	
	private void createSlimeAt(int x, int y) {
		Slime slime = new Slime(new Pair(x, y));
		createDefaultEntity(slime, "Slime", slime.getPosition());
		monsterCount++;
	}
	
	private void createSlimeKingAt(int x, int y) {
		SlimeKing slimeKing = new SlimeKing(new Pair(x, y));
		createDefaultEntity(slimeKing, "SlimeKing", slimeKing.getPosition());
		monsterCount++;
	}
	
	private void createLv1HeroAt(int x, int y) {
		hero = new Novice(MainMenu.name, new Pair(x, y));
		createDefaultEntity(hero, "Novice", hero.getPosition());
		namePane.getChildren().add(hero.getNameCanvas());
		isHeroDead=false;
	}
	
	private void createProgressedHeroAt(int x, int y) {
		hero.setPosition(x, y);
		board[(int) x][(int) y].setTileType(TileType.HERO);
		board[(int) x][(int) y].setEntity(hero);
		entityGroup.getChildren().add(hero.getLevelUpCanvas());
		entityGroup.getChildren().add(hero.getCanvas());
		effectGroup.getChildren().add(hero.getAtkCanvas());
		hero.draw();
		namePane.getChildren().add(hero.getNameCanvas());
		isHeroDead=false;
		return;
	}
	public static boolean getIsHeroDead() {
		return isHeroDead;
	}
	public static void setIsHeroDead(boolean isDead) {
		isHeroDead=isDead;
	}
	
	
	public static void playMusic() {
		if(!isMusicPlaying) {stageMusic.volumeProperty().set(0);}
		stageMusic.play();	
		Timeline fadeIn = new Timeline(
				new KeyFrame(Duration.millis(15000), new KeyValue(stageMusic.volumeProperty(), 1)));
    fadeIn.play();
	isMusicPlaying=true;
	}
	public static void stopMusic() {
		if(!isMusicPlaying) return;
		Timeline fadeOut = new Timeline(
				new KeyFrame(Duration.millis(1000), new KeyValue(stageMusic.volumeProperty(), 0)));
    fadeOut.play();
    fadeOut.setOnFinished(finish -> {
    	stageMusic.stop();
    	isMusicPlaying=false;
    });
	}
	public static void decreaseMonsterCount() {
		monsterCount--;
	}
	
	public static boolean getIsStageFinished() {
		return monsterCount<=0;
	}
	public static int getCurrentStage() {
		return currentStage;
	}
}