/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FreshPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author subedipiyush
 */
public class Composite {
    
    String[] genes;
    final String path = "C:\\Users\\subedipiyush\\Desktop\\Project\\All\\croppedImage\\";
    
    public Composite(String[] chromo)
    {
        genes=chromo;
    }
    
    public BufferedImage createComposite(int pos)
    {
        BufferedImage finalImage=null;
        try {
            String bg_path = path + "\\background\\"+ genes[0] + ".jpg";
            String eyes_path = path + "\\eyes\\"+ genes[1] + ".jpg";
            String nose_path = path + "\\nose\\" +genes[2] + ".jpg";
            String mouth_path = path + "\\mouth\\" +genes[3] + ".jpg";
            String forehead_path = path + "\\forehead\\" +genes[4] + ".jpg";
            
            BufferedImage bg_image = ImageIO.read(new File(bg_path));
            BufferedImage eyes_image = ImageIO.read(new File(eyes_path));
            BufferedImage nose_image = ImageIO.read(new File(nose_path));
            BufferedImage mouth_image = ImageIO.read(new File(mouth_path));
            BufferedImage forehead_image = ImageIO.read(new File(forehead_path));
            
            int[][] details = new int[4][4];
            details = new DatabaseInterface().getDetailsArray(genes[0]);

            BufferedImage composite = ImageIO.read(new File(bg_path));
            Graphics g = composite.getGraphics();
            
            BufferedImage[] collection = {eyes_image,nose_image,mouth_image,forehead_image};
            for(int i=0;i<4;i++)
            {
                g.drawImage(collection[i],details[i][0]-350,details[i][1]-120,details[i][2],details[i][3],null);
            }
            finalImage = new MedianFilter(7).filter(composite);
            ImageIO.write(finalImage,"jpg",new File("C:\\Users\\subedipiyush\\Desktop\\Project\\All\\composites\\"+pos+".jpg"));
            
        } catch (IOException ex) {
            Logger.getLogger(Composite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Composite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Composite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return finalImage;
    }
    /*
    public static void main(String args[]) 
    {
        new Composite("11 2 5 8 10");
    }*/
}
