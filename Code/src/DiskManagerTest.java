import java.util.Random;

import junit.framework.*;

public class DiskManagerTest extends TestCase {
	
	private int randomIndex = new Random().nextInt(1000 - 1 + 1) + 1;
	private DiskManager dbManager = DiskManager.getInstance();
	
	public DiskManagerTest() {
		dbManager.createFile(this.randomIndex);
	}

	public void TestAddPage() throws Exception {		
		fail("Cas de tests a ecrire");
	}
	
	public void TestReadPage() throws Exception {
		fail("Cas de tests a ecrire");
	}
	
	public void TestWritePage() throws Exception {
		fail("Cas de tests a ecrire");
	}
}
