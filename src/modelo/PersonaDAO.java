package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List Listar() {
        List<Persona> datos = new ArrayList<>();

        String sql = "SELECT id, nombre, correo, telefono FROM users";

        try {

            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona p = new Persona();

                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCorreo(rs.getString(3));
                p.setTelefono(rs.getString(4));
                datos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
        return datos;
    }

    public int Agregar(Persona p) {
        String sql = "INSERT INTO users(nombre, correo, telefono) VALUES (?, ?, ?)";
        
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        return 1;
    }

    public int Editar(Persona p) {
        int r = 0;
        
        String sql = "UPDATE users SET nombre = ?, correo = ?, telefono = ? WHERE id = ?";
        
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.setInt(4, p.getId());
            r = ps.executeUpdate();
            
            if (r == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        return r;
    }

    public void Eliminar(int id) {
        String sql = "DELETE FROM users WHERE id = " + id;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
    }

    public List<Persona> Filtro(String nombre, String correo, String telefono) {
        List<Persona> datos = new ArrayList<>();

        String sql = "SELECT id, nombre, correo, telefono FROM users WHERE 1=1";

        if (nombre != null && !nombre.isEmpty()) {
            sql += " AND nombre LIKE ?";
        }
        if (correo != null && !correo.isEmpty()) {
            sql += " AND correo LIKE ?";
        }
        if (telefono != null && !telefono.isEmpty()) {
            sql += " AND telefono LIKE ?";
        }

        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);

            int index = 1;
            if (nombre != null && !nombre.isEmpty()) {
                ps.setString(index++, "%" + nombre + "%");
            }
            if (correo != null && !correo.isEmpty()) {
                ps.setString(index++, "%" + correo + "%");
            }
            if (telefono != null && !telefono.isEmpty()) {
                ps.setString(index++, "%" + telefono + "%");
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCorreo(rs.getString(3));
                p.setTelefono(rs.getString(4));
                datos.add(p);
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        }
        return datos;
    }
}
