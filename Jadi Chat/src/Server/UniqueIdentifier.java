package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueIdentifier {

	
	private static List<Integer> ids = new ArrayList<Integer>();
	private static final int RANGE = 100;
	private static int index = 0;
	
	static {
		for(int i = 0; i < 1000; i++){
			ids.add(i);
		}
		Collections.shuffle(ids);
	}
	
	public static int getIdentifier(){
		return ids.get(index++);
	}
}