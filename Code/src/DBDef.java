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
}
	
