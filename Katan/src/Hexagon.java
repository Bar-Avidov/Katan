

public class Hexagon {
	
	int index;
	Enums.resource resource;
	int score;
	
	Hexagon[] hexagons = new Hexagon[6];
	Vertex[] vertices = new Vertex[6];
	Edge [] edges = new Edge[6];
	
	public Hexagon(int i, Enums.resource resource, int score) {
		this.index = i;
		this.resource = resource;
		this.score = score;
	}
	
	
}
