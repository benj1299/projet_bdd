package bdd;
/**
 * 
 *classe Rid qui correspond au Rid (Record Id, donc identifiant) d’un Record.
 */
public class Rid {


	private PageId pageId;
	private int slotIdx;
	
	public Rid (PageId pageId, int slotIdx){
		this.pageId=pageId;
		this.slotIdx=slotIdx;
	}
	
	
}
