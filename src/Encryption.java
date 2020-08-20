import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Encryption {

    public List<int[][]> generateRandomSequenceForChannels(long seed, int rows, int columns) throws NoSuchAlgorithmException {
        int[][] redMatrixRandomSequence = new int[rows][columns];
        int[][] greenMatrixRandomSequence = new int[rows][columns];
        int[][] blueMatrixRandomSequence = new int[rows][columns];
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG") ;
        random.setSeed(seed);
        for(int i=-1;++i<rows;){
            for(int j=-1;++j<columns;){
                redMatrixRandomSequence[i][j]=random.nextInt(12);
                greenMatrixRandomSequence[i][j]=random.nextInt(14);
                blueMatrixRandomSequence[i][j]=random.nextInt(15);
            }
        }
        List<int[][]> list2d=new ArrayList<>();
        list2d.add(redMatrixRandomSequence);
        list2d.add(greenMatrixRandomSequence);
        list2d.add(blueMatrixRandomSequence);
        return list2d;
    }

    public List<Integer> extractBitsFromString(String inputString){
        List<Integer> outputBitList=new ArrayList<>();
        String bitRepresentation=getBitRepresentationFromString(inputString);
        for(int i=-1;++i<bitRepresentation.length();){
            outputBitList.add(Integer.parseInt(String.valueOf(bitRepresentation.charAt(i))));
        }
        return outputBitList;
    }

    public String getBitRepresentationFromString(String inputString){
        StringBuilder  result=new StringBuilder();
        for(int i=-1;++i<inputString.length();){
            result.append(String.format("%8s", Integer.toBinaryString(inputString.charAt(i) & 0xFF)).replace(' ', '0'));
        }
        return String.valueOf(result);
    }

    public BufferedImage doEncryption(BufferedImage bufferedImage, int[][] shiftValueMatrix, List<Integer> key) {
        int width=bufferedImage.getWidth();
        int height=bufferedImage.getHeight();
        int lenghtOfKey=key.size(),counter=0;
        for(int i=-1;++i<height-1;){
            for(int j=-1;++j<width-1;){
                if(key.get(counter)==0){
                    for(int randomMatrixVal=-1;++randomMatrixVal<=shiftValueMatrix[i][j];) {
                        int aux = bufferedImage.getRGB(width-1,i);
                        for (int z = width-1; z >= 1; z--) {
                            bufferedImage.setRGB(z,i, bufferedImage.getRGB(z - 1,i));
                        }
                        bufferedImage.setRGB(0, i, aux);
                    }
                }
                counter++;
                if(counter>=lenghtOfKey){
                    counter=0;
                }
            }
        }
        counter=0;
        for(int i=-1;++i<height-1;){
            for(int j=-1;++j<width-1;) {
                if(key.get(counter)==1){
                    for(int randomMatrixVal=-1;++randomMatrixVal<=shiftValueMatrix[i][j];) {
                        int aux = bufferedImage.getRGB(j,height-1);
                        for(int z=height-1;z>=1;z--){
                            bufferedImage.setRGB(j,z,bufferedImage.getRGB(j,z-1));
                        }
                        bufferedImage.setRGB(j,0,aux);
                    }
                }
                counter++;
                if(counter>=lenghtOfKey){
                    counter=0;
                }
            }
        }
        return bufferedImage;
    }
}
