

enum Type{Visa, MC, AmericanExpress, Discover};

public class CreditCard {
	private Type type;
	private String number;
	private boolean isActive;
	private long limit;
	private int balanceInCents; //must be less than the credit limit;
	
	/*
	For example, credit card with number 2432143232 is Visa, credit limit of $10,000 
	and current balance of $3444.23.  The credit card number is unique. 
	You cannot have two credit cards with the same number. 
	Add an additional bit that represents if the credit card is active.
	Credit cards are initially inactive.
	*/
	
	public CreditCard(String number, long limit,int balance, Type type) {
		//TODO check unique number
		this.number = number;
		this.limit = limit;
		this.type = type;
		isActive = false;
	}
	
	public Type value(){
		return type;
	}
	
	public String number(){
		return number;
	}
	
	public int balance(){
		return balanceInCents;
	}
	
	public void changeBalanceBy(int cents){
		balanceInCents += cents;
	}
	
	public long limit(){
		return limit;
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	public void deactivate() {
		isActive = false;
	}
	
	public void activate() {
		isActive = true;
	}
	
	public String toString() {
		String ret = "";
		ret += type + " ";
		ret += number + "\n";
		ret += "limit: " + Main.money(limit)+"\n";
		ret += "balance: " + Main.money(balanceInCents) + "\n";
		ret += "isActive: " + isActive + "\n";
		return ret;
	}
	
}
