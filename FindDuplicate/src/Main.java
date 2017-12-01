
public class Main {

	public static void main(String[] args) {
		int[] arr = new int[10];   // 10 is arbitrary, makes for nice output
		populate(arr);
		printArray(arr);
		System.out.println(findDuplicate(arr));
	}

	private static void populate(int[] arr) {
		RandP rand = new RandP(arr.length);
		for (int i = 0; i < arr.length; i++) {
			arr[i] = rand.nextInt();
			if (arr[i] == arr.length) {
				arr[i] = (int) (Math.random() * (arr.length - 1)) + 1;
			}
		}
	}

	private static int findDuplicate(int[] arr) {
		while (true) {
			if(arr[0] == arr[arr[0]]) {
				return arr[0];
			}
			else {
				swap(arr, 0, arr[0]);
			}
		}
	}
	
	public static void printArray(int[] arr) {
		for (int i : arr) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public static void swap(int[] nums2, int index1, int index2) {
		int temp = nums2[index1];
		nums2[index1] = nums2[index2];
		nums2[index2] = temp;
	}

}
