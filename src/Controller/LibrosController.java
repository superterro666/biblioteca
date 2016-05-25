/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modelos.Libros;
import Modelos.LibrosDAO;
import static Utils.Utils.cambiarArrayLibros;
import Vistas.LibrosVista;
import Vistas.PrincipalVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static Utils.Utils.camposVacios;
import Vistas.LibrosTable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LibrosController implements ActionListener {

    private PrincipalVista principal;
    private LibrosVista libroVista;
    private Libros libros;
    private LibrosTable librosTable;
    private ArrayList<Libros> arrayLibros = new ArrayList();

    private boolean modificar;

    public LibrosController(PrincipalVista v) {
        this.principal = v;
        this.modificar = false;

    }

    public void showCreateUser() {

        this.libroVista = new LibrosVista(this.principal);
        this.libroVista.jButton1.addActionListener(this);
        this.principal.desktopPanel.add(this.libroVista);

    }

    public void showTable() {

        LibrosDAO dao = new LibrosDAO();
        arrayLibros = dao.resultLibros();

        String[][] datosTabla = cambiarArrayLibros(arrayLibros);
        String[] columns = {"Titulo", "Autor", "Editorial", "ISBN"};

        this.librosTable = new LibrosTable(datosTabla, columns, this.principal);
      
        this.librosTable.btnBorrar.addActionListener(this);
        this.librosTable.btnModificar.addActionListener(this);
        this.principal.desktopPanel.add(this.librosTable, 1);
      

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {

            case "Crear":
                this.principal.libroItem.setEnabled(true);
                String[] valor = {this.libroVista.tituloTxt.getText(),
                    this.libroVista.autorTxt.getText(),
                    this.libroVista.editorialTxt.getText(),
                    this.libroVista.isbnTxt.getText()
                };

                if (camposVacios(valor)) {

                    this.libros = new Libros();
                    this.libros.setTitulo(this.libroVista.tituloTxt.getText());
                    this.libros.setAutor(this.libroVista.autorTxt.getText());
                    this.libros.setEditorial(this.libroVista.editorialTxt.getText());
                    this.libros.setIsbn(this.libroVista.isbnTxt.getText());

                    LibrosDAO dao = new LibrosDAO();
                    dao.crearLibros(this.libros, this.modificar);

                }else{
                
                JOptionPane.showMessageDialog(this.principal,"Debes de rellenar todos los campos");
                
                }
                break;

            case "Borrar":

                int row = this.librosTable.tabla_libros.getSelectedRow();
                String name = this.librosTable.dtm.getValueAt(row, 3).toString();
                this.librosTable.dtm.removeRow(row);

                System.out.println(name);

                LibrosDAO dao = new LibrosDAO();
                dao.borrarLibro(name);

                break;

            case "Modificar":
                this.principal.libroListarItem.setEnabled(true);
                this.modificar = true;
                int rows = this.librosTable.tabla_libros.getSelectedRow();
                String names = this.librosTable.dtm.getValueAt(rows, 3).toString();
                LibrosDAO daos = new LibrosDAO();

                Libros libros = daos.modificarLibros(names);

                this.libroVista = new LibrosVista(this.principal);

                this.libroVista.tituloTxt.setText(libros.getTitulo());
                this.libroVista.autorTxt.setText(libros.getAutor());
                this.libroVista.editorialTxt.setText(libros.getEditorial());
                this.libroVista.isbnTxt.setText(libros.getIsbn());

                this.libroVista.jButton1.addActionListener(this);
                this.librosTable.setVisible(false);
                this.principal.desktopPanel.add(this.libroVista);

                break;

        }

    }

}
