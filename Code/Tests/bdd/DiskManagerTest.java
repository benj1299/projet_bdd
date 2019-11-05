package bdd;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

public class DiskManagerTest {
	
	protected int fileIndex = 1000;
	protected DiskManager dkManager = DiskManager.getInstance();
	protected PageId pageId;
	protected byte[] buff;
	
	@Test
	public void TestCreateFile() {
		assertDoesNotThrow(() -> this.dkManager.createFile(this.fileIndex));
	}

	@Test
	public void TestAddPageException() {
		assertDoesNotThrow(() -> this.dkManager.addPage(this.fileIndex));	
	}
	
	@Test
	public void TestReadPageException() {
		assertDoesNotThrow(() -> this.dkManager.readPage(this.pageId, this.buff));
	}
	
	@Test
	public void TestReadPageBufferEmpty() {
		assertTrue(this.buff.length > 0, "Buffer non remplie pour lecture");
	}
	
	@Test
	public void TestReadPageResult() {
		byte[] buffer = new byte[Constants.PAGE_SIZE];
		this.dkManager.readPage(this.pageId, buffer);
		assertEquals(this.buff, buffer);
	}
	
	@Test
	public void TestWritePageException() {
		for(int i=0; i<this.buff.length/2; i++){
			  byte temp = this.buff[i];
			  this.buff[i] = this.buff[this.buff.length -i -1];
			  this.buff[this.buff.length -i -1] = temp;
		}
		assertDoesNotThrow(() -> this.dkManager.writePage(this.pageId, this.buff));		
	}
	
	@Test
	public void TestWritePageResult() {
		byte[] buffer = new byte[Constants.PAGE_SIZE];
		this.dkManager.readPage(this.pageId, buffer);
		assertEquals(this.buff, buffer);
	}
}
