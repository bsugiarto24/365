import java.util.HashMap;


public class VenderCollection {
	private HashMap<Integer, Vender> map;
	
	public VenderCollection(){
		map = new HashMap<Integer, Vender>();
	}
	
	public Vender get(int id) {
		return map.get(id);
	}
	
	public void add(Vender vender){
		map.put(vender.vid, vender);
	}
	
	public String toString() {
		return map.toString();
	}
	
}
