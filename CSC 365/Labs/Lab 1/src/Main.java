import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
	
	static CustomerCollection cColl 	= new CustomerCollection();
	static CreditCardCollection ccColl 	= new CreditCardCollection();
	static OwnershipCollection oColl	= new OwnershipCollection();
	static PaymentCollection pColl 		= new PaymentCollection();
	static TransactionCollection tColl 	= new TransactionCollection();
	static VenderCollection vColl 		= new VenderCollection();
	static Scanner scan					= new Scanner(System.in);
	
	public static String money(long cents){
		return  ((cents < 0)? "-" : "") + "$" + Math.abs(cents) / 100 + "." + Math.abs(cents) % 100;
	}
	
	public static void printSpace() {
		System.out.println("\n\n");
	}
	
	private static void print(String print) {
		System.out.println("-----"+print+"-----");
	}
	
	public static String prompt(String prompt) {
		System.out.print("Input "+ prompt+ " >>> ");
		String scanned = scan.nextLine();
		System.out.println("\n");
		if(scanned.equals("")) {
			scanned = prompt(prompt);
		}
		return scanned;
	}
	
	public static void createCustomer() throws Exception {
		print("Creating Customer");
		String name 	= prompt("name");
		String phone	= prompt("phone");
		String address 	= prompt("address");
		String SSN 		= prompt("SSN");
		
		if(cColl.getSSN(SSN) != null)
			throw new Exception("SSN already exists");
		
		Customer c = new Customer(name, phone, address, SSN);
		cColl.add(c);
	}
	
	public static void createCreditCard() throws Exception {
		print("Creating Credit Card");
		String number 	= prompt("number");
		long limit		= Long.parseLong(prompt("limit"));
		int id 			= Integer.parseInt(prompt("Customer Id"));
		Type type 		= Type.valueOf(prompt("Type of Card"));
		CreditCard card = new CreditCard(number, limit, 0, type);
		Ownership own 	= new Ownership(id, number, true);
		
		if(ccColl.get(number) != null)
			throw new Exception("Card Number already exists");
		
		ccColl.add(card);
		oColl.add(own);
	}
	
	//issues duplicate card
	public static void issueDuplicateCreditCard() {
		print("Issuing Credit Card");
		String num	 	= prompt("Credit Card number");
		int id		 	= Integer.parseInt(prompt("Customer ID"));
		Ownership own 	= new Ownership(id, num, true);
		oColl.add(own);
	}
	
	public static void deactivateCard(){
		print("Deactivating Credit Card");
		String card	= prompt("CardNumber");
		ccColl.get(card).deactivate();	
	}
	
	public static void activateCard(){
		print("Activating Credit Card");
		String card	= prompt("CardNumber");
		ccColl.get(card).activate();
	}
	
	
	public static void createVender() {
		print("Creating Vender");
		String name 	= prompt("name");
		String location	= prompt("location");
		Vender vender 	= new Vender(name, location);
		vColl.add(vender);
	}
	
	public static void createTransaction() throws Exception {
		print("Creating Transaction");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date 		= sdf.parse(prompt("date"));
		String cardNum	= prompt("CardNumber");
		int vid 		= Integer.parseInt(prompt("Vender Id"));
		int cid			= Integer.parseInt(prompt("Customer Id"));
		int amount		= Integer.parseInt(prompt("Amount in Cents"));
		Transaction tran = new Transaction(date, cardNum, vid, cid, amount);
		
		CreditCard card = ccColl.get(cardNum);
		//check if valid
		if(!card.isActive() || card.limit() < -1 * (card.balance() - amount)){
			throw new Exception("Credit Card Declined");
		}
		
		tColl.add(tran);
		
		//perform transaction
		CreditCard cc = ccColl.get(cardNum);
		cc.changeBalanceBy(-1 * amount);
	}
	
	public static void createPayment() throws ParseException {
		print("Customer Payment");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date 		= sdf.parse(prompt("date"));
		String cardNum	= prompt("CardNumber");
		int amount		= Integer.parseInt(prompt("Amount in Cents"));
		Payment tran = new Payment(date, cardNum, amount);
		pColl.add(tran);
		
		//perform transaction
		CreditCard cc = ccColl.get(cardNum);
		cc.changeBalanceBy(1 * amount);
	}
	
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		File f = new File("input.txt");
		f.isDirectory();
		scan = new Scanner(f);
		
		while(true) {
			try {
				String cmd = prompt("Command");
				
				//1. Create a new customer.
				if(cmd.equals("1")) {
					createCustomer();
					
				}
				
				//2. Create a new credit card for an existing customer (will affect both the credit card and ownership data). 
				//Initially, the credit card will not be active.
				else if(cmd.equals("2")) {
					createCreditCard();
				}
				
				//3. Issue a credit card duplicate for additional customer (will affect only the ownership data)
				else if(cmd.equals("3")) {
					issueDuplicateCreditCard();
				}
				
				//4. Cancel a Credit Card
				else if(cmd.equals("4")) {
					deactivateCard();
				}
				
				//5. Activate a Credit Card
				else if(cmd.equals("5")) {
					activateCard();
				}
				
				//6. Add new vendor
				else if(cmd.equals("6")) {
					createVender();
				}
				
				
				//7. Create a new transaction. This will affect the balance of the credit card.
				else if(cmd.equals("7")) {
					createTransaction();
				}
				
				
				//8. Allow a customer to pay off credit card. This will affect both the payment data and credit card balance.
				else if(cmd.equals("8")) {
					createPayment();
				}
			
				
				//2. For a given customer (specified ID or SSN), print credit card information 
				//(i.e., credit card number, credit limit, and balance for each credit card).

				else if(cmd.equals("2id")) {
					int id = Integer.parseInt(prompt("Customer Id"));
					printSpace();
					for(Ownership own : oColl.getById(id)) {
						System.out.println(ccColl.get(own.card));
					}
				}
				
				else if(cmd.equals("2ssn")) {	
					String SSN = prompt("Customer SSN");
					printSpace();
					for(Ownership own : oColl.getBySSN(SSN)) {
						System.out.println(ccColl.get(own.card));
					}
				}
				
				
				//3. For a given credit card (specified by CC number), print credit card information
				//(e.g., balance, credit limit, card holders).
				else if(cmd.equals("3cc")) {
					String card = prompt("Card Number");
					printSpace();
					System.out.println(ccColl.get(card));
					System.out.println("Card Holders:");
					for(Ownership own : oColl.getByCard(card)) {
						int cid = own.custId;
						System.out.println(cColl.get(cid).name);
					}
				}
				
				//4. For a given credit card, print all transactions that are in a specified date range.
				else if(cmd.equals("4t")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date start 			 = sdf.parse(prompt("Start Date"));
					Date end	 		 = sdf.parse(prompt("End Date"));
					String card = prompt("Card Number");
					printSpace();
					for(Transaction trans : tColl.get(card)) {
						Date date = trans.date;
						if(date.before(end) && date.after(start)) {
							System.out.println(trans);
						}
					}
				}
				
				else if(cmd.equals("print")) {
					for(String str: ccColl.numbers())
						System.out.println(str);
				}
				
				printSpace();
				
			}catch(NoSuchElementException e) {
				System.exit(0);
			}
			catch(NullPointerException e) {
				System.out.println("Thing you are looking for does not exist");
				System.out.println("\n\n\n");
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("\n\n\n");
			}
			
		}

	}

}
