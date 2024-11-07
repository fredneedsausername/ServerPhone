package fredver.ioutils;

import fredver.errorhandling.CheckedIllegalArgumentException;

public enum RequestType {
	
	// da mettere i vari request types che implementano tostring per essere comunicati fra il server e il client
	NULL,
	GETFILE,
	CLIENT_DISCONNECTS,
	SERVER_SHUTS_OFF
	;
	
	public static RequestType fromString(String input) throws CheckedIllegalArgumentException {
        for (RequestType type : RequestType.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new CheckedIllegalArgumentException("Unknown RequestType: " + input);
    }
	
}
