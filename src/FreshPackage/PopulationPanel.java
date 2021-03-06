/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FreshPackage;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author subedipiyush
 */
public class PopulationPanel extends javax.swing.JPanel {

    /**
     * Creates new form PopulationPanel
     */
    JButton[][] img_but;
    JButton but;
    private final int row_length = 4;
    private final int col_length = 4;
    //ScratchGA generation;
    public PopulationPanel(Main parent, ScratchGA generation){
        //initComponents();
        img_but = new JButton[row_length][col_length];
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        setLayout(new GridLayout(4,4,3,3));
        int index=0;
       // generation= new ScratchGA();
        for(int i=0;i<row_length;i++)
        {
            for(int j=0;j<col_length;j++)
            {
                String[] chromo = generation.getChromo(index);
                /*for(int x=0;x<chromo.length;x++)
                    System.out.print(chromo[x]+" ");*/
               // System.out.println();
                BufferedImage img = new Composite(chromo).createComposite(i*4+j+1);
                //BufferedImage img = ImageIO.read(new File("C:\\Users\\subedipiyush\\Desktop\\Project\\Final face images\\"+index+".jpg"));
                Image scaledImg = img.getScaledInstance(150,150,Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImg);
                but = new JButton(icon);
                //System.out.println(but.getPreferredSize().width);
                but.addActionListener(parent);
                add(but);
                //System.out.println(but.getPreferredSize());
                img_but[i][j] = but;
                index++;
            }
        }
        
        //setSize(200,200);
        setVisible(true);
        
    }
    
    public void reGenerate(Main parent,ScratchGA generation,int img_no,double[] ratings)
    {
        generation.generate(img_no, ratings);
        rePaintPanel(generation,parent);
    }
  
    public void rePaintPanel(ScratchGA generation,Main parent)
    {
        int index=0;
        for(int i=0;i<row_length;i++)
        {
            for(int j=0;j<col_length;j++)
            {
                this.remove(index);
                String[] chromo = generation.getChromo(index);
                BufferedImage img = new Composite(chromo).createComposite(i*4+j+1);
      
                Image scaledImg = img.getScaledInstance(150,150,Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImg);
                but = new JButton(icon);
      
                but.addActionListener(parent);
                this.add(but, index);
                //System.out.println(but.getPreferredSize());
                img_but[i][j] = but;
                index++;
                //this.repaint();
            }
        }
     
    }
    public int getImageNumber(JButton tmp)
    {
        int pos=1;
        for(int i=0;i<row_length;i++)
        {
            for(int j=0;j<col_length;j++)
            {
                if(tmp == img_but[i][j]){
                    pos = i * 4 + j + 1;
                }
                    
            }
        }
        return pos;
        //System.out.println(pos);
        //Main.bestSelected(pos);        
    }
   
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
