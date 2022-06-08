package exception;

public class DuplicateUsernameException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DuplicateUsernameException() {
		super();
	}
	
	public DuplicateUsernameException(String message) {
		super(message);
	}
}
