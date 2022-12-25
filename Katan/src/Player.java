import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

	public static int num = 0; 
	String name;
	int id;
	int points;
	
	List<Structure> structures;
	List<Development_Card> developmentCards = new ArrayList<Development_Card>();

	Map<Enums.resource, Integer> hand = new HashMap<>();
	Map<Integer, Map<Enums.resource, Integer>> mapValueToResource;
	Map<Enums.resource, Integer> ExchangeRates = new HashMap<>();
	
	public Player(String name) {
		this.name = name;
		this.id = getNextId();
		this.points = 0;
		structures = new ArrayList<>();
		
		
		
		
		for (Enums.resource res : Enums.resource.values()) {
			hand.put(res, 0);
			ExchangeRates.put(res, 4);
		}
	}
	
	public void addResource(Enums.resource resource) {
		this.hand.put(resource, this.hand.get(resource) + 1);
	}
	
	public int getNextId() {
		return ++num;
	}
	
	public boolean ExchageCards(Enums.resource requested, Enums.resource give) {
		if (hand.get(give) >= ExchangeRates.get(give)) {
			hand.put(give, hand.get(give) - ExchangeRates.get(give));
			hand.put(requested, hand.get(requested) + 1);
			return true;
		}
		return false;
	}
	
	public void addMapValueToResource(int num, Enums.resource resource, int count) {
		
		if (!mapValueToResource.containsKey(num)) {
			mapValueToResource.put(num, new HashMap<>());
			mapValueToResource.get(num).put(resource, count);
			return;
		}
		if (!mapValueToResource.get(num).containsKey(resource)) {
			mapValueToResource.get(num).put(resource, count);
			return;
		}
		int value = mapValueToResource.get(num).get(resource) + 1;
		mapValueToResource.get(num).put(resource, value);
		return;
		
		
	}
	
	public void addDevelopmentCard(Development_Card card) {
		developmentCards.add(card);
	}
	
	public void addCardsToPlayer(int num) {
		
		if (this.mapValueToResource.containsKey(num)) {
			Map<Enums.resource, Integer> map = this.mapValueToResource.get(num);
			for (Map.Entry<Enums.resource, Integer> a : map.entrySet()) {
				int count = hand.get(a.getKey());
				hand.put(a.getKey(), count + a.getValue());
			}
		}
	}
	public void addPoint(int point) {
		points = points + point;
	}
	public void reducePoint(int point) {
		points = points - point;
	}
	public boolean canAfford(Enums.buildTypes buildType) {
		
		switch (buildType){
			case Road:
				return (hand.get(Enums.resource.wood) > 0) && (hand.get(Enums.resource.clay) > 0);
			case Village:
				return ((hand.get(Enums.resource.wood) > 0) && (hand.get(Enums.resource.clay) > 0) && (hand.get(Enums.resource.sheep) > 0) && (hand.get(Enums.resource.wheat) > 0));
			case Town:
				return (hand.get(Enums.resource.basalt) > 2 && (hand.get(Enums.resource.wheat) > 1));
			case DevelopmentCard:
				return (hand.get(Enums.resource.basalt) > 0 && (hand.get(Enums.resource.wheat) > 0) && (hand.get(Enums.resource.sheep) > 0));

		}
		return false;
	}
public void pay(Enums.buildTypes buildType) {
		
		switch (buildType){
			case Road:
				hand.put(Enums.resource.wood, hand.get(Enums.resource.wood) - 1);
				hand.put(Enums.resource.clay, hand.get(Enums.resource.clay) - 1);
				break;
			case Village:
				hand.put(Enums.resource.wood, hand.get(Enums.resource.wood) - 1);
				hand.put(Enums.resource.clay, hand.get(Enums.resource.clay) - 1);
				hand.put(Enums.resource.wheat, hand.get(Enums.resource.wheat) - 1);
				hand.put(Enums.resource.sheep, hand.get(Enums.resource.sheep) - 1);
				break;
			case Town:
				hand.put(Enums.resource.basalt, hand.get(Enums.resource.basalt) - 3);
				hand.put(Enums.resource.wheat, hand.get(Enums.resource.wheat) - 2);
			case DevelopmentCard:
				hand.put(Enums.resource.basalt, hand.get(Enums.resource.basalt) - 1);
				hand.put(Enums.resource.wheat, hand.get(Enums.resource.wheat) - 1);
				hand.put(Enums.resource.sheep, hand.get(Enums.resource.sheep) - 1);
				break;
				
		}
	}
	
	
	
}
