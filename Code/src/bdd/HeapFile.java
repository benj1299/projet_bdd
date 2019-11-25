package bdd;

import java.io.IOException;
import java.util.ArrayList;

public class HeapFile {

	private RelDef relDef;
	private DiskManager dm;
	private BufferManager bm;
	
	public HeapFile(RelDef reldef) {
		this.dm = DiskManager.getInstance();
		this.bm = BufferManager.getInstance();
		this.relDef = reldef;
	}

	/*
	 *  Création du fichier disque correspondant au HeapFile et rajoute une Header Page « vide » à ce fichier.
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

	/*
	 * Rajoute une page au fichier Disk correspondant et actualise les infos de la headerPage
	 */
	public PageId addDataPage() throws IOException{
		byte[] buffHeader = null;
		PageId pid = this.dm.addPage(relDef.getFileIdx());
		PageId headerPage = new PageId(relDef.getFileIdx(), 0);
		
		try {
			buffHeader = this.bm.getPage(headerPage);
			buffHeader[0]+= 1;
			buffHeader[buffHeader.length] = 0;
			this.dm.writePage(headerPage, buffHeader);
			this.bm.freePage(headerPage, true);
			this.relDef.setSlotCount(this.relDef.getSlotCount()-1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return pid;
	}
	
	/*
	 *  Identifie les pages libres sinon créé une nouvelle page
	 * @return PageId d’une page de données qui a encore des cases libres sinon null
	 */
	public PageId getFreeDataPageId() {
		PageId headerPage = new PageId(this.relDef.getFileIdx(), 0);
		try {
			byte[] buffHeader = this.bm.getPage(headerPage);
			if(((int)buffHeader.length) - 1 >= this.relDef.getSlotCount()) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Pour inserer un record
	 * @param record
	 * @return
	 */
	public Rid insertRecord(Record record) {
		
	}
	/**
	 * 
	 * @return ArrayList<Record> liste, une liste de Record
	 */
	public ArrayList<Record> getAllRecords(){
		
	}
	
	
			public Rid writeRecordToDataPage(Record record, PageId pageId){
				
				byte[] Buffer = bm.getPage(pageId);
				int pos=Buffer.length;
			
			
				 record.writeToBuffer(Buffer, pos);
				 
				 bm.freePage(pageId, true);
				 
				  PageId headerPage = new PageId(relDef.getFileIdx(), 0);
				  
				  byte[]  buffheader = bm.getPage(headerPage);
				 
			
			
			}
		
			
	
	
	
	
	
	
	
	
	
}
