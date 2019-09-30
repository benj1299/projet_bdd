import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class DBManager {
	private static  DBManager instance = new DBManager();

	
	private DBDef dbdef;
	private String[] args;
	
	public DBManager() {
		this.dbdef = new DBDef();
	}
	
	public void init() {
		this.dbdef.init();
	}
	
	public void finish() {
		this.dbdef.finish();
	}
	
	public Boolean processCommand(String chaine) {
		this.args = chaine.split(" ");
		
		if(this.args[0].contentEquals("exit")) {
			return false;
		}

		if(args[0].contentEquals("create")) {
			this.createRelation(args[1], Integer.parseInt(args[2]), typeColumn);
		}
		
		return true;
	}
	
	public void createRelation(String name, int nbColumn, String [] typeColumn){
		try {
			RandomAccessFile file = new RandomAccessFile(args[1]+".txt", "rw");
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	  public static DBManager getInstance(){
	    if(instance == null)
	    	instance = new DBManager();
	    return instance;
	  }
		   
		  
}
