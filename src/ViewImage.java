import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ViewImage {

    /** Afiseaza o imagine.
     * @param img Imaginea pentru afisare.
     * @param title Titlul ferestrei care contine imaginea.
     * @param width Latimea imaginii.
     * @param height Inaltimea imaginii.
     */
    public void displayImage(BufferedImage img, String title, int width, int height)
    {
        ImageIcon icon=new ImageIcon(img);
        JFrame frame=new JFrame(title);
        frame.setLayout(new FlowLayout());
        frame.setSize(width+50,height+50);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}