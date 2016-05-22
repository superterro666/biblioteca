/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modelos.Alquiler;
import Modelos.AlquilerDAO;
import Modelos.Usuario;
import Modelos.UsuarioDAO;
import static Utils.Utils.cambiarArrayAlquiler;
import static Utils.Utils.cambiarArrayLibros;
import Vistas.AlquilerVista;
import Vistas.PrincipalVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author terro
 */
public class AlquilerController implements ActionListener {

    private PrincipalVista principal;
    private ArrayList<Alquiler> ArrayAlquiler = new ArrayList<Alquiler>();

    private AlquilerVista datosvista;
    private Usuario usuario;
    private UsuarioDAO dao;
    private Alquiler alquiler;
    private AlquilerDAO alquilerDAO;
    private String id_user;

    public AlquilerController(PrincipalVista v) {
        this.principal = v;

        usuario = new Usuario();
        dao = new UsuarioDAO();
        alquiler = new Alquiler();
        alquilerDAO = new AlquilerDAO();

    }

    public void showDatos(Usuario usuario) {

        AlquilerDAO dao = new AlquilerDAO();
        ArrayAlquiler = dao.resultDatos(usuario.getSalt());

        alquilerDAO = new AlquilerDAO();

        ArrayAlquiler = alquilerDAO.resultDatos(usuario.getSalt());
        id_user = usuario.getSalt();

        String[][] datos = cambiarArrayAlquiler(ArrayAlquiler);
        String[] columns = {"Titulo", "ISBN", "Fecha_alta", "Fecha_baja"};

        ArrayAlquiler = alquilerDAO.resultLibros();

        String[][] datos_2 = cambiarArrayLibros(ArrayAlquiler);
        String[] columns_2 = {"Titulo", "Autor", "Editorial", "ISBN"};

        this.datosvista = new AlquilerVista(datos, columns, datos_2, columns_2);
        this.datosvista.labelNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        this.datosvista.labelDireccion.setText(usuario.getDireccion());
        this.datosvista.labelTelefono.setText(usuario.getTelefono());
        this.datosvista.labelEmail.setText(usuario.getEmail());
        this.principal.desktopPanel.add(this.datosvista);
        this.datosvista.btlAlquilar.addActionListener(this);
        this.datosvista.btnDevolver.addActionListener(this);
        this.datosvista.btnAmpliar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "Alquilar":

                
                    if(!alquilerDAO.librosAlquilados(id_user)){    


                        int row = this.datosvista.tabla_libros_alquiler.getSelectedRow();
                        String name = this.datosvista.tabla_libros_alquiler.getValueAt(row, 3).toString();
                        alquilerDAO.updateEstado(id_user, name);

                        String[] datos = alquilerDAO.datosLibro(name);
                        
                       
                        this.datosvista.stm.addRow(datos);
                        this.datosvista.stm_2.removeRow(row);

                      }else{

                        JOptionPane.showMessageDialog(principal,"No puedes alquilar mas de dos libros");

                    }
            break;
            
            
            case "Devolver":
                        int row = this.datosvista.datos_table.getSelectedRow();
                        String name = this.datosvista.datos_table.getValueAt(row, 3).toString();
                        alquilerDAO.devolverAlquilados(name);
                        this.datosvista.stm.removeRow(row);
            break;
            
            case "Ampliar":
                
                        int rows = this.datosvista.datos_table.getSelectedRow();
                        String names = this.datosvista.datos_table.getValueAt(rows, 3).toString();
                        System.out.println(names);
                        String[] datos = alquilerDAO.datosLibro(names);
                        int ampliar = Integer.parseInt(datos[4]);
                        
                        if(ampliar<2){
                        
                            Date fecha = alquilerDAO.ampliarAquiler(datos[3], ampliar, datos[2]);
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String fe = df.format(fecha);
                        
                        }else{
                        
                        JOptionPane.showMessageDialog(principal, "No puede renovar mas veces");
                        
                        }
                        
                        
                       
                        
                        
                        
                        
                
                
                break;

        }

    }

}
