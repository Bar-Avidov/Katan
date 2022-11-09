import java.util.ArrayList;
import java.util.List;

public class Game_Runner {
	
	
	List<Player> players = new ArrayList<Player>();
	
	Board board = new Board(players);
	
	
	
	
	
	
	public Game_Runner() {
		
	}
	
	
	public void Run() {
		Preset();
		Game();
	}
	
	public void Preset() {
		
	}
	public void Game() {
		
	}
	
	public boolean BuildRoad(int hexagon, int vertex, int playerId) {
		if (players.get(playerId).canAfford(Enums.buildTypes.Road) && board.buildRoad(hexagon, vertex, playerId)) {
			players.get(playerId).pay(Enums.buildTypes.Road);
			return true;
		}
		return false;
	}
	
	public boolean BuildVillage(int hexagon, int vertex, int playerId) {
		if (players.get(playerId).canAfford(Enums.buildTypes.Village) && board.buildVillage(hexagon, vertex, playerId, false)) {
			players.get(playerId).addMapValueToResource(board.hexagons[hexagon].score, board.hexagons[hexagon].resource, 1);
			players.get(playerId).addPoint(1);
			players.get(playerId).pay(Enums.buildTypes.Village);
			return true;
		}
		return false;
	}
	public boolean BuildTown(int hexagon, int vertex, int playerId) {
		if (players.get(playerId).canAfford(Enums.buildTypes.Town) && board.buildVillage(hexagon, vertex, playerId, false)) {
			players.get(playerId).addMapValueToResource(board.hexagons[hexagon].score, board.hexagons[hexagon].resource, 1);
			players.get(playerId).addPoint(1);
			players.get(playerId).pay(Enums.buildTypes.Town);
			return true;
		}
		return false;
	}
	
	
	public void addResourcesToPlayers(int num) {
		
		for (Player player : players) {
			player.addCardsToPlayer(num);
		}
		
	}

}
