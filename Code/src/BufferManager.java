
public class BufferManager {

	private static BufferManager instance; 	
	
	public static BufferManager getInstance(){
	    if(instance == null)
	    	instance = new BufferManager();
	    return instance;
	}

	/**
	 * Rempli (ou remplace si besoin) le buffer correspondant à la bonne frame 
	 * avec le contenu de la page désignée par l’argument pageId
	 * @param pageId
	 * @return un des buffers associés à une case
	 */
	public byte[] getPage(PageId pageId) {
		byte[] content = null;
		for(int i = 0; bufferPull.length; i++) {
			
		}
		return content;
	}
	
	/**
	 * Décremente pinCount et actualise le flag dirty
	 * @param pageId
	 * @param valdirty
	 */
	public void freePage(PageId pageId, int valdirty) {
		
	}
	
	/**
	 * Écriture sur disk (via diskManager) des pages avec dirty = 1 
	 * et remise à 0 des flags/infos/contenus des buffers
	 */
	public void flushAll() {
		
	}
	
}
