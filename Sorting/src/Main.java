
public class Main {

	public static void main(String[] args) {
		int[] array = {6, 2, 5, 4, 7, 1, 10, 9, 3, 8};
		
		//HeapSort hello = new HeapSort();
		//hello.heap(array, 0, array.length-1);
		
		MergeSort sort = new MergeSort();
		sort.mergesort(array);
		for(int i = 0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	

}
