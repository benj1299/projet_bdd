package bdd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class DBManager {
	
	private static DBManager instance;
	private DBDef dbdef;
	private BufferManager bm;

	/**
	 * Singleton de DBManager
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
		this.bm = BufferManager.getInstance();
	}
	
	public void init() throws FileNotFoundException, ClassNotFoundException, IOException {
		this.dbdef.init();
	}
	
	public void finish() throws Exception {
		this.dbdef.finish();
		this.bm.flushAll();
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
		
		return true;
	}
	
	/**
	 * Créé une relation avec RelDef et l'ajoute dans DBDef
	 * 
	 * @param name - Nom de la relation
	 * @param nbColumn - Nombre de column
	 * @param typeColumns - Tableau de tous les types de colonnes
	 * 
	 * @return void
	 */
	public void createRelation(String name, int nbColumn, Vector<String> typeColumn){
		int recordSize = 0;
		for (int i = 0; i < nbColumn; i++)
			if (typeColumn.get(i).equals("float") || typeColumn.get(i).equals("int")) {
				recordSize += 4;
			}
			else {
				String mots[] = typeColumn.get(i).split("");
				recordSize += Integer.parseInt(mots[6])*2;	
			}
		RelDef relation = new RelDef(name, nbColumn, typeColumn, recordSize, this.dbdef.getCount());		
		this.dbdef.addRelation(relation);
	}
}
