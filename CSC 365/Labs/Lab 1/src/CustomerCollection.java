import java.util.HashMap;


public class CustomerCollection {
	private HashMap<Integer, Customer> map;
	private HashMap<String, Customer> SSNmap;
	
	public CustomerCollection(){
		map = new HashMap<Integer, Customer>();
		SSNmap = new HashMap<String, Customer>();
	}
	
	
	public Customer get(int id) {
		return map.get(id);
	}
	
	public Customer getSSN(String ssn) {
		return SSNmap.get(ssn);
	}
	
	public void add(Customer cust){
		map.put(cust.cid, cust);
		SSNmap.put(cust.SSN, cust);
	}
	
	public String toString() {
		return map.toString() + SSNmap.toString();
	}
	
}
