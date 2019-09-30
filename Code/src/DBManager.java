import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class DBManager {
	
	private DBDef dbdef = new DBDef();
	private String[] args;
	
	public void init() {
		this.dbdef.init();
	}
	
	public void finish() {
		this.dbdef.finish();
	}
	
	public Boolean ProcessCommand(String chaine) {
		this.args = chaine.split(" ");
		
		if(this.args[0].contentEquals("exit")) {
			return false;
		}

		if(args[0].contentEquals("create") && args[1] != null && isInteger(args[2])) {
			int nbColumn = Integer.parseInt(args[2]);			
			try {
				RandomAccessFile file = new RandomAccessFile(args[1]+".txt", "rw");
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public void CreateRelation(String name, int nbColumn, String [] typeColumn){
		
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
