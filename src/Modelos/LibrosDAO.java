/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static Utils.Utils.generateHash;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LibrosDAO {

    private Connection con;
    private Libros libros;
    private ArrayList<Libros> arrayLibros = new ArrayList();

    public LibrosDAO() {

        Conexion c = new Conexion();
        this.con = c.conectar();
    }

    public boolean crearLibros(Libros libros, boolean valor) {
        String query = "";

        if (valor == false) {
            query = "INSERT INTO libros (titulo,autor,editorial,isbn)"
                    + " values (?, ?, ?, ?)";
        } else {
            query = "UPDATE libros SET titulo=?,autor=?,"
                    + "editorial=?,isbn=? WHERE isbn='"+libros.getIsbn()+"'";

        }

        PreparedStatement preparedStmt = null;

        try {
            preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, libros.getTitulo());
            preparedStmt.setString(2, libros.getAutor());
            preparedStmt.setString(3, libros.getEditorial());
            preparedStmt.setString(4, libros.getIsbn());

            preparedStmt.execute();

            con.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
    
    
      public ArrayList resultLibros() {

        Statement stmt = null;
        String query = "SELECT * FROM libros";

        try {

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                libros = new Libros();
                libros.setTitulo(rs.getString("titulo"));
                libros.setAutor(rs.getString("autor"));
                libros.setEditorial(rs.getString("editorial"));
                libros.setIsbn(rs.getString("isbn"));
                

                arrayLibros.add(libros);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return arrayLibros;
    }
      
      
      public void borrarLibro(String valor) {

        String query = "DELETE FROM libros WHERE isbn='"+valor+"'";
          System.out.println(query);
         

        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {

            e.printStackTrace();
        }
    } 
      
      
      public Libros modificarLibros(String valor) {

        String query = "SELECT * FROM libros WHERE isbn='"+valor+"'";

        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            libros = new Libros();
            while (rs.next()) {

                libros.setTitulo(rs.getString("titulo"));
                libros.setAutor(rs.getString("autor"));
                libros.setEditorial(rs.getString("editorial"));
                libros.setIsbn(rs.getString("isbn"));
              ;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return libros;

    } 
      
    

}
