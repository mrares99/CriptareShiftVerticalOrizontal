import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Encryption {

    public List<int[][]> generateRandomSequenceForChannels(long seed, int rows, int columns){
        int[][] redMatrixRandomSequence = new int[columns][rows];
        int[][] greenMatrixRandomSequence = new int[columns][rows];
        int[][] blueMatrixRandomSequence = new int[columns][rows];
        Random random=new Random();
        random.setSeed(seed);
        int maxFrom=(rows>columns)?rows:columns;
        for(int i=0;i<columns;i++){
            for(int j=0;j<rows;j++){
                redMatrixRandomSequence[i][j]=random.nextInt(3);
                greenMatrixRandomSequence[i][j]=random.nextInt(4);
                blueMatrixRandomSequence[i][j]=random.nextInt(5);
            }
        }
        List<int[][]> list2d=new ArrayList<int[][]>();
        list2d.add(redMatrixRandomSequence);
        list2d.add(greenMatrixRandomSequence);
        list2d.add(blueMatrixRandomSequence);
        return list2d;
    }

    public List<Integer> extractBitsFromString(String inputString){
        List<Integer> outputBitList=new ArrayList<Integer>();
        String bitRepresentation=getBitRepresentationFromString(inputString);
        for(int i=0;i<bitRepresentation.length();i++){
            outputBitList.add(Integer.parseInt(String.valueOf(bitRepresentation.charAt(i))));
        }
        return outputBitList;
    }

    public String getBitRepresentationFromString(String inputString){
        String result="";
        for(int i=0;i<inputString.length();i++){
            result+=String.format("%8s", Integer.toBinaryString(inputString.charAt(i) & 0xFF)).replace(' ', '0');
        }
        return result;
    }


    public BufferedImage doEncryption(BufferedImage bufferedImage, int[][] shiftValueMatrix, List<Integer> key) throws IOException {
        BufferedImage inputBufferedImage=bufferedImage;
        //viewImage.displayImage(inputBufferedImage,"din criptare",inputBufferedImage.getWidth(),inputBufferedImage.getHeight());
        int width=inputBufferedImage.getWidth();
        int height=inputBufferedImage.getHeight();
        int lenghtOfKey=key.size(),counter=0;
        for(int i=0;i<width-1;i++){
            for(int j=0;j<height-1;j++){
                if(key.get(counter)==0){//orizontal
                    for(int randomMatrixVal=0;randomMatrixVal<=shiftValueMatrix[i][j];randomMatrixVal++) {
                        //System.out.println("i="+i+" width="+(width-1));
                        int aux = inputBufferedImage.getRGB(i,width-1);
                        for (int z = width-1; z >= 1; z--) {
                            inputBufferedImage.setRGB(i, z, inputBufferedImage.getRGB(i,z - 1));
                        }
                        inputBufferedImage.setRGB(i, 0, aux);
                    }
                }
                else if(key.get(counter)==1){//vertical
                    for(int randomMatrixVal=0;randomMatrixVal<=shiftValueMatrix[i][j];randomMatrixVal++) {
                        int aux = inputBufferedImage.getRGB(height-1,j);
                        for(int z=height-1;z>=1;z--){
                            inputBufferedImage.setRGB(z,j,inputBufferedImage.getRGB(z-1,j));
                        }
                        inputBufferedImage.setRGB(0,j,aux);
                    }
                }
                counter++;
                if(counter==lenghtOfKey){
                    counter=0;
                }

            }
        }
        return inputBufferedImage;
    }

}
