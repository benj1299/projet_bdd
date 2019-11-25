package bdd;
import java.util.ArrayList;

public class FileManager {



	private static FileManager instance;
	private FileManager fileManager;
	private ArrayList<HeapFile> heapFiles;


	/**
	 * Singleton de FileManager
	 * @return FileManager
	 */
	public static FileManager getInstance(){
		if (instance == null) {
			synchronized(FileManager.class) {
				if (instance == null) {
					instance = new FileManager() ;
				}
			}
		}
		return instance ;
	}

	public FileManager() {
		this.fileManager = FileManager.getInstance();
		this.heapFiles = new ArrayList<>();
	}



}
