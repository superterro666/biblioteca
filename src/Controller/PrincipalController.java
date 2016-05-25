/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Vistas.PrincipalVista;
import Vistas.UsuarioVista;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalController implements ActionListener {
    
    
private PrincipalVista principal ;  

    public PrincipalController() {
        
      this.principal = new PrincipalVista();
      this.principal.setLocationRelativeTo(null);
      this.principal.setVisible(true);
      this.principal.ListarItem.addActionListener(this);
      this.principal.UserItem.addActionListener(this);
      this.principal.libroItem.addActionListener(this);
      this.principal.libroListarItem.addActionListener(this);
  
      
      
      
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
        
        
        
        switch(e.getActionCommand()){
        
            case "userItem":
                this.principal.UserItem.setEnabled(false);
                this.principal.desktopPanel.setLayout(new FlowLayout());
                UsuarioController userC = new UsuarioController(this.principal);
                userC.showCrearUser();
              
            break;     
            
                
            case "userListItem":
                
                 this.principal.ListarItem.setEnabled(false);
                 this.principal.desktopPanel.setLayout(new FlowLayout());
                 UsuarioController userT = new UsuarioController(this.principal);
                 userT.showTable();
            break;  
            
            case "crearLibro":
                this.principal.libroItem.setEnabled(false);
                this.principal.desktopPanel.setLayout(new FlowLayout());
                LibrosController libroC = new LibrosController(this.principal);
                libroC.showCreateUser();
                break;
                
            case "listarLibro":
                 this.principal.libroListarItem.setEnabled(false);
                 this.principal.desktopPanel.setLayout(new FlowLayout());
                 LibrosController libroT = new LibrosController(this.principal);
                 libroT.showTable();
                
                break;
            
            
                
                
             
               
               
                
          
        
        }
        
        
        
        
    }




}
