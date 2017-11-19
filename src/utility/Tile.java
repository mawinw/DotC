package utility;

import entity.Entity;
import environment.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
	private Entity entity;
	
	public Tile(boolean lightColor, int x, int y) {
		
		setWidth(Map.WIDTH*Map.TILE_SIZE);
		setHeight(Map.HEIGHT*Map.TILE_SIZE);
		
		relocate(x*Map.TILE_SIZE,y*Map.TILE_SIZE);
		
		setFill(lightColor ? Color.GREENYELLOW:Color.GREEN);
		
	}
	



	public boolean hasEntity() {
		return entity != null;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	public void setEntity(Entity entity) {
		this.entity=entity;
	}
	
	
	
}
