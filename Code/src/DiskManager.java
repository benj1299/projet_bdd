import java.io.File;
import java.io.RandomAccessFile;

public class DiskManager {
    

    public DiskManager(){

    }

    /**
     * Créer un fichier data_file_idx.rf dans le DIRECTORY_DB
     * @param fileIdx - Identifiant / indice du fichier
     */
    public void createFile(int fileIdx) {
        String fileName = "Data_" + fileIdx + ".rf";
        try {
            File file = new File(Constants.DIRECTORY_DB + fileName);
            if (file.createNewFile()) {
              System.out.println("File created: " + file.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }

    /**
     * Rajoute une page au fichier spécifié par fileIdx et renvoie la nouvelle page ajoutée
     * @param fileIdx - Identifiant / indice du fichier
     * @return PageId
     */
    public PageId addPage(int fileIdx) {
        File file = this.getNamePage(fileIdx);
        
        if(!file.exists()){ 
            // Lever une erreur et interrompre
        }
        
        long fileSize = file.length();
        long pageIdx = fileSize / Constants.PAGE_SIZE + 1;
        byte space[Constants.PAGE_SIZE] = new byte[Constants.PAGE_SIZE];

        RandomAccessFile file = new RandomAccessFile(file, "rw");
        file.write(space);
        file.close();

        return new PageId(fileIdx, pageIdx);
    }

    /**
     * Remplie l'argument buff avec le contenu du disque de la page identifiée par l'argument pageId
     * @param pageId - Identifiant de la page
     * @param buff - Buffer
     */
    public void readPage(PageId pageId, byte[] buff) {
      File file = this.getNamePage(pageId.getFileIdx());
      file.seek(pageId.getPageIdx() * Constants.PAGE_SIZE);
      
      for(int i = 0; i < Constants.PAGE_SIZE; i++){
        file.readByte();
      }
    }

    /**
     * Ecrit le contenu de l'argument buff dans le fichier et à la position indiquée par l'argument pageId
     * @param pageId - Identifiant de la page
     * @param buff - Buffer
     */
    public void writePage(pageId pageId, byte []buff) {
      
    }


    private File getNamePage(String fileIdx){
      String fileName = "Data_" + fileIdx + ".rf";
      File file = new File(Constants.DIRECTORY_DB + fileName);
      return file;
    }
}
	
