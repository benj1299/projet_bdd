
public class Frame {

	private PageId pageId;
	private int pinCount;
	private boolean dirty;
	
	public Frame(PageId pageId, int pinCount, boolean dirty) {
		this.pageId=pageId;
		this.pinCount=pinCount;
		this.dirty=dirty;
	}
	
	public void increment() {
		this.pinCount++;
	}
	public void decrement() {
		this.pinCount--;
	}
	

}
