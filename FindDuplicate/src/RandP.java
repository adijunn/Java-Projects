public class RandP {
	private int counter;
	private int[] nums;
	
	public RandP(int n) {
		nums = new int[n];
		initNums(nums);
		counter = n;
	}
	
	public void initNums(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i+1;
		}
	}
	
	public int nextInt() {
		if (counter == 0) return 0;
		
		int position = (int) (counter * Math.random());
		counter--;
		int returnValue = nums[position];
		nums[position] = nums[counter];
		return returnValue;
	}
}