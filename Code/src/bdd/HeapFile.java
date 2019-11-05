import java.io.File;


public class HeapFile {
	
	
	RelDef relDef;
	
	
	
	
	
	void createNewOnDisk(){
		
		
				DiskManager.createFile(relDef.getfileIdx());
				PageId headerPage = DiskManager.addPage(relDef.getfileIdx());	
					byte[] buff =BufferManager.getPage(headerPage);
					
					buff[0]=0;
					
					DiskManager.writePage(headerPage, buff);
		
					BufferManager.freePage(headerPage, 1);
		
	}	

	
	
	PageId addDataPage(){
		
			
		DiskManager.addPage(relDef.getfileIdx());
		
		PageId pid =new PageId (relDef.getfileIdx(),0);
		
		byte [] buff = BufferManager.getPage(pid);
		buff[0]=+1;
		
		DiskManager.writePage(pid, buff);

		
		
		
		
		
		
		
		
	}
	
	
	
	
	//PageId  getFreeDataPageId(){	
		
		
		
		
			
		
		
		
	
