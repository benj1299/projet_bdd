package bdd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.lang.*;

public class DBManager {

	private static DBManager instance;
	private DBDef dbdef;
	private BufferManager bm;
	private FileManager fm;

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
		this.fm = FileManager.getInstance();
	}

	public void init() throws FileNotFoundException, ClassNotFoundException, IOException {
		this.dbdef.init();
		this.fm.init();
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
	 * @throws IOException 
	 */
	public boolean processCommand(String chaine) throws IOException {
		String[] args = chaine.split(" ");

		if(args[0].contentEquals("exit")) {
			return false;
		}
		
		//Effectuer les vérifications des données de chaque paramètre
		if(args[0].contentEquals("create")) {
			String relationName = args[1];
			int nbColumn = Integer.parseInt(args[2]);
			Vector<String> typeColumn = new Vector<String>();
			for(int i = 3; i < args.length; i++) {
				typeColumn.add(args[i]);
			}
			
			this.createRelation(relationName, nbColumn, typeColumn);
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
	 * @throws IOException 
	 */
	public void createRelation(String name, int nbColumn, Vector<String> typeColumn) throws IOException{
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
		this.fm.CreateRelationFile(relation);
	}
	
	/**
	 * Nettoit la base de donnée en reinitialisant le BufferManager, le DBDef, le FileManager, le catalogue
	 */
	public void clean() {		
		this.deleteDirectory(new File(Constants.DB_DIRECTORY));
		this.bm.reset();
		this.dbdef.reset();
		this.fm.reset();
	}
	
	boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}
}
