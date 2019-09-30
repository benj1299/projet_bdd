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
	
	public void addRelation(RelDef x) {
		this.tabRelDef.addElement(x);
		this.count++;
		
	}

	public Vector<RelDef> getTabRelDef() {
		return tabRelDef;
	}

	public void setTabRelDef(Vector<RelDef> tabRelDef) {
		this.tabRelDef = tabRelDef;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	

	
}
	
