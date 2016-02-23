import java.util.Date;

public class Transaction {
	
	public Date date;
	public String cardNum;
	public int vid;
	public int cid;
	public int amountInCents;
	
	/*Contains information that a customer purchased something with a credit card. 
	Example: On January 5th, 2015, 
	customer with id 255 used CC with credit card number 2342343432 
	at vendor id 233 for $654.23 worth of purchases. 
	Store all information: date, customer id, cc number, and vender id.
	*/
	
	public Transaction(Date date, String cardNum, int vid, int cid, int amountInCents){
		this.date = date;
		this.cardNum = cardNum;
		this.vid = vid;
		this.cid = cid;
		this.amountInCents = amountInCents;	
	}
	
	
	public String toString() {
		String ret = "";
		ret += date +", customer with id "+ cid +" used CC with credit card number "+ cardNum; 
		ret += " at vendor id "+ vid +" for " + Main.money(amountInCents);
		return ret;
	}
	
}
