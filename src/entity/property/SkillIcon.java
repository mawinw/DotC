package entity.property;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SkillIcon extends Canvas {
	private double coolDown;
	private double remainingCoolDown;
	private GraphicsContext gc = this.getGraphicsContext2D();
	
	public SkillIcon(double coolDown) {
		this.coolDown=coolDown;
		remainingCoolDown=this.coolDown;
	}
	
}
