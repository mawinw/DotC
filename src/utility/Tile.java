package utility;

import entity.Entity;
import entity.hero.Novice;
import entity.monster.Slime;
import environment.GameScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
	private TileType tileType;
	private Entity entity;

	public Tile(boolean lightColor, int x, int y, Entity entity) {

		setWidth(GameScene.TILE_SIZE);
		setHeight(GameScene.TILE_SIZE);

		relocate((x * GameScene.TILE_SIZE), (y * GameScene.TILE_SIZE));

		setFill(lightColor ? Color.WHITE : Color.BLACK);
		setOpacity(0.05);

		// if (entity == null) {
		// this.entity = null;
		//// this.tileType = TileType.NONE;
		// }else if (entity instanceof Novice) {
		// System.out.println("WTFFFFFF");
		// this.entity = new Novice();
		//// this.tileType = TileType.HERO;
		// } else if (entity instanceof Slime) {
		// this.entity = new Slime();
		//// this.tileType = TileType.MONSTER;
		// }
		// can't initial entity
		this.entity = entity;

	}

	public void setTileFill(Color color) {
		setFill(color);
	}

	public boolean hasEntity() {
		// System.out.println(tileType);
		return tileType != TileType.NONE;
	}

	public TileType getTileType() {
		return this.tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;

	}

	public Entity getEntity() {
		return entity;
	}

}
