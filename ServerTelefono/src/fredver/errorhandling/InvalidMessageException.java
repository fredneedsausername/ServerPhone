package fredver.errorhandling;

public class InvalidMessageException extends Exception {

	private static final long serialVersionUID = -2855879121525513703L;

	private String invalidMessage;
	
	public InvalidMessageException(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}
	
	public String getInvalidMessage() {
		return invalidMessage;
	}
	
}
