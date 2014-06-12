package da25_3.test.rba;

import da25_3.people.General;

public class Test2 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * No disloyal general, but huge number of them.
		 */
		t = new StandardTest();
		t.run(100, 0, General.DISLOYAL_TRAITOR, 0);
	}
}
