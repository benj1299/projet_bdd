import java.util.Vector;
public class BufferManager {

	private static BufferManager instance; 	
	private Vector<Frame> bufferPool = new Vector<Frame>();
	private DiskManager dkManager = DiskManager.getInstance();

	public static BufferManager getInstance(){
		if (instance == null) {
	         synchronized(BufferManager.class) {
	            if (instance == null) {
	               instance = new BufferManager() ;
	            }
	         }
	      }
	      return instance ;
	}
	
	/**
	 * Rempli (ou remplace si besoin) le buffer correspondant à la bonne frame 
	 * avec le contenu de la page désignée par l’argument pageId
	 * @param pageId
	 * @return un des buffers associés à une case
	 */
	public byte[] getPage(PageId pageId) {
		// Verifie si la page est dans le bufferpool (bufferPool.get(i))
		// Si c'est le cas, renvoyer la page
		// Sinon verifier sur disk si la page y est (readPage)
		// Si elle n'y est pas, renvoies une exception
		
		byte[] content = null;
		
		for(int i = 0; i < bufferPool.size(); i++) {
			if(bufferPool.get(i).getPageId() == pageId)
				bufferPool.get(i).increment();
				return bufferPool.get(i).getBuff();
		}
		
		for(Frame x : this.bufferPool) {
			if(x.isNull()) {
				// rajouter pageId dans le bufferPool au premier emplacement initialisé (null)
			}
		}
		
		return content;
	}
	
	/**
	 * Décremente pinCount et actualise le flag dirty
	 * @param pageId
	 * @param valdirty
	 */
	public void freePage(PageId pageId, boolean valdirty) {
		for(int i = 0; i < this.bufferPool.size(); i++) {
			if(this.bufferPool.get(i).getPageId() == pageId)
				this.bufferPool.get(i).decrement();	
			if(valdirty)
				flushAll();
		}
	}
	
	/**
	 * Écriture sur disk (via diskManager) des pages avec dirty = 1 
	 * et remise à 0 des flags/infos/contenus des buffers
	 */
	public void flushAll() {
		for(int i = 0; i < bufferPool.size(); i++) {
			if (bufferPool.get(i).isDirty()) {
				dkManager.writePage(bufferPool.get(i).getPageId(), bufferPool.get(i).getBuff());
				bufferPool.get(i).initFlags();
			}	
		}
	}
	
}
