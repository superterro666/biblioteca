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
import Vistas.DatosVista;
import Vistas.PrincipalVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author terro
 */
public class DatosController implements ActionListener {

    private PrincipalVista principal;
    private ArrayList<Alquiler> ArrayAlquiler = new ArrayList<Alquiler>();
    
    private DatosVista datosvista;
    private Usuario usuario;
    private UsuarioDAO dao;
    private Alquiler alquiler;
    private AlquilerDAO alquilerDAO;
    private String id_user;

    public DatosController(PrincipalVista v) {
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
        String[] columns_2 = {"Titulo","Autor","Editorial","ISBN"};
     
        this.datosvista = new DatosVista(datos, columns,datos_2,columns_2);
        this.datosvista.labelNombre.setText(usuario.getNombre() + " " + usuario.getApellidos());
        this.datosvista.labelDireccion.setText(usuario.getDireccion());
        this.datosvista.labelTelefono.setText(usuario.getTelefono());
        this.datosvista.labelEmail.setText(usuario.getEmail());
        this.principal.desktopPanel.add(this.datosvista);
        this.datosvista.btlAlquilar.addActionListener(this);
        this.datosvista.btnDevolver.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()){
        
            case "Alquilar":
                
            int row = this.datosvista.tabla_libros_alquiler.getSelectedRow();
            String name = this.datosvista.tabla_libros_alquiler.getValueAt(row, 3).toString();
            
           
                
            alquilerDAO.updateEstado(id_user, name);
                
            
                
                
                
                break;
        
        
        
        
        }
        
        
        
        
    }

}
