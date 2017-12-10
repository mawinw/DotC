package exception;

public class AttackDeadEntityException extends Exception {
	@Override
	public String getMessage() {
		return "Cannot Attack Dead Entity.";
	}
}
