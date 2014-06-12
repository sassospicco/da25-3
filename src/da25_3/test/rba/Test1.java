package da25_3.test.rba;

import da25_3.people.General;

public class Test1 {
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
		t.run(50, 0, General.DISLOYAL_TRAITOR, 0);

		System.out.println();
		
		/*
		 * No disloyal general, but huge number of them.
		 */
//		t = new StandardTest();
//		t.run(500, 0, General.DISLOYAL_TRAITOR, 0);
//
//		System.out.println();
		
		System.exit(0);
	}
}
