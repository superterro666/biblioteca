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

public class UsuarioDAO {

    private Connection con;
    private Usuario user;
    private ArrayList<Usuario> usuarioArray = new ArrayList<Usuario>();

    public UsuarioDAO() {
        Conexion c = new Conexion();
        this.con = c.conectar();

    }

    public boolean crearUser(Usuario user, boolean valor) {
        String query = "";
        
        if (valor == false) {
          query = "INSERT INTO usuario (nombre,apellidos,direccion,telefono,email,password,role,username,salt)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }else{
          query = "UPDATE usuario SET nombre=?,apellidos=?,"
             + "direccion=?,telefono=?,email=?,password=?,role=?,username=?,salt=? WHERE telefono="+user.getTelefono();
                   
        }

          
            
            String role = "user";
            String salt = generateHash(String.valueOf(System.currentTimeMillis()));
            String username = user.getEmail();

            PreparedStatement preparedStmt = null;

            try {
                preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, user.getNombre());
                preparedStmt.setString(2, user.getApellidos());
                preparedStmt.setString(3, user.getDireccion());
                preparedStmt.setString(4, user.getTelefono());
                preparedStmt.setString(5, user.getEmail());
                preparedStmt.setString(6, generateHash(user.getPassword()));
                preparedStmt.setString(7, role);
                preparedStmt.setString(8, username);
                preparedStmt.setString(9, salt);

                preparedStmt.execute();

                con.close();

            } catch (SQLException e) {

                e.printStackTrace();
            }

        

        return false;
    }

    public ArrayList resultUser() {

        Statement stmt = null;
        String query = "SELECT * FROM usuario";

        try {

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                user = new Usuario();
                user.setNombre(rs.getString("nombre"));
                user.setApellidos(rs.getString("apellidos"));
                user.setDireccion(rs.getString("direccion"));
                user.setTelefono(rs.getString("telefono"));
                user.setEmail(rs.getString("email"));

                usuarioArray.add(user);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return usuarioArray;
    }

    public void borrarUser(String valor) {

        String query = "DELETE FROM usuario WHERE telefono=" + valor;

        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Usuario modificarUsuario(String valor) {

        String query = "SELECT * FROM usuario WHERE telefono=" + valor;

        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            user = new Usuario();
            while (rs.next()) {

                user.setNombre(rs.getString("nombre"));
                user.setApellidos(rs.getString("apellidos"));
                user.setDireccion(rs.getString("direccion"));
                user.setTelefono(rs.getString("telefono"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setSalt(rs.getString("salt"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;

    }
    
    
  

}
