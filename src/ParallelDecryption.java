import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ParallelDecryption extends Thread{

    private BufferedImage colorChannel;
    private final Decryption decryption;
    private static List<BufferedImage> outputEncryptedImageList;
    private int[][] shiftValueMatrix;
    private List<Integer> keyList;


    /** Constructor.
     */
    public ParallelDecryption(){
        this.decryption=new Decryption();
        outputEncryptedImageList=new ArrayList<>();
    }

    /** Metoda necesara pentru rularea paralela a decriptarii.
     */
    public void run(){
        try{
            BufferedImage outputEncryptedImage= decryption.doDecryption(colorChannel, shiftValueMatrix,keyList);
            addEncryptedImageInList(outputEncryptedImage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** Returneaza lista cu imaginile criptate.
     * @return Lista cu imagini criptate.
     */
    public  List<BufferedImage> getOutputEncryptedImageList() {
        return outputEncryptedImageList;
    }

    /** Adauga o imagine criptata intr-o lista statica.
     * @param bufferedImage Imaginea originala.
     */
    public synchronized void addEncryptedImageInList(BufferedImage bufferedImage){
        outputEncryptedImageList.add(bufferedImage);
    }

    /** Setter - Seteaza imaginea pentru criptare.
     * @param colorChannel Imaginea pentru criptare.
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

    /**
     * @param keyList
     */
    public void setKeyList(List<Integer> keyList) {
        this.keyList = keyList;
    }
}
