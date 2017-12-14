package exception;

public class SpecialCharacterException extends Exception {
	private String text;
	public SpecialCharacterException(String text) {
		this.text = text;
	}
	
	@Override
	public String getMessage() {
		return "Character [" + text + "] is not allowed.";
	}
}
