import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

enum Type{Visa, MC, AmericanExpress, Discover};

class PlayWithMySQL {
    static Connection connect;
    static Scanner scan	= new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
			connect = DriverManager.getConnection("jdbc:mysql://csc223m01.csc.calpoly.edu/bsugiart?user=bsugiart&password=abc123");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

		File f = new File("input.txt");
		
		while(true) {
			try {
				String cmd = prompt("Command");
				
				//1. Create a new customer.
				if(cmd.equals("1")) {
					print("Creating Customer");
					String id	 	= prompt("id");
					String name 	= prompt("name");
					String phone	= prompt("phone");
					String address 	= prompt("address");
					String SSN 		= prompt("SSN");
					
					executeStatement("INSERT INTO Customer (Id, SSN, Name, Address, Phone) Values ('"+id+"', '"+SSN+"', '"+name+"', '"+address+"', '"+phone+"');");
				}
				
				//2. Create a new credit card for an existing customer (will affect both the credit card and ownership data). 
				//Initially, the credit card will not be active.
				else if(cmd.equals("2")) {
					print("Creating Credit Card");
					String number 	= prompt("number");
					long limit		= Long.parseLong(prompt("limit"));
					int id 			= Integer.parseInt(prompt("Customer Id"));
					Type type 		= Type.valueOf(prompt("Type of Card"));
					
					executeStatement("INSERT INTO CreditCard (Number, Type, cclimit, isActive, Balance) Values ('"+number+"', '"+type+"', '"+limit+"', '"+"0"+"', '"+0+"');");
					executeStatement("INSERT INTO Ownership (CustomerId, CCNum, isActive) Values ('"+id+"', '"+number+"', '"+0+"');");

				}
				
				//3. Issue a credit card duplicate for additional customer (will affect only the ownership data)
				else if(cmd.equals("3")) {
					print("Issuing Credit Card");
					String num	 	= prompt("Credit Card number");
					int id		 	= Integer.parseInt(prompt("Customer ID"));
					executeStatement("INSERT INTO Ownership (CustomerId, CCNum, isActive) Values ('"+id+"', '"+num+"', '"+false+"');");
				}
				
				//4. Cancel a Credit Card
				else if(cmd.equals("4")) {
					print("Deactivating Credit Card");
					String card	= prompt("CardNumber");
					executeStatement("UPDATE Ownership o SET o.isActive = FALSE WHERE o.ccNum = '"+card+"';");
					executeStatement("UPDATE CreditCard o SET o.isActive = FALSE WHERE o.number = '"+card+"';");
				}
				
				//5. Activate a Credit Card
				else if(cmd.equals("5")) {
					print("Activating Credit Card");
					String card	= prompt("CardNumber");
					executeStatement("UPDATE Ownership o SET o.isActive = TRUE WHERE o.ccNum = '"+card+"';");
					executeStatement("UPDATE CreditCard o SET o.isActive = TRUE WHERE o.number = '"+card+"';");
				}
				
				//6. Add new vendor
				else if(cmd.equals("6")) {
					print("Creating Vender");
					String id 		= prompt("id");
					String name 	= prompt("name");
					String location	= prompt("location");
					executeStatement("INSERT INTO Vender (Id, Name, Location) Values ('"+id+"', '"+name+"', '"+location+"');");
					
				}
				
				
				//7. Create a new transaction. This will affect the balance of the credit card.
				else if(cmd.equals("7")) {
					print("Creating Transaction");
					
					String id				= prompt("id");
					SimpleDateFormat sdf 	= new SimpleDateFormat("dd/MM/yyyy");
					String date 			= prompt("date");
					String cardNum			= prompt("CardNumber");
					int vid 				= Integer.parseInt(prompt("Vender Id"));
					int cid					= Integer.parseInt(prompt("Customer Id"));
					int amount				= Integer.parseInt(prompt("Amount in Cents"));

					//check if valid (done by trigger)
					executeStatement("INSERT INTO Transaction (Id, Date, vid, cid, CCNum, amount) Values ('"+id+"', '"+date+"', '"+vid+"', '"+cid+"', '"+cardNum+"', '"+amount+"');");

					//perform transaction (done by trigger)
				}
				
				
				//8. Allow a customer to pay off credit card. This will affect both the payment data and credit card balance.
				else if(cmd.equals("8")) {
					print("Customer Payment");
					int id		= Integer.parseInt(prompt("Id"));
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String date 		= prompt("date");
					String cardNum	= prompt("CardNumber");
					int amount		= Integer.parseInt(prompt("Amount in Cents"));
					
					executeStatement("INSERT INTO Payment (Id, Date, CCNum, amount)  Values ('"+id+"', '"+date+"', '"+cardNum+"', '"+amount+"');");
				}
			
				
				//2. For a given customer (specified ID or SSN), print credit card information 
				//(i.e., credit card number, credit limit, and balance for each credit card).

				else if(cmd.equals("2id")) {
					int id = Integer.parseInt(prompt("Customer Id"));
					printSpace();
										
					sendQuery("Select cc.* from CreditCard cc, Ownership o WHERE cc.number = o.CCNum AND o.CustomerId = '"+id+"'");
					
				}
				
				else if(cmd.equals("2ssn")) {	
					String SSN = prompt("Customer SSN");
					printSpace();
					
					sendQuery("Select cc.* from CreditCard cc, Ownership o, Customer c WHERE c.id = o.CustomerId AND cc.number = o.CCNum AND c.SSN = '"+SSN+"'");
				}
				
				
				//3. For a given credit card (specified by CC number), print credit card information
				//(e.g., balance, credit limit, card holders).
				else if(cmd.equals("3cc")) {
					String card = prompt("Card Number");
					printSpace();
					
					sendQuery("Select * from CreditCard cc WHERE cc.number = '"+card+"'");
					System.out.println("Card Holders:");
					sendQuery("Select c.name from CreditCard cc, Ownership o, Customer c WHERE c.id = o.CustomerId AND cc.number = o.CCNum AND cc.number = '"+card+"'");

				}
				
				//4. For a given credit card, print all transactions that are in a specified date range.
				else if(cmd.equals("4t")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String start 		 = prompt("date");
					String end 			 = prompt("date");
					String card = prompt("Card Number");
					printSpace();
					
					sendQuery("Select * from Transaction WHERE date > '"+start+"' AND date < '"+end+"'");
				}
				
				else if(cmd.equals("print")) {
					sendQuery("Select number from CreditCard");
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
    
	public static void printSpace() {
		System.out.println("\n\n");
	}
    
	  public static void executeStatement(String query) throws SQLException {
	    	String input = query;
	        Statement statement = connect.createStatement();
	        boolean rs = statement.execute(input);
	        
	        System.out.println("Error?: " + rs);
	        
	    }
	
	
    public static void sendQuery(String query) throws SQLException {
    	String input = query;
        Statement statement = connect.createStatement();
        ResultSet rs = statement.executeQuery(input);
        
        while (rs.next()) {
        	int index = 1;
        	String print = "";
        	
        	try{
        		while(true) {
            		print += rs.getString(index);
            		print += ",";
            		index++;
        		}
        	}catch (Exception e){}
        	
            System.out.println(print);
        }
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
	
	private static void print(String print) {
		System.out.println("-----"+print+"-----");
	}
	
}
    
    
    

