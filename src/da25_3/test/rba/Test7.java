package da25_3.test.rba;

import da25_3.people.General;

public class Test7 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * Large network test.
		 */
		t = new StandardTest();
		t.run(70, 0, General.DISLOYAL_TRAITOR, 13);
	}
}
