import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Populator {

	public static FileWriter writer = null;
	public static int custId  = 1;
	public static int vendId  = 1;
	public static int tid  = 1;
	public static int pid  = 1;
	public static String line = ");" + System.getProperty("line.separator");
	
	public static HashMap<String, Integer> SSNmap = new HashMap<String,Integer>();
	public static HashMap<String, Integer> custName = new HashMap<String,Integer>();
	public static HashMap<String, Integer> CCnum = new HashMap<String,Integer>();
	public static ArrayList<String> CCnumbers = new ArrayList<String>();
	public static HashMap<Integer, ArrayList<String>> ownership = new HashMap<Integer,ArrayList<String>>();
	public static ArrayList<Integer> venders = new ArrayList<Integer>();
	
	
	
	
	
	public static String [] names = {
			"Marla","Kenny","Sharie","Lawana","Aaron","Andre","Hailey","Rubin","Reginald","Tijuana","Dell","Gonzalo",
			"Antonia","Felicitas","Jeremy","Robert","Ruby","Iluminada","Normand","Flossie","Jasmin","Jorge","Ninfa",
			"Meredith","Loma","Renata","Keith","Willard","Diedra","Lieselotte","Gwyneth","Trinh","Christopher","Ronnie",
			"Meta","Lamonica","Era","Michiko","Arletta","Young","Manda","Lauryn","Justin","Pricilla","Esta","Aurelia",
			"Malisa","Ivory","Summer","Wendy"};
	
	public static String[] streets = {
		    "Devon Road","Vine Street","Orchard Avenue","Fairway Drive","Lilac Lane","Fairview Road","Water Street",
		    "Grand Avenue","Heather Lane","Ivy Court","Eagle Road","Pennsylvania Avenue","Circle Drive","Cemetery Road",
		    "5th Street South","Main Street East","Victoria Court","Cleveland Avenue","13th Street","Hawthorne Lane",
		    "Garfield Avenue","Augusta Drive","5th Avenue","Route 29","Highland Drive","Lafayette Street","Briarwood Court",
		    "Sheffield Drive","Depot Street","Bank Street","Washington Street","Schoolhouse Lane","Country Club Road",
		    "Atlantic Avenue","Hamilton Road","Beechwood Drive","Holly Drive","State Street","Beech Street","Canterbury Drive",
		    "Route 202","6th Street North","Clark Street","Park Avenue","Dogwood Lane","3rd Avenue","Buttonwood Drive",
		    "Overlook Circle","8th Avenue","Route 10"
	};
	
	public static String[] cardType = {
		    "AMEX", "MASTERCARD", "VISA", "DISCOVER"
	};
	
	
	public static void createCustomer() throws IOException {
		/*INSERT 
		INTO Customer (Id, SSN, Name, Address, Phone) 
		VALUES (233, '545645', 'Bob', '123 Main', '43535344'),*/
		String ssn = randomSSN();
		while(SSNmap.containsKey(ssn)) {
			ssn = randomSSN();
		}
		SSNmap.put(ssn, 1);
		
		String name = names[random(50)] + " " + names[random(50)];
		String address = randomNumber() + " " + streets[random(50)];
		String phone = randomPhone();
		
		writer.write("INSERT INTO Customer (Id, SSN, Name, Address, Phone) Values (" + custId++ +", '" + ssn + "', '" 
				+ name + "', '" + address + "', '" + phone +"'" + line);
		writer.flush();
	}
	
	public static void createCard() throws IOException {
		/*INSERT
		INTO CreditCard (Number, Type, cclimit, isActive, Balance)
		VALUES('4353453', 'AMEX', 2345334, true, 0);*/
		
		String num = randomPhone();
		while(CCnum.containsKey(num)) {
			num = randomPhone();
		}
		CCnum.put(num, 1);
		CCnumbers.add(num);
		
		String type =  cardType[random(4)];
		int balance = random(100000);
		int limit = random(100000) + balance;
		
		writer.write("INSERT INTO CreditCard (Number, Type, cclimit, isActive, Balance) Values ('" + num +"', '" + type + 
				"', " + limit + ", " + rando() + ", " + balance + line);
		writer.flush();
	}
	
	public static void createOwnership() throws IOException {
		/*INSERT 
		INTO Ownership (CustomerId, CCNum, isActive) 
		VALUES (233, '12345', true);*/
		String cc = CCnumbers.get(random(CCnumbers.size()));
		int cid = random(SSNmap.keySet().size()) + 1;
		
		while(ownership.get(cid) != null &&  (ownership.get(cid).contains(cc) || ownership.get(cid).size() > 3)){
			cid = random(SSNmap.keySet().size()) + 1;
		}
		
		if(ownership.get(cid) == null)
			ownership.put(cid, new ArrayList<String>());
		ownership.get(cid).add(cc);
		
		writer.write("INSERT INTO Ownership (CustomerId, CCNum, isActive)  Values (" + cid + ", '" + cc + "', " + rando() + line);
		writer.flush();
	}
	
	public static void createVender() throws IOException {
		/*INSERT 
		INTO Vender (Id, Name, Location) 
		VALUES (1, 'Best Buy', '123 Main');*/
		String name = names[random(50)] + " " + names[random(50)];
		venders.add(vendId);
		writer.write("INSERT INTO Vender (Id, Name, Location)  Values (" + vendId++ +", '" + name + "', '" + streets[random(50)] + "'" + line);
		writer.flush();
	}
	
	public static void createTransaction() throws IOException {
		/*INSERT 
		INTO Transaction (Id, Date, vid, cid, CCNum, amount) 
		VALUES (1, 9/4/15, 1, 233, '12345', 1234);*/
		
		Date date = new Date((long) random(100000000));
		String date2 = "'19" + date.getYear() + "-" + (random(12)+1) + "-" + (random(28)+1) + "'";
		int amount = random(10000);
		int cid = random(SSNmap.size()) + 1;
		int vid = random(venders.size()) + 1;
		
		while(ownership.get(cid) == null)
			cid = random(SSNmap.size()) + 1;
		String cc = ownership.get(cid).get(random(ownership.get(cid).size()));
		
		writer.write("INSERT INTO Transaction (Id, Date, vid, cid, CCNum, amount)  Values (" + tid++ +", " 
				+ date2 + ", " + vid + ", " + cid + ", '" + cc + "', " + amount + line);
		writer.flush();
	}
	
	public static void createPayment() throws IOException {
		/*INSERT 
		INTO Payment (Id, Date, CCNum, amount) 
		VALUES (2, 9/2/15, '12345', 600);*/		
		Date date = new Date((long) random(100000000));
		String date2 = "'19" + date.getYear() + "-" + (random(12)+1) + "-" + (random(28)+1) + "'";
		int amount = random(10000);
		
		writer.write("INSERT INTO Payment (Id, Date, CCNum, amount)  Values (" + pid++ +", " 
				+ date2 + ", '" + CCnumbers.get(random(CCnumbers.size())) + "', " + amount + line);
		writer.flush();
	}
	
	
	public static int random(int max) {
		return (int) (Math.random() * max);
	}
	
	public static String randomString(int size) {
		String num = "";
		for(int i = 0; i < size; i++) {
			num += random(10);
		}
		return num;
	}
	
	public static String randomPhone() {
		return randomString(10);
	}
	
	public static String randomSSN() {
		return randomString(9);
	}
	
	public static String randomNumber() {
		return randomString(3);
	}
	
	public static boolean rando() {
		int r = (int) (Math.random() * 2);
		return (r == 1)? true : false;
	}
	
	
	public static void main(String[] args) throws IOException {
		
		File f = new File("output.txt");
		writer = new FileWriter(f);

		for(int i = 0; i < 1000; i++) {
			createCustomer();
		}
		for(int i = 0; i < 1000; i++) {
			createCard();
		}
		for(int i = 0; i < 2000; i++) {
			createOwnership();
		}
		for(int i = 0; i < 100; i++) {
			createVender();
		}
		for(int i = 0; i < 2000; i++) {
			createTransaction();
		}
		for(int i = 0; i < 100; i++) {
			createPayment();
		}
	}
}
