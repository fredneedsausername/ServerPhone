package fredver.ioutils;

import fredver.errorhandling.CheckedIllegalArgumentException;

public enum HeaderTitle {
	
	NULL,
	LIST_FILES_AND_DIRECTORIES,
	GET_FILE_OR_FOLDER,
	PUBLISH_FILE_OR_FOLDER,
	NOT_SUFFICIENT_STORAGE,
	NEW_TLS_CERTIFICATE,
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
