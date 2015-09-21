/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FreshPackage;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author subedipiyush
 */
public class FaceComposite extends javax.swing.JFrame {

    /**
     * Creates new form FaceComposite
     */
    String croppedSection[] = {"eyes","nose","mouth","forehead"};
    String a[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    Rectangle rect;
    BufferedImage image,eyes_section = null,mouth_section=null;
    int index = 0;
    
    public FaceComposite() {
        //initComponents();
        setPreferredSize(new Dimension(700,700));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel src_panel = new JPanel();
        //JButton cropButton = new JButton("Save");
        rect = new Rectangle(250, 100, 256, 256);
        String path = "C:\\Users\\subedipiyush\\Desktop\\Project\\Final face images\\1.jpg";
        try {
            image = ImageIO.read(new File(path));
           
        } catch (IOException ex) {
            Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.add(src_panel);
        //cropButton.setSize(new Dimension(60,30));
        //src_panel.add(cropButton);
      
        composeFace();
         /*cropButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                 try {
            Robot robot = new Robot();
            BufferedImage croppedImage;
            croppedImage = robot.createScreenCapture(new Rectangle(rect.x,rect.y,rect.width,rect.height));   // 6 because of the radius of the circular dots
            ImageIO.write(croppedImage,"jpg",new File("C:\\Users\\subedipiyush\\Desktop\\xyz.jpg"));
        }catch(Exception e)
        {
            
        }
            }
        });*/
        pack();
    }
    
    private void composeFace()
    {
           //BufferedImage section;
            Graphics g = getGraphics();
            Graphics2D g2D = (Graphics2D)g;
           // for(int i = 0; i < 4 ; i++)
            //{
                String eyes_path = "C:\\Users\\subedipiyush\\Desktop\\Project\\Final Face Images\\eyes\\3.jpg";
                String mouth_path = "C:\\Users\\subedipiyush\\Desktop\\Project\\Final Face Images\\mouth\\2.jpg";
                try {
                   eyes_section = ImageIO.read(new File(eyes_path));
                   mouth_section = ImageIO.read(new File(mouth_path));
                   repaint();
                   
                  } catch (IOException ex) {
                     Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
           // }
            
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(image, rect.x, rect.y, rect.width,rect.height, this);
        g2D.draw(rect);
        int[] a = new int[4];
        if(eyes_section!=null)
        {    
            try {
                a = new DatabaseInterface().getData("eyes", 1);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2D.drawImage(eyes_section, a[0]-100,a[1]-a[3]/2-1,a[2],a[3],this);
            Robot robot;
            try {
                robot = new Robot();
                BufferedImage croppedImage = robot.createScreenCapture(new Rectangle(rect.x,rect.y,rect.width,rect.height));
                BufferedImage dest_Image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
                dest_Image = new MedianFilter(3).filter(croppedImage);
                ImageIO.write(croppedImage,"png",new File("C:\\Users\\subediPiyush\\Desktop\\mix.jpg"));
                g2D.drawImage(dest_Image, rect.x, rect.y, rect.width,rect.height, this);
            } catch (AWTException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            }
                       
        }
        if(mouth_section!=null)
        {
            try {
                a = new DatabaseInterface().getData("mouth", 1);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2D.drawImage(mouth_section, a[0]-100,a[1]-a[3]/2-1,a[2],a[3],this);
            Robot robot;
            try {
                robot = new Robot();
                BufferedImage croppedImage = robot.createScreenCapture(new Rectangle(rect.x,rect.y,rect.width,rect.height));
                BufferedImage dest_Image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
                dest_Image = new MedianFilter(3).filter(croppedImage);
                ImageIO.write(croppedImage,"png",new File("C:\\Users\\subediPiyush\\Desktop\\mix.jpg"));
                g2D.drawImage(dest_Image, rect.x, rect.y, rect.width,rect.height, this);
            } catch (AWTException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FaceComposite.class.getName()).log(Level.SEVERE, null, ex);
            }
                           
        }
        //ImageIO.write(g2D, "jpg",new File("C:\\Users\\subediPiyush\\Desktop\\xyz.jpg"));
    }


    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FaceComposite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FaceComposite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FaceComposite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FaceComposite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceComposite().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
