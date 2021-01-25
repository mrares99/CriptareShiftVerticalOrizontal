import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageOperations {

    /** Citeste o imagine.
     * @return Imaginea citita.
     */
    public BufferedImage readImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setAcceptAllFileFilterUsed(false);
        File path;
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path=chooser.getSelectedFile();
        } else {
            path= new File("Wrong path");
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(path);
        } catch (IOException e) {
            System.out.println("Calea spre imagine si/sau numele imaginii este gresit!");
        }
        return image;
    }

    /** Extrage canalele de culoare din imagine.
     * @param bufferedImage Imaginea originala.
     * @return Lista continand cele trei canale de culoare.
     */
    public List<BufferedImage> extractColorChannels(BufferedImage bufferedImage) {
        List<BufferedImage> bufferedImageList=new ArrayList<>();
        int width=bufferedImage.getWidth(),height=bufferedImage.getHeight();
        BufferedImage redChannel=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        BufferedImage greenChannel=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        BufferedImage blueChannel=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        for(int i=-1;++i<height;){
            for(int j=-1;++j<width;){
                redChannel.setRGB(j,i,(bufferedImage.getRGB(j,i) & 0x00ff0000) ); //red
                greenChannel.setRGB(j,i,bufferedImage.getRGB(j,i) & 0x0000ff00); //green
                blueChannel.setRGB(j,i,bufferedImage.getRGB(j,i) & 0x000000ff); //blue
            }
        }
        bufferedImageList.add(redChannel);
        bufferedImageList.add(greenChannel);
        bufferedImageList.add(blueChannel);
        return bufferedImageList;
    }

    /** Construieste o imagine din cele trei canale de culoare.
     * @param firstImage Prima imagine.
     * @param secondImage A doua imagine.
     * @param thirdImage A treia imagine.
     * @return Imagine, reprezentand combinatia celor trei culori.
     */
    public BufferedImage constructImageFromRGBChannels(BufferedImage firstImage, BufferedImage secondImage, BufferedImage thirdImage){
        int width=firstImage.getWidth(),height=firstImage.getHeight();
        BufferedImage outputImage=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
        for(int i=-1;++i<height;){
            for(int j=-1;++j<width;){
                outputImage.setRGB(j,i,firstImage.getRGB(j,i) | secondImage.getRGB(j,i) | thirdImage.getRGB(j,i));
            }
        }
        return outputImage;
    }

}