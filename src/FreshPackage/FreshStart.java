/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FreshPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.scene.control.RadioButton;
import javax.imageio.ImageIO;
import javax.swing.JRadioButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author subedipiyush
 */
public class FreshStart extends javax.swing.JFrame {

    /**
     * Creates new form FreshStart
     */
    BufferedImage image;
    Rectangle rect;
    Boolean clicked = false;
    ArrayList coordsX = new ArrayList(); 
    ArrayList coordsY; 
    String a[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    int index=0;
    String cropSection = new String();
    
    public FreshStart() throws IOException {
        //initComponents();
        setPreferredSize(new Dimension(700,500));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel src_panel = new JPanel();
        JButton cropButton = new JButton("Save");
        JButton nextButton = new JButton("Next");
        
        JRadioButton eyes_radio = new JRadioButton("Eyes");
        JRadioButton nose_radio = new JRadioButton("Nose");
        JRadioButton forehead_radio = new JRadioButton("Forehead");
        JRadioButton mouth_radio = new JRadioButton("Mouth");
        //JRadioButton eyes_radio = new JRadioButton("Eyes");
        
        
        
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(eyes_radio);
        btnGroup.add(nose_radio);
        btnGroup.add(forehead_radio);       
        btnGroup.add(mouth_radio);
        
        coordsX = new ArrayList();
        coordsY = new ArrayList();

        src_panel.setBorder(BorderFactory.createLineBorder(Color.black,3));
        src_panel.setVisible(true);
        src_panel.setLayout(null);
       
        eyes_radio.setLocation(100,100);
        eyes_radio.setSize(60, 30);
        nose_radio.setLocation(100,130);
        nose_radio.setSize(60, 30);
        mouth_radio.setLocation(100,160);
        mouth_radio.setSize(60, 30);
        forehead_radio.setLocation(100,190);
        forehead_radio.setSize(90, 30);
        cropButton.setLocation(350,400);
        cropButton.setSize(new Dimension(60,30));
        nextButton.setLocation(420,400);
        nextButton.setSize(new Dimension(60,30));
        
        rect = new Rectangle(350, 100, 256, 256);
        
        String path = "C:\\Users\\subedipiyush\\Desktop\\Project\\Final face images\\"+a[index]+".jpg";
        image = ImageIO.read(new File(path));
               
        this.add(src_panel);
        src_panel.add(cropButton);
        src_panel.add(nextButton);
        src_panel.add(eyes_radio);
        src_panel.add(nose_radio);
        src_panel.add(mouth_radio);
        src_panel.add(forehead_radio);

        cropButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cropImage(evt);
            }
        });
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextImage();
            }
        });
        addMouseListener(mouser);
        
        eyes_radio.setActionCommand("eyes");
        nose_radio.setActionCommand("nose");
        mouth_radio.setActionCommand("mouth");
        forehead_radio.setActionCommand("forehead");
        
        
        eyes_radio.addActionListener(btnListen);
        nose_radio.addActionListener(btnListen);
        mouth_radio.addActionListener(btnListen);
        forehead_radio.addActionListener(btnListen);
        pack();
    }
   
    public void nextImage()
    {
        index = (index+1)%20;
        String path = "C:\\Users\\subedipiyush\\Desktop\\Project\\Final face images\\"+a[index]+".jpg";
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(FreshStart.class.getName()).log(Level.SEVERE, null, ex);
        }
        coordsX.clear();
        coordsY.clear();
        repaint();
    }
    
    public void cropImage(java.awt.event.ActionEvent evt)
    {
        Graphics g = getGraphics();
        g.setColor(Color.black);
        int width = (int)coordsX.get(1) - (int)coordsX.get(0);
        int height = (int)coordsY.get(3) - (int)coordsY.get(2);
        g.drawRect((int)coordsX.get(0),(int)coordsY.get(2),width, height);
       // BufferedImage croppedImage = image.getSubimage((int)coordsX.get(0), (int)coordsY.get(2), width, height);
        try {
            String croppedImagePath = "C:\\Users\\subedipiyush\\Desktop\\Project\\Final Face Images\\"+cropSection+"\\"+a[index]+".jpg";
            Robot robot = new Robot();
            BufferedImage croppedImage;
            croppedImage = robot.createScreenCapture(new Rectangle((int)coordsX.get(0)+3,(int)coordsY.get(2)+3,width-6,height-6));   // 6 because of the radius of the circular dots
            ImageIO.write(croppedImage,"png",new File(croppedImagePath));
            addToDatabase(a[index],croppedImagePath,(int)coordsX.get(0),(int)coordsY.get(0),width,height);
            System.out.println("File Cropped Succesfully");            
        } catch (Exception ex) {
            Logger.getLogger(FreshStart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.drawImage(image, rect.x, rect.y, rect.width,rect.height, this);
        g2D.draw(rect);
        
    }
    
    private MouseListener mouser = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            
            if(rect.contains(e.getPoint())){
                coordsX.add((int)e.getX());
                coordsY.add((int)e.getY());
                Graphics g = getGraphics();
                g.setColor(Color.red);
                g.fillOval(e.getX()-3,e.getY()-3,6, 6);
                
               // repaint();
            }
            else
                clicked = false;
            
        }
    };
    
    private ActionListener btnListen = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            cropSection = e.getActionCommand();
            coordsX.clear();
            coordsY.clear();
            repaint();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    public void addToDatabase(String id,String path,int x, int y, int w, int h) throws ClassNotFoundException, SQLException
    {
        new DatabaseInterface().addData(Integer.parseInt(id), path, x, y, w, h,cropSection);
      //  new DatabaseInterface().getData("eyes", 1);
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
            java.util.logging.Logger.getLogger(FreshStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FreshStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FreshStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FreshStart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FreshStart().setVisible(true);
                    
                } catch (IOException ex) {
                    Logger.getLogger(FreshStart.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

