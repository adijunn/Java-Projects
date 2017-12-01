	public class HeapSort {




	/*public HeapSort(int[] a) {

		heap(a);

	}*/

	int left(int n) {

		return (2*n) + 1;

	}


	int right(int n) {

		return (2*n) + 2; 

	}


	boolean hasRight(int n, int[] a, int left, int right) {

		return (2*n)+2 < right+1;

	}


	boolean hasChildren(int n, int[] a, int left, int right) {

		return (2*n)+1 < right+1;

		}


	int maxChildren(int n, int[] a, int left, int right) {

		if(hasRight(n, a, left, right)) {

			if(a[left(n)] > a[right(n)]) {
				return left(n);
			}
			else {
				return right(n);
			}

		}

		else {
			return left(n);
		}

	}


	void heap(int[] a, int left, int right) {
	if(heapified(a, left, right)) {
		for(int i = 0; i<a.length; i++) {
			System.out.print(a[i] + " ");
		}
		
		sort(a, left, right);
	}

	else{
		for(int i=right; i>=left; i--) {

			trickle(i, a, left, right);
			
			

		}
		heap(a, left, right);
	}

	}
	
	boolean heapified(int[] a, int left, int right) {
		boolean check = false;
		for(int i=right; i>=left; i--) {
			if(hasChildren(i, a, left, right)) {
				if(a[i] > a[maxChildren(i, a, left, right)]) {
					check = true;
				}
				else {
					check = false;
					return check;
				}
			}
				
		}
		
		return check;
	}


	void trickle(int n, int[] a, int left, int right) {
		
		if(hasChildren(n, a, left, right)) {

			if(a[n] < a[maxChildren(n, a, left, right)]) {

				
				swap(a, maxChildren(n, a, left, right), n);
				n = maxChildren(n, a, left, right);
			
				System.out.print("   " + n);

				trickle(n, a, left, right);

			}
		}
		

		}
	
	void sort(int[] a, int left, int right) {
		
		for(int i = right; i>=left; i--) {
			System.out.println("");
			swap(a, 0, i);
			for(int z = 0; z<a.length; z++) {
				System.out.print(a[z] + " ");
			}
			trickle(0, a, left, --right);
			System.out.println("");
			for(int z = 0; z<a.length; z++) {
				System.out.print(a[z] + " ");
			}
		}
		for(int i = 0; i<a.length; i++) {
			System.out.print(a[i] + " ");
		}
	
		
	}
		
		
		void swap(int[] a, int i, int j) {
		    int temp = a[i];
		    a[i] = a[j];
		    a[j] = temp;
		}
		
	}
	
	
	