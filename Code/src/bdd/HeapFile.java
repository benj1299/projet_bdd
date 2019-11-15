package bdd;

public class HeapFile {

	RelDef relDef;
	DiskManager dm;
	BufferManager bm;
	
	public HeapFile() {
		this.dm = DiskManager.getInstance();
		this.bm = BufferManager.getInstance();
		this.relDef = new RelDef();
	}

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

	public PageId addDataPage(){
		this.dm.addPage(relDef.getFileIdx());
		PageId pid = new PageId (relDef.getFileIdx(), 0);
		byte[] buff = null;
		try {
			buff = this.bm.getPage(pid);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		buff[0]+= 1;
		try {
			this.dm.writePage(pid, buff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}
}
	//PageId getFreeDataPageId()