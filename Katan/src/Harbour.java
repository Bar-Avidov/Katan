
public class Harbour {

	Enums.resource resource;
	int ExchangeRate;
	
	public Harbour(Enums.resource resource) {
		if (resource == null) {
			this.ExchangeRate = 3;
		}
		else {
			this.resource = resource;
			this.ExchangeRate = 2;
		}
	}
}
