package fredver.constants;

public class Constants {

	public static final int SERVER_PORT_NUMBER = 48484;
	public static final int SOCKET_BUFFER_SIZE = 512 * 1024; // 512KB for socket buffer // Socket buffer size can never be less than the metadata size
	public static final int READ_TIMEOUT = 15_000; // 15 seconds
	public static final int HEATBEAT_INTERVAL = 5_000; // 5 seconds
	public static final int UUID_LENGTH = 32; // 32 hexadecimal numbers
	public static final int MAX_PATH = 4096; // max linux path length, which has the longest max path length
	public static final int DEFAULT_SERVER_FOLDER_SIZE = 5 * 1024 * 1024; // 5MBs to keep all the user info, keys, and other server stuff
	
	public static final char FILE_NAME_AND_FILE_DATA_SEPARATOR = '@'; // not used in base85 encoding, which is used to send data of file names and data
	public static final char FILE_DATA_AND_NEXT_FILE_NAME_SEPARATOR = '^'; // not used in base85 encoding, which is used to send data of file names and data
	public static final char MESSAGE_END_SEPARATOR = '[';
	public static final char MESSAGE_HEADER_FIELDS_SEPARATOR = '|';
	
	
	public static final String SERVER_FOLDER_NAME = "Server";
	public static final String SERVER_USERS_INFO_NAME = "UsersInfo.txt"; // To keep info like the allocated space and if they are an admin
	public static final String SERVER_USERS_FOLDER_NAME = "Users";
	public static final String NULL_VALUE = "null";
	
}
