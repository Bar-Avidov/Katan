import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Board {
	
	public final static int[] rows = {0,3,7,12,16};
	public final static int[] difference = {0, 3, 4, 5, 4};

	
	Hexagon[]hexagons = new Hexagon[19];
	Hexagon bandit = hexagons[9];
	public Vertex[][] vertices = new Vertex[19][6];
	public Edge[][] edges = new Edge[19][6];
	public List<Player> players;
	
	public Board(List<Player> players) {
		
		this.players = players;
		
		//hexagons with no arrays
		int[] score = { 5, 2, 6, 8, 10, 2, 3, 3, 7, 11, 4, 8, 4, 6, 5, 10, 11, 12, 9};
		List<Enums.resource> resourceList = resourcesList();
		Random rand = new Random();
		for (int i = 0; i < 19; i ++) {
			if (i != 7) {
				int j;
				if (resourceList.size() == 1) {
					j = 0;
				}
				else {
					j = rand.nextInt(resourceList.size()-1);
				}
				this.hexagons[i] = new Hexagon(i, resourceList.get(j), score[i]);
				resourceList.remove(j);
			}
			else {
				this.hexagons[i] = new Hexagon(i, null, 7);
			}
		}
		
		//vertices
		for (int i = 0; i < 19; i++) {
			for(int j = 0; j < 6; j++) {
				this.vertices[i][j]= new Vertex(i,j);
				this.hexagons[i].vertices[j] = this.vertices[i][j];
				this.edges[i][j] = new Edge(i,j);
				this.hexagons[i].vertices[j] = this.vertices[i][j];
			}
		}
		
		
		int diff = 0;
		Map<Integer, Integer> DifferenceChange = new HashMap<>();
		DifferenceChange.put(0, 0);
		DifferenceChange.put(3, 3);
		DifferenceChange.put(7, 4);
		DifferenceChange.put(12, 4);

		DifferenceChange.put(16, 3);
		
		
		for(int i = 0; i < 19; i++) {
			
			if (i != 0 && i != 3 && i != 7 && i != 12 && i != 16) {

				this.vertices[i][5] = this.vertices[i-1][1];
				this.vertices[i-1][1].addOverlap(i, 5);
				
				this.vertices[i][4] = this.vertices[i-1][2];
				this.vertices[i-1][2].addOverlap(i, 4);
				
				this.edges[i][4] = this.edges[i-1][1];
				this.edges[i-1][1].addOverLap(i, 4);
				
				
			}
			
			if (DifferenceChange.containsKey(i)) {
				diff = DifferenceChange.get(i);
			}
			
			if(i != 0 && i != 1 && i != 2 && i != 3 && i != 7) {
				
				this.vertices[i][0] = this.vertices[i- 1 - diff][2];
				this.vertices[i][5] = this.vertices[i - 1 -diff][3];
				
				this.vertices[i-1- diff][2].addOverlap(i, 0);
				this.vertices[i-1- diff][3].addOverlap(i, 5);
				
				this.edges[i][5] = this.edges[i-1-diff][2];
				this.edges[i-1-diff][2].addOverLap(i, 5);
				

			}
			
			if(i != 0 && i != 1 && i != 2 && i != 6 && i != 11) {
				
				
				this.vertices[i][0] = this.vertices[i-diff][4];
				this.vertices[i][1] = this.vertices[i-diff][3];
				
				this.vertices[i-diff][4].addOverlap(i,  0);
				this.vertices[i-diff][3].addOverlap(i,  1);
				
				this.edges[i][0] = this.edges[i-diff][3];
				this.edges[i-diff][3].addOverLap(i, 0);
				

			}
			
			
			
		}
		Harbour []harbours = new Harbour[9];
		
		
		harbours[0] = new Harbour(Enums.resource.sheep);
		harbours[1] = new Harbour(null);
		harbours[2] = new Harbour(Enums.resource.basalt);
		harbours[3] = new Harbour(Enums.resource.wheat);
		harbours[4] = new Harbour(null);
		harbours[5] = new Harbour(Enums.resource.wood);
		harbours[6] = new Harbour(Enums.resource.clay);
		harbours[7] = new Harbour(null);
		harbours[8] = new Harbour(null);
		
		vertices[0][1].harbour = harbours[0];
		vertices[1][0].harbour = harbours[0];
		
		vertices[3][1].harbour = harbours[1];
		vertices[3][1].harbour = harbours[1];
		
		vertices[6][0].harbour = harbours[2];
		vertices[6][1].harbour = harbours[2];
		
		vertices[14][1].harbour = harbours[3];
		vertices[14][2].harbour = harbours[3];
		
		vertices[17][2].harbour = harbours[4];
		vertices[17][3].harbour = harbours[4];
		
		vertices[16][3].harbour = harbours[5];
		vertices[16][4].harbour = harbours[5];
		
		vertices[12][3].harbour = harbours[6];
		vertices[12][4].harbour = harbours[6];
		
		vertices[7][4].harbour = harbours[7];
		vertices[7][5].harbour = harbours[7];
		
		vertices[3][5].harbour = harbours[8];
		vertices[3][5].harbour = harbours[8];

		
		
		
		
		
		
		
	}
	
	
	public List<Enums.resource> GetResourcesFromVertex(int hexagon, int vertex){
		List<Enums.resource> resourceList = new ArrayList<>();
		
		for (Map.Entry<Integer, Integer> overlaps : this.vertices[hexagon][vertex].overlap.entrySet()) {
			resourceList.add(hexagons[overlaps.getKey()].resource);
		}
		
		return resourceList;
	}
	
	private List<Enums.resource> resourcesList () {
		
		List<Enums.resource> resourceList = new ArrayList<Enums.resource>();
		resourceList.add(Enums.resource.clay);
		resourceList.add(Enums.resource.clay);
		resourceList.add(Enums.resource.clay);
		
		resourceList.add(Enums.resource.wood);
		resourceList.add(Enums.resource.wood);
		resourceList.add(Enums.resource.wood);
		resourceList.add(Enums.resource.wood);
		
		resourceList.add(Enums.resource.wheat);
		resourceList.add(Enums.resource.wheat);
		resourceList.add(Enums.resource.wheat);
		resourceList.add(Enums.resource.wheat);
		
		resourceList.add(Enums.resource.sheep);
		resourceList.add(Enums.resource.sheep);
		resourceList.add(Enums.resource.sheep);
		resourceList.add(Enums.resource.sheep);
		
		resourceList.add(Enums.resource.basalt);
		resourceList.add(Enums.resource.basalt);
		resourceList.add(Enums.resource.basalt);
		
		return resourceList;
		
	}
	
	
public boolean buildRoad(int hexagon, int edge, int player) {
		
	
	if (edges[hexagon][edge].isEmpty) {
		if (((!vertices[hexagon][edge].isEmpty) && (vertices[hexagon][edge].playerID == player)) || (!vertices[hexagon][edge+1].isEmpty) && (vertices[hexagon][edge+1].playerID == player)) {
			this.edges[hexagon][edge].BuildRoad(player);
			return true;
		}
		for (Map.Entry<Integer, Integer> overlaps : this.edges[hexagon][edge].overlap.entrySet()) {
			int next = (6 + overlaps.getValue()+1)%6;
			int prev = (6 + overlaps.getValue()-1)%6;
			
			if (((!this.edges[hexagon][next].isEmpty) && (this.edges[hexagon][next].playerID == player)) || ((!this.edges[hexagon][prev].isEmpty) && (this.edges[hexagon][prev].playerID == player))){
				this.edges[hexagon][edge].BuildRoad(player);
				return true;
			}
		}
		
	}
	return false;
	
	
		
	}

	public boolean buildVillage(int hexagon, int vertex, int player, boolean isPreset) {
		
		if (this.vertices[hexagon][vertex].isEmpty) {
			
			for (Map.Entry<Integer, Integer> overlaps : this.vertices[hexagon][vertex].overlap.entrySet()) {
				
				int next = (6 + overlaps.getValue()+1)%6;
				if (this.vertices[hexagon][next].isEmpty) {
					return false;
				}
			}
			if (isPreset) {
				this.vertices[hexagon][vertex].BuildVillage(player);
				return true;
			}
			for (Map.Entry<Integer, Integer> overlaps : this.vertices[hexagon][vertex].overlap.entrySet()) {
				
				int edge = overlaps.getValue();
				if (this.edges[overlaps.getKey()][edge].isEmpty && this.edges[hexagon][vertex].playerID == player) {
					this.vertices[hexagon][vertex].BuildVillage(player);
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean buildTown(int hexagon, int vertex, int player) {
		
		if ((!this.vertices[hexagon][vertex].isEmpty) && (this.vertices[hexagon][vertex].structure.getClass() == Village.class && (this.vertices[hexagon][vertex].playerID == player))){
			this.vertices[hexagon][vertex].BuildTown();
			return true;
		}
		return false;
	}
}
