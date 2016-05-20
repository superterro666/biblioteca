/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Modelos.Alquiler;
import Modelos.Libros;
import Modelos.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author terro
 */
public class Utils {
    
    
   
    
  public static String generateHash(String password) {

        MessageDigest md = null;
        byte[] hash = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            hash = md.digest(password.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return convertToHex(hash);
    }

    private static String convertToHex(byte[] raw) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < raw.length; i++) {
            sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }   
    
    
    
    
    public static boolean camposVacios(String[] valor){
    
        for (int i = 0; i < valor.length; i++) {
            
            if(valor[i].equals(""))
                return false;
            
            
        }
       return true;    
     }    
        
        
    public static String[][] cambiarArray(ArrayList valor) {

        String[][] informacion = new String[valor.size()][5];
        ArrayList<Usuario> user = valor;
        for (int x = 0; x < informacion.length; x++) {

            informacion[x][0] = user.get(x).getNombre();
            informacion[x][1] = user.get(x).getApellidos();
            informacion[x][2] = user.get(x).getDireccion();
            informacion[x][3] = user.get(x).getTelefono();
            informacion[x][4] = user.get(x).getEmail();

        }

        return informacion;

    }
    
    public static String[][] cambiarArrayLibros(ArrayList valor){
    
     String[][] informacion = new String[valor.size()][5];
        ArrayList<Libros> libros = valor;
        for (int x = 0; x < informacion.length; x++) {

            informacion[x][0] = libros.get(x).getTitulo();
            informacion[x][1] = libros.get(x).getAutor();
            informacion[x][2] = libros.get(x).getEditorial();
            informacion[x][3] = libros.get(x).getIsbn();
           

        }

        return informacion;
    
    }
    
    
 public static String[][] cambiarArrayAlquiler(ArrayList valor) {

        String[][] informacion = new String[valor.size()][5];
        ArrayList<Alquiler> libros = valor;
        for (int x = 0; x < informacion.length; x++) {

            informacion[x][0] = libros.get(x).getTitulo();
            informacion[x][1] = libros.get(x).getFecha_alta();
            informacion[x][2] = libros.get(x).getFecha_baja();
            informacion[x][3] = libros.get(x).getIsbn();
           

        }

        return informacion;

    }
  
    
    
    
    
}
