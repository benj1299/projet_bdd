package bdd;
import java.util.ArrayList;

public class FileManager {
	private static FileManager instance;
	private FileManager fileManager;
	private ArrayList<HeapFile> heapFiles;

	/**
	 * Singleton de FileManager
	 * @return FileManager
	 */
	public static FileManager getInstance(){
		if (instance == null) {
			synchronized(FileManager.class) {
				if (instance == null) {
					instance = new FileManager() ;
				}
			}
		}
		return instance ;
	}

	public FileManager() {
		this.fileManager = FileManager.getInstance();
		this.heapFiles = new ArrayList<>();
	}
	
	/**
	 * parcourir la liste des RelDef de la DBDef
	 * •créer pour chaque telle RelDef un objet de type HeapFile en lui attribuant la RelDef en question
	 * •rajouter le HeapFile ainsi créé à la liste heapFiles.
	 * Rajoutez un appel à cette méthode dans la méthode Init du DBManager
	 */
	public void init() {

		for (RelDef relDef : DBDef.getInstance().getTabRelDef()) {
			HeapFile x = new HeapFile(relDef);
			this.heapFiles.add(x);
		}
		
		
	}
	/**
	 * créer un nouvel objet de type HeapFile et lui attribuer relDef
	 * •le rajouter à la liste heapFiles
	 * •puis appeler sur cet objet la méthode createNewOnDisk.
	 * Rajoutez un appel à CreateRelationFile dans la méthode CreateRelation du DBManager.
	 * @param relDef
	 */
	public void createRelationFile(RelDef relDef) {

	}

	public Rid inserRecordInRelation(Record record, String relName) {

	}

	/**
	 * Cette méthode doit retourner une liste contenant tous les records de la relation dont le nom est relName.
	 * @param relName
	 * @return liste de Record
	 */
	public ArrayList<Record> selectAllFromRelation(String relName){

	}
	/**
	 * Cette méthode doit retourner une liste contenant tous les records de la relation nomméerelName pour lesquels la valeur sur la colonne idxCol (convertie en chaîne de caractères)est égale à valeur.
	 * @param String relName
	 * @param int idxCol, un entier correspondant à un indice de colonne
	 * @param String  valeur
	 * @return
	 */
	public ArrayList<Record> selectFromRelation(String relName, int idxCol, String valeur ){

	}
	
	
	

}
