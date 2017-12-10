package entity.property;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class SkillIcon extends Pane {
	public double coolDown;
	public double remainingCoolDown;
	private Canvas background;
	private Canvas arc;
	private Image image;
	private final static double SIZE = 50;
	public boolean canUse;

	public SkillIcon(double coolDown,Image image,boolean canUse) {
		background = new Canvas(SIZE,SIZE);
		arc = new Canvas(SIZE,SIZE);
		arc.setOpacity(0.5);
		this.coolDown = coolDown;
		remainingCoolDown = 0;
		this.image = image;
		this.canUse=canUse;
		this.getChildren().addAll(background,arc);
	}

	public void draw() {
		GraphicsContext gc = background.getGraphicsContext2D();
		double progress = 1 - remainingCoolDown / coolDown;
		if(progress==1) progress=0;
		gc.clearRect(0, 0, SIZE,SIZE);
		gc.drawImage(image,0, 0, SIZE,SIZE);
		gc = arc.getGraphicsContext2D();
		gc.clearRect(0, 0, SIZE,SIZE);
		gc.setFill(Color.BLACK);
		//gc.se
		if(canUse) gc.fillArc(0, 0, SIZE,SIZE, 90, (360 * progress), ArcType.ROUND);
		else gc.fillArc(0, 0, SIZE,SIZE, 90, 360, ArcType.ROUND);
	}

}
