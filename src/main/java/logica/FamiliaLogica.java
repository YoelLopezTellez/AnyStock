package logica;

import dades.FamiliaDAOImpl;
import aplicacio.model.Familia;

import java.util.List;

/**
 * Clase que gestiona la lógica de negocio relacionada con la entidad Familia.
 * Proporciona métodos para realizar operaciones sobre las familias de
 * productos.
 *
 * @author Yoel
 */
public class FamiliaLogica {

    private final FamiliaDAOImpl familiaDAO;

    public FamiliaLogica() {
        this.familiaDAO = new FamiliaDAOImpl();
    }

    /**
     * Agrega una nueva familia después de realizar las validaciones necesarias.
     *
     * @param familia La familia a agregar.
     */
    public void afegirFamilia(Familia familia) {
        if (familia == null) {
            throw new IllegalArgumentException("La familia no puede ser nula.");
        }
        validarFamilia(familia);
        familiaDAO.afegir(familia);
        System.out.println("Familia agregada correctamente.");
    }

    /**
     * Modifica una familia existente después de realizar las validaciones
     * necesarias.
     *
     * @param familia La familia con datos actualizados.
     */
    public void modificarFamilia(Familia familia) {
        if (familia == null) {
            throw new IllegalArgumentException("La familia no puede ser nula.");
        }
        if (familia.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la familia debe ser positivo.");
        }
        validarFamilia(familia);
        familiaDAO.modificar(familia);
        System.out.println("Familia modificada correctamente.");
    }

    /**
     * Elimina una familia dado su ID después de realizar las validaciones
     * necesarias.
     *
     * @param id El ID de la familia a eliminar.
     */
    public void eliminarFamilia(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la familia debe ser positivo.");
        }
        familiaDAO.delete(id);
        System.out.println("Familia eliminada correctamente.");
    }

    /**
     * Obtiene una familia por su ID.
     *
     * @param id El ID de la familia a obtener.
     * @return La familia correspondiente o null si no se encuentra.
     */
    public Familia obtenirFamilia(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la familia debe ser positivo.");
        }
        return familiaDAO.obtenir(id);
    }

    /**
     * Lista todas las familias.
     *
     * @return Una lista de todas las familias.
     */
    public List<Familia> llistarFamilias() {
        return familiaDAO.LlistarTot();
    }

    /**
     * Valida los campos de la familia para evitar errores comunes.
     *
     * @param familia La familia a validar.
     */
    private void validarFamilia(Familia familia) {
        if (familia.getNom() == null || familia.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la familia no puede estar vacío.");
        }
        if (familia.getDescripcio() == null || familia.getDescripcio().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la familia no puede estar vacía.");
        }
        if (familia.getDataAlta() == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser nula.");
        }
    }
}
