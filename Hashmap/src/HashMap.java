
public class HashMap {
	
	private int BUCKET_SIZE = 256;
	private Node[] hashmap;
	
	public HashMap(int bucketSize) {
		this.BUCKET_SIZE = bucketSize;
		hashmap = new Node[bucketSize];
		
	}
	
	public HashMap() {
		hashmap = new Node[BUCKET_SIZE];
	}
	
	
	
	public boolean set(String key, String value) {
		
		int hash = Math.abs(key.hashCode() % BUCKET_SIZE);
		//System.out.println(hash);
		
		Node node = new Node(key, value);
		
		//implement try catch
		if(hashmap[hash] == null) {
			hashmap[hash] = node;
			return true;
		}
		else if(hashmap[hash].getKey() == key) {
			//Collision detected
			hashmap[hash].setValue(value);
			return true;
		}
		else {
			Node current = hashmap[hash];
			while(current.getNext() != null) {
				if(current.getNext().getKey().equals(key)) {
					System.out.println("Collision detected");
					current.getNext().setValue(value);
					break;
				}
				current = current.getNext();
			}
				
			current.setNext(node);
			return true;
		}
		
		
	}
	
	public String get(String key) {
		int hash = Math.abs(key.hashCode() % BUCKET_SIZE);
		
		if(hashmap[hash] != null) {
			Node current = hashmap[hash];
			while(current.getKey().equals(key) == false) {
				if(current.getNext() == null) {
					return "Key doesn't exist";
				}
				current = current.getNext();
			}
			
			return current.getValue();
		}
		
		System.out.println("Key does not exist.");
		return null;
	}
	
	
	public String delete(String key) {
		int hash = Math.abs(key.hashCode() % BUCKET_SIZE);
		
		if(hashmap[hash] == null) {
			return "Key doesn't exist";
		}
		
		if(hashmap[hash].getKey().equals(key)) {
			hashmap[hash] = hashmap[hash].getNext();
			return key;
		}
		else {
			Node current = hashmap[hash];
			while(current.getNext() != null) {
				if(current.getNext().getKey().equals(key)) {
					if(current.getNext().getNext() != null) {
						current.setNext(current.getNext().getNext());
					}
					else {
						current.setNext(null);
					}
					
					return key;
				}
				current = current.getNext();
			}	
		}
		
		return "Key doesn't exist";
	}
	
	
	public double getLoad() {
		int elements = 0;
		for(int i=0; i<BUCKET_SIZE; i++) {
			if(this.hashmap[i] != null) {
				elements += 1;
			}
		}
		return ((double) elements) / ((double) BUCKET_SIZE);
	}
	
	public int getBucketSize() {
		return this.BUCKET_SIZE;
	}
	
	public Node getNode(int hash) {
		return hashmap[hash];
	}
	
	

}
