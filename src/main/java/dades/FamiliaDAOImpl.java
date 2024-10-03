/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aplicacio.model.Familia;
import java.sql.Date;

/**
 * Implementación de la interfaz DAO para gestionar las operaciones CRUD de la 
 * entidad Familia en la base de datos.
 * 
 * @author Yoel
 */
public class FamiliaDAOImpl implements DAOInterface<Familia> {

    /**
     * Inserta una nueva familia en la base de datos.
     * 
     * @param entitat La entidad Familia a agregar.
     */
    @Override
    public void afegir(Familia entitat) {
        String sql = "INSERT INTO familia (id, dataAlta, observacions, nom, descripcio, proveidorPerDefecte) VALUES (id, ?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, new Date(entitat.getDataAlta().getTime()));
            stmt.setString(2, entitat.getObservacions());
            stmt.setString(3, entitat.getNom());
            stmt.setString(4, entitat.getDescripcio());
            stmt.setString(5, entitat.getProveidorPerDefecte());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica una familia existente en la base de datos.
     * 
     * @param entitat La entidad Familia con los datos actualizados.
     */
    @Override
    public void modificar(Familia entitat) {
        String sql = "UPDATE familia SET nom = ?, descripcio = ?, dataAlta = ?, proveidorPerDefecte = ?, observacions = ? WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entitat.getNom());
            stmt.setString(2, entitat.getDescripcio());
            stmt.setDate(3, new Date(entitat.getDataAlta().getTime()));
            stmt.setString(4, entitat.getProveidorPerDefecte());
            stmt.setString(5, entitat.getObservacions());
            stmt.setInt(6, entitat.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una familia de la base de datos dado su ID.
     * 
     * @param id El ID de la familia a eliminar.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM familia WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todas las familias registradas en la base de datos.
     * 
     * @return Una lista de objetos Familia.
     */
    @Override
    public List<Familia> LlistarTot() {
        List<Familia> familias = new ArrayList<>();
        String sql = "SELECT * FROM familia";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Familia familia = new Familia(
                        rs.getString("nom"),
                        rs.getString("descripcio"),
                        rs.getDate("dataAlta"),
                        rs.getString("proveidorPerDefecte"),
                        rs.getString("observacions"),
                        rs.getInt("id")
                );
                familias.add(familia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return familias;
    }

    /**
     * Obtiene una familia de la base de datos dado su ID.
     * 
     * @param id El ID de la familia a obtener.
     * @return El objeto Familia si se encuentra, de lo contrario null.
     */
    @Override
    public Familia obtenir(int id) {
        Familia familia = null;
        String sql = "SELECT * FROM familia WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    familia = new Familia(
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getDate("dataAlta"),
                            rs.getString("proveidorPerDefecte"),
                            rs.getString("observacions"),
                            rs.getInt("id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return familia;
    }
}
