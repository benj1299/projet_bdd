package bdd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class HeapFile {

	private RelDef relDef;
	private DiskManager dm;
	private BufferManager bm;

	public HeapFile(RelDef reldef) {
		this.dm = DiskManager.getInstance();
		this.bm = BufferManager.getInstance();
		this.relDef = reldef;
	}

	/**
	 *  Création du fichier disque correspondant au HeapFile et rajoute une Header Page « vide » à ce fichier.
	 *  @throws IOException
	 */
	public void createNewOnDisk() throws IOException{
		this.dm.createFile(relDef.getFileIdx());
		PageId headerPage = this.dm.addPage(relDef.getFileIdx());	
		byte[] buff = null;
		try {
			buff = this.bm.getPage(headerPage);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		buff[0]=0;
		try {
			this.dm.writePage(headerPage, buff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.bm.freePage(headerPage, true);
	}	

	/**
	 * Rajoute une page au fichier Disk correspondant et actualise les infos de la headerPage
	 * @throws IOException
	 */
	public PageId addDataPage() throws IOException {
		byte[] buffHeader = null;
		PageId pid = this.dm.addPage(relDef.getFileIdx());
		PageId headerPage = new PageId(relDef.getFileIdx(), 0);

		try {
			buffHeader = this.bm.getPage(headerPage);
			buffHeader[0]+= 1;
			buffHeader[buffHeader.length] = (byte) this.relDef.getSlotCount();
			this.dm.writePage(headerPage, buffHeader);
			this.bm.freePage(headerPage, true);
			this.relDef.setSlotCount(this.relDef.getSlotCount()-1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return pid;
	}

	/**
	 * Identifie les pages libres du fichier
	 * @return PageId d’une page de données qui a encore des cases libres sinon null
	 */
	public PageId getFreeDataPageId() {
		PageId headerPage = new PageId(this.relDef.getFileIdx(), 0);
		try {
			byte[] buffHeader = this.bm.getPage(headerPage);
			for(int i = 1; i < buffHeader.length; i++) {
				if((int) buffHeader[i] > 0) {
					this.bm.freePage(headerPage, false);
					return new PageId(this.relDef.getFileIdx(), i);
				}
			}
			this.bm.freePage(headerPage, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * É́crit un record dans la page de données identifiée par pageId, et renvoyer son Rid
	 * @param record
	 * @param pageId
	 * @return record Id
	 * @throws Exception 
	 */
	public Rid writeRecordToDataPage(Record record, PageId pageId) throws Exception{
		byte[] pageBuffer = this.bm.getPage(pageId);
		int pos = record.getRelation().getSlotCount();
		record.writeToBuffer(pageBuffer, pos);
		this.bm.freePage(pageId, true);

		PageId headerPage = new PageId(relDef.getFileIdx(), 0);
		byte[] buffheader = bm.getPage(headerPage);
		buffheader[pageId.getPageIdx()] -= 1;
		
		Rid rid = new Rid(pageId, pageBuffer.length);
		record.setRid(rid);
		
		return rid;
	}

	/**
	 * Récupère les records d'une page
	 * @return ArrayList<Record> liste, une liste de Record
	 * @throws Exception 
	 */
	public Vector<Record> getRecordsInDataPage(PageId pageId) throws Exception{	
		Vector<Record> records = new Vector<Record>();
		byte[] bufferPage = this.bm.getPage(pageId);
		
		for(byte buff : bufferPage) {
			if(buff == 1) {
				Record record = new Record(this.relDef);
			}
		}
		
		for(int i = 0; i < this.relDef.getSlotCount(); i++) {
			records.add(new Record(this.relDef));
		}
		
		return records;
	}

	/**
	 * Insère un record dans une page libre
	 * @param record
	 * @return Record ID correspondant
	 * @throws Exception 
	 */
	public Rid insertRecord(Record record) throws Exception {
		PageId pid = getFreeDataPageId();
		return this.writeRecordToDataPage(record, pid);	
	}

	/**
	 * Récupère tous les records du heapFile
	 * @return ArrayList<Record> liste, une liste de Record
	 * @throws Exception 
	 */
	public Vector<Record> getAllRecords() throws Exception{
		PageId headerPage = new PageId(this.relDef.getFileIdx(), 0);
		byte[] buffHeader = this.bm.getPage(headerPage);
		Vector<Record> allrecords = new Vector<Record>();
		
		for (int i=1; i < buffHeader.length; i++){
			allrecords.addAll(this.getRecordsInDataPage(new PageId(this.relDef.getFileIdx(), i)));
		}
		
		return allrecords;
	}

	/**
	 * 
	 * @return this.relDef
	 */
	public RelDef getRelDef() {
		return this.relDef;
	}
}
