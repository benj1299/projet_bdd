import java.util.Scanner; 

public class SGBD {
	
	public static void main(String[] args) {
		
		DBManager sgbd = new DBManager();
		Scanner scan = new Scanner(System.in);
		StringBuffer chaine = new StringBuffer();
		boolean inProgress = true;
		
		sgbd.init();
		
		while(inProgress) 
		{
			chaine.append(scan.next());
			inProgress = sgbd.ProcessCommand(chaine);
		}
		
		sgbd.finish();
	}
}
