/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FreshPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author subedipiyush
 */
public class DatabaseInterface {
    
    Connection con;
    public DatabaseInterface() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/facecompositiondb","root","");
    }
    public void addData(int id, String path, int x, int y, int w, int h,String section) throws SQLException
    {
        PreparedStatement ps = null;
        switch(section)
        {
            case "eyes":
                ps = con.prepareStatement("insert into eyes(eyes_id,path,x,y,width,height) values(?,?,?,?,?,?)");
                break;
            case "nose":
                ps = con.prepareStatement("insert into nose(nose_id,path,x,y,width,height) values(?,?,?,?,?,?)");
                break;
            case "mouth":
                ps = con.prepareStatement("insert into mouth(mouth_id,path,x,y,width,height) values(?,?,?,?,?,?)");
                break;
            case "forehead":
                ps = con.prepareStatement("insert into forehead(forehead_id,path,x,y,width,height) values(?,?,?,?,?,?)");
                break;
        }
         
        ps.setInt(1,id);
        ps.setString(2,path);
        ps.setInt(3,x);
        ps.setInt(4,y);
        ps.setInt(5,w);
        ps.setInt(6,h);
        ps.addBatch();
        ps.executeBatch();
    }       
    
    public int[] getData(String section,int id) throws SQLException
    {
        Statement s = con.createStatement();
        String query = null;
        switch (section)
        {
            case "eyes":
                query = "select * from eyes where eyes_id = "+id;
                break;
            case "nose":
                query = "select * from nose where nose_id = "+id;
                break;
            case "mouth":
                query = "select * from mouth where mouth_id = "+id;
                break;
            case "forehead":
                query = "select * from forehead where forehead_id = "+id;
                break;
        }
        ResultSet rs = s.executeQuery(query);
        int x = 0,y = 0,w = 0,h = 0;
        while(rs.next()){
            x = rs.getInt("x");
            y = rs.getInt("y");
            w = rs.getInt("width");
            h = rs.getInt("height");
        }
        int[] a = new int[4];
        a[0]=x;
        a[1]=y;
        a[2]=w;
        a[3]=h;
        return a;
    }
  /*  public void addData() throws SQLException
    {
        //Statement s = con.createStatement();
        PreparedStatement ps = con.prepareStatement("insert into mastertable(img_id,path) values(?,?)");
        for(int i=1;i<=20;i++)
        {
            ps.setInt(1, i);
            ps.setString(2,"C:\\Users\\subedipiyush\\Desktop\\Project\\Final face images\\"+i+".jpg\"");
            ps.addBatch();
        }
        ps.executeBatch();
    // s.execute("insert into mastertable(img_id,path) values("+i+",\"C:\\Users\\subedipiyush\\Desktop\\Project\\Final face images\\"+i+".jpg\"");
        System.out.println("All data successfully inserted");
    }*/
    public static void main(String args[]) throws ClassNotFoundException, SQLException
    {
        new DatabaseInterface();
        
        
    }
}
