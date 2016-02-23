
public class Ownership {
	/*
	 * ownership: which customer owns which credit card. 
	 * For example, customer with id 7 owns credit card with credit card number 123432432.  
	 * A customer can own multiple credit cards and a credit card can be owned by multiple customers 
	 * (e.g., husband and wife may carry credit cards with the same credit card number). 
	 *  Add an additional bit that represents whether the ownership information is current. 
	 *  For example, if a user cancels a credit card, than this bit needs to be set to false.
	 */
	
	
	public boolean isCurrent;
	public int custId;
	public String card;
	
	public Ownership(int id, String card, boolean isCurrent) {
		custId = id;
		this.card = card;
		this.isCurrent = isCurrent;
	}
	
}
