import java.io.IOException;
import java.util.Scanner;
import bdd.*; 

public class Launcher {
	
	public static void main(String[] args) throws Exception {
		System.out.println("Projet BDD");
		boolean inProgress = true;
		DBManager dbmanager = DBManager.getInstance();
		Scanner scan = new Scanner(System.in);
		
		dbmanager.init();
		
		// Scenario test
		scenario(dbmanager);
		
		while(inProgress) 
		{
			System.out.print("Entrer une commande : ");
			inProgress = dbmanager.processCommand(scan.next());
		}
		
		scan.close();
		dbmanager.finish();
	}
	
	private static void scenario(DBManager dbmanager) throws Exception {
		try {
			dbmanager.processCommand("clean");
			dbmanager.processCommand("create R 3 int string3 int");
			dbmanager.processCommand("insert R 1 aab 2");
			dbmanager.processCommand("insert R 2 abc 2");
			dbmanager.processCommand("insert R 1 agh 1");
			dbmanager.processCommand("selectall R");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
