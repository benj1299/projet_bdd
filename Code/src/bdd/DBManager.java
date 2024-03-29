package bdd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

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
		this.dbdef = DBDef.getInstance();
		this.bm = BufferManager.getInstance();
		this.fm = FileManager.getInstance();
	}
	
	public void init() throws FileNotFoundException, ClassNotFoundException, IOException {
		File directory = new File(Constants.DB_DIRECTORY);
	      if(!directory.exists()) {
	    	  directory.mkdir();
	    }
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
	 * @throws Exception 
	 */
	public boolean processCommand(String chaine) throws Exception {
		String[] args = chaine.split(" ");

		switch(args[0]) {
			case "exit" :
				return false;
				
			case "create":
				int nbColumn = Integer.parseInt(args[2]);
				Vector<String> typeColumn = new Vector<String>();
				for(int i = 3; i < args.length; i++) {
					typeColumn.add(args[i]);
				}
				this.createRelation(args[1], nbColumn, typeColumn);
				break;
				
			case "insert":
				Vector<Object> values = new Vector<Object>();
				for(int i = 2; i < args.length; i++) {
					values.add(args[i]);
				}
				this.insert(args[1], values);
				break;
				
			case "insertall":
				this.insertAll(args[1], args[2]);
				break;
				
			case "select":
				this.select(args[1], Integer.parseInt(args[2]), args[3]);
				break;
			
			case "selectall":
				this.selectAll(args[1]);
				break;
			
			case "delete":
				this.delete(args[1], Integer.parseInt(args[2]), args[3]);
				break;
				
			case "createindex":		
				this.createIndex(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
				
			case "selectindex":
				this.selectIndex(args[1], Integer.parseInt(args[2]), args[3]);
				
			case "clean": 
				this.clean();
				System.out.println("La base de donnée a bien été nettoyée");
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
	 * @throws Exception 
	 */
	public void createRelation(String name, int nbColumn, Vector<String> typeColumn) throws Exception{
		int recordSize = 0;
		String typeColumnValue = null;
		
		for (int i = 0; i < nbColumn; i++)
			typeColumnValue = typeColumn.get(i).toLowerCase();
			if (typeColumnValue.equals("float") || typeColumnValue.equals("int")) {
				recordSize += 4;
			}
			else if(typeColumnValue.substring(0, 5).equals("string")) {
				int tall = Character.getNumericValue(typeColumnValue.charAt(6));
				recordSize += tall*2;	
			}
		
		int slotCount = Constants.PAGE_SIZE/recordSize;
		RelDef relation = new RelDef(name, nbColumn, typeColumn, recordSize, this.dbdef.getCount(), slotCount);
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
	/**
	 * Insert les données envoyées en paramètre dans la base de données
	 * @param relationName
	 * @param values
	 * @throws Exception
	 */
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
	
	/**
	 * Affiche les données de la base selon la colone et la valeur données en paramètre
	 * @param relationName
	 * @param idCol
	 * @param value
	 * @throws Exception
	 */
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
	
	/**
	 * Supprimer les données de la base selon l'index et la valeur données en paramètre
	 * @param relationName
	 * @param idxCol
	 * @param valeur
	 * @throws Exception
	 */
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
	
	/**
	 * Insert un ensemble de données provenant d'un csv dans la base de données
	 * @param relationName
	 * @param csvFilePath
	 * @throws Exception
	 */
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
			System.out.println("Une erreur s'est produite dans l'insertion des données du fichier csv");
		}
	}
	
	/**
	 * Affiche toutes les données d'une relation donnée en paramètre
	 * @param relationName
	 * @throws Exception
	 */
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
	 * @throws Exception 
	 */
	public void createIndex(String relationName, int idCol, int order) throws Exception {
		Vector<Rid> rids = new Vector<Rid>();
		BTree<Integer, Vector<Rid>> btree = new BTree<Integer, Vector<Rid>>();
		
		Vector<Record> records = this.fm.selectAllFromRelation(relationName);
		
		for(Record r : records)
				rids.add(r.getRid());
		
		btree.put(idCol, rids);
		
		// Créé tableau résident en mémoire stockant les pointeurs de records avec la même clé dans une même entrée.
		// FORMAT : <clé, liste de rid>
		// On ne garde pas à jour l'index avec les insertions dans la relation qui apparaissent après createIndex()
		// il peut y avoir plusieurs index dans la DB, pour la même relation mais avec des colonnes différentes
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
	 * Permet de cree une jointure entre 2 relations
	 * @param RN1
	 * @param RN2
	 * @param indicecol1
	 * @param indicecol2	
	 * @throws Exception 
	*/
	public void join (String RN1, String RN2, int indicecol1, int indicecol2) throws Exception{
		Vector<Record> vecteurjoin = new Vector <Record>(); 
		Vector<String> typeColumn = new Vector<String>();
		String relname = RN1+RN2;

		HeapFile hp1 = getHeapFileViaName(RN1);
		HeapFile hp2 = getHeapFileViaName(RN2);
		
		int nbColumn = hp1.getRelDef().getNbColumn()+hp2.getRelDef().getNbColumn();

		byte [] bufferHeader1 = this.bm.getPage(new PageId (hp1.getRelDef().getFileIdx(), 0));
		byte [] bufferHeader2 = this.bm.getPage(new PageId (hp2.getRelDef().getFileIdx(), 0));

		RelDef redjoin = this.dbdef.getRelDefviaName(RN1+RN2);
		
		typeColumn.addAll(hp1.getRelDef().getTypeColumn());
		typeColumn.addAll(hp2.getRelDef().getTypeColumn());
		
		this.createRelation(relname, nbColumn, typeColumn);

		for (int i = 1; i < bufferHeader1[0]; i++)
		{	
			//byte pagern1 = this.bm.getPage(new PageId(hp1.getRelDef().getFileIdx(), i));
			 Vector<Record> recordshp1 = hp1.getRecordsInDataPage(new PageId(hp1.getRelDef().getFileIdx(), i));
			
			for(int j = 0; j < bufferHeader2[0]; j++)
			{	
				Vector<Record> recordshp2 = hp2.getRecordsInDataPage(new PageId(hp2.getRelDef().getFileIdx(), j));	
				for (int ii=0; ii < bufferHeader1[i]; ii++)
				{
					for (int jj=0; jj < bufferHeader1[j]; jj++)
					{
						if(recordshp1.get(ii).getValues().get(indicecol1).equals(recordshp2.get(jj).getValues().get(indicecol2))) {
							Vector<Object> values = new Vector<Object> ();
							values.addAll(recordshp1.get(ii).getValues());
							values.addAll(recordshp2.get(jj).getValues());
							
							Record r = new Record (redjoin);
							
							r.setValues(values);// a finaliser 
							
							vecteurjoin.add(r);
						}
					}
				}
			}		
	     }	
		this.displayRecords(vecteurjoin);
		//Supprimer la relation ephemere
	}
	
	
	/**
	 * Fonction permettant de retourner un HeapFile en connaissant son nom
	 * @args name
	 * @return HeapFile
	 */	
	public HeapFile getHeapFileViaName (String name){
		RelDef rd = dbdef.getRelDefviaName(name);
		for (HeapFile hp : fm.getHeapFiles()){
			if (hp.getRelDef().equals(rd)) return hp;
		}
		return null;	
	}
	
	/**
	 * Fonction récursive supprimant le contenu d'un dossier
	 * @param directoryToBeDeleted
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
