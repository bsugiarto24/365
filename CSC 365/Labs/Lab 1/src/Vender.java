
public class Vender {
	/* id, name, location of main office. 
	 * Example: vendor with id 23 has name “Best Buy” and main office location “123 Main St., Austin, TX”. 
	 * The id of the vender is unique and assigned by the system.
	 */
	
	public String name, location;
	public int vid;
	
	private static int id = 1;
	
	private static int assignId() {
		return id++;
	}
	
	public Vender(String name, String location){
		id = assignId();
		this.name = name;
		this.location = location;
	}
	
}
