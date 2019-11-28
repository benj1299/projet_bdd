package bdd;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;

public class DBDef {
	private static DBDef instance ;
	 
	public static DBDef getInstance() {
      if (instance == null) {
         synchronized(DBDef.class) {
            if (instance == null) {
               instance = new DBDef() ;
            }
         }
      }
      return instance ;
   }
	
	private Vector<RelDef> tabRelDef;
	private int count;
	
	public DBDef() {
		this.tabRelDef = new Vector<RelDef>();
		this.count = 0;
	}
	
	public void init() throws FileNotFoundException, IOException, ClassNotFoundException{
    	FileInputStream fis = new FileInputStream(Constants.DB_DIRECTORY +"Catalog.def");
    	ObjectInputStream ois = new ObjectInputStream(fis);
    	DBDef.instance = (DBDef) ois.readObject();
    	ois.close();	
	}
	
	public void finish() throws FileNotFoundException, IOException {
		FileOutputStream fos = new FileOutputStream(Constants.DB_DIRECTORY +"Catalog.def");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(DBDef.instance);
		oos.close();
	}
	
	/**
	 * Ajoute un element à la liste vector et incrémente count
	 * @param x
	 */
	public void addRelation(RelDef relation) {
		this.tabRelDef.addElement(relation);
		this.count++;
	}

	/**
	 * Créé une nouvelle instance remettant ainsi tout à 0
	 */
	public void reset() {
		DBDef.instance = new DBDef();
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
	
