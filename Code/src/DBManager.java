
public class DBManager {
	
	private DBDef dbdef = new DBDef();
	private String[] args; 
	
	public void init() {
		this.dbdef.init();
	}
	
	public void finish() {
		this.dbdef.finish();
	}
	
	public Boolean ProcessCommand(String chaine) {
		this.args = chaine.split(" ");
		
		if(this.args[0].contentEquals("exit")) {
			return false;
		}
		
		if(args[0].contentEquals("create")) {
			
		}
		
		return true;
	}
	
	public void CreateRelation(String name, int nbColumn, String [] typeColumn){
		
	}
}
