package da25_3.test.rba;

import da25_3.people.General;

public class Test6 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * Too many disloyal generals. They sit silent, so the onest ones will
		 * never be able to reach a decision.
		 */
		t = new StandardTest();
		t.run(20, 6, General.DISLOYAL_SILENT, 6);
	}
}
