package fredver.ioutils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import fredver.constants.Constants;
import fredver.errorhandling.CheckedIllegalArgumentException;

public class Message {
	
	private String content;
	private Header[] headers;
	
	private static String clientToServerDisconnectionMessage;// to be sent by client to server to signal they request to terminate the connection
	
	private static String serverToClientWantsToShutOffMessage;// to be sent by the server to the client to signal it wants to shut off, so to not send data anymore
	
	public static String getClientToServerDisconnectionMessage() {
		return clientToServerDisconnectionMessage;
	}

	public static String getServerToClientWantsToShutOffMessage() {
		return serverToClientWantsToShutOffMessage;
	}

	static {
		try {
			clientToServerDisconnectionMessage = 
					new Message(new Header[] {
							new Header(RequestType.CLIENT_DISCONNECTS, Constants.NULL_VALUE)
					}, null).toString();
			
			serverToClientWantsToShutOffMessage = 
					new Message(new Header[] {
							new Header(RequestType.SERVER_SHUTS_OFF, Constants.NULL_VALUE)
					}, null).toString();
		} catch (CheckedIllegalArgumentException e) { }
	}
	
	
	public Message(Header[] headers, String content) {
		this.content = content;
		this.headers = headers;
	}
	
	public Message(String message) throws CheckedIllegalArgumentException { 
		
		List<Header> headers = new ArrayList<>();
		String content;
		
		if(!isMessageValid(message))
			throw new CheckedIllegalArgumentException("Illegal argument in Message(String) constructor: " + message);
		
		String stringHeaders = message.split(Character.toString(Constants.HEADER_AND_MESSAGE_SEPARATOR))[0];
		content = message.split(Character.toString(Constants.HEADER_AND_MESSAGE_SEPARATOR))[1];
		
		String[] stringHeadersArray = stringHeaders.split(Character.toString(Constants.HEADER_AND_HEADER_SEPARATOR));
		for(String s : stringHeadersArray) {
			String headerType = s.split(Character.toString(Constants.HEADER_AND_HEADER_DESCRIPTION_SEPARATOR))[0];
			String headerMessage = s.split(Character.toString(Constants.HEADER_AND_HEADER_DESCRIPTION_SEPARATOR))[1];
			
			try {
				RequestType requestType = RequestType.fromString(headerType);
				Header header = new Header(requestType, headerMessage);
				headers.add(header);
			} catch (CheckedIllegalArgumentException e) {
				throw new CheckedIllegalArgumentException("Illegal request-type argument: " + headerType);
			}
			
		}
		
		this.headers = headers.toArray(new Header[headers.size()]);
		this.content = content;
		
	}
	
	public static boolean isMessageValid(String message) {
        String regex = "^([^:;]+:[^:;]+(?:;[^:;]+:[^:;]+)*)\\|(.+)$";
        
        return Pattern
        		.compile(regex)
        		.matcher(message)
        		.matches();
    }
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
	
		if((headers != null) && (headers.length != 0)) {
			for(Header h : headers) {
				ret
				.append(h.getRequestType().toString())
				.append(Constants.HEADER_AND_HEADER_DESCRIPTION_SEPARATOR)
				.append(h.getValue())
				.append(Constants.HEADER_AND_HEADER_SEPARATOR);
			}
			ret.deleteCharAt(ret.length() - 1); // remove extra header and header separator
		}
		else {
			ret
			.append(Constants.NULL_VALUE + Character.toString(Constants.HEADER_AND_HEADER_DESCRIPTION_SEPARATOR) + Constants.NULL_VALUE);
		}
		
		if((content != null) && (content != "")) {			
			ret
			.append(Constants.HEADER_AND_MESSAGE_SEPARATOR)
			.append(content);
		} else {
			ret
			.append(Constants.HEADER_AND_MESSAGE_SEPARATOR)
			.append(Constants.NULL_VALUE);
		}
		
		return ret.toString();
	}
	
	public String getContent() {
		return content;
	}

	public Header[] getHeaders() {
		return headers;
	}
	
}