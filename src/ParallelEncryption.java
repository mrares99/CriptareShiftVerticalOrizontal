import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ParallelEncryption extends Thread{
    private BufferedImage colorChannel;
    private final Encryption encryption;
    private static List<BufferedImage> outputEncryptedImageList;
    private int[][] shiftValueMatrix;
    private List<Integer> keyList;

    /** Constructor.
     */
    public ParallelEncryption(){
        this.encryption=new Encryption();
        outputEncryptedImageList=new ArrayList<>();
    }

    /** Metoda necesara pentru rularea paralela a criptarii.
     */
    public void run(){
        try{
            BufferedImage outputEncryptedImage= encryption.doEncryption(colorChannel, shiftValueMatrix,keyList);
            addEncryptedImageInList(outputEncryptedImage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Returneaza lista cu imagini criptate.
     * @return Lista de imagini criptate.
     */
    public  List<BufferedImage> getOutputEncryptedImageList() {
        return outputEncryptedImageList;
    }

    /** Adauga in mod sincron imagini criptate intr-o lista statica.
     * @param bufferedImage Imaginea pentru adaugat in lista.
     */
    public synchronized void addEncryptedImageInList(BufferedImage bufferedImage){
        outputEncryptedImageList.add(bufferedImage);
    }

    /** Setter - Seteaza imaginea pentru criptare.
     * @param colorChannel Imaginea pentru criptat.
     */
    public void setColorChannel(BufferedImage colorChannel) {
        this.colorChannel = colorChannel;
    }

    /** Setter - Seteaza matricea de valori aleatoare.
     * @param shiftValueMatrix Matrice de valori aleatoare.
     */
    public void setShiftValueMatrix(int[][] shiftValueMatrix) {
        this.shiftValueMatrix = shiftValueMatrix;
    }

    /** Setter - Seteaza lista de valori de '0' si '1'.
     * @param keyList Lista de biti.
     */
    public void setKeyList(List<Integer> keyList) {
        this.keyList = keyList;
    }
}
