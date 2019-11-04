import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskManager {
	
	private static DiskManager instance ;
		 
	public static DiskManager getInstance() {
      if (instance == null) {
         synchronized(DiskManager.class) {
            if (instance == null) {
               instance = new DiskManager() ;
            }
         }
      }
      return instance ;
   }
    /**
     * Créer un fichier data_file_idx.rf dans le DIRECTORY_DB
     * @param fileIdx - Identifiant / indice du fichier
     * @return 
     */
    public void createFile(int fileIdx) {
        try {
            File file = getFile(fileIdx);
            file.createNewFile();
          } catch (IOException e) {
            System.out.println("Une erreur est apparue.");
            e.printStackTrace();
          }
    }

    /**
     * Rajoute une page au fichier spécifié par fileIdx et renvoie la nouvelle page ajoutée
     * @param fileIdx - Identifiant / indice du fichier
     * @return PageId
     */
    public PageId addPage(int fileIdx) {
        File file = getFile(fileIdx);
        if(!file.exists()){
        	throw new NullPointerException();
        }
        
        int fileSize = (int) file.length();
        int pageIdx = fileSize / Constants.PAGE_SIZE;

		try {
			RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
			// Créer les metadonnées correspondantes
			// Ajouter au ficher la page avec les metadonnées
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        return new PageId(fileIdx, pageIdx);
    }

    /**
     * Remplie l'argument buff avec le contenu du disque de la page identifiée par l'argument pageId
     * @param pageId - Identifiant de la page
     * @param buff - Buffer
     */
    public void readPage(PageId pageId, byte[] buff) {
      try {
    	  RandomAccessFile file = new RandomAccessFile(getFile(pageId.getFileIdx()), "r");
	      long pos = pageId.getPageIdx() * Constants.PAGE_SIZE;
	      file.seek(pos);
	       
	      for(int i = 0; i < Constants.PAGE_SIZE; i++){
	      	buff[i] = file.readByte();
	      	pos++;
	      	file.seek(pos);
	      }
	
	      file.close();
      } 
      catch(IOException e) {
  		// Gérer l'exception
      }	
    	
    }
    
    /**
     * Ecrit le contenu de l'argument buff dans le fichier et à la position indiquée par l'argument pageId
     * @param pageId - Identifiant de la page
     * @param buff - Buffer
     */
    public void writePage(PageId pageId, byte[] buff) {
    	if(buff.length > Constants.PAGE_SIZE) {
    		// Lever une Exception
    	}
    	try {
    		int pos = pageId.getPageIdx() * Constants.PAGE_SIZE;
            RandomAccessFile file = new RandomAccessFile(getFile(pageId.getFileIdx()), "rw");  
            file.seek(pos);
            file.write(buff);  
            file.close();  
    	} 
    	catch(IOException e) {
      		// Gérer l'exception
    	}
    		
      }

    /**
     * @param fileIdx
     * @return le fichier de la page demandé selon son index
     */
    private static File getFile(int fileIdx){
      String fileName = "Data_" + fileIdx + ".rf";
      File file = new File(Constants.DB_DIRECTORY + fileName);
      return file;
    }
}
	
