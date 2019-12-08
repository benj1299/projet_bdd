package bdd;

/**
 * 
 * Correspond au Rid (Record Id, donc identifiant) d’un Record.
 */
public class Rid {
	
	private PageId pageId;
	private int slotIdx;
	
	public Rid (PageId pageId, int slotIdx){
		this.pageId = pageId;
		this.slotIdx = slotIdx;
	}

	/**
	 * @return the pageId
	 */
	public PageId getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(PageId pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return the slotIdx
	 */
	public int getSlotIdx() {
		return slotIdx;
	}

	/**
	 * @param slotIdx the slotIdx to set
	 */
	public void setSlotIdx(int slotIdx) {
		this.slotIdx = slotIdx;
	}
}
