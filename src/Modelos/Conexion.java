/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.sql.*;

public class Conexion {

    Connection conexion;

    public Connection conectar() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            
          //  conexion = DriverManager.getConnection("jdbc:mysql://localhost/proyecto", "root", "terro1975");
              conexion = DriverManager.getConnection("jdbc:mysql://jose.nimelec.com/jose", "jose", "Dam2016$");
          
            System.out.println(conexion);
            
        } catch (Exception e) {
                
            e.printStackTrace();
        }

        return conexion;

    }

}
