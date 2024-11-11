package fredver.test.message;

/**
 * A simple class used in testing to verify that the valid message parsing regex is correct
 */
public class MessageStructureTest {

	public static void main(String[] args) {
		String regex = "^([a-fA-F0-9]{32})\\|([0-9]{1,15})\\|([^|]{1,50})\\|([^|]{1,4096})$";
		
		// all strings not hexadecimal and digit
		String test1 = "hello-test;hello:lol|hehe";
		String test2 = "hello-test;:lol|hehe";
		
		// correct format
		String test3 = "aaabbbcccaaabbbcccaaabbbcccaaabb-001;LOLOLOLOLOLO:POPOPOPOPOPO|KOKOKOKOKO";
		String test4 = "aaabbbcccaaabbbcccaaabbbcccaaabb|010|LOLOLOLOLOLO|POPOPOPOPOPO";
		String test5 = "aaabbbcccaaabbbcccaaabbbcccaaabb-111;LOLOLOLOLOLO:POPOPOPOPOPO";
		
		// correct format with leading or up to 2 trailing zeros
		String test6 = "aaabbbcccaaabbbcccaaabbbcccaaabb;11100;LOLOLOLOLOLO;POPOPOPOPOPO;KOKOKOKOKO";
		String test7 = "aaabbbcccaaabbbcccaaabbbcccaaabb-0000111;LOLOLOLOLOLO:POPOPOPOPOPO|KOKOKOKOKO";
		
		
		// correct format with missing fields
		String test8 = "-123;LOLO:POPO";
		
		System.out.println("test1: " + (test1.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test2: " + (test2.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test3: " + (test3.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test4: " + (test4.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test5: " + (test5.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test6: " + (test6.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test7: " + (test7.matches(regex) ? "matches" : "doesn't match"));
		System.out.println("test8: " + (test8.matches(regex) ? "matches" : "doesn't match"));
	}

}
