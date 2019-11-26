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
    créer un nouvel objet de type HeapFile et lui attribue relDef, le rajouter à la liste heapFiles puis appelle sur cet objet la méthode createNewOnDisk.	
	 * @param RelDef relDef
	 * @return
	 * @throws IOException 
	 */
	public void  CreateRelationFile (RelDef relDef) throws IOException{
		HeapFile hp = new HeapFile(relDef);		
		this.heapFiles.add(hp);	
		this.heapFiles.get(heapFiles.size()-1).createNewOnDisk();
	}

         /**
	 * Cette méthode s’occupera de l’insertion de record dans la relation dont le nom est relName.
	 * Pour cela, il faudra parcourir la liste heapFiles pour trouver celui qui correspond à la relation en question,
	 *  et ensuite appeler sa propre méthode InsertRecord.
	 * @param Record record record à inserer 
	 * @param String relName relation ou il faut inserer record
	 * @return
	 */
	
	public Rid inserRecordInRelation(Record record, String relName) throws Exception {

		Rid rid = null;
		for(HeapFile heapFile : this.heapFiles){
		
			if(heapFile.getRelDef().getName() == relName) {
				rid =  heapFile.insertRecord(record);
			}
		}
		
		if(rid == null) {
			throw new Exception("relation pas trouvé dans la liste");
		}
		return rid;
	}

	/**
	 * Cette méthode doit retourner une liste contenant tous les records de la relation dont le nom est relName.
	 * @param relName
	 * @return liste de Record
	 */
	public ArrayList<Record> selectAllFromRelation(String relName)throws Exception {
		for(HeapFile heapFile : this.heapFiles) {
			if(heapFile.getRelDef().getName() == relName) {
				return heapFile.getAllRecords();
			}
		}
		throw new Exception("relation pas trouvé dans heapFiles de FileManager");
	}


	/**
	 * Cette méthode doit retourner une liste contenant tous les records de la relation nommée relName pour lesquels la valeur sur la colonne idxCol (convertie en chaîne de caractères)est égale à valeur.
	 * @param String relName
	 * @param int idxCol, un entier correspondant à un indice de colonne
	 * @param String  valeur
	 * @return
	 * @throws Exception 
	 */

	public ArrayList<Record> selectFromRelation(String relName, int idxCol, String valeur ) throws Exception  {
		ArrayList<Record> records = new ArrayList<>();
		for(HeapFile heapFile : this.heapFiles) {

			if(heapFile.getRelDef().getName() == relName) {
				for(Record record : this.selectAllFromRelation(relName)) {
					if((String) record.getValues().get(idxCol) == valeur) {
						records.add(record);
					}

				}
			}
		}
		return records;
	}
	
	
	

}
