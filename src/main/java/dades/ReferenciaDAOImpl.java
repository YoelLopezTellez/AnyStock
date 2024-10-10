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
 * Implementació de la interfície DAO per a gestionar les operacions CRUD de la
 * entitat Referència en la base de dades.
 *
 * @author Mario
 */
public class ReferenciaDAOImpl implements DAOInterface<Referencia> {

    /**
     * Inserida una nova referència en la base de dades.
     *
     * @param entitat L'entitat Referència a agregar.
     */
    @Override
    public void afegir(Referencia entitat) {
        String sql = "INSERT INTO referencia (id, dataAlta, FAMILIA_id) VALUES (id, ?, ?)";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(2, entitat.getFamiliaID());
            stmt.setDate(1, java.sql.Date.valueOf(entitat.getDataAlta().now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica una referència existent en la base de dades.
     *
     * @param entitat L'entitat referencia amb les dades actualitzades.
     */
    @Override
    public void modificar(Referencia entitat) throws Exception {
        String sql = "UPDATE referencia SET vegadesAlarma = ?, preuCompra = ?, observacions = ?, quantitat = ?, nom = ?, UoM = ?, dataAlta = ?, PROVEIDOR_ID = ?, FAMILIA_ID = ? WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entitat.getVegadesAlarma());
            stmt.setFloat(2, entitat.getPreuCompra());
            stmt.setString(3, entitat.getObservacions());
            stmt.setInt(4, entitat.getQuantitat());
            stmt.setString(5, entitat.getNom());
            stmt.setString(6, entitat.getUom().toString());
            stmt.setDate(7, java.sql.Date.valueOf(entitat.getDataAlta()));
            stmt.setInt(8, entitat.getProveidor());
            stmt.setInt(9, entitat.getFamiliaID());

            // Establir el id al final, ja que és el paràmetre de la clàusula WHERE
            stmt.setInt(10, entitat.getId());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Elimina una referència de la base de dades donat el seu ID.
     *
     * @param id El id de la referència a eliminar.
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
     * Llista totes les referències de la família seleccionada prèviament.
     *
     * @return Una llista d'objectes Família.
     */
    public List<Referencia> LlistarTot(int idFamilia) {
        List<Referencia> referencias = new ArrayList<>();
        String sql = "SELECT * FROM referencia";
        if (idFamilia != 0) {
            sql = "SELECT * FROM referencia where FAMILIA_ID = " + idFamilia;
        }

        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UOM uom;
                if (rs.getString("UoM") != null) {
                    uom = UOM.valueOf(rs.getString("UoM").toUpperCase());
                } else {
                    uom = UOM.ALTRES;
                }
                Date dataAlarmaSql = rs.getDate("ultimaDataAlarma");
                LocalDate dataAlarma = (dataAlarmaSql != null) ? dataAlarmaSql.toLocalDate() : null;

                Referencia referencia = new Referencia(
                        rs.getInt("id"),
                        rs.getInt("vegadesAlarma"),
                        rs.getFloat("preuCompra"),
                        rs.getString("observacions"),
                        rs.getInt("quantitat"),
                        rs.getString("nom"),
                        uom,
                        rs.getDate("dataAlta").toLocalDate(),
                        dataAlarma, // Usem la variable que conté null si la data és nul·la
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
     * Obté una referència de la base de dades donat el seu ID.
     *
     * @param id El ID de la referència a obtenir.
     * @return L'objecte Referència si es troba, en cas contrari null.
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
