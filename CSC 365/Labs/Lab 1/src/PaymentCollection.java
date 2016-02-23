import java.util.ArrayList;
import java.util.HashMap;


public class PaymentCollection {
	private HashMap<String, ArrayList<Payment>> map;
	
	public PaymentCollection(){
		map = new HashMap<String, ArrayList<Payment>>();
	}
	
	public ArrayList<Payment> get(String card) {
		return map.get(card);
	}
	
	public void add(Payment pay){
		
		if(map.get(pay.card) == null) {
			ArrayList<Payment> arr = new ArrayList<Payment>();
			arr.add(pay);
			map.put(pay.card, arr);
		}
		else{
			map.get(pay.card).add(pay);
		}
	}
	
	public String toString() {
		return map.toString();
	}
	
}
