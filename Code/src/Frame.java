
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
	
	public void increment() {
		this.pinCount++;
	}

	public void decrement() {
		this.pinCount--;
	}
}
