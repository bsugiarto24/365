import java.util.ArrayList;
import java.util.HashMap;


public class OwnershipCollection {
	private HashMap<Customer, ArrayList<Ownership>> customerMap;
	private HashMap<String, ArrayList<Ownership>> creditCardMap;
	
	public OwnershipCollection(){
		customerMap = new HashMap<Customer, ArrayList<Ownership>>();
		creditCardMap = new HashMap<String, ArrayList<Ownership>>();
	}
	
	
	public ArrayList<Ownership> getByCard(String cardNum) {
		return creditCardMap.get(cardNum);
	}
	
	public ArrayList<Ownership> getById(int id) {
		Customer c = Main.cColl.get(id);
		return customerMap.get(c);
	}
	
	public ArrayList<Ownership> getBySSN(String SSN) {
		Customer c = Main.cColl.getSSN(SSN);
		return customerMap.get(c);
	}

	public void add(Ownership own){
		if(customerMap.get(own.custId) == null) {
			ArrayList<Ownership> arr = new ArrayList<Ownership>();
			arr.add(own);
			Customer c = Main.cColl.get(own.custId);
			customerMap.put(c, arr);
		}
		else{
			Customer c = Main.cColl.get(own.custId);
			customerMap.get(c).add(own);
		}
		
		if(creditCardMap.get(own.card) == null) {
			ArrayList<Ownership> arr = new ArrayList<Ownership>();
			arr.add(own);
			creditCardMap.put(own.card, arr);
		}
		else{
			creditCardMap.get(own.card).add(own);
		}
			
	}
	
	public String toString() {
		return customerMap.toString();
	}
	
}
