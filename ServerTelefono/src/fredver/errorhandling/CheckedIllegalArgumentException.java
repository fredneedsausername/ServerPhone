package fredver.errorhandling;

public class CheckedIllegalArgumentException extends Exception {
	
	private static final long serialVersionUID = 2303744697324319013L;
	private String message;
	
	public CheckedIllegalArgumentException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
