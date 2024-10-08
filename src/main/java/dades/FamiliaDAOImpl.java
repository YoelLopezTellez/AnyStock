package dades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aplicacio.model.Familia;
import java.time.LocalDate;

public class FamiliaDAOImpl implements DAOInterface<Familia>, DAOInterfaceLlistat<Familia> {

    @Override
    public void afegir(Familia entitat) {
        String sql = "INSERT INTO familia (dataAlta, observacions, nom, descripcio, PROVEIDOR_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(2, entitat.getObservacions());
            stmt.setString(3, entitat.getNom());
            stmt.setString(4, entitat.getDescripcio());
            stmt.setInt(5, entitat.getProveidorPerDefecte());

            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entitat.setId(generatedKeys.getInt(1)); // Establece el ID en el objeto Familia
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al agregar la familia: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Familia entitat) {
        String sql = "UPDATE familia SET nom = ?, descripcio = ?, dataAlta = ?, PROVEIDOR_id = ?, observacions = ? WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entitat.getNom());
            stmt.setString(2, entitat.getDescripcio());
            stmt.setDate(3, java.sql.Date.valueOf(entitat.getDataAlta()));
            stmt.setInt(4, entitat.getProveidorPerDefecte());
            stmt.setString(5, entitat.getObservacions());
            stmt.setInt(6, entitat.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al modificar la familia: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String verificarFamiliaSql = "SELECT * FROM familia WHERE id = ?";
        String verificarReferenciasSql = "SELECT COUNT(*) FROM referencia WHERE familia_id = ?";
        String eliminarSql = "DELETE FROM familia WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement stmtVerificarFamilia = conn.prepareStatement(verificarFamiliaSql); PreparedStatement stmtVerificarReferencias = conn.prepareStatement(verificarReferenciasSql); PreparedStatement stmtEliminar = conn.prepareStatement(eliminarSql)) {

            // Verificar si la familia existe
            stmtVerificarFamilia.setInt(1, id);
            try (ResultSet rsFamilia = stmtVerificarFamilia.executeQuery()) {
                if (!rsFamilia.next()) {
                    System.out.println("La familia con ID " + id + " no existe.");
                    return;
                }
            }

            // Verificar si hay referencias asociadas
            stmtVerificarReferencias.setInt(1, id);
            try (ResultSet rsReferencias = stmtVerificarReferencias.executeQuery()) {
                if (rsReferencias.next() && rsReferencias.getInt(1) > 0) {
                    System.out.println("No se puede eliminar la familia porque tiene referencias asociadas.");
                    return;
                }
            }

            // Eliminar la familia
            stmtEliminar.setInt(1, id);
            stmtEliminar.executeUpdate();
            System.out.println("Familia eliminada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar la familia: " + e.getMessage());
        }
    }

    @Override
    public List<Familia> LlistarTot() {
        List<Familia> familias = new ArrayList<>();
        String sql = "SELECT * FROM familia";

        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Familia familia = new Familia(
                        rs.getInt("id"),
                        rs.getDate("dataAlta").toLocalDate(),
                        rs.getString("observacions"),
                        rs.getString("nom"),
                        rs.getString("descripcio"),
                        rs.getInt("PROVEIDOR_id")
                );
                familias.add(familia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al listar las familias: " + e.getMessage());
        }
        return familias;
    }

    @Override
    public Familia obtenir(int id) {
        Familia familia = null;
        String sql = "SELECT * FROM familia WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    familia = new Familia(
                            rs.getInt("id"),
                            rs.getDate("dataAlta").toLocalDate(),
                            rs.getString("observacions"),
                            rs.getString("nom"),
                            rs.getString("descripcio"),
                            rs.getInt("PROVEIDOR_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener la familia: " + e.getMessage());
        }
        return familia;
    }
}
