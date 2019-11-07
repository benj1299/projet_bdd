package bdd;
import java.util.Scanner; 

public class SGBD {
	
	public static void main(String[] args) throws Exception {
		
		boolean inProgress = true;
		DBManager dbmanager = DBManager.getInstance();
		Scanner scan = new Scanner(System.in);
		
		dbmanager.init();
		
		while(inProgress) 
		{
			inProgress = dbmanager.processCommand(scan.next());
		}
		
		scan.close();
		dbmanager.finish();
	}
}
