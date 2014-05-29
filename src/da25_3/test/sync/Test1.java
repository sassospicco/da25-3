package da25_3.test.sync;

import da25_3.people.General;

public class Test1 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * No disloyal generals: One commander, three loyal generals. Of course,
		 * the generals must agree on the commander's value.
		 */
		t = new StandardTest();
		t.run(true, General.LOYAL, 3, 0, General.DISLOYAL_TRAITOR, 1);

		System.out.println();
		
		/*
		 * The most simple case of byzantine failure: One commander, two loyal
		 * generals and one disloyal general. The loyal generals will still
		 * agree on the commander's value.
		 */
		t = new StandardTest();
		t.run(true, General.LOYAL, 2, 1, General.DISLOYAL_TRAITOR, 1);

		System.out.println();
		
		/*
		 * The most simple case of disloyal commander: one disloyal commander,
		 * three loyal generals. The generals will agree on a random value.
		 */
		t = new StandardTest();
		t.run(true, General.DISLOYAL_TRAITOR, 3, 0, General.DISLOYAL_TRAITOR, 1);

		System.exit(0);
	}
}
