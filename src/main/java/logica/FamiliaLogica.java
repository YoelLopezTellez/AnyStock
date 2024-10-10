package logica;

import dades.FamiliaDAOImpl;
import aplicacio.model.Familia;
import presentacio.Error;

import java.util.List;

/**
 * Classe que gestiona la lògica de negoci relacionada amb les famílies de
 * productes. Aquesta classe s'encarrega de les operacions com afegir,
 * modificar, eliminar i obtenir famílies.
 *
 * @author Yoel
 */
public class FamiliaLogica {

    private final FamiliaDAOImpl familiaDAO;
    private final Error errorDialog = new Error(); // Instància de Error per mostrar missatges

    public FamiliaLogica() {
        this.familiaDAO = new FamiliaDAOImpl();
    }

    /**
     * Afegeix una nova família després de realitzar les validacions
     * necessàries.
     *
     * @param familia La família a afegir.
     */
    public void afegirFamilia(Familia familia) {
        if (familia == null) {
            errorDialog.mostrarError("Error", "La família no pot ser nul·la.");
            return;
        }
        try {
            familiaDAO.afegir(familia);
            System.out.println("Família afegida correctament.");
        } catch (Exception e) {
            errorDialog.mostrarError("Error", "Error en afegir la família: " + e.getMessage());
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
            errorDialog.mostrarError("Error", "La família no pot ser nul·la.");
            return;
        }
        if (familia.getId() <= 0) {
            errorDialog.mostrarError("Error", "L'ID de la família ha de ser positiu.");
            return;
        }
        try {
            familiaDAO.modificar(familia);
            System.out.println("Família modificada correctament.");
        } catch (Exception e) {
            errorDialog.mostrarError("Error", "Error al modificar la família: " + e.getMessage());
        }
    }

    /**
     * Elimina una família donat el seu ID després de realitzar les validacions
     * necessàries.
     *
     * @param id El ID de la família a eliminar.
     * @throws Exception Si la família té referències associades.
     */
    public void eliminarFamilia(int idFamilia) throws Exception {
        // Aquí pots verificar si la família té referències.
        if (familiaDAO.tieneReferencias(idFamilia)) {
            // Llança una excepció si hi ha referències
            throw new Exception("No es pot eliminar la família perquè té referències associades.");
        }

        // Si no hi ha referències, crida al mètode d'eliminació
        familiaDAO.delete(idFamilia);
    }

    /**
     * Obté una família pel seu ID.
     *
     * @param id El ID de la família a obtenir.
     * @return La família corresponent o null si no es troba.
     */
    public Familia obtenirFamilia(int id) {
        if (id <= 0) {
            errorDialog.mostrarError("Error", "L'ID de la família ha de ser positiu.");
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

}
