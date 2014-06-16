package da25_3.test.rba;

import da25_3.people.General;

public class Test8 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * 5f = n
		 */
		t = new StandardTest();
		t.run(4, 1, General.DISLOYAL_TRAITOR, 1);
	}
}