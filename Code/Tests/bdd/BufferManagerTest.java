package bdd;

import static org.junit.jupiter.api.Assertions.*; 
import org.junit.jupiter.api.Test;

public class BufferManagerTest extends DiskManagerTest {
	
	private BufferManager bm = BufferManager.getInstance();
	
	@Test
	public final void TestGetPage() {
		byte[] tab = assertDoesNotThrow(() -> bm.getPage(this.pageId));
		assertTrue(tab.length > 0, "La page n'est pas recupere, le tableau de byte est vide");
	}
	
	@Test
	public final void TestFreePage() {
		// Tests à écrire
	}

	@Test
	public final void TestFlushAll() {
		// Tests à écrire
	}
	
}
