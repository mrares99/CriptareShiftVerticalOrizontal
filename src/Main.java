import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageOperations imageOperations=new ImageOperations();
        Encryption encryption=new Encryption();
        ViewImage viewImage=new ViewImage();
        //Flower;Flower2;Flower3;PinkFlower;Daisy;Lenna;Owl;Roses;Smoke;Umbrellas;testHeight;testWidth;testHeightScurt;testHeightScurtUmbrellas
        BufferedImage inputBufferedImage = imageOperations.readImage(new File("D:/An4/Licenta/TestImages/Lenna.png"));
        int width=inputBufferedImage.getWidth(), height=inputBufferedImage.getHeight();
        viewImage.displayImage(inputBufferedImage,"Original",width,height);
        Files.write(Paths.get("TimpRulare.txt"),("Width imagine="+inputBufferedImage.getWidth()+" Height imagine="+inputBufferedImage.getHeight()+"\n").getBytes(), StandardOpenOption.APPEND);


        //criptare

        long startTime=System.currentTimeMillis();
        long seed=123;
        List<int[][]> randomSequenceMatrixForChannel=encryption.generateRandomSequenceForChannels(seed, inputBufferedImage.getHeight(),inputBufferedImage.getWidth());
        String key="<";
        List<Integer> keyList=encryption.extractBitsFromString(key);

        List<BufferedImage> extractedColorChannelList=imageOperations.extractColorChannels(inputBufferedImage);
//        viewImage.displayImage(extractedColorChannelList.get(0),"red",width,height);
//        viewImage.displayImage(extractedColorChannelList.get(1),"green",width,height);
//        viewImage.displayImage(extractedColorChannelList.get(2),"blue",width,height);

        BufferedImage encryptRed=encryption.doEncryption(extractedColorChannelList.get(0),randomSequenceMatrixForChannel.get(0),keyList);
        BufferedImage encryptGreen=encryption.doEncryption(extractedColorChannelList.get(1),randomSequenceMatrixForChannel.get(1),keyList);
        BufferedImage encryptBlue=encryption.doEncryption(extractedColorChannelList.get(2),randomSequenceMatrixForChannel.get(2),keyList);

//        viewImage.displayImage(encryptRed,"encryptedRed",width,height);
//        viewImage.displayImage(encryptGreen,"encryptedGreen",width,height);
//        viewImage.displayImage(encryptBlue,"encryptedBlue",width,height);


        BufferedImage finalEncryptedImage=imageOperations.constructImageFromRGBChannels(encryptBlue,encryptGreen,encryptRed);
        viewImage.displayImage(finalEncryptedImage,"finalEncrypted",width,height);

        long endTime=System.currentTimeMillis();
        NumberFormat formatter=new DecimalFormat("#0.00000");
        Files.write(Paths.get("TimpRulare.txt"),("Timpul total pentru criptare="+formatter.format((endTime-startTime)/1000d)+" secunde\n").getBytes(), StandardOpenOption.APPEND);

        //terminare criptare


        //decriptare

        Decryption decryption=new Decryption();

         startTime=System.currentTimeMillis();
        randomSequenceMatrixForChannel=decryption.generateRandomSequenceForChannels(seed, finalEncryptedImage.getHeight(),finalEncryptedImage.getWidth());
        keyList=decryption.extractBitsFromString(key);

        extractedColorChannelList=imageOperations.extractColorChannels(finalEncryptedImage);

        BufferedImage decryptRed=decryption.doDecryption(extractedColorChannelList.get(0),randomSequenceMatrixForChannel.get(0),keyList);
        BufferedImage decryptGreen=decryption.doDecryption(extractedColorChannelList.get(1),randomSequenceMatrixForChannel.get(1),keyList);
        BufferedImage decryptBlue=decryption.doDecryption(extractedColorChannelList.get(2),randomSequenceMatrixForChannel.get(2),keyList);

//        viewImage.displayImage(decryptRed,"decryptRed",width,height);
//        viewImage.displayImage(decryptGreen,"decryptGreen",width,height);
//        viewImage.displayImage(decryptBlue,"decryptBlue",width,height);

        BufferedImage finalDecryptedImage=imageOperations.constructImageFromRGBChannels(decryptRed,decryptGreen,decryptBlue);
        viewImage.displayImage(finalDecryptedImage,"finalDecryptedImage",width,height);

         endTime=System.currentTimeMillis();
         formatter=new DecimalFormat("#0.00000");
        Files.write(Paths.get("TimpRulare.txt"),("Timpul total pentru decriptare="+formatter.format((endTime-startTime)/1000d)+" secunde\n\n").getBytes(), StandardOpenOption.APPEND);

        //terminare decriptare


    }

}
