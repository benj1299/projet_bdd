package bdd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.lang.*;

public class DBManager {

	private static DBManager instance;
	private DBDef dbdef;
	private BufferManager bm;
	private FileManager fm;

	/**
	 * Singleton de DBManager
	 * @return DBManager
	 */
	public static DBManager getInstance(){
		if (instance == null) {
			synchronized(DBManager.class) {
				if (instance == null) {
					instance = new DBManager() ;
				}
			}
		}
		return instance ;
	}

	public DBManager() {
		this.dbdef = new DBDef();
		this.bm = BufferManager.getInstance();
		this.fm = FileManager.getInstance();
	}
	
	public void init() throws FileNotFoundException, ClassNotFoundException, IOException {
		this.dbdef.init();
		this.fm.init();
	}

	public void finish() throws Exception {
		this.dbdef.finish();
		this.bm.flushAll();
	}

	/**
	 * Parse la chaine des commandes et redirige vers la fonction correspondante
	 * 
	 * @param chaine - Exemple : create NomRelation NbCol TypeColl[1] TypeCol[2] ... TypeCol[NbCol]
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean processCommand(String chaine) throws IOException {
		String[] args = chaine.split(" ");

		switch(args[0]) {
			case "exit" :
				return false;
			case "create":
				String relationName = args[1];
				int nbColumn = Integer.parseInt(args[2]);
				Vector<String> typeColumn = new Vector<String>();
				for(int i = 3; i < args.length; i++) {
					typeColumn.add(args[i]);
				}
				this.createRelation(relationName, nbColumn, typeColumn);
				break;
			default : 
				return true;
		}
		return true;
	}

	/**
	 * Créé une relation avec RelDef et l'ajoute dans DBDef
	 * 
	 * @param name - Nom de la relation
	 * @param nbColumn - Nombre de column
	 * @param typeColumns - Tableau de tous les types de colonnes
	 * 
	 * @return void
	 * @throws IOException 
	 */
	public void createRelation(String name, int nbColumn, Vector<String> typeColumn) throws IOException{
		int recordSize = 0;
		for (int i = 0; i < nbColumn; i++)
			if (typeColumn.get(i).equals("float") || typeColumn.get(i).equals("int")) {
				recordSize += 4;
			}
			else {
				String mots[] = typeColumn.get(i).split("");
				recordSize += Integer.parseInt(mots[6])*2;	
			}
		RelDef relation = new RelDef(name, nbColumn, typeColumn, recordSize, this.dbdef.getCount());		
		this.dbdef.addRelation(relation);
		this.fm.createRelationFile(relation);
	}
	
	/**
	 * Nettoit la base de donnée en reinitialisant le BufferManager, le DBDef, le FileManager, le catalogue
	 */
	public void clean() {		
		this.deleteDirectory(new File(Constants.DB_DIRECTORY));
		this.bm.reset();
		this.dbdef.reset();
		this.fm.reset();
	}
	
	public void insert(String relationName, Vector<Object> values) throws Exception {
		Iterator<RelDef> relation = this.dbdef.getTabRelDef().iterator();
		RelDef currentRelation;
		
		while(relation.hasNext()) {
			currentRelation = relation.next();
			if(currentRelation.getName().equals(relationName)) {
				Record record = new Record(currentRelation);
				record.setValues(values);
				this.fm.inserRecordInRelation(record, relationName);
				System.out.println("Les données ont bien été ajouté à la base de données");
				break;
			}
		}
	}
	
	public void select(String relationName, int idCol, String value) throws Exception {
		Iterator<RelDef> relation = this.dbdef.getTabRelDef().iterator();
		RelDef currentRelation;
		
		while(relation.hasNext()) {
			currentRelation = relation.next();
			if(currentRelation.getName().equals(relationName)) {
				Vector<Record> records = this.fm.selectFromRelation(relationName, idCol, value);
				this.displayRecords(records);
				break;
			}
		}
	}
	
	public void delete(String relationName, int idxCol, Object valeur) throws Exception {
		Iterator<RelDef> relation = this.dbdef.getTabRelDef().iterator();
		RelDef currentRelation;
		
		while(relation.hasNext()) {
			currentRelation = relation.next();
			if(currentRelation.getName().equals(relationName)) {
				Vector<Record> records = this.fm.selectFromRelation(relationName, idxCol, valeur);
				for (Record r : records){
					this.fm.deleteRecord(r);
				}
				System.out.println("Total deleted records = " + records.size());
				break;
			}
		}
	}
	
	public void insertAll(String relationName, String csvFilePath) throws Exception {
		File csvFile = new File(csvFilePath);
		if (csvFile.isFile()) {
			String row;
			Vector<Object> values = new Vector<Object>();
			BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
			
			while ((row = csvReader.readLine()) != null) {
			    String[] datas = row.split(",");
			    for(String data : datas) {
			    	values.add(data);
			    }
			    this.insert(relationName, values);
			    values.clear();
			}
			csvReader.close();
			System.out.println("Les données ont bien été ajouté à la base de données");
		} else {
			System.out.println("Une erreur s'est produite dans l'insertion des données");
		}
	}
	
	public void selectAll(String relationName) throws Exception {
		Iterator<RelDef> relation = this.dbdef.getTabRelDef().iterator();
		RelDef currentRelation;
		
		while(relation.hasNext()) {
			currentRelation = relation.next();
			if(currentRelation.getName().equals(relationName)) {
				Vector<Record> records = this.fm.selectAllFromRelation(relationName);
				System.out.println("Total records = " + records.size());
				this.displayRecords(records);
				break;
			}
		}
	}
	
	/**
	 * Créé un B+Tree résidant uniquement en mémoire
	 * @param relationName
	 * @param idxCol
	 * @param order
	 */
	public void createIndex(String relationName, int idxCol, int order) {
		
	}
	
	/**
	 * 
	 * @param relationName
	 * @param idxCol
	 * @param valeur
	 */
	public void selectIndex(String relationName, int idxCol, Object valeur) {
		
	}
	
	/**
	 * Supprime le contenu d'un dossier
	 * @param directoryToBeDeleted
	 * @return
	 */
	private boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}
	
	/**
	 * Affiche un ensemble de records
	 * @param records
	 */
	private void displayRecords(Vector<Record> records) {
		Iterator<Record> it = records.iterator();
		while(it.hasNext()) {
			System.out.println(it.next().toString() + ";");
		}
	}
}
