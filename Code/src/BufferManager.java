
public class BufferManager {

	private static BufferManager instance = new BufferManager(); 	
	
	public static BufferManager getInstance(){
	    if(instance == null)
	    	instance = new BufferManager();
	    return instance;
	}

	public byte[] getPage(PageId pageId) {
		byte[] contenu=null;
		for(int i=0;bufferPull.length;i++) {
			
		}
		return contenu;
	}
	
	public void freePage(PageId pageId, int valdirty) {
		
	}
	
	public void flushAll() {
		
	}
	
}
