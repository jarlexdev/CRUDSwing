package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Persona;
import modelo.PersonaDAO;
import vista.Vista;

public class Controlador implements ActionListener {

    PersonaDAO dao = new PersonaDAO();
    Persona p = new Persona();
    Vista vista = new Vista();
    DefaultTableModel modelo = new DefaultTableModel();

    public Controlador(Vista v) {
        this.vista = v;
        this.vista.getBtnListar().addActionListener(this);
        this.vista.getBtnGuardar().addActionListener(this);
        this.vista.getBtnEditar().addActionListener(this);
        this.vista.getBtnListo().addActionListener(this);
        this.vista.getBtnEliminar().addActionListener(this);
        this.vista.getBtnBuscar().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
        Listar(vista.tabla);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnListar()) {
            limpiarTabla();
            Listar(vista.tabla);
        }
        if (e.getSource() == vista.getBtnGuardar()) {
            Agregar();
            limpiarTabla();
            Listar(vista.tabla);
        }
        if (e.getSource() == vista.getBtnEditar()) {
            int fila = vista.tabla.getSelectedRow();
            
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "DEBE SELECCIONAR UNA FILA...");
            } else {
                int id = (Integer) vista.tabla.getValueAt(fila, 0);
                String nombre = (String) vista.tabla.getValueAt(fila, 1);
                String correo = (String) vista.tabla.getValueAt(fila, 2);
                String telefono = (String) vista.tabla.getValueAt(fila, 3);
                
                vista.getTxtID().setText("" + id);
                vista.getTxtNombre().setText(nombre);
                vista.getTxtCorreo().setText(correo);
                vista.getTxtTelefono().setText(telefono);
            }
        }
        if (e.getSource() == vista.getBtnListo()) {
            Editar();
            limpiarTabla();
            Listar(vista.tabla);
        }
        if (e.getSource() == vista.getBtnEliminar()) {
            Eliminar();
            limpiarTabla();
            Listar(vista.tabla);
        }
        if (e.getSource() == vista.getBtnBuscar()) {
            String nombre = vista.getTxtBuscarNombre().getText();
            String correo = vista.getTxtBuscarCorreo().getText();
            String telefono = vista.getTxtBuscarTelefono().getText();
            
            if (nombre.isEmpty() && correo.isEmpty() && telefono.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "POR FAVOR INGRESE AL MENOS UN CRITERIO DE BUSQUEDA...");
            } else {

                limpiarTabla();

                List<Persona> personasFiltradas = dao.Filtro(nombre, correo, telefono);

                modelo = (DefaultTableModel) vista.tabla.getModel();

                Object[] object = new Object[4];
                for (int i = 0; i < personasFiltradas.size(); i++) {
                    object[0] = personasFiltradas.get(i).getId();
                    object[1] = personasFiltradas.get(i).getNombre();
                    object[2] = personasFiltradas.get(i).getCorreo();
                    object[3] = personasFiltradas.get(i).getTelefono();
                    modelo.addRow(object);
                }
                if (personasFiltradas.isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "NO SE ENCONTRARON RESULTADOS...");
                }
            }
        }
        if (e.getSource() == vista.getBtnLimpiar()) {
            limpiarCajasdeTexto();
        }
    }

    public void Eliminar() {
        int fila = vista.tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "DEBE SELECCIONAR UN USUARIO...");
        } else {
            int id = (Integer) vista.tabla.getValueAt(fila, 0);
            dao.Eliminar(id);
            JOptionPane.showMessageDialog(vista, "USUARIO ELIMINADO...");
        }
    }

    void limpiarTabla() {
        modelo.setRowCount(0);
    }
    
    void limpiarCajasdeTexto() {
        vista.getTxtID().setText("");
        vista.getTxtNombre().setText("");
        vista.getTxtCorreo().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtBuscarNombre().setText("");
        vista.getTxtBuscarCorreo().setText("");
        vista.getTxtBuscarTelefono().setText("");
    }

    public void Editar() {
        int id = Integer.parseInt(vista.getTxtID().getText());
        String nombre = vista.getTxtNombre().getText();
        String correo = vista.getTxtCorreo().getText();
        String telefono = vista.getTxtTelefono().getText();
        p.setId(id);
        p.setNombre(nombre);
        p.setCorreo(correo);
        p.setTelefono(telefono);
        int r = dao.Editar(p);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "USUARIO ACTUALIZADO CON EXITO...");
        } else {
            JOptionPane.showMessageDialog(vista, "ERROR...");
        }
    }

    public void Agregar() {
        String nombre = vista.getTxtNombre().getText();
        String correo = vista.getTxtCorreo().getText();
        String telefono = vista.getTxtTelefono().getText();
        p.setNombre(nombre);
        p.setCorreo(correo);
        p.setTelefono(telefono);
        int r = dao.Agregar(p);
        if (r == 1) {
            JOptionPane.showMessageDialog(vista, "USUARIO AGREGADO CON EXITO...");
        } else {
            JOptionPane.showMessageDialog(vista, "ERROR...");
        }
    }

    public void Listar(JTable tabla) {
        modelo = (DefaultTableModel) tabla.getModel();
        List<Persona> lista = dao.Listar();
        Object[] object = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getCorreo();
            object[3] = lista.get(i).getTelefono();
            modelo.addRow(object);
        }
    }
}
