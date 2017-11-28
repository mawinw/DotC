package entity.property;

import entity.Entity;
import entity.hero.Novice;
import entity.monster.Slime;
import entity.monster.SlimeKing;
import environment.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.Side;

public class HpBar {
	private Entity entity;
	private double maxWidth;
	private double width;
	private Canvas canvas;

	public HpBar(Entity entity) {
		this.entity = entity;
		maxWidth = entity.getPicWidth() * 50;
		this.canvas = new Canvas();
		// draw();
	}

	public void draw() {
		canvas.setWidth(entity.getCanvas().getWidth());
		canvas.setHeight(entity.getCanvas().getHeight());
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// System.out.println(maxWidth);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setLineWidth(2);
		if (entity.getSide() == Side.MONSTER) {
			gc.setStroke(Color.ORANGERED);
			gc.setFill(Color.ORANGERED);
		} else if (entity.getSide() == Side.HERO) {
			gc.setStroke(Color.CORNFLOWERBLUE);
			gc.setFill(Color.CORNFLOWERBLUE);
		} else {
			gc.setStroke(Color.GOLD);
			gc.setFill(Color.GOLD);
		}
		gc.setStroke(Color.GREEN);
		width = (entity.getHp() / entity.getMaxHp()) * maxWidth;
		// System.out.println(entity.getPosition().first+"
		// "+entity.getPosition().second);
		gc.strokeRoundRect(entity.getPosition().first * Map.TILE_SIZE, entity.getPosition().second * Map.TILE_SIZE - 10,
				maxWidth, 5, 2, 2);
		gc.fillRoundRect(entity.getPosition().first * Map.TILE_SIZE, entity.getPosition().second * Map.TILE_SIZE - 10,
				width, 5, 2, 2);
		// gc.strokeRoundRect(1 * Map.TILE_SIZE, 5 * Map.TILE_SIZE - 10,
		// maxWidth, 5, 2, 2);
		// gc.fillRoundRect(1 * Map.TILE_SIZE, 5 * Map.TILE_SIZE - 10,
		// width, 5, 2, 2);
	}

	public Entity getEntity() {
		return entity;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void die() {
		// System.out.println(Map.statusBarGroup.getChildren().contains(canvas));
		Map.statusBarGroup.getChildren().remove(canvas);
		canvas.setOpacity(0);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 200, 200);

		// System.out.println(Map.statusBarGroup.getChildren().contains(canvas));

	}
}
