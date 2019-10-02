import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class DBManager {
	
	private static DBManager instance = new DBManager();
	private DBDef dbdef;
	private String[] args;
	
	public DBManager() {
		this.dbdef = new DBDef();
	}
	
	public static DBManager getInstance(){
	    if(instance == null)
	    	instance = new DBManager();
	    return instance;
	}
	
	public void init() {
		this.dbdef.init();
	}
	
	public void finish() {
		this.dbdef.finish();
	}
	
	/**
	 * Parse la chaine des commandes et redirige vers la fonction correspondante
	 */
	public boolean processCommand(String chaine) {
		this.args = chaine.split(" ");
		
		if(this.args[0].contentEquals("exit")) {
			return false;
		}

		if(args[0].contentEquals("create")) {
			this.createRelation(args[1], Integer.parseInt(args[2]), typeColumn);
		}
		
		return true;
	}
	
	/**
	 * Créé une relation
	 */
	public void createRelation(String name, int nbColumn, String... typeColumns){
		try {
			RandomAccessFile file = new RandomAccessFile(args[1]+".txt", "rw");
			for(String typeColumn : typeColumns) {
				// Ajout des types de colonnes dans le fichier
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
