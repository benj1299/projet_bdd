import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class DBManager {
	
	private static DBManager instance = new DBManager();
	private DBDef dbdef;

	public DBManager() {
		this.dbdef = new DBDef();
	}
	
	/**
	 * Singleton retournant l'instance unique de DBManager
	 * @return DBManager
	 */
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
	 * 
	 * @param chaine - Exemple : create NomRelation NbCol TypeColl[1] TypeCol[2] ... TypeCol[NbCol]
	 * @return boolean
	 */
	public boolean processCommand(String chaine) {
		String[] args = chaine.split(" ");
		
		if(args[0].contentEquals("exit")) {
			return false;
		}

		if(args[0].contentEquals("create")) {
			this.createRelation(args[1], Integer.parseInt(args[2]), typeColumn);
		}
		
		return true;
	}
	
	/**
	 * Créé une relation via un fichier Binaire RandomAccessFile 
	 * 
	 * @param name - Nom de la relation
	 * @param nbColumn - Nombre de column
	 * @param typeColumns - Tableau de tous les types de colonnes
	 * 
	 * @return void
	 */
	public void createRelation(String name, int nbColumn, String[] typeColumns){
		
	}
}
