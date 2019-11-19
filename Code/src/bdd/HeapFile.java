package bdd;

public class HeapFile {

	RelDef relDef;
	DiskManager dm;
	BufferManager bm;
	
	public HeapFile(int fileIdx) {
		this.dm = DiskManager.getInstance();
		this.bm = BufferManager.getInstance();
		this.relDef = new RelDef();
		this.relDef.setFileIdx(fileIdx);
	}

	/*
	 *  Création du fichier disque correspondant au Heap File et rajoute une Header Page « vide » à ce fichier.
	 */
	public void createNewOnDisk(){
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
	public PageId addDataPage(){
		this.dm.addPage(relDef.getFileIdx());
		PageId pid = new PageId(relDef.getFileIdx(), 0);
		byte[] buff = null;
		try {
			buff = this.bm.getPage(pid);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		buff[0]+= 1;
		try {
			this.dm.writePage(pid, buff);
			this.relDef.setSlotCount(this.relDef.getSlotCount()+1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}
	
	/*
	 * Identifie les pages libres
	 * @return PageId d’une page de données qui a encore des cases libres sinon null
	 */
	public PageId getFreeDataPageId() {
		PageId pid = new PageId(this.relDef.getFileIdx(), 0);
		try {
			byte[] Page = this.bm.getPage(pid);
			if(((int)Page.length) - 1 >= this.relDef.getSlotCount()) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}