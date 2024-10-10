package dades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import aplicacio.model.Familia;
import java.time.LocalDate;
import presentacio.Error;

/**
 * Classe que implementa les operacions de la interfície DAO per a la gestió de
 * famílies. Aquesta classe s'encarrega d'afegir, modificar, eliminar i obtenir
 * famílies de la base de dades.
 *
 * @author Yoel
 */
public class FamiliaDAOImpl implements DAOInterface<Familia>, DAOInterfaceLlistat<Familia> {

    private Error errorDialog = new Error(); // Instància de Error per mostrar missatges

    /**
     * Afegir una nova família a la base de dades.
     *
     * @param entitat La família a afegir.
     */
    public void afegir(Familia entitat) {
        String sql = "INSERT INTO familia (dataAlta, observacions, nom, descripcio, PROVEIDOR_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(2, entitat.getObservacions());
            stmt.setString(3, entitat.getNom());
            stmt.setString(4, entitat.getDescripcio());

            // Si no hi ha proveïdor, s'estableix com a NULL
            if (entitat.getProveidorPerDefecte() == 0) {
                stmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(5, entitat.getProveidorPerDefecte());
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entitat.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            errorDialog.mostrarError("Error al agregar la família", e.getMessage());
        }
    }

    /**
     * Modificar una família existent a la base de dades.
     *
     * @param entitat La família a modificar.
     * @throws Exception Si hi ha un error durant la modificació.
     */
    @Override
    public void modificar(Familia entitat) throws Exception {
        // Verificar si el proveïdor existeix abans de modificar la família
        if (!proveedorExiste(entitat.getProveidorPerDefecte())) {
            errorDialog.mostrarError("Error de proveïdor", "El proveïdor amb ID " + entitat.getProveidorPerDefecte() + " no existeix.");
            return;
        }

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
            errorDialog.mostrarError("Error al modificar la família", e.getMessage());
            throw e;
        }
    }

    /**
     * Eliminar una família de la base de dades.
     *
     * @param id L'ID de la família a eliminar.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM familia WHERE id = ?";

        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            errorDialog.mostrarError("Error al eliminar la família", e.getMessage());
        }
    }

    /**
     * Llistar totes les famílies de la base de dades.
     *
     * @return Una llista de famílies.
     */
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
            errorDialog.mostrarError("Error al listar les famílies", e.getMessage());
        }
        return familias;
    }

    /**
     * Obtenir una família per l'ID.
     *
     * @param id L'ID de la família a obtenir.
     * @return La família amb l'ID especificat, o null si no existeix.
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
            errorDialog.mostrarError("Error al obtenir la família", e.getMessage());
        }
        return familia;
    }

    /**
     * Verifica si la família té referències associades.
     *
     * @param familiaId L'ID de la família a verificar.
     * @return true si té referències, false si no.
     */
    public boolean tieneReferencias(int idFamilia) {
        String sql = "SELECT COUNT(*) FROM referencia WHERE familia_id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFamilia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si hi ha referències
                }
            }
        } catch (SQLException e) {
            errorDialog.mostrarError("Error al verificar referències", e.getMessage());
        }
        return false; // Si ocorre un error, assumim que no hi ha referències
    }

    /**
     * Verifica si el proveïdor existeix.
     *
     * @param proveedorId L'ID del proveïdor a verificar.
     * @return true si existeix, false si no.
     */
    private boolean proveedorExiste(int proveedorId) {
        String sql = "SELECT COUNT(*) FROM proveidor WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proveedorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si el proveïdor existeix
                }
            }
        } catch (SQLException e) {
            errorDialog.mostrarError("Error al verificar el proveïdor", e.getMessage());
        }
        return false; // Si ocorre un error, assumim que no existeix
    }
}
