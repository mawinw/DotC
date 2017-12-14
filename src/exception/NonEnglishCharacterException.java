package exception;

public class NonEnglishCharacterException extends Exception {
	private String text;
	public NonEnglishCharacterException(String text) {
		this.text = text;
	}
	
	@Override
	public String getMessage() {
		return "Character [" + text + "] is not allowed.";
	}
}
