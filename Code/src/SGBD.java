import java.util.Scanner; 

public class SGBD {
	
	public static void main(String[] args) {
		
		DBManager dbmanager = DBManager.getInstance();
		Scanner scan = new Scanner(System.in);
		String chaine;
		boolean inProgress = true;
		
		dbmanager.init();
		
		while(inProgress) 
		{
			chaine = scan.next();
			inProgress = dbmanager.processCommand(chaine);
		}
		
		scan.close();
		dbmanager.finish();
	}
}
