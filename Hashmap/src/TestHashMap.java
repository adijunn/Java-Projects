
public class TestHashMap {
	
	private HashMap hashmap;
	private final int BUCKETS;
	
	public TestHashMap(HashMap hashmap, int bucketSize) {
		this.hashmap = hashmap;
		this.BUCKETS = bucketSize;
	}
	
	public void testSetAndGet() {
		hashmap.set("TestKey", "TestValue");
		if(hashmap.get("TestKey").equals("TestValue")) {
			System.out.println("Set and Get Tests Passed!");
		}
		else {
			System.out.println("Set and Get Tests Failed!");
		}
	}
	
	public void testCollisions() {
		hashmap.set("TestKey", "NewTestValue");
		if(hashmap.get("TestKey").equals("NewTestValue")) {
			System.out.println("Collision Test Passed!");
		}
		else {
			System.out.println("Collision Test Failed!");
		}
		
		for(int i = 0; i < BUCKETS; i++){
            hashmap.set(Integer.toString(i), Integer.toString(i));
        }
        /* Test all values of the get method */
        for(int i = 0; i < BUCKETS; i++){
            String value = hashmap.get(Integer.toString(i));
            assert Integer.toString(i).equals(value): "Failure";
        }
	}
	
	public void testDelete() {
		this.deleteHashMap(); //starting with blank hashmap to test delete
		hashmap = new HashMap();
		//Test case 1 testing to see if delete works for primary node in linked list
		String key = "FB";
		String value = "Value1";
		hashmap.set(key, value);
		hashmap.set("Ea", "Value 2"); // Ea has the same hash as FB
		int hash = Math.abs(key.hashCode() % BUCKETS);
		Node next = hashmap.getNode(hash).getNext();
		hashmap.delete(key);
		if(hashmap.get(key) != value && hashmap.getNode(hash) == next) {
			System.out.println("Delete Test Passed!");
		}
		else {
			System.out.println("Delete Test Failed!");
		}
		
	}
	
	public void testLoad() {
		double load = (1.0 / 256.0);
		if(Double.compare(hashmap.getLoad(), load) == 0) {
			System.out.println("Load Test Passed!");
		}
		else {
			System.out.println("Load Test Failed!");
		}
	}
	
	public void deleteHashMap() {
		this.hashmap = null;
	}
	
	
}
