package logica;

import dades.FamiliaDAOImpl;
import aplicacio.model.Familia;
import javafx.scene.control.Alert;

import java.util.List;

/**
 * Classe que gestiona la lògica de negoci relacionada amb l'entitat Família.
 * Proporciona mètodes per a realitzar operacions sobre les famílies de
 * productes.
 *
 * @author Yoel
 */
public class FamiliaLogica {

    private final FamiliaDAOImpl familiaDAO;

    public FamiliaLogica() {
        this.familiaDAO = new FamiliaDAOImpl();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Afegeix una nova família després de realitzar les validacions
     * necessàries.
     *
     * @param familia La família a afegir.
     */
    public void afegirFamilia(Familia familia) {
        if (familia == null) {
            mostrarError("La família no pot ser nul·la.");
            return;
        }
        try {
            validarFamilia(familia);
            familiaDAO.afegir(familia);
            System.out.println("Família afegida correctament.");
        } catch (Exception e) {
            mostrarError("Error en afegir la família: " + e.getMessage());
        }
    }

    /**
     * Modifica una família existent després de realitzar les validacions
     * necessàries.
     *
     * @param familia La família amb dades actualitzades.
     */
    public void modificarFamilia(Familia familia) {
    if (familia == null) {
        mostrarError("La familia no puede ser nula.");
        return;
    }
    if (familia.getId() <= 0) {
        mostrarError("El ID de la familia debe ser positivo.");
        return;
    }
    try {
        validarFamilia(familia);
        familiaDAO.modificar(familia);
        System.out.println("Familia modificada correctamente.");
    } catch (Exception e) {
        mostrarError("Error al modificar la familia: " + e.getMessage());
    }
}


    /**
     * Elimina una família donat el seu ID després de realitzar les validacions
     * necessàries.
     *
     * @param id El ID de la família a eliminar.
     */
    public void eliminarFamilia(int id) {
        if (id <= 0) {
            mostrarError("El ID de la família ha de ser positiu.");
            return;
        }
        try {
            familiaDAO.delete(id);
            System.out.println("Família eliminada correctament.");
        } catch (Exception e) {
            mostrarError("Error en eliminar la família: " + e.getMessage());
        }
    }

    /**
     * Obté una família pel seu ID.
     *
     * @param id El ID de la família a obtenir.
     * @return La família corresponent o null si no es troba.
     */
    public Familia obtenirFamilia(int id) {
        if (id <= 0) {
            mostrarError("El ID de la família ha de ser positiu.");
            return null;
        }
        return familiaDAO.obtenir(id);
    }

    /**
     * Llista totes les famílies.
     *
     * @return Una llista de totes les famílies.
     */
    public List<Familia> llistarFamilias() {
        return familiaDAO.LlistarTot();
    }

    /**
     * Valida els camps de la família per evitar errors comuns.
     *
     * @param familia La família a validar.
     */
    private void validarFamilia(Familia familia) {
        // Implementar validacions aquí si és necessari
    }
}
