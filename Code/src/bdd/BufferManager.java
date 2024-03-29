package bdd;
import java.util.Vector;
public class BufferManager {

	private static BufferManager instance; 	
	private Vector<Frame> bufferPool;
	private DiskManager dkManager;
	private Vector<PageId> lru;
	
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

	public BufferManager() {
		this.bufferPool = new Vector<Frame>();
		this.dkManager = DiskManager.getInstance();
		this.lru = new Vector<>();
	}
	/**
	 * Rempli (ou remplace si besoin) le buffer correspondant à la bonne frame 
	 * avec le contenu de la page désignée par l’argument pageId
	 * @param pageId
	 * @return un des buffers associés à une case
	 * @throws Exception 
	 */
	public byte[] getPage(PageId pageId) throws Exception {
		byte content[] = new byte[Constants.PAGE_SIZE];
		Frame suppr = null;

		for(Frame x : this.bufferPool)
			if(x.getPageId().equals(pageId)) {
				x.increment();
				return x.getBuff();		
			}

		if(this.bufferPool.size() < Constants.FRAME_COUNT) {
			this.dkManager.readPage(pageId, content);
			Frame frame = new Frame(content, pageId);
			frame.increment();
			this.bufferPool.add(frame);
			
			return frame.getBuff();			
		}
		PageId pI = this.lRU();
		for(Frame x : this.bufferPool) {
			if(x.getPageId().equals(pI)) {
				suppr = x;
			}
		}
		this.bufferPool.remove(suppr);
		this.dkManager.readPage(pageId, content);
		Frame newFrame = new Frame(content, pageId);
		newFrame.increment();
		this.bufferPool.add(newFrame);
		content = newFrame.getBuff();
		return content;
	}
	
	/**
	 * politique de remplacement des Frames dans le bufferpool
	 * @return
	 */
	public PageId lRU() throws Exception {
		if(this.lru.isEmpty()) {
			return null;
		}
		PageId pI = this.lru.firstElement();
		this.lru.remove(pI);

		for(Frame x : this.bufferPool) {
			if(x.getPageId().equals(pI)) {
				if(x.isDirty()) {
					this.dkManager.writePage(x.getPageId(),x.getBuff() );
				}
			}
		}
		return pI;
		
	}
	
	public byte[] getPageClock(PageId pageId) throws Exception {		
		byte content[] = new byte[Constants.PAGE_SIZE];
		boolean exception = true;

		for(Frame x : this.bufferPool)
			if(x.getPageId().equals(pageId)) {
				x.increment();
				return x.getBuff();		
			}

		if(this.bufferPool.size() < Constants.FRAME_COUNT) {
			this.dkManager.readPage(pageId, content);
			Frame frame = new Frame(content, pageId);
			frame.increment();
			this.bufferPool.add(frame);
			return frame.getBuff();			
		}
		else {
			for(int i = 0 ; i<2 ; i++)
				for(Frame x1 : this.bufferPool)
					if(x1.getPinCount() == 0) {
						if(x1.isDirty()) {
							this.dkManager.writePage(x1.getPageId(), x1.getBuff());
							x1.setDirty(false);
						}
						if(x1.isRefBit()) {
							x1.setRefBit(false);
						}
						else {
							this.bufferPool.remove(x1);
							this.dkManager.readPage(pageId, content);
							Frame newFrame = new Frame(content, pageId);
							newFrame.increment();
							this.bufferPool.add(newFrame);
							content = newFrame.getBuff();
							exception = false;
						}
					}
		}

		if(exception) {
			throw new BufferPoolNonLibreException("tout les pinCount à 1 et plus de place");
		}
		
		return content;
	}
/**
	 * Libère la page en décrémentant pinCount et actualise le flag dirty
	 * @param pageId
	 * @param valdirty
	 */
	public void freePage(PageId pageId, boolean valdirty) {
		for(Frame x : this.bufferPool) {
			if(x.getPageId().equals(pageId)) {
				x.decrement();
				x.setDirty(valdirty);
				if(x.getPinCount() == 0) {
					x.setRefBit(true);
					this.lru.add(x.getPageId());
				}
			}
		}
	}

	/**
	 * Écriture sur disk (via diskManager) des pages avec dirty = 1 
	 * et remise à 0 des flags/infos/contenus des buffers
	 * @throws Exception 
	 */
	public void flushAll() throws Exception {
		for(int i = 0; i < bufferPool.size(); i++) {
			if (bufferPool.get(i).isDirty()) {
				this.dkManager.writePage(bufferPool.get(i).getPageId(), bufferPool.get(i).getBuff());
				bufferPool.get(i).initFlags();
			}	
			bufferPool.get(i).initFlags(); // pas sur
		}
	}
	
	/**
	 * Créé une nouvelle instance remettant ainsi tout à 0
	 */
	public void reset() {
		BufferManager.instance = new BufferManager();
	}

}
