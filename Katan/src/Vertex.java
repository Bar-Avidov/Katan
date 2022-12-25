

public class Vertex extends Board_item{
	
	public Harbour harbour = null;
	
	
	
	//special trade
	
	public Vertex(int hexagon, int vertex) {
		this.hexagonIndex = hexagon;
		this.itemIndex = vertex;
		this.overlap.put(hexagon, vertex);
	}
	
	public void addOverlap(int hexagon, int vertex) {
		this.overlap.put(hexagon, vertex);
	}
	
	public void BuildVillage(int player) {
		
		structure = new Village();
		isEmpty = false;
		playerID = player;
		
	}

	public void BuildTown() {
		
		structure = new Town();
		
	}
}
