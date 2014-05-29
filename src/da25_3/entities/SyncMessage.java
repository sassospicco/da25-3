package da25_3.entities;


public class SyncMessage extends Message {
	private static final long serialVersionUID = 1L;

	public int round;
	public Node content;
	
	public SyncMessage(int from, int to, int round, Node content) {
		this.from = from;
		this.to = to;
		this.round = round;
		this.content = content;
	}
}
