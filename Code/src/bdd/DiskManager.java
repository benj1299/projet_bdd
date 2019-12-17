package bdd;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

public class DiskManager {
	
	private static DiskManager instance ;
	private static HashMap<Integer, Integer> nbPage = new HashMap<Integer, Integer>();
		 
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
			nbPage.put(fileIdx, 0);
			
          } catch (IOException e) {
            System.out.println("Une erreur est apparue pour la création d'un fichier.");
            e.printStackTrace();
          }
    }

    /**
     * Rajoute une page au fichier spécifié par fileIdx et renvoie la nouvelle page ajoutée
     * @param fileIdx - Identifiant / indice du fichier
     * @return PageId
     * @throws Exception 
     */
    public PageId addPage(int fileIdx) throws Exception {
        File file = getFile(fileIdx);
		PageId p = null;
        
        if(!file.exists() || !nbPage.containsKey(fileIdx)){
        	throw new NullPointerException("Le fichier " + fileIdx + "n'existe pas");
        }
        
		try {
			byte[] buff = new byte[Constants.PAGE_SIZE];
			int finalPageCount = nbPage.get(fileIdx) + 1;
        	p = new PageId(fileIdx, finalPageCount);
			this.writePage(p, buff);
			nbPage.put(fileIdx, finalPageCount);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Une erreur est survenue lors de l'ajout de la page");
		}

        return p;
    }

    /**
     * Remplie l'argument buff avec le contenu du disque de la page identifiée par l'argument pageId
     * @param pageId - Identifiant de la page
     * @param buff - Buffer
     */
    public void readPage(PageId pageId, byte[] buff) {
      try {
    	  RandomAccessFile file = new RandomAccessFile(this.getFile(pageId.getFileIdx()), "r");
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
    	new Exception("Impossible de lire la page, une erreur s'est produite.");
      }	    	
    }
    
    /**
     * Ecrit le contenu de l'argument buff dans le fichier et à la position indiquée par l'argument pageId
     * @param pageId - Identifiant de la page
     * @param buff - Buffer
     * @throws IOException 
     */
    public void writePage(PageId pageId, byte[] buff) throws Exception {
    	if(buff.length > Constants.PAGE_SIZE) {
    		throw new Exception("Longueur du buffer superieur a la longueur maximum autorise");
    	}
    	try {
            RandomAccessFile file = new RandomAccessFile(this.getFile(pageId.getFileIdx()), "rw");  
    		int pos = Constants.PAGE_SIZE * pageId.getPageIdx() + Constants.PAGE_SIZE;
    		
    		if (pageId.getPageIdx() == 0) {
    			file.write(buff, 0, Constants.PAGE_SIZE);
    			} else {
				file.seek(pos);
				file.write(buff);
			}
    		
            file.close();  
    	} 
    	catch(IOException e) {
    		throw new IOException("Probleme d'ecriture fichier");
    	}
      }

    /**
     * @param fileIdx
     * @return le fichier de la page demandé selon son index
     */
    private File getFile(int fileIdx){
      File directory = new File(Constants.DB_DIRECTORY);
      if(!directory.exists()) {
    	  directory.mkdir();
      }
      String fileName = "Data_" + fileIdx + ".rf";
      File file = new File(Constants.DB_DIRECTORY + fileName);
      return file;
    }
}
	
