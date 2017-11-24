package utility;

import entity.Entity;
import entity.hero.Novice;
import entity.monster.Monster;
import environment.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
	private TileType tileType;
	private Entity entity;

	public Tile(boolean lightColor, int x, int y, Entity entity) {

		setWidth(Map.WIDTH * Map.TILE_SIZE);
		setHeight(Map.HEIGHT * Map.TILE_SIZE);

		relocate(x * Map.TILE_SIZE, y * Map.TILE_SIZE);

		setFill(lightColor ? Color.WHITE : Color.BLACK);
//		if (entity == null) {
//			this.entity = null;
////			this.tileType = TileType.NONE;
//		}else if (entity instanceof Novice) {
//			System.out.println("WTFFFFFF");
//			this.entity = new Novice();
////			this.tileType = TileType.HERO;
//		} else if (entity instanceof Slime) {
//			this.entity = new Slime();
////			this.tileType = TileType.MONSTER;
//		}
		// can't initial entity
		this.entity = entity;

	}
	
	public void setTileFill(Color color) {
		setFill(color);
	}
	
	

	public boolean hasEntity() {
	//	System.out.println(tileType);
		return tileType != TileType.NONE;
	}

	public TileType getTileType() {
		return this.tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}

	public void setEntity(Entity entity) {
		this.entity=entity;
		
	}

	public Entity getEntity() {
		return entity;
	}

}
