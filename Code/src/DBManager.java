import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class DBManager {
	
	private static DBManager instance;
	private DBDef dbdef;
	private BufferManager bufferManager;

	/**
	 * Singleton retournant l'instance unique de DBManager
	 * @return DBManager
	 */
	public static DBManager getInstance(){
		if (instance == null) {
	         synchronized(DBManager.class) {
	            if (instance == null) {
	               instance = new DBManager() ;
	            }
	         }
	      }
	      return instance ;
	}
	
	public DBManager() {
		this.dbdef = new DBDef();
		this.bufferManager = BufferManager.getInstance();
	}
	
	public void init() {
		this.dbdef.init();
	}
	
	public void finish() {
		this.dbdef.finish();
		this.bufferManager.flushAll();
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
		int recordSize = 0;
		for (int i = 0; i < nbColumn; i++){
			if (typeColumns[i].equals("float")||typeColumns[i].equals("int")){
				recordSize+=4;
			}   
			else {
				String mots []= typeColumns[i].split("");
				recordSize +=Integer.parseInt(mots[6]);	
			}
		}
		int slotCount = Constants.PAGE_SIZE/recordSize;
	}
}
