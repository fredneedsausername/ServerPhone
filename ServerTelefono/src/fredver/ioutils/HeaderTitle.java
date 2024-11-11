package fredver.ioutils;

import fredver.errorhandling.CheckedIllegalArgumentException;


/**
 * An enumeration to represent the various header titles that could appear in a message.
 * @see Documentation: "Header titles" section in https://github.com/fredneedsausername/ServerPhone/edit/master/README.md
 */
public enum HeaderTitle {
	
	
	LIST_FILES_AND_DIRECTORIES, IMPLEMENT THIS
	GET_FILE_OR_FOLDER, IMPLEMENT THIS
	PUBLISH_FILE_OR_FOLDER, 	IMPLEMENT THIS
	PATH_OPERATION,	IMPLEMENT THIS
	
	ERROR_CODE,
	
	NEW_CERTIFICATE IMPLEMENT THIS
	PUBLIC_KEY IMPLEMENT THIS
	;
	
	/**
	 * 
	 * @param input The string to turn into a header, case insensitive
	 * @return The created {@code HeaderTitle}
	 * @throws CheckedIllegalArgumentException If the input string is invalid
	 */
	public static HeaderTitle fromString(String input) throws CheckedIllegalArgumentException {
        for (HeaderTitle type : HeaderTitle.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new CheckedIllegalArgumentException("Unknown HeaderTitle: " + input);
    }
	
	public String toString() {
		return this.name();
	}
	
}
