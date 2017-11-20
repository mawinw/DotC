package utility;

import entity.Entity;
import environment.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
	private TileType tileType;
	
	public Tile(boolean lightColor, int x, int y) {
		
		setWidth(Map.WIDTH*Map.TILE_SIZE);
		setHeight(Map.HEIGHT*Map.TILE_SIZE);
		
		relocate(x*Map.TILE_SIZE,y*Map.TILE_SIZE);
		
		setFill(lightColor ? Color.GREENYELLOW:Color.GREEN);
		
	}
	



	public boolean hasEntity() {
		return tileType != TileType.NONE;
	}
	
	public TileType getTileType() {
		return this.tileType;
	}
	public void setTileType(TileType tileType) {
		this.tileType=tileType;
	}
	
	
	
}
