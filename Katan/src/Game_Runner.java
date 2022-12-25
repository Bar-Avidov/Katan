import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Game_Runner {
	
	static int i = 0;
	List<Player> players = new ArrayList<Player>();
	List<Development_Card> DevelopmentCardsList = new ArrayList<Development_Card>();
	
	
	Board board = new Board(players);
	
	
	
	
	
	
	public Game_Runner() {
		players.add(new Player("firstPlayer"));
		players.add(new Player("secondPlayer"));
		players.add(new Player("thirdPlayer"));
		initAndShuffleDevCards();
		Run();
		

	}
	
	public void initAndShuffleDevCards() {
		for (int i = 0; i < 15; i++) {
			DevelopmentCardsList.add(new Development_Card(Enums.DevelopmentCardTypes.Knight));
		}
		for (int i = 0; i < 10; i++) {
			DevelopmentCardsList.add(new Development_Card(Enums.DevelopmentCardTypes.VictoryPoint));
		}
		for (int i = 0; i < 5; i++) {
			DevelopmentCardsList.add(new Development_Card(Enums.DevelopmentCardTypes.BuildRoads));
		}
		for (int i = 0; i < 5; i++) {
			DevelopmentCardsList.add(new Development_Card(Enums.DevelopmentCardTypes.Monopoly));

		}
		for (int i = 0; i < 5; i++) {
			DevelopmentCardsList.add(new Development_Card(Enums.DevelopmentCardTypes.YearOfWealth));

		}
		Collections.shuffle(DevelopmentCardsList);
	}
	
	
	public void Run() {
		Preset();
		Game();
	}
	
	public void Preset() {
		
		List<Player> reverse = new ArrayList<>(players);
		Collections.reverse(reverse);
		
		int hexagon;
		int vertex;
		
		for (Player player : players) {
			hexagon = Integer.parseInt(System.console().readLine());
			vertex = Integer.parseInt(System.console().readLine());
			BuildVillagePreset(hexagon, vertex, player.id , true);
			
			hexagon = Integer.parseInt(System.console().readLine());
			vertex = Integer.parseInt(System.console().readLine());
			BuildRoadPreset(hexagon, vertex, player.id);
		}
		
		for (Player player : reverse) {
			hexagon = Integer.parseInt(System.console().readLine());
			vertex = Integer.parseInt(System.console().readLine());
			BuildVillagePreset(hexagon, vertex, player.id , false);
			BuildRoadPreset(hexagon, vertex, player.id);
		}
		
	}
	public void Game() {
		Player currentPlayer;
		while(true) {
			currentPlayer = getNextPlayer();
			Random rand1 = new Random();
			int dice1 = rand1.nextInt(6) + 1;
			int dice2 = rand1.nextInt(6) + 1;
			
			int sumDice = dice1+ dice2;
			
			
			
			for (Player player : players) {
				player.addCardsToPlayer(sumDice);
			}
			boolean a = false;
			
			String action = System.console().readLine();
			Enums.PlayerActions playerAction = Enums.PlayerActions.valueOf(action);
			while(playerAction != Enums.PlayerActions.DoNothing) {
				
				switch(playerAction) {
					case BuildRoad:
						int hexagon = Integer.parseInt(System.console().readLine());
						int vertex = Integer.parseInt(System.console().readLine());
						
						if (BuildRoad(hexagon, vertex, currentPlayer.id)) {
							System.out.println("success");
						}
						else {
							System.out.println("failure");

						}
					break;
					case BuildVillage:
						int hexagon1 = Integer.parseInt(System.console().readLine());
						int vertex1 = Integer.parseInt(System.console().readLine());
						
						if (BuildVillage(hexagon1, vertex1, currentPlayer.id)) {
							System.out.println("success");
						}
						else {
							System.out.println("failure");

						}
					break;
						
					case  BuildTown:
						int hexagon2 = Integer.parseInt(System.console().readLine());
						int vertex2 = Integer.parseInt(System.console().readLine());
						
						if (BuildTown(hexagon2, vertex2, currentPlayer.id)) {
							System.out.println("success");
						}
						else {
							System.out.println("failure");

						}
						break;
				case BuyDevelopmentCard:
					if (BuyDevelopmentCard(currentPlayer.id)) {
						System.out.println("success");
					}
					else {
						System.out.println("failure");

					}
					break;
				case DoNothing:
					break;
				case OpenDevelopmentCard:
					break;
				case TradeCards:
					break;
				default:
					break;
				case ExchangeCards:
					
					Enums.resource requested = Enums.resource.clay;
					Enums.resource give = Enums.resource.basalt;
					boolean exchange = currentPlayer.ExchageCards(requested, give);
					if (!exchange) {
						break;
					}
					
					
				}
				
				
				
			}

			
		}
		
		
	}
	public Player getNextPlayer() {
		i++;
		return players.get(i%players.size());
		
	}
	
	public boolean BuildVillagePreset(int hexagon, int vertex, int playerId, boolean isFirstMove) {
		if ( board.buildVillage(hexagon, vertex, playerId, false)) {
			players.get(playerId).addMapValueToResource(board.hexagons[hexagon].score, board.hexagons[hexagon].resource, 1);
			players.get(playerId).addPoint(1);
			
			if (isFirstMove) {
				List<Enums.resource> resources = board.GetResourcesFromVertex(hexagon, vertex);
				
				for (Enums.resource resource : resources) {
					players.get(playerId).addResource(resource);
				}
				
			}
			
			return true;
		}
		return false;
	}
	public boolean BuildRoadPreset(int hexagon, int vertex, int playerId) {
		return board.buildRoad(hexagon, vertex, playerId);
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
			
			if (board.vertices[hexagon][vertex].harbour != null) {
				Enums.resource resource = board.vertices[hexagon][vertex].harbour.resource;
				int exchangeRate = board.vertices[hexagon][vertex].harbour.ExchangeRate;
				if (resource == null){
					
					for (Enums.resource temp : Enums.resource.values()) {
						if (players.get(playerId).ExchangeRates.get(temp) == 4) {
							players.get(playerId).ExchangeRates.put(temp, 3);
						}
					}
				}
				else {
					players.get(playerId).ExchangeRates.put(resource, 2);
				}
			}
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
	
	public boolean BuyDevelopmentCard(int playerId) {
		if (players.get(playerId).canAfford(Enums.buildTypes.DevelopmentCard)) {
			players.get(playerId).addDevelopmentCard(DevelopmentCardsList.get(0));
			DevelopmentCardsList.remove(0);
			return true;
		}
		return false;
	}
	
	public boolean openDevelopmentCard(int playerId, int index) {
		if (players.get(playerId).developmentCards.size() >= index) {
			return false;
		}
		switch (players.get(playerId).developmentCards.get(index).cardType) {
		
		case Knight:
				
				break;
		case VictoryPoint:
				players.get(playerId).addPoint(1);
				break;
		case BuildRoads:
				BuildRoadPreset(0, 0, playerId);
				break;
		case YearOfWealth:
				players.get(playerId).addResource(Enums.resource.clay);
				players.get(playerId).addResource(Enums.resource.clay);
				break;
		case Monopoly:
				break;
		}
		return true;
	}
	
	public void addResourcesToPlayers(int num) {
		
		for (Player player : players) {
			player.addCardsToPlayer(num);
		}
		
	}

}
