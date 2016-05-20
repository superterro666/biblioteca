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
import static Utils.Utils.cambiarArray;
import static Utils.Utils.camposVacios;
import Vistas.PrincipalVista;
import Vistas.UsuarioTable;
import Vistas.UsuarioVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UsuarioController implements ActionListener {
    
    private UsuarioVista userVista;
    private UsuarioTable userTable;
    private PrincipalVista principal;
    private Usuario usuario;
    private ArrayList<Usuario> ArrayUsuario = new ArrayList<Usuario>();
    private ArrayList<Alquiler> ArrayAlquiler = new ArrayList<Alquiler>();
    private boolean modificar;
    
    public UsuarioController(PrincipalVista u) {
        
        this.usuario = new Usuario();
        this.principal = u;
        this.modificar=false;
       
        
    }
    
    public void showCrearUser() {
        
        this.userVista = new UsuarioVista();
        this.userVista.btnCrearUser.addActionListener(this);
        this.principal.desktopPanel.add(this.userVista,2);
        
    }
    
    public void showTable() {
        
        UsuarioDAO dao = new UsuarioDAO();
        ArrayUsuario = dao.resultUser();
        
        String[][] datosTabla = cambiarArray(ArrayUsuario);
        String[] columns = {"Nombre", "Apellidos", "Direccion", "Telefono", "Email"};
        
        this.userTable = new UsuarioTable(datosTabla, columns);
        
        this.userTable.btnBorrarUser.addActionListener(this);
        this.userTable.btnModificarUser.addActionListener(this);
        this.userTable.btnDatos.addActionListener(this);
        
        this.principal.desktopPanel.add(this.userTable,1);
        
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (e.getActionCommand()) {
            
            case "Crear":
                
                String[] valor = {this.userVista.FieldNombre1.getText(), this.userVista.FieldApellidos1.getText(),
                    this.userVista.FieldDireccion.getText(),
                    this.userVista.FieldTelefono2.getText(),
                    this.userVista.FieldEmail.getText(),
                    this.userVista.Fieldpassword.getText()
                };
                
                if (camposVacios(valor)) {
                    
                    this.usuario.setNombre(this.userVista.FieldNombre1.getText());
                    this.usuario.setApellidos(this.userVista.FieldApellidos1.getText());
                    this.usuario.setDireccion(this.userVista.FieldDireccion.getText());
                    this.usuario.setTelefono(this.userVista.FieldTelefono2.getText());
                    this.usuario.setEmail(this.userVista.FieldEmail.getText());
                    this.usuario.setPassword(this.userVista.Fieldpassword.getText());
                    
                    UsuarioDAO dao = new UsuarioDAO();
                    dao.crearUser(this.usuario,this.modificar);
                    
                }else{
                
                  JOptionPane.showMessageDialog(this.principal,"Debes de rellenar todos los campos");
                
                
                }
                
                break;
            
            case "Borrar":
                
                int row = this.userTable.tabla_user.getSelectedRow();
                this.userTable.dtm.removeRow(row);                
                String name = this.userTable.dtm.getValueAt(row, 3).toString();
                
                UsuarioDAO dao = new UsuarioDAO();
                dao.borrarUser(name);
                
                break;   
                
            case "Modificar":

                this.modificar = true;
                int rows = this.userTable.tabla_user.getSelectedRow();
                String names = this.userTable.dtm.getValueAt(rows, 3).toString();
                UsuarioDAO daos = new UsuarioDAO();

                Usuario user = daos.modificarUsuario(names);

                this.userVista = new UsuarioVista();

                this.userVista.FieldNombre1.setText(user.getNombre());
                this.userVista.FieldApellidos1.setText(user.getApellidos());
                this.userVista.FieldDireccion.setText(user.getDireccion());
                this.userVista.FieldTelefono2.setText(user.getTelefono());
                this.userVista.FieldEmail.setText(user.getEmail());
                this.userVista.Fieldpassword.setText(user.getPassword());

                this.userVista.btnCrearUser.addActionListener(this);
                this.userTable.setVisible(false);
                this.principal.desktopPanel.add(this.userVista);

                break;
                
              case "Datos":
               
                 int rowss = this.userTable.tabla_user.getSelectedRow();
                 String namess = this.userTable.dtm.getValueAt(rowss, 3).toString();
                
                 UsuarioDAO userr = new UsuarioDAO();
                 usuario = userr.modificarUsuario(namess);
                 DatosController datosC = new DatosController(this.principal);
                 datosC.showDatos(usuario); 
                 this.userTable.setVisible(false);
                 
                 break;
                 
             
           
              
                
                 
              
              
                 
                 
                 
                 
                
                
                
                
              
        }
     
    }
    
}
