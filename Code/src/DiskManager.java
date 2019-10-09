import java.io.File;
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
            file.close();
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
          file.close();
          // Lever une erreur et interrompre
        }
        
        long fileSize = file.length();
        long pageIdx = fileSize / Constants.PAGE_SIZE;
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
      File file = this.getFile(pageId.getFileIdx());
      int pos = pageId.getPageIdx() * Constants.PAGE_SIZE;
      file.seek(pos);
      
      if(!file.canRead()){
        file.close();
        // Lever une erreur
      }

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
    public void writePage(pageId pageId, byte[] buff) {
      File file = this.getFile(pageId.getFileIdx());
      int pos = pageId.getPageIdx() * Constants.PAGE_SIZE;
      file.seek(pos);

      if(!file.canWrite()){
        file.close();
        // Lever une erreur
      }

      for(int i = 0; i < Constants.PAGE_SIZE; i++){
        file.write(buff[i]);
        pos++;
        file.seek(pos);
      }
      file.close();
    }


    /**
     * @param fileIdx
     * @return le fichier de la page demandé selon son index
     */
    private File getFile(String fileIdx){
      String fileName = "Data_" + fileIdx + ".rf";
      File file = new File(Constants.DIRECTORY_DB + fileName);
      return file;
    }
}
	
