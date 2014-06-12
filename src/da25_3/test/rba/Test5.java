package da25_3.test.rba;

import da25_3.people.General;

public class Test5 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * Too many disloyal generals. They actively cheat the onest ones, so
		 * they will decide on different values.
		 */
		t = new StandardTest();
		t.run(8, 8, General.DISLOYAL_TRAITOR, 1);
	}
}
