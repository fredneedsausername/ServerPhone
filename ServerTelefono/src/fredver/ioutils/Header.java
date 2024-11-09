package fredver.ioutils;

import fredver.errorhandling.CheckedIllegalArgumentException;

public class Header {
	
	private String body;
	private HeaderTitle headerTitle;
	
	public static final String HEADER_BODY_GRANTED = "GRANTED";
	public static final String HEADER_BODY_NEGATED = "NEGATED";
	
	public Header(HeaderTitle headerTitle, String body) throws CheckedIllegalArgumentException {
		if(headerTitle == null) 
			throw new CheckedIllegalArgumentException("Request type of value null when initializing Header class instance");
		if(body == "")
			throw new CheckedIllegalArgumentException("Content of empty value when initializing Header class instance");
		this.body = body;
		this.headerTitle = headerTitle;
	}

	public String getValue() {
		return body;
	}

	public HeaderTitle getRequestType() {
		return headerTitle;
	}
		
}
