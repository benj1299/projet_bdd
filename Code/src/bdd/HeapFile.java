package bdd;

import java.io.File;

public class HeapFile {

	RelDef relDef;

	public void createNewOnDisk() {
		DiskManager.createFile(relDef.getfileIdx());
		PageId headerPage = DiskManager.addPage(relDef.getfileIdx());	
		byte[] buff =BufferManager.getPage(headerPage);
		buff[0]=0;
		BufferManager.freePage(headerPage, 1);
	}	

	public PageId addDataPage() {
		DiskManager.addPage(relDef.getfileIdx());
		byte [] buff = BufferManager.getPage(0);
		buff[0]=+1;
	}
}

