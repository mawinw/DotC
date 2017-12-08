package entity.property;

import entity.Entity;

public interface Attackable {
	public void attack(Entity entity);
	public void drawAttackAnimation();
	public double calculateDamage(Entity entity);
	public void takeDamage(double dmg);
}
