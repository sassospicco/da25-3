package da25_3.test.sync;

import da25_3.people.General;

public class Test2 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * F too large. One commander, three loyal generals, two disloyal
		 * generals. Since f = 2 > 5/3 = 1.66, the algorithm might fail. Failure
		 * probability is around 30%.
		 */
		t = new StandardTest();
		t.run(true, General.DISLOYAL_TRAITOR, 5, 1, General.DISLOYAL_TRAITOR, 2);

		System.out.println();

		/*
		 * Silent failure. Same setup as before, but now the disloyal general is
		 * silent. Other generals will assume he always says false. This is the
		 * least damaging case: failure probabililty is around 5%.
		 */
		t = new StandardTest();
		t.run(true, General.DISLOYAL_TRAITOR, 5, 1, General.DISLOYAL_SILENT, 2);

		System.out.println();

		/*
		 * Random failure. Same setup as before, but now the disloyal general
		 * only sends a subset of messages. Failure probability is (clearly) in
		 * a middle ground between previous cases.
		 */
		t = new StandardTest();
		t.run(true, General.DISLOYAL_TRAITOR, 5, 1, General.DISLOYAL_SPOTTY, 2);

		System.exit(0);
	}
}
