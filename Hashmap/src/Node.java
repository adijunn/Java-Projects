
public class Node {

	private String key;
	private String value;
	private Node nextNode;
	
	
	
	// value temporarily set to string -- should ideally be an arbitrary object
	public Node(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	
	public String getKey(){
		return this.key;
	}
	
	public Node getNext() {
		return this.nextNode;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setNext(Node node) {
		this.nextNode = node;
	}
	
	
}
