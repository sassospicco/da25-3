package da25_3.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class Node implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean value;
	public HashMap<Integer, Node> children = new HashMap<>();
	
	public Node(boolean value) {
		this.value = value;
	}
	
	public void adopt(int id, Node child) {
		children.put(id, child);
	}
	
	public Node get(int id) {
		if (children.containsKey(id)) {
			return children.get(id);
		} else {
			return new Node(false);
		}
	}
	
	public boolean containsKey(int id) {
		return children.containsKey(id);
	}
	
	public void deleteId(int id) {
		children.remove(id);
		for (Node child : children.values()) {
			child.deleteId(id);
		}
	}
	
	public boolean decide() {
		if (children.isEmpty()) {
			return value;
		} else {
			int counter = value ? +1 : -1;
			for (Node child : children.values()) {
				int childDecision = child.decide() ? +1 : -1;
				counter = counter + childDecision;
			}
			return counter > 0;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		
		if (children.isEmpty()) {
			b.append("{"+value+"}");
		} else {
			b.append("{"+value+":[");
			for (Entry<Integer, Node> entry : children.entrySet()) {
				b.append(entry.getKey());
				b.append(":");
				b.append(entry.getValue().toString());
				b.append(",");
			}
			b.setLength(b.length() - 1);
			b.append("]}");
		}
		
		return b.toString();
	}
}
