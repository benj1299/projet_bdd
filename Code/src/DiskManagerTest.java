import junit.framework.TestCase;

public class DiskManagerTest extends TestCase {
	
	protected int randomIndex = 1000;
	protected DiskManager dkManager = DiskManager.getInstance();
	protected PageId pageId;
	protected byte[] buff;
	
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
