package fredver.errorhandling;

public class ExceptionHandler {

	public static void handle(Exception ex) {
		if(ex instanceof InvalidMessageException) {
			System.out.println("Invalid message detected:");
			System.out.println( ((InvalidMessageException) ex).getInvalidMessage() );
		}
	}
	
}
