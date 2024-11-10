package fredver.ioutils;

import fredver.errorhandling.CheckedIllegalArgumentException;

public class Header {
	
	private String body;
	private HeaderTitle headerTitle;
	
	public static final String HEADER_BODY_GRANTED = "GRANTED";
	public static final String HEADER_BODY_NOT_ENOUGH_STORAGE = "NOT_ENOUGH_STORAGE";
	public static final String HEADER_BODY_NOT_AUTHORIZED = "NOT_AUTHORIZED";
	public static final String HEADER_BODY_NON_EXISTENT_FOLDER = "NON_EXISTENT_FOLDER";
	public static final String HEADER_BODY_INVALID_HEADER_FORMAT = "INVALID_HEADER_FORMAT"; 
	
	public static final String HEADER_BODY_CD = "CD";
	public static final String HEADER_BODY_RMDIR = "RMDIR";
	public static final String HEADER_BODY_DEL = "DEL";
	
	/**
	 * 
	 * @param headerTitle The title of the header
	 * @param body The body of the header
	 * @throws CheckedIllegalArgumentException If headerTitle is null (it doesn't have sense to
	 * create a header with no type) or if the body is an empty string {@code ""} because it can
	 * be null to represent an empty value but cannot be an empty string
	 */
	public Header(HeaderTitle headerTitle, String body) throws CheckedIllegalArgumentException {
		if(headerTitle == null) 
			throw new CheckedIllegalArgumentException("Request type of value null when initializing Header class instance");
		if(body == "")
			throw new CheckedIllegalArgumentException("Content of empty value when initializing Header class instance");
		this.body = body;
		this.headerTitle = headerTitle;
	}

	/**
	 * 
	 * @return The body of the header
	 */
	public String getBody() {
		return body;
	}

	/**
	 * 
	 * @return The title of the header
	 */
	public HeaderTitle getTitle() {
		return headerTitle;
	}
		
}
