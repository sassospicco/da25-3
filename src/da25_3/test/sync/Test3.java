package da25_3.test.sync;

import da25_3.people.General;

public class Test3 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * Even larger network: One commander, six loyal generals, three disloyal
		 * generals. This is still a working setup for the algorithm.
		 */
		 t = new StandardTest();
		 t.run(true, General.LOYAL, 6, 3, General.DISLOYAL_TRAITOR, 3);

		System.out.println();

		/*
		 * Value swap. Same setup as before, but with different commander's
		 * value.
		 */
		 t = new StandardTest();
		 t.run(false, General.LOYAL, 6, 3, General.DISLOYAL_TRAITOR, 3);

		System.out.println();

		/*
		 * Commander disloyal. F is still three, but now one of the traitor is
		 * the commander. The algorithm still works, but generals agree on a
		 * random value.
		 */
		t = new StandardTest();
		t.run(true, General.DISLOYAL_TRAITOR, 7, 2, General.DISLOYAL_TRAITOR, 3);

		System.out.println();
		
		/*
		 * Insufficient number of rounds. Same setup as before, but with a round
		 * less. The algorithm now might fail (it happens 20% of times, roughly).
		 */
		t = new StandardTest();
		t.run(true, General.DISLOYAL_TRAITOR, 7, 2, General.DISLOYAL_TRAITOR, 2);

		System.out.println();

		System.exit(0);
	}
}
