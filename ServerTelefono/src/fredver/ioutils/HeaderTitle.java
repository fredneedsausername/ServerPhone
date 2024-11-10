package fredver.ioutils;

import fredver.errorhandling.CheckedIllegalArgumentException;

public enum HeaderTitle {
	LIST_FILES_AND_DIRECTORIES, IMPLEMENT THIS
	GET_FILE_OR_FOLDER, IMPLEMENT THIS
	PUBLISH_FILE_OR_FOLDER, 	IMPLEMENT THIS
	PATH_OPERATION,	IMPLEMENT THIS
	
	NEW_CERTIFICATE IMPLEMENT THIS
	;
	
	public static HeaderTitle fromString(String input) throws CheckedIllegalArgumentException {
        for (HeaderTitle type : HeaderTitle.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new CheckedIllegalArgumentException("Unknown HeaderTitle: " + input);
    }
	
}
