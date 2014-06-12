package da25_3.entities;

public class RbaMessage extends Message {
	private static final long serialVersionUID = 1L;
	
	public static final int VALUE_TRUE = 1;
	public static final int VALUE_FALSE = 0;
	public static final int VALUE_QUESTION = -1;

	public int phase;
	public int round;
	public int value;
	
	public RbaMessage(int from, int to, int phase, int round, int value) {
		this.from = from;
		this.to = to;
		this.phase = phase;
		this.round = round;
		this.value = value;
	}
}
