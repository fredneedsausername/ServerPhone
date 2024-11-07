package fredver.ioutils;

import fredver.errorhandling.CheckedIllegalArgumentException;

public class Header {
	
	private String value;
	private RequestType requestType;
	
	public Header(RequestType requestType, String value) throws CheckedIllegalArgumentException {
		if(requestType == null) 
			throw new CheckedIllegalArgumentException("Request type of value null when initializing Header class instance");
		if(value == "")
			throw new CheckedIllegalArgumentException("Request type of empty value when initializing Header class instance");
		this.value = value;
		this.requestType = requestType;
	}

	public String getValue() {
		return value;
	}

	public RequestType getRequestType() {
		return requestType;
	}
		
}
