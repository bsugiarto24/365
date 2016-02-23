
public class Customer {
	public String name, phone, address, SSN;
	public int cid;
	
	private static int id = 1;
	
	private static int assignId() {
		return id++;
	}
	
	public Customer(String name, String phone, String address, String SSN){
		this.name = name;
		this.phone = phone;
		this.address = address;
		//TODO check SSN
		this.SSN = SSN;
		cid = assignId();
	}
	
	
}
