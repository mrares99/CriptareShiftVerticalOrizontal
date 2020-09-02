import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException {
        ImageOperations imageOperations=new ImageOperations();
        Encryption encryption=new Encryption();
        ViewImage viewImage=new ViewImage();
        BufferedImage inputBufferedImage = imageOperations.readImage();
        int width=inputBufferedImage.getWidth(), height=inputBufferedImage.getHeight();
        viewImage.displayImage(inputBufferedImage,"Original",width,height);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Files.write(Paths.get("TimpRulare.txt"),("Date="+dateFormat.format(date)+"\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(Paths.get("TimpRulare.txt"),("Width imagine="+inputBufferedImage.getWidth()+" Height imagine="+inputBufferedImage.getHeight()+"\n").getBytes(), StandardOpenOption.APPEND);

        //criptare

        long startTime=System.currentTimeMillis();
        long seed=13251235;
        String key="asdgdfgsadgs";
        List<int[][]> randomSequenceMatrixForChannel=encryption.generateRandomSequenceForChannels(seed, inputBufferedImage.getHeight(),inputBufferedImage.getWidth());
        List<BufferedImage> extractedColorChannelList=imageOperations.extractColorChannels(inputBufferedImage);
        List<Integer> keyList=encryption.extractBitsFromString(key);

        ExecutorService executorService= Executors.newFixedThreadPool(randomSequenceMatrixForChannel.size());
        ParallelEncryption parallelEncryption=new ParallelEncryption();
        for(int i=0;i<randomSequenceMatrixForChannel.size();i++){
            parallelEncryption=new ParallelEncryption();
            parallelEncryption.setColorChannel(extractedColorChannelList.get(i));
            parallelEncryption.setShiftValueMatrix(randomSequenceMatrixForChannel.get(i));
            parallelEncryption.setKeyList(keyList);
            executorService.execute(parallelEncryption);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        List<BufferedImage> outputEncryptedImageList=parallelEncryption.getOutputEncryptedImageList();
        BufferedImage finalEncryptedImage=imageOperations.constructImageFromRGBChannels(outputEncryptedImageList.get(0),outputEncryptedImageList.get(1),outputEncryptedImageList.get(2));
        viewImage.displayImage(finalEncryptedImage,"finalEncrypted",width,height);

        long endTime=System.currentTimeMillis();
        NumberFormat formatter=new DecimalFormat("#0.00000");
        Files.write(Paths.get("TimpRulare.txt"),("Timpul total pentru criptare paralela="+formatter.format((endTime-startTime)/1000d)+" secunde\n").getBytes(), StandardOpenOption.APPEND);

        //terminare criptare




        //decriptare

        Decryption decryption=new Decryption();

        startTime=System.currentTimeMillis();
        randomSequenceMatrixForChannel=decryption.generateRandomSequenceForChannels(seed, finalEncryptedImage.getHeight(),finalEncryptedImage.getWidth());
        keyList=decryption.extractBitsFromString(key);
        extractedColorChannelList=imageOperations.extractColorChannels(finalEncryptedImage);
        executorService= Executors.newFixedThreadPool(randomSequenceMatrixForChannel.size());
        ParallelDecryption parallelDecryption=new ParallelDecryption();
        for(int i=0;i<randomSequenceMatrixForChannel.size();i++){
            parallelDecryption=new ParallelDecryption();
            parallelDecryption.setColorChannel(extractedColorChannelList.get(i));
            parallelDecryption.setShiftValueMatrix(randomSequenceMatrixForChannel.get(i));
            parallelDecryption.setKeyList(keyList);
            executorService.execute(parallelDecryption);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        outputEncryptedImageList=parallelDecryption.getOutputEncryptedImageList();

        BufferedImage finalDecryptedImage=imageOperations.constructImageFromRGBChannels(outputEncryptedImageList.get(0),outputEncryptedImageList.get(1),outputEncryptedImageList.get(2));
        viewImage.displayImage(finalDecryptedImage,"finalDecryptedImage",width,height);

        endTime=System.currentTimeMillis();
        formatter=new DecimalFormat("#0.00000");
        Files.write(Paths.get("TimpRulare.txt"),("Timpul total pentru decriptare paralela="+formatter.format((endTime-startTime)/1000d)+" secunde\n\n").getBytes(), StandardOpenOption.APPEND);

        //terminare decriptare

















//        //implementare secventiala
//
//
//        ImageOperations imageOperations=new ImageOperations();
//        Encryption encryption=new Encryption();
//        ViewImage viewImage=new ViewImage();
//        BufferedImage inputBufferedImage = imageOperations.readImage();
//        int width=inputBufferedImage.getWidth(), height=inputBufferedImage.getHeight();
//        viewImage.displayImage(inputBufferedImage,"Original",width,height);
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        Files.write(Paths.get("TimpRulare.txt"),("Date="+dateFormat.format(date)+"\n").getBytes(), StandardOpenOption.APPEND);
//        Files.write(Paths.get("TimpRulare.txt"),("Width imagine="+inputBufferedImage.getWidth()+" Height imagine="+inputBufferedImage.getHeight()+"\n").getBytes(), StandardOpenOption.APPEND);
//
//        //criptare
//
//        long startTime=System.currentTimeMillis();
//        long seed=13251235;
//        String key="asdgdfgsadgs";
//        List<int[][]> randomSequenceMatrixForChannel=encryption.generateRandomSequenceForChannels(seed, inputBufferedImage.getHeight(),inputBufferedImage.getWidth());
//        List<BufferedImage> extractedColorChannelList=imageOperations.extractColorChannels(inputBufferedImage);
//        List<Integer> keyList=encryption.extractBitsFromString(key);
//
//
//        BufferedImage encryptedRed=encryption.doEncryption(extractedColorChannelList.get(0), randomSequenceMatrixForChannel.get(0),keyList);
//        BufferedImage encryptedGreen=encryption.doEncryption(extractedColorChannelList.get(1), randomSequenceMatrixForChannel.get(1),keyList);
//        BufferedImage encryptedBlue=encryption.doEncryption(extractedColorChannelList.get(2), randomSequenceMatrixForChannel.get(2),keyList);
//
//        BufferedImage finalEncryptedImage=imageOperations.constructImageFromRGBChannels(encryptedRed,encryptedGreen,encryptedBlue);
//        viewImage.displayImage(finalEncryptedImage,"finalEncrypted",width,height);
//
//        long endTime=System.currentTimeMillis();
//        NumberFormat formatter=new DecimalFormat("#0.00000");
//        Files.write(Paths.get("TimpRulare.txt"),("Timpul total pentru criptare secventiala="+formatter.format((endTime-startTime)/1000d)+" secunde\n").getBytes(), StandardOpenOption.APPEND);
//
//        //terminare criptare
//
//
//
//
//        //decriptare
//
//        Decryption decryption=new Decryption();
//
//        startTime=System.currentTimeMillis();
//        randomSequenceMatrixForChannel=decryption.generateRandomSequenceForChannels(seed, finalEncryptedImage.getHeight(),finalEncryptedImage.getWidth());
//        keyList=decryption.extractBitsFromString(key);
//        extractedColorChannelList=imageOperations.extractColorChannels(finalEncryptedImage);
//
//        BufferedImage decryptedRed=decryption.doDecryption(extractedColorChannelList.get(0),randomSequenceMatrixForChannel.get(0),keyList);
//        BufferedImage decryptedGreen=decryption.doDecryption(extractedColorChannelList.get(1),randomSequenceMatrixForChannel.get(1),keyList);
//        BufferedImage decryptedBlue=decryption.doDecryption(extractedColorChannelList.get(2),randomSequenceMatrixForChannel.get(2),keyList);
//
//        BufferedImage finalDecryptedImage=imageOperations.constructImageFromRGBChannels(decryptedRed,decryptedGreen,decryptedBlue);
//        viewImage.displayImage(finalDecryptedImage,"finalDecryptedImage",width,height);
//
//        endTime=System.currentTimeMillis();
//        formatter=new DecimalFormat("#0.00000");
//        Files.write(Paths.get("TimpRulare.txt"),("Timpul total pentru decriptare secventiala="+formatter.format((endTime-startTime)/1000d)+" secunde\n\n").getBytes(), StandardOpenOption.APPEND);
//
//        //terminare decriptare



    }

}
