package logica;

import dades.ReferenciaDAOImpl;
import aplicacio.model.Referencia;

import java.util.Date;
import java.util.List;

/**
 * Clase que gestiona la lógica de negocio relacionada con la entidad Referencia.
 * Proporciona métodos para realizar operaciones sobre las referencias del almacen
 *
 * @author mario
 */
public class ReferenciaLogica {

    private final ReferenciaDAOImpl referenciaDAO;

    public ReferenciaLogica() {
        this.referenciaDAO = new ReferenciaDAOImpl();
    }

    /**
     * Agrega una nueva referencia después de realizar las validaciones necesarias.
     *
     * @param referencia La referencia a agregar.
     */
    public void afegirReferencia(Referencia referencia) {
        if (referencia == null) {
            throw new IllegalArgumentException("La referencia no puede ser nula.");
        }
        validarReferencia(referencia);
        referenciaDAO.afegir(referencia);
        System.out.println("Referencia agregada correctamente.");
    }

    /**
     * Modifica una referencia existente después de realizar las validaciones
     * necesarias.
     *
     * @param referencia La referencia con datos actualizados.
     */
    public void modificarReferencia(Referencia referencia) {
        if (referencia == null) {
            throw new IllegalArgumentException("La referencia no puede ser nula.");
        }
        if (referencia.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la referencia debe ser positivo.");
        }
        validarReferencia(referencia);
        referenciaDAO.modificar(referencia);
        System.out.println("Referencia modificada correctamente.");
    }

    /**
     * Elimina una referencia dado su ID después de realizar las validaciones
     * necesarias.
     *
     * @param id El ID de la referencia a eliminar.
     */
    public void eliminarReferencia(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la referencia debe ser positivo.");
        }
        referenciaDAO.delete(id);
    }

    /**
     * Obtiene una referencia por su ID.
     *
     * @param id El ID de la referencia a obtener.
     * @return La referencia correspondiente o null si no se encuentra.
     */
    public Referencia obtenirReferencia(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la familia debe ser positivo.");
        }
        return referenciaDAO.obtenir(id);
    }

    /**
     * Lista todas las referencia.
     *
     * @param idFamilia El ID de la familia de la que mostraremos las referencias
     * @return Una lista de todas las referencia.
     */
    public List<Referencia> llistarReferencias(int idFamilia) {
        return referenciaDAO.LlistarTot(idFamilia);
    }

    /**
     * Valida los campos de la referencia para evitar errores comunes.
     *
     * @param referencia La referencia a validar.
     */
    private void validarReferencia(Referencia referencia) {
        if (referencia.getNom() == null || referencia.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la familia no puede estar vacío.");
        }
        if (referencia.getDataAlta() == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser nula.");
        }
    }
}
