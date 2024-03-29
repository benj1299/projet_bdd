package bdd;

public class PageId {
	private int fileIdx; 
	private int pageIdx;
	
	public PageId(int fileIdx, int pageIdx) {
		this.fileIdx = fileIdx;
		this.pageIdx = pageIdx;
	}

	public int getFileIdx() {
		return this.fileIdx;
	}
	
	public int getPageIdx() {
		return this.pageIdx;
	}
	
	@Override
	public String toString() {
		return "file index : " + this.fileIdx + ", page index : " + this.pageIdx;
	}
}
