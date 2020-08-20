import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ParallelDecryption extends Thread{

    private BufferedImage colorChannel;
    private final Decryption decryption;
    private static List<BufferedImage> outputEncryptedImageList;
    private int[][] shiftValueMatrix;
    private List<Integer> keyList;

    public ParallelDecryption(){
        this.decryption=new Decryption();
        outputEncryptedImageList=new ArrayList<>();
    }

    public void run(){
        try{
            BufferedImage outputEncryptedImage= decryption.doDecryption(colorChannel, shiftValueMatrix,keyList);
            addEncryptedImageInList(outputEncryptedImage);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public  List<BufferedImage> getOutputEncryptedImageList() {
        return outputEncryptedImageList;
    }

    public synchronized void addEncryptedImageInList(BufferedImage bufferedImage){
        outputEncryptedImageList.add(bufferedImage);
    }

    public void setColorChannel(BufferedImage colorChannel) {
        this.colorChannel = colorChannel;
    }

    public void setShiftValueMatrix(int[][] shiftValueMatrix) {
        this.shiftValueMatrix = shiftValueMatrix;
    }

    public void setKeyList(List<Integer> keyList) {
        this.keyList = keyList;
    }
}
