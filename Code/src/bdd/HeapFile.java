package bdd;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
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
	 * @throws Exception 
	 */
	public void createNewOnDisk() throws Exception{
		this.dm.createFile(relDef.getFileIdx());
		PageId headerPage = this.dm.addPage(relDef.getFileIdx());
		byte[] buff = null;
		try {
			buff = this.bm.getPage(headerPage);
			this.dm.writePage(headerPage, buff);
			this.bm.freePage(headerPage, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * Rajoute une page au fichier Disk correspondant et actualise les infos de la headerPage
	 * @throws Exception 
	 */
	public PageId addDataPage() throws Exception {
		PageId newPid = this.dm.addPage(relDef.getFileIdx());

		try {
			PageId headerPage = new PageId(relDef.getFileIdx(), 0);
			byte[] buff = this.bm.getPage(headerPage);
			ByteBuffer bbBuff = ByteBuffer.wrap(buff);
			
			bbBuff.putInt(0, bbBuff.getInt(0) + 1);
			bbBuff.putInt(newPid.getPageIdx(), this.relDef.getSlotCount());
			
			this.dm.writePage(headerPage, buff);
			this.bm.freePage(headerPage, true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return newPid;
	}

/**
	 * Identifie les pages libres du fichier
	 * @return PageId d’une page de données qui a encore des cases libres sinon null
	 * @throws Exception 
	 */
	public PageId getFreeDataPageId() throws Exception {		
		try {
			PageId headerPage = new PageId(this.relDef.getFileIdx(), 0);
			byte[] buffHeader = this.bm.getPage(headerPage);
			ByteBuffer bb = ByteBuffer.wrap(buffHeader);
			int pageCount = bb.getInt(0);
			
			if(pageCount == 0) return this.addDataPage();
						
			for(int i = 1; i <= pageCount; i++) {				
				if (bb.getInt(i) > 0) {
					this.bm.freePage(headerPage, false);
					return new PageId(this.relDef.getFileIdx(), i);
				}
				else {
					this.bm.freePage(headerPage, false);
					return this.addDataPage();
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
	public Rid writeRecordToDataPage(Record record, PageId pageId) throws Exception {
		int indiceSlot = 0;	
		byte[] buffRecord = new byte[this.getRelDef().getRecordSize()];
		
		PageId headerPage = new PageId(relDef.getFileIdx(), 0);
		byte[] buffHeader = bm.getPage(headerPage);
		ByteBuffer bbHeader = ByteBuffer.wrap(buffHeader);
		
		byte[] pageBuffer = this.bm.getPage(pageId);
		ByteBuffer bb = ByteBuffer.wrap(pageBuffer);
		
		for(int i = 0; i < this.getRelDef().getSlotCount(); i++) {
			if (bbHeader.get(i) == 0) {
				record.writeToBuffer(buffRecord, 0);

				indiceSlot = this.getRelDef().getSlotCount() + i * this.getRelDef().getRecordSize();
				bbHeader.put(i, (byte) 1);
				bbHeader.position(indiceSlot);
				bbHeader.put(buffRecord);
	
				this.dm.writePage(pageId, pageBuffer);
				this.bm.freePage(pageId, true);
				break;
			}

		}
		
		bbHeader.putInt(0, bb.getInt(0) - 1);
		this.dm.writePage(headerPage, buffRecord);
		this.bm.freePage(headerPage, true);

		Rid rid = new Rid(pageId, indiceSlot);
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
		ByteBuffer bb = ByteBuffer.wrap(bufferPage);
		int pos = 0;
		int notEmptySlot = this.relDef.getSlotCount();
		
		for(int i = 0; i < notEmptySlot; i++) {
			Record rd = new Record(this.relDef);
			pos = rd.getRelation().getRecordSize()*i;
			rd.readFromBuffer(bufferPage, pos);
			records.add(rd);
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
		PageId pid = this.getFreeDataPageId();
		if(pid == null) throw new Exception ("Il n'y a pas de renvoie de page avec getFreeDataPage");
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
