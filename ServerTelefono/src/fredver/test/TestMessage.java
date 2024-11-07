package fredver.test;

import fredver.constants.Constants;
import fredver.errorhandling.CheckedIllegalArgumentException;
import fredver.errorhandling.InvalidMessageException;
import fredver.errorhandling.NotRecognizedRequestTypeException;
import fredver.ioutils.Header;
import fredver.ioutils.Message;
import fredver.ioutils.RequestType;

public class TestMessage {

	public static void main(String[] args) throws NotRecognizedRequestTypeException {
		try {
			Message mes = new Message("GETFILE" + 
		Constants.HEADER_AND_HEADER_DESCRIPTION_SEPARATOR + "test" +
					Constants.HEADER_AND_HEADER_SEPARATOR
					+ Constants.HEADER_AND_MESSAGE_SEPARATOR + "Supertesty");
			
			System.out.println(mes.getContent());
			for(Header h : mes.getHeaders()) {
				System.out.println(h.getRequestType());
				System.out.println(h.getValue());
			}
			
			System.out.println("--------");
			
			try {
				Message mes2 = new Message(new Header[] {
						new Header(RequestType.GETFILE, "Hello"),
				}, new String("Content"));
				System.out.println(mes2.getContent());
				for(Header h : mes2.getHeaders()) {
					System.out.println(h.getRequestType());
					System.out.println(h.getValue());
				}
				System.out.println(mes2.toString());
			} catch (CheckedIllegalArgumentException e) {
				e.printStackTrace();
			}
			
		} catch (InvalidMessageException e) {
			e.printStackTrace();
		}
		
	}

}
