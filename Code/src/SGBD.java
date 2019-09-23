import java.util.Scanner; 

public class SGBD {

	private boolean inProgress = true;
	
	public static void main(String[] args) {
		
		DBManager sgbd = new DBManager();
		Scanner scan = new Scanner(System.in);
		StringBuffer chaine = new StringBuffer();
		
		sgbd.init();
		
		while(inProgress) 
		{
			chaine.append(scan.next());
			inProgress = sgbd.ProcessCommand(chaine);
		}
		
		sgbd.finish();
	}
}
