package da25_3.test.rba;

import da25_3.people.General;

public class Test2 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * No disloyal general. The most simple case.
		 */
		t = new StandardTest();
		t.run(3, 0, General.DISLOYAL_TRAITOR, 0);

		System.out.println();

		/*
		 * No disloyal general, but much larger number of them.
		 */
		t = new StandardTest();
		t.run(8, 8, General.DISLOYAL_TRAITOR, 1);

		System.out.println();
		
		System.exit(0);
	}
}
