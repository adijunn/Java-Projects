
public class Position {
	
	private int[] array;
	private static final int TIME = 10;
	private int REF = 0;
	private int SAMPLE_SIZE;
	private int distance = 0;
	
	public Position(int[] a) {
		this.array = a;
		SAMPLE_SIZE = a.length;
		calibrate(a);
	}
	
	void calibrate(int[] a) {
		//NO MOVEMENT WHEN CALIBRATING----OBTAINING REF VALUE
		int count = 0;
		for(int i = 0; i<a.length; i++) {
			count = count + a[i];
		}
		REF = count / SAMPLE_SIZE;
	}
	
	public int getVelocity(int i) {
		if(i == 0) {
			return 0;
		}
		else {
			return getVelocity(i-1) + (array[i]-REF)*TIME;
		}
	}
	
	public int getDistance(int i) {
		if(array[i] == REF && array[i-1] == REF) {
			return 0;
		}
		else {
			return ((getVelocity(i) + getVelocity(i-1)) / 2) * TIME;
		}
	}
	
	public void accDistance(int time) {
		if(time == 0) {
			System.out.println(distance);
		}
		else {
			distance = distance + getDistance(time / TIME);
			accDistance(time - TIME);
		}
	}
	
	
	
	
	
	
}
