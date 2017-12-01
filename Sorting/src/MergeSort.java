
public class MergeSort {

		public void mergesort(int[] a) {
			ms(a, 0, a.length-1);
		}
		
		public void ms(int[] a, int left, int right) {
			if(left < right) {
				int mid = (left + right) / 2;
				ms(a, left, mid);
				ms(a, mid+1, right);
				merge(a, left, mid, right);
			}
		}
		
		public void merge(int[] a, int left, int mid, int right) {
			int[] b = new int[a.length];
			for(int i = left; i<=right; i++) {
				b[i] = a[i];
			}
			int j = mid+1;
			int k = left;
			int i = left;
			
			while(i<=mid && j<=right) {
				if(b[i] <= b[j]) {
					a[k] = b[i];
					k++;
					i++;
				}
				else {
					a[k] = b[j];
					j++;
					k++;
				}
			}
			
			while(i<=mid) {
				a[k] = b[i];
				k++;
				i++;
			}
		}
}
