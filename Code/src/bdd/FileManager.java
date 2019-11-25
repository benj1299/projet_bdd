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
	
	/**
	 * Pour inserer un record
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	public Rid insertRecord(Record record) throws Exception {
		PageId pid = getFreeDataPageId() ;
		return this.writeRecordToDataPage(record, pid);	
	}

}
