package bdd;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class DBDef {
	private static DBDef instance;
	private Vector<RelDef> tabRelDef;
	private int count;
	 
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
	
	public DBDef() {
		this.tabRelDef = new Vector<RelDef>();
		this.count = 0;
	}
	
	public void init() throws FileNotFoundException, IOException, ClassNotFoundException {
		String input = Constants.DB_DIRECTORY + "Catalog.def";
		File file = new File(input);

		if(!file.exists()) {
			return;
		} 
		
       	try {
       		FileInputStream fichier = new FileInputStream(input);
        	ObjectInputStream ois = new ObjectInputStream(fichier);
        	DBDef.instance = (DBDef) ois.readObject();
        	if (ois != null) {
                ois.close();
            }

       	} catch (final java.io.IOException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
          e.printStackTrace();
        }
	}
	
	public void finish() throws FileNotFoundException, IOException {
		String input = Constants.DB_DIRECTORY + "Catalog.def";
		File file = new File(input);
	    
		if(!file.exists()) {
		    file.createNewFile();
		}
		
		try {
			FileOutputStream fichier = new FileOutputStream(input);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(DBDef.instance);
			if (oos != null) {
	          oos.flush();
	          oos.close();
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ajoute un element à la liste vector et incrémente count
	 * @param x
	 */
	public void addRelation(RelDef relation) {
		this.tabRelDef.add(relation);
		this.count++;
	}

	/**
	 * Créé une nouvelle instance remettant ainsi tout à 0
	 */
	public void reset() {
		DBDef.instance = new DBDef();
	}
	
	/**
	 * Fonction permettant de retourner un RelDef en connaissant son nom
	 * @args name a mettre dans DBdef
	 * 
	 */	
	public RelDef getRelDefviaName(String name){
		for (RelDef rd : this.tabRelDef){
			if (rd.getName().equals(name)) return rd;
		}
		return null;
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
	
