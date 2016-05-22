/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

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
    private int numRows = 0;

    public AlquilerDAO() {

        Conexion c = new Conexion();
        this.con = c.conectar();

    }

    public ArrayList<Alquiler> resultDatos(String valor) {

        String query = "SELECT aquiler.* , libros.* FROM aquiler "
                + "INNER JOIN libros ON libros.isbn=aquiler.id_libro WHERE aquiler.id_user='" + valor + "'";

        ResultSet rs = null;

        ArrayList<Alquiler> arraylist = new ArrayList<Alquiler>();
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                user = new Alquiler();
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

    public ArrayList<Alquiler> resultLibros() {

        String query = "SELECT libros.* FROM libros WHERE estado='" + "N" + "'";

        ResultSet rs = null;

        ArrayList<Alquiler> arraylist = new ArrayList<Alquiler>();
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                user = new Alquiler();
                user.setTitulo(rs.getString("titulo"));
                user.setIsbn(rs.getString("isbn"));
                user.setAutor(rs.getString("autor"));
                user.setEditorial(rs.getString("editorial"));

                arraylist.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            preparedStmt.setDate(3, Fin);
            preparedStmt.setString(4, name);

            preparedStmt.execute();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        query = "UPDATE libros SET estado='Y' WHERE isbn='" + name + "'";
        System.out.println(query);

        try {

            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public boolean librosAlquilados(String valor) {

        String query = "SELECT COUNT(id) AS total FROM aquiler WHERE id_user='" + valor + "'";

        ResultSet rs = null;

        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            rs.last();
            numRows = rs.getInt("total");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (numRows <= 1) {

            return false;

        } else {

            return true;
        }

    }

    public void devolverAlquilados(String valor) {

        String query = "UPDATE libros SET estado='N' WHERE isbn='" + valor + "'";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);

            query = "DELETE FROM aquiler WHERE id_libro='" + valor + "'";
            stmt.executeUpdate(query);

        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public String[] datosLibro(String valor) {

        String query = "SELECT aquiler.fecha_alta, aquiler.fecha_baja, aquiler.ampliaciones, libros.titulo, libros.isbn  "
                + "FROM aquiler INNER JOIN libros ON libros.isbn=aquiler.id_libro WHERE aquiler.id_libro='" + valor + "'";
        ResultSet rs = null;
        Statement stmt = null;
        String[] libro = new String[5];
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {

                libro[0] = rs.getString("titulo");
                libro[1] = rs.getString("fecha_alta");
                libro[2] = rs.getString("fecha_baja");
                libro[3] = rs.getString("isbn");
                libro[4] = rs.getString("ampliaciones");

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return libro;
    }

    public Date ampliarAquiler(String isbn, int ampliaciones, String fecha_baja) {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = formato.parse(fecha_baja);
        } catch (Exception e) {

            e.printStackTrace();
        }

        java.sql.Date fechaSql = new java.sql.Date(fecha.getTime());

        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fecha.getTime());
        cal.add(Calendar.DATE, 15);

        java.util.Date fechaFin = cal.getTime();
        java.sql.Date Fin = new java.sql.Date(fechaFin.getTime());

        ampliaciones = ampliaciones + 1;

        String query = "UPDATE aquiler SET fecha_baja='" + Fin + "' , ampliaciones='" + ampliaciones + "' WHERE id_libro='" + isbn + "'";

        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {

            e.printStackTrace();

        }

        return fechaFin;

    }

}
