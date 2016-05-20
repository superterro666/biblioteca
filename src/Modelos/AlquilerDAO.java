/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import static Utils.Utils.generateHash;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;

public class AlquilerDAO {

    private Connection con;
    private Alquiler user;
    private ArrayList<Alquiler> usuarioArray = new ArrayList<Alquiler>();

    public AlquilerDAO() {

        Conexion c = new Conexion();
        this.con = c.conectar();

    }

    public ArrayList<Alquiler> resultDatos(String valor) {

        String query = "SELECT aquiler.* , libros.* FROM aquiler "
                + "INNER JOIN libros ON libros.isbn=aquiler.id_libro WHERE aquiler.id_user='" + valor + "'";

        ResultSet rs = null;
        user = new Alquiler();
        ArrayList<Alquiler> arraylist = new ArrayList<Alquiler>();
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {

                user.setTitulo(rs.getString("titulo"));
                user.setIsbn(rs.getString("isbn"));
                user.setFecha_alta(rs.getString("fecha_alta"));
                user.setFecha_baja(rs.getString("fecha_baja"));

                arraylist.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return arraylist;

    }
    
    
    public ArrayList<Alquiler> resultLibros(){
    
        String query = "SELECT libros.* FROM libros WHERE estado='" + "N" + "'";
        
        ResultSet rs = null;
        user = new Alquiler();
        ArrayList<Alquiler> arraylist = new ArrayList<Alquiler>();
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {

                user.setTitulo(rs.getString("titulo"));
                user.setIsbn(rs.getString("isbn"));
                user.setAutor(rs.getString("autor"));
                user.setEditorial(rs.getString("editorial"));
                

                arraylist.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i <arraylist.size(); i++) {
            
            System.out.println(arraylist.get(i).getAutor());
        }
        
        
        return arraylist;
        
        
    }
    
    
    public void updateEstado(String salt, String name) {

        String query = "INSERT aquiler (id_user,fecha_alta, fecha_baja,id_libro) VALUES (?,?,?,?)";

        java.util.Date fechaActual = new java.util.Date();
        java.sql.Date fechaSql = new java.sql.Date(fechaActual.getTime());
        
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fechaActual.getTime());
        cal.add(Calendar.DATE, 15);
        
        java.util.Date fechaFin = cal.getTime();
        java.sql.Date Fin = new java.sql.Date(fechaFin.getTime());
        
        
       
     
        
      
        Date fecha = cal.getTime();
        PreparedStatement preparedStmt = null;
       

        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, salt);
            preparedStmt.setDate(2, fechaSql);
            preparedStmt.setDate(3,Fin);
            preparedStmt.setString(4, name);

            preparedStmt.execute();

          

        } catch (SQLException e) {

            e.printStackTrace();
        }
        
        
        query = "UPDATE libros SET estado='Y' WHERE isbn='"+name+"'";
        System.out.println(query);
        
        
        try{
       
       preparedStmt= con.prepareStatement(query);
       preparedStmt.executeQuery();
       con.close();
        }catch(SQLException ex)
         {
             ex.printStackTrace();
         
         }
       

    }
    
    
   






          

          

        
    
  

}
