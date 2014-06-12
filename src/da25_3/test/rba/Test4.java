package da25_3.test.rba;

import da25_3.people.General;

public class Test4 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * Some disloyal general. They are not enough to jeopardize the
		 * algorithm, but this time they actively try to. This means that some
		 * onest general may reach the decision before the others.
		 */
		t = new StandardTest();
		t.run(20, 4, General.DISLOYAL_TRAITOR, 4);
	}
}
