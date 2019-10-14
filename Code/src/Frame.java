
public class Frame {

	private PageId pageId;
	private int pinCount;
	private boolean dirty;
	private byte[] buff;
	
	public Frame(byte[] buff, PageId pageId, int pinCount, boolean dirty) {
		this.pageId = pageId;
		this.pinCount = pinCount;
		this.dirty = dirty;
		this.buff = buff;
	}
	
	/**
	 * incremente pinCount de +1
	 */
	public void increment() {
		this.pinCount++;
	}
	/**
	 * decremente pinCount de - 1
	 */
	public void decrement() {
		this.pinCount--;
	}
	
	/**
	 * Initialise tous les flags à 0 ou null
	 */
	public void initFlags() {
		this.setPageId(null);
		this.setPinCount(0);
		this.setDirty(false);
		this.setBuff(null);
	}
	
	/**
	 * 
	 * @return boolean true si tous les flags sont null ou à un (donc initilisé);
	 */
	public boolean isNull() {
		if(this.isDirty() == false && this.pageId == null && this.getPinCount() == 0 && this.getBuff() == null) {
			return true;
		}
		return false;
	}

	
	// Getters / Setters
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
	 * @return the pinCount
	 */
	public int getPinCount() {
		return pinCount;
	}
	

	/**
	 * @param pinCount the pinCount to set
	 */
	public void setPinCount(int pinCount) {
		this.pinCount = pinCount;
	}
	

	/**
	 * @return the dirty
	 */
	public boolean isDirty() {
		return dirty;
	}
	

	/**
	 * @param dirty the dirty to set
	 */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	

	/**
	 * @return the buff
	 */
	public byte[] getBuff() {
		return buff;
	}
	

	/**
	 * @param buff the buff to set
	 */
	public void setBuff(byte[] buff) {
		this.buff = buff;
	}
}
