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
import aplicacio.model.Referencia;
import aplicacio.model.UOM;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Implementación de la interfaz DAO para gestionar las operaciones CRUD de la
 * entidad Referencia en la base de datos.
 *
 * @author Mario
 */
public class ReferenciaDAOImpl implements DAOInterface<Referencia> {

    /**
     * Inserta una nueva referencia en la base de datos.
     *
     * @param entitat La entidad Referencia a agregar.
     */
    @Override
    public void afegir(Referencia entitat) {
        String sql = "INSERT INTO referencia (id, FAMILIA_id) VALUES (id, ?)";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, entitat.getFamiliaID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica una referencia existente en la base de datos.
     *
     * @param entitat La entidad referencia con los datos actualizados.
     */
    @Override
    public void modificar(Referencia entitat) {
        String sql = "UPDATE referencia SET vegadesAlarma = ?, preuCompra = ?, observacions = ?, quantitat = ?, nom = ?, UoM = ?, dataAlta = ?, dataUltimaAlarma = ?, PROVEIDOR_CIF = ?, FAMILIA_ID = ? WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entitat.getVegadesAlarma());
            stmt.setFloat(2, entitat.getPreuCompra());
            stmt.setString(3, entitat.getObservacions());
            stmt.setInt(4, entitat.getQuantitat());
            stmt.setString(5, entitat.getNom());
            stmt.setString(6, entitat.getUom().toString());
            stmt.setDate(7, java.sql.Date.valueOf(entitat.getDataAlta()));
            stmt.setDate(8, java.sql.Date.valueOf(entitat.getUltimaDataAlarma()));
            stmt.setInt(9, entitat.getProveidor());
            stmt.setInt(10, entitat.getFamiliaID());

            // Establecer el id al final, ya que es el parámetro de la cláusula WHERE
            stmt.setInt(11, entitat.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una referencia de la base de datos dado su ID.
     *
     * @param id El ID de la referencia a eliminar.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM referencia WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todas las referencias de la familia seleccionada previamente.
     *
     * @return Una lista de objetos Familia.
     */
    
    public List<Referencia> LlistarTot(int idFamilia) {
        List<Referencia> referencias = new ArrayList<>();
        String sql = "SELECT * FROM referencia where FAMILIA_ID = "+idFamilia;
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UOM uom = UOM.valueOf(rs.getString("UoM").toUpperCase());
                Referencia referencia = new Referencia(
                        rs.getInt("id"),
                        rs.getInt("vegadesAlarma"),
                        rs.getFloat("preuCompra"),
                        rs.getString("observacions"),
                        rs.getInt("quantitat"),
                        rs.getString("nom"),
                        uom,
                        rs.getDate("dataAlta").toLocalDate(),
                        rs.getDate("ultimaDataAlarma").toLocalDate(),
                        rs.getInt("PROVEIDOR_ID"),
                        rs.getInt("FAMILIA_ID")
                );
                referencias.add(referencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return referencias;
    }

    /**
     * Obtiene una referencia de la base de datos dado su ID.
     *
     * @param id El ID de la referencia a obtener.
     * @return El objeto Referencia si se encuentra, de lo contrario null.
     */
    @Override
    public Referencia obtenir(int id) {
        Referencia referencia = null;
        String sql = "SELECT * FROM referencia WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UOM uom = UOM.valueOf(rs.getString("UoM").toUpperCase());
                    referencia = new Referencia(
                            rs.getInt("id"),
                            rs.getInt("vegadesAlarma"),
                            rs.getFloat("preuCompra"),
                            rs.getString("observacions"),
                            rs.getInt("quantitat"),
                            rs.getString("nom"),
                            uom,
                            rs.getDate("dataAlta").toLocalDate(),
                            rs.getDate("ultimaDataAlarma").toLocalDate(),
                            rs.getInt("PROVEIDOR_ID"),
                            rs.getInt("FAMILIA_ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return referencia;
    }
}