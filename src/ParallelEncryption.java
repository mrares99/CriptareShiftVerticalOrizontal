import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ParallelEncryption extends Thread{
    private BufferedImage colorChannel;
    private final Encryption encryption;
    private static List<BufferedImage> outputEncryptedImageList;
    private int[][] shiftValueMatrix;
    private List<Integer> keyList;

    public ParallelEncryption(){
        this.encryption=new Encryption();
        outputEncryptedImageList=new ArrayList<>();
    }

    public void run(){
        try{
            BufferedImage outputEncryptedImage= encryption.doEncryption(colorChannel, shiftValueMatrix,keyList);
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
