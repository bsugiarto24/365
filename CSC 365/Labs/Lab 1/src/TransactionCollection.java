import java.util.ArrayList;
import java.util.HashMap;


public class TransactionCollection {
	private HashMap<String, ArrayList<Transaction>> map;
	
	public TransactionCollection(){
		map = new HashMap<String, ArrayList<Transaction>>();
	}
	
	public ArrayList<Transaction> get(String card) {
		return map.get(card);
	}
	
	public void add(Transaction trans){
		
		if(map.get(trans.cardNum) == null) {
			ArrayList<Transaction> arr = new ArrayList<Transaction>();
			arr.add(trans);
			map.put(trans.cardNum, arr);
		}
		else{
			map.get(trans.cardNum).add(trans);
		}
	}
	
	public String toString() {
		return map.toString();
	}
	
}
