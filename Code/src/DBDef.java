import java.util.Vector;

public class DBDef {
	
	private Vector<RelDef> tabRelDef;
	private int count;
	
	public DBDef() {
		this.tabRelDef = new Vector<RelDef>();
		this.count = 0;
	}
	
	public void init() {
		
	}
	
	public void finish() {
		
	}
	
	/**
	 * Ajoute un element à la liste vector et incrémente count
	 * @param x
	 */
	public void addRelation(RelDef x) {
		this.tabRelDef.addElement(x);
		this.count++;
	}

	// Getters / Setters
	
	/**
	 * 
	 * @return Vector
	 */
	public Vector<RelDef> getTabRelDef() {
		return this.tabRelDef;
	}

	/**
	 * 
	 * @param tabRelDef
	 */
	public void setTabRelDef(Vector<RelDef> tabRelDef) {
		this.tabRelDef = tabRelDef;
	}

	/**
	 * 
	 * @return int
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}	
}
	
