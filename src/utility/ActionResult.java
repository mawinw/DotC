package utility;

import entity.Entity;

public class ActionResult {

	private ActionType type;
	private Entity entity;

	public ActionResult(ActionType type) {
		this(type, null);
	}

	public ActionResult(ActionType type, Entity entity) {
		this.type = type;
		this.entity = entity;
	}

	public ActionType getActionType() {
		return type;
	}

	public Entity getEntity() {
		return entity;
	}
}
