

public class Edge extends Board_item{
	
	
	
	
	public Edge(int hexagon, int edge) {
		this.hexagonIndex = hexagon;
		this.itemIndex = edge;
		this.overlap.put(hexagon, edge);
	}
	
public void BuildRoad(int player) {
		
		structure = new Road();
		isEmpty = false;
		playerID = player;
		
	}
	
}
