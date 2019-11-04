import java.util.Random;

import junit.framework.*;

public class DiskManagerTest extends TestCase {
	
	private int randomIndex = new Random().nextInt(1000 - 1 + 1) + 1;
	private DiskManager dkManager = DiskManager.getInstance();
	
	public DiskManagerTest() {
		this.dkManager.createFile(this.randomIndex);
	}

	public void TestAddPage() throws Exception {
		try {
			PageId pageTest = this.dkManager.addPage(this.randomIndex);
			if(!(pageTest instanceof PageId)) {
				fail("L'ajout de page ne renvoie pas une instance de PageId");
			}
		} catch(NullPointerException e) {
			fail("AddPage n'arrive pas à accéder à la page créer précédement sur le disk");
		}
		
	}
	
	public void TestReadPage() throws Exception {
		fail("Cas de tests a ecrire");
	}
	
	public void TestWritePage() throws Exception {
		fail("Cas de tests a ecrire");
	}
}
