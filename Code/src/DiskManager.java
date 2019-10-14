import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskManager {
    /**
     * Créer un fichier data_file_idx.rf dans le DIRECTORY_DB
     * @param fileIdx - Identifiant / indice du fichier
     */
    public void createFile(int fileIdx) {
        try {
            File file = this.getFile(fileIdx);
            if (file.createNewFile()) {
              System.out.println("Fichier cree: " + file.getName());
            } else {
              System.out.println("Le fichier existe deja.");
            }
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
        File file = this.getFile(fileIdx);
        
        if(!file.exists()){
          // Lever une erreur et interrompre
        }
        
        int fileSize = (int) file.length();
        int pageIdx = fileSize / Constants.PAGE_SIZE;
        byte[] space = new byte[Constants.PAGE_SIZE];

		try {
			RandomAccessFile randomFile = new RandomAccessFile(file, "rw");
			randomFile.write(space);
	        randomFile.close();
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
      RandomAccessFile file = new RandomAccessFile(this.getFile(pageId.getFileIdx()), "r");
      int pos = pageId.getPageIdx() * Constants.PAGE_SIZE;
      file.seek(pos);
     
      for(int i = 0; i < Constants.PAGE_SIZE; i++){
        buff[] = file.readByte();
        pos++;
        file.seek(pos);
      }

      file.close();
    }
    
    /**
     * Ecrit le contenu de l'argument buff dans le fichier et à la position indiquée par l'argument pageId
     * @param pageId - Identifiant de la page
     * @param buff - Buffer
     */
    public void writePage(PageId pageId, byte[] buff) {
        RandomAccessFile file = new RandomAccessFile(this.getFile(pageId.getFileIdx()), "r");
        int pos = pageId.getPageIdx() * Constants.PAGE_SIZE;
        file.seek(pos);
       
        for(int i = 0; i < Constants.PAGE_SIZE; i++){
          
        }

        file.close();
      }

    /**
     * @param fileIdx
     * @return le fichier de la page demandé selon son index
     */
    private File getFile(int fileIdx){
      String fileName = "Data_" + fileIdx + ".rf";
      File file = new File(Constants.DB_DIRECTORY + fileName);
      return file;
    }
}
	
