package bdd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class FileManager {
	private static FileManager instance;
	private Vector<HeapFile> heapFiles;
	private BufferManager bm;

	/**
	 * Singleton de FileManager
	 * @return FileManager
	 */
	public static FileManager getInstance(){
		if (instance == null) {
			synchronized(FileManager.class) {
				if (instance == null) {
					instance = new FileManager();
				}
			}
		}
		return instance ;
	}

	public FileManager() {
		this.bm = BufferManager.getInstance();
		this.heapFiles = new Vector<HeapFile>();
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
		this.heapFiles.lastElement().createNewOnDisk();
	}

	/**
	 * Insert un record dans une relation.
	 * @param Record record record à inserer 
	 * @param String relName relation ou il faut inserer record
	 * @return
	 */

	public Rid inserRecordInRelation(Record record, String relName) throws Exception {
		Rid rid = null;
		
		for(HeapFile heapFile : this.heapFiles) {
			if(heapFile.getRelDef().getName().equals(relName)) {
				rid = heapFile.insertRecord(record);
			}
		}
		
		if(rid == null)
			throw new Exception("La relation n'a pas été trouvé dans la liste");
		
		record.setRid(rid);
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
			if(heapFile.getRelDef().getName().contentEquals(relName)) {
				return heapFile.getAllRecords();
			}
		}
		throw new Exception("La relation n'a été pas trouvé dans la liste de heapFiles de FileManager");
	}


	/**
	 * Renvoies tous les records de la relation donnée en paramètre pour lesquels la valeur sur la colonne idxCol est égale à valeur.
	 * @param String relName
	 * @param int idxCol, un entier correspondant à un indice de colonne
	 * @param String  valeur
	 * @return Liste de records
	 * @throws Exception 
	 */

	public Vector<Record> selectFromRelation(String relName, int idxCol, Object valeur) throws Exception  {
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
	 * @throws Exception 
	 */
	public boolean deleteRecord(Record record) throws Exception{
		for(HeapFile heapFile : this.heapFiles) {
			if(heapFile.getRelDef().getName() == record.getRelation().getName()) {
				byte[] buff = this.bm.getPage(record.getRid().getPageId());
				
				for(int i = record.getRid().getSlotIdx(); i < record.getRid().getSlotIdx() + record.getRelation().getRecordSize(); i++) {
					buff[i] = 0;
				}
				
				PageId headerPage = new PageId(heapFile.getRelDef().getFileIdx(), 0);
				byte[] buffheader = this.bm.getPage(headerPage);
				buffheader[record.getRid().getSlotIdx()] -= 1;
				
				this.bm.freePage(record.getRid().getPageId(), true);
				this.bm.freePage(headerPage, true);
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the heapFiles
	 */
	public Vector<HeapFile> getHeapFiles() {
		return heapFiles;
	}

	/**
	 * @param heapFiles the heapFiles to set
	 */
	public void setHeapFiles(Vector<HeapFile> heapFiles) {
		this.heapFiles = heapFiles;
	}
	
}
