import java.util.Date;


public class Payment {

	/*
	payment: Contains information that a payment was made on the balance of a credit card. 
	For example, on June 2, 2015, $400.34 was payed on credit card 23424123432.
	*/
	
	public Date date;
	public String card;
	public int amount;
	public int pid;
	
	private static int id = 1;
	
	private static int assignId() {
		return id++;
	}
	
	
	
	public Payment(Date date, String card, int amount) {
		this.date = date;
		this.card = card;
		this.amount = amount;
		pid = assignId();
	}
	
}
