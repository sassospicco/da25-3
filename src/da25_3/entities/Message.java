package da25_3.entities;

import java.io.Serializable;

public abstract class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	public int from;
	public int to;
}
