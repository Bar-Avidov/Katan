import java.util.HashMap;
import java.util.Map;

public abstract class Board_item {
	
	int hexagonIndex;
	int itemIndex;
	
	boolean isEmpty = true;
	int playerID;
	Structure structure; 
	
	Map <Integer, Integer> overlap = new HashMap<>();
	
	public void addOverLap(int hexagon, int item) {
		overlap.put(hexagon,  item);
	}
	
	
	

}
