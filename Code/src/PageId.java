
public class PageId {
<<<<<<< HEAD
	
=======
>>>>>>> 1547a0d2d19c93dbadaea2ebd719cef95bf40c27
	private int fileIdx; 
	private int pageIdx;
	
	/**
	 * 
	 * @param fileIdx
	 * @param pageIdx
	 */
	public PageId(int fileIdx, int pageIdx ) {
		this.fileIdx=fileIdx;
		this.pageIdx=pageIdx;
	}
	public int getFileIdx() {
		return this.fileIdx;
	}
	
	public int getPageIdx() {
		return this.pageIdx;
	}
}
