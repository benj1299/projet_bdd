import java.util.Random;
import junit.framework.*;

public class DiskManagerTest extends TestCase {
	
	private int randomIndex = new Random().nextInt(1000 - 1 + 1) + 1;
	private DiskManager dkManager = DiskManager.getInstance();
	private PageId pageId;
	private byte[] buff;
	
	public DiskManagerTest() {
		this.dkManager.createFile(this.randomIndex);
	}

	public void TestAddPage() throws Exception {
		try {
			this.pageId = this.dkManager.addPage(this.randomIndex);
			if(!(this.pageId instanceof PageId)) {
				fail("L'ajout de page ne renvoie pas une instance de PageId");
			}
		} catch(NullPointerException e) {
			fail("AddPage n'arrive pas à accéder à la page créer précédement sur le disk");
		}
	}
	
	public void TestReadPage() throws Exception {
		try {
			this.dkManager.readPage(this.pageId, this.buff);
			assertTrue("Buffer non remplie pour lecture", this.buff.length > 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	public void TestWritePage() throws Exception {
		try {
			//inverse le tableau pour écriture
			for(int i=0; i<this.buff.length/2; i++){
				  byte temp = this.buff[i];
				  this.buff[i] = this.buff[this.buff.length -i -1];
				  this.buff[this.buff.length -i -1] = temp;
			}
			this.dkManager.writePage(this.pageId, this.buff);
		} catch(Exception e) {
			fail(e.getMessage());
		}
	}
}
