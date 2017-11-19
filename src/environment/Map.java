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
import utility.Tile;

public class Map {
	private Canvas canvas;

	public static final int TILE_SIZE = 50;
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	
	private Tile[][] board = new Tile[WIDTH][HEIGHT];
	
	private Group tileGroup = new Group();
	private Group entityGroup = new Group();
	
	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(WIDTH*TILE_SIZE, HEIGHT*TILE_SIZE);
		root.getChildren().addAll(tileGroup,entityGroup);
		for(int x=0;x<WIDTH;x++) {
			for(int y=0;y<HEIGHT;y++) {
				Tile tile = new Tile((x+y)%2==0,x,y);

				tile.setEntity(null);
				board[x][y] = tile;
				tileGroup.getChildren().add(tile);
			}
		}
		//add monsters below
		Entity slime = createDefaultEntity("Slime");
		board[2][4].setEntity(slime);
		board[4][4].setEntity(slime);
		board[7][1].setEntity(slime);
		board[7][7].setEntity(slime);
		
		//add monsters above
		
		//add hero below
		Entity novice = createDefaultEntity("Novice");
		board[1][4].setEntity(novice);
		//add hero above
		root.getChildren().addAll(tileGroup,entityGroup);
		return root;
	}
	
	private ActionResult tryAction(ActionType type,int clickX,int clickY) {
		switch(type) {
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
	
	
	
	private Entity createDefaultEntity(String entityType) {
		switch(entityType) {
		case "Slime":
			return new Slime();
		case "Novice":
			return new Novice();
		}
		return null;
	}
	
	
	private int toBoard(double pixel) {
		return (int)(pixel+TILE_SIZE/2)/TILE_SIZE;
	}
	
}
