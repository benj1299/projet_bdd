public class BufferManagerTest extends DiskManagerTest {
	
	private BufferManager bm = BufferManager.getInstance();
	
	public void TestGetPage() throws Exception {
		byte[] tab = bm.getPage(this.pageId);
		assertTrue("La page n'est pas recupere, le tableau de byte est vide", tab.length > 0);
	}
	
	public void TestFreePage() throws Exception {
		fail("Cas de tests a ecrire");
	}

	public void TestFlushAll() throws Exception {
		fail("Cas de tests a ecrire");
	}
	
}
