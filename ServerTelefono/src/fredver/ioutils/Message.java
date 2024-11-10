package fredver.ioutils;

import java.util.regex.Pattern;

import fredver.errorhandling.CheckedIllegalArgumentException;

public class Message {
	
	public static char MESSAGE_START = '[' IMPLEMENT THIS IN MESSAGE STRUCTURE
	
	private String content;
	private Header header;
	
	public Message(Header header, String content) {
		this.content = content;
		this.header = header;
	}
	
	public Message(String message) throws CheckedIllegalArgumentException { 
		WRITE THIS METHOD
		CALL isMessageValid to check if the message is valid
	}
	
	public static boolean isMessageValid(String message) {
        String regex = "^([^;]+);([^:]+):([^|]+)\\|(.+)$";
        return message.matches(regex);
    }
	
	@Override
	public String toString() {
		
	}
	
	public String getContent() {
		return content;
	}

	public Header getHeaders() {
		return header;
	}
	
}