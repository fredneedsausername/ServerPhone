package fredver.test.message;

public class MessageStructureTest {

	public static void main(String[] args) {
		String regex = "^([a-fA-F0-9]{32})-([0-9]{1,3});([^;:\\|]{1,50}):([^;:\\|]{1,4096})\\|(.+)$";
		
		// all strings not hexadecimal and digit
		String test1 = "hello-test;hello:lol|hehe";
		String test2 = "hello-test;:lol|hehe";
		
		// correct format
		String test3 = "aaabbbcccaaabbbcccaaabbbcccaaabb-123;LOLOLOLOLOLO:POPOPOPOPOPO|KOKOKOKOKO";
		
		// correct format with missing fields
		String test4 = "-123;LOLO:POPO";
		
		System.out.println("test1: " + (test1.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test2: " + (test2.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test3: " + (test3.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test4: " + (test4.matches(regex) ? "matches" : "doesn't match"));
	}

}
