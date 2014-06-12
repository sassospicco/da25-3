package da25_3.test.rba;

import da25_3.people.General;

public class Test3 {
	public static StandardTest t;

	public static void main(String[] args) {
		/*
		 * Some disloyal general. They are not enough to jeopardize the
		 * algorithm and they are simply silent. However, the number of rounds
		 * required is usually much higher than the perfect case.
		 */
		t = new StandardTest();
		t.run(20, 4, General.DISLOYAL_SILENT, 4);
	}
}
