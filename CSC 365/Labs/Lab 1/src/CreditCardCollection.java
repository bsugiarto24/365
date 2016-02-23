import java.util.ArrayList;
import java.util.HashMap;


public class CreditCardCollection {
	private HashMap<String, CreditCard> map;

	public CreditCardCollection(){
		map = new HashMap<String, CreditCard>();
	}
	
	public CreditCard get(String num) {
		return map.get(num);
	}
	
	public boolean add(CreditCard card){
		if(map.put(card.number(), card) != null)
			return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> numbers(){
		return (ArrayList<String>) map.keySet();
	}
	
}
