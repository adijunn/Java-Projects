import java.util.Random;


public class ParkCars {

	Random rand;
	
	public ParkCars(double n) {
		rand = new Random();
		randomlyAssign(n);
	}
	
	public void randomlyAssign(double n) {
		double track = 0;
		int numCars = 0;
		while(track < n) {
			track += (rand.nextDouble() + 1.0);
			numCars++;
		}
		
		System.out.println(numCars);
	}
}
