package fredver.ioutils;

import java.util.regex.Pattern;

import fredver.errorhandling.CheckedIllegalArgumentException;

public class Message {
	
	private String content;
	private Header header;
	
	public Message(Header header, String content) {
		this.content = content;
		this.header = header;
	}
	
	public Message(String message) throws CheckedIllegalArgumentException { 
		WRITE THIS METHOD		
	}
	
	public static boolean isMessageValid(String message) {
        String regex = "^([^;]+);([^:]+):([^|]+)\\|(.+)$";
        
        return Pattern
        		.compile(regex)
        		.matcher(message)
        		.matches();
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