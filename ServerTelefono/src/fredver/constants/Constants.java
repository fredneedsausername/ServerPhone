package fredver.constants;

public class Constants {

	public static final int SERVER_PORT_NUMBER = 48484;
	public static final int SOCKET_BUFFER_SIZE = 512 * 1024; // 512KB for socket buffer // Socket buffer size can never be less than the metadata size
	public static final int READ_TIMEOUT = 15_000; // 15 seconds
	public static final int HEATBEAT_INTERVAL = 5_000; // 5 seconds
	public static final String NULL_VALUE = "null";
	
	public static final char FILE_NAME_AND_FILE_DATA_SEPARATOR = '@'; // not used in base85 encoding, which is used to send data of file names and data
	public static final char FILE_DATA_END = '^'; // not used in base85 encoding, which is used to send data of file names and data
	public static final char MESSAGE_HEADER_TITLE_AND_BODY_SEPARATOR = ':';
	public static final char MESSAGE_RAW_DATA_LENGTH_AND_HEADER_TITLE_SEPARATOR = ';';
	public static final char MESSAGE_HEADER_BODY_AND_RAW_DATA_SEPARATOR = '|';
	
}
