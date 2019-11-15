import java.io.File;

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
		this.dm.createFile(relDef.getfileIdx());
		PageId headerPage = DiskManager.addPage(relDef.getfileIdx());	
		byte[] buff = this.bm.getPage(headerPage);
		buff[0]=0;
		this.dm.writePage(headerPage, buff);
		this.bm.freePage(headerPage, 1);

	}	

	public PageId addDataPage(){
		DiskManager.addPage(relDef.getfileIdx());
		PageId pid = new PageId (relDef.getfileIdx(),0);
		byte [] buff = this.bm.getPage(pid);
		buff[0]=+1;
		this.dm.writePage(pid, buff);
	}

	//PageId getFreeDataPageId()