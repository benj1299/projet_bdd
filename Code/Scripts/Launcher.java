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
			dbmanager.processCommand("select R 1 1");
			dbmanager.processCommand("select R 3 1");
			dbmanager.processCommand("create S 8 string2 int string4 float string5 int int int");
			dbmanager.processCommand("insertall S S1.csv");
			dbmanager.processCommand("selectall S");
			dbmanager.processCommand("select S 2 19");
			dbmanager.processCommand("select S 3 Nati");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	private static void scenario2(DBManager dbmanager) throws Exception {
		try {
		dbmanager.processCommand("clean");
			dbmanager.processCommand("create R 3 int string3 int");
			dbmanager.processCommand("insert R 1 aab 2");
			dbmanager.processCommand("insert R 2 abc 2");
			dbmanager.processCommand("insert R 1 agh 1");
			dbmanager.processCommand("selectall R");
			dbmanager.processCommand("delete 3 2");
			dbmanager.processCommand("selectall R");
	
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	private static void scenario(DBManager dbmanager) throws Exception {
		try {
			
			
			
			dbmanager.processCommand("clean");
			dbmanager.processCommand("create R 3 int string3 int");
			dbmanager.processCommand("insert R 1 aab 2");
			dbmanager.processCommand("insert R 2 abc 2");
			dbmanager.processCommand("insert R 1 agh 1");
			dbmanager.processCommand("selectall R");
			dbmanager.processCommand("select R 1 1");
			dbmanager.processCommand("select R 3 1");
			dbmanager.processCommand("create S 8 string2 int string4 float string5 int int int");
			dbmanager.processCommand("insertall S S1.csv");
			dbmanager.processCommand("selectall S");
			dbmanager.processCommand("select S 2 19");
			dbmanager.processCommand("select S 3 Nati");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	private static void scenario3(DBManager dbmanager) throws Exception {
		try {
		dbmanager.processCommand("clean");
			dbmanager.processCommand("create R 3 int string3 int");
			dbmanager.processCommand("insert R 1 aab 2");
			dbmanager.processCommand("insert R 2 abc 2");
			dbmanager.processCommand("insert R 1 agh 1");
			dbmanager.processCommand("create S int int");
			dbmanager.processCommand("insert S 1 2");
			dbmanager.processCommand("join R S 1 1");
	
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
