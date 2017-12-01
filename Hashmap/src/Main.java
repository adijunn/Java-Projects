
public class Main {

	public static void main(String[] args) {
		
		HashMap hashmap = new HashMap(); //default 256 buckets
		TestHashMap test = new TestHashMap(hashmap, hashmap.getBucketSize());
		//must keep tests in this order
		test.testSetAndGet();
		test.testCollisions();
		test.testDelete();
		test.testLoad();
		
	}


}
