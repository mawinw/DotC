package main;

import java.util.HashSet;

import entity.Entity;
import environment.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utility.ActionResult;
import utility.ActionType;
import utility.Tile;
import utility.TileType;


public class Handler {
	// set all event
	private static HashSet<KeyCode> activeKey = new HashSet<KeyCode>();
	private static boolean autoshoot = false;
	private static boolean skillUpgradable = false;
	
	public static void keyPressed(KeyEvent event) {
		if(event.getCode() == KeyCode.SPACE) {
			activeKey.add(KeyCode.SPACE);
		}
		
		if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
			activeKey.add(KeyCode.UP);
		}
		
		if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
			activeKey.add(KeyCode.DOWN);
		}
		
		if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
			activeKey.add(KeyCode.LEFT);
		}
		
		if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
			activeKey.add(KeyCode.RIGHT);
		}
		
		
	}
	
	public static void keyReleased(KeyEvent event) {
		if(event.getCode() == KeyCode.SPACE) {
			activeKey.remove(KeyCode.SPACE);
		}
		
		if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
			activeKey.remove(KeyCode.UP);
		}
		
		if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
			activeKey.remove(KeyCode.DOWN);
		}
		
		if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
			activeKey.remove(KeyCode.LEFT);
		}
		
		if(event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
			activeKey.remove(KeyCode.RIGHT);
		}
	
		
	}
	
	private static void movePlayer() {
		
		Map.setBoard((int) Map.getNovice().getPosition().first, (int) Map.getNovice().getPosition().second, TileType.NONE);
					if(activeKey.contains(KeyCode.UP)&&Map.getNovice().getPosition().second>0
						&&!Map.getBoard()[(int) Map.getNovice().getPosition().first][(int) (Map.getNovice().getPosition().second-1)].hasEntity()) {
					activeKey.remove(KeyCode.UP);
					Map.getNovice().move(0, -1);

					
				
				}
				
				if(activeKey.contains(KeyCode.DOWN)&&Map.getNovice().getPosition().second<=Map.HEIGHT-2
						&&!Map.getBoard()[(int) Map.getNovice().getPosition().first][(int) (Map.getNovice().getPosition().second+1)].hasEntity()) {
					activeKey.remove(KeyCode.DOWN);
					Map.getNovice().move(0, 1);


				}
				
				if(activeKey.contains(KeyCode.LEFT)&&Map.getNovice().getPosition().first>0
						&&!Map.getBoard()[(int) Map.getNovice().getPosition().first-1][(int) (Map.getNovice().getPosition().second)].hasEntity()) {
					activeKey.remove(KeyCode.LEFT);
					Map.getNovice().move(-1, 0);
					

				}
				
				if(activeKey.contains(KeyCode.RIGHT)&&Map.getNovice().getPosition().first<=Map.WIDTH-2
						&&!Map.getBoard()[(int) Map.getNovice().getPosition().first+1][(int) (Map.getNovice().getPosition().second)].hasEntity()) {
					activeKey.remove(KeyCode.RIGHT);
					Map.getNovice().move(1, 0);

				}
				
		
				// if run then goto out of map 

	
	}
	
	public static void update() {
		movePlayer();
	}
	
	private static ActionResult tryMove(Entity entity, int newX, int newY) {
		//Map.getBoard()[newX][newY].hasEntity() 
		if(newX<0&& newY<0&& newX>=Map.WIDTH&&newY>=Map.HEIGHT ) {
			return new ActionResult(ActionType.NONE);
		}
		else
			return new ActionResult(ActionType.MOVE);
	
	}
	
	
	
	
	
}
	
	
	
	
	
	
	
	

