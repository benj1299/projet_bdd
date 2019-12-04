package bdd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

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
	 * Parcours la liste des RelDef de la DBDef
	 * Créé pour chaque RelDef un objet de type HeapFile en lui attribuant la RelDef en question
	 * Rajoute le HeapFile ainsi créé à la liste heapFiles.
	 */
	public void init() {
		for (RelDef relDef : DBDef.getInstance().getTabRelDef()) {
			HeapFile x = new HeapFile(relDef);
			this.heapFiles.add(x);
		}
	}

	/**
	 * Créé le HeapFile de la relation donnée en paramètre
	 * @param RelDef relDef
	 * @throws IOException 
	 */
	public void createRelationFile(RelDef relDef) throws IOException{
		HeapFile hp = new HeapFile(relDef);		
		this.heapFiles.add(hp);	
		this.heapFiles.get(heapFiles.size()-1).createNewOnDisk();
	}

	/**
	 * Insert un record dans une relation.
	 * @param Record record record à inserer 
	 * @param String relName relation ou il faut inserer record
	 * @return
	 */

	public Rid inserRecordInRelation(Record record, String relName) throws Exception {
		Rid rid = null;
		for(HeapFile heapFile : this.heapFiles)
			if(heapFile.getRelDef().getName() == relName)
				rid =  heapFile.insertRecord(record);
		
		if(rid == null)
			throw new Exception("relation pas trouvé dans la liste");
		
		return rid;
	}

	/**
	 * Renvoies la liste de tous les records de la relation donnée en paramètre
	 * @param relName
	 * @return liste de Record
	 * @throws Exception
	 */
	public Vector<Record> selectAllFromRelation(String relName) throws Exception {
		for(HeapFile heapFile : this.heapFiles) {
			if(heapFile.getRelDef().getName() == relName) {
				return heapFile.getAllRecords();
			}
		}
		throw new Exception("relation pas trouvé dans heapFiles de FileManager");
	}


	/**
	 * Renvoies tous les records de la relation donnée en paramètre pour lesquels la valeur sur la colonne idxCol est égale à valeur.
	 * @param String relName
	 * @param int idxCol, un entier correspondant à un indice de colonne
	 * @param String  valeur
	 * @return Liste de records
	 * @throws Exception 
	 */

	public Vector<Record> selectFromRelation(String relName, int idxCol, String valeur) throws Exception  {
		Vector<Record> records = new Vector<Record>();
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
	
	/**
	 * Créé une nouvelle instance remettant ainsi tout à 0
	 */
	public void reset() {
		FileManager.instance = new FileManager();
	}
	
	
	
	
	/**
	 * Permet de suprimer un record 
	  @param Record record
	 */
	public boolean deleteRecord(Record record){
		
		for (HeapFile hp: heapFiles){
			
			if(hp.getRelDef().getName()==record.getRelation().getName()){
				
				for (int i=1; i<hp.size(); i++){
					
					byte[] buff = hp.getBm().getPage(new PageId(hp.getRelDef().getFileIdx(), i));
					
					for (int j=0; j<buff.length; j++){
						
						if(){
							
							buff[j]=0;
							
							hp.getBm().freePage(new PageId(hp.getRelDef().getFileIdx(), true));
							
							PageId headerPage = new PageId(hp.getRelDef().getFileIdx(), 0);
							byte[] buffheader = bm.getPage(headerPage);
							buffheader[i] -= 1;
							
							
						}
						
						
						
						
						
					}
					
				}
	
	
}
