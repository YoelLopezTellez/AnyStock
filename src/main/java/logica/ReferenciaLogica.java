package logica;

import dades.ReferenciaDAOImpl;
import aplicacio.model.Referencia;
import java.time.LocalDate;

import java.util.Date;
import java.util.List;

/**
 * Classe que gestiona la lògica de negoci relacionada amb l'entitat Referència.
 * Proporciona mètodes per a realitzar operacions sobre les referències de
 * l'almacen.
 *
 * @author mario
 */
public class ReferenciaLogica {

    private final ReferenciaDAOImpl referenciaDAO;

    /**
     * Constructor de la classe ReferenciaLogica. Inicialitza el DAO per a
     * gestionar les operacions de referència.
     */
    public ReferenciaLogica() {
        this.referenciaDAO = new ReferenciaDAOImpl();
    }

    /**
     * Constructor de la classe ReferenciaLogica. Inicialitza el DAO per a
     * gestionar les operacions de referència.
     * Este Constructor se utiliza para los test.
     */
    public ReferenciaLogica(ReferenciaDAOImpl referenciaDAO) {
        this.referenciaDAO = referenciaDAO;
    }

    /**
     * Afegeix una nova referència després de realitzar les validacions
     * necessàries.
     *
     * @param referencia La referència a afegir.
     * @throws IllegalArgumentException Si la referència és null.
     */
    public void afegirReferencia(Referencia referencia) {
        if (referencia == null) {
            throw new IllegalArgumentException("La referencia no puede ser nula.");
        }
        if(referencia.getQuantitat()<=10){
            referencia.setUltimaDataAlarma(LocalDate.now());
            vegadesAlarma(referencia);
        }
        referenciaDAO.afegir(referencia);
        System.out.println("Referencia agregada correctamente.");
    }

    /**
     * Modifica una referència existent després de realitzar les validacions
     * necessàries.
     *
     * @param referencia La referència amb dades actualitzades.
     * @throws IllegalArgumentException Si la referència és null o el seu ID no
     * és positiu.
     * @throws Exception Si ocorre un error en la modificació.
     */
    public void modificarReferencia(Referencia referencia) throws Exception {
        if (referencia == null) {
            throw new IllegalArgumentException("La referencia no puede ser nula.");
        } else if (referencia.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la referencia debe ser positivo.");
        } else {
            if(referencia.getQuantitat()<=10){
                referencia.setUltimaDataAlarma(LocalDate.now());
                vegadesAlarma(referencia);
            }
            try {
                referenciaDAO.modificar(referencia);
                System.out.println("Referencia modificada correctamente.");
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * Elimina una referència donat el seu ID després de realitzar les
     * validacions necessàries.
     *
     * @param id L'ID de la referència a eliminar.
     * @throws IllegalArgumentException Si l'ID no és positiu.
     */
    public void eliminarReferencia(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la referencia debe ser positivo.");
        }
        referenciaDAO.delete(id);
    }

    /**
     * Obtén una referència pel seu ID.
     *
     * @param id L'ID de la referència a obtenir.
     * @return La referència corresponent o null si no es troba.
     * @throws IllegalArgumentException Si l'ID no és positiu.
     */
    public Referencia obtenirReferencia(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la referencia debe ser positivo.");
        }
        return referenciaDAO.obtenir(id);
    }

    /**
     * Llista totes les referències. Per defecte mostrarem totes les referències
     * de l'almacen.
     *
     * @param idFamilia L'ID de la família de la qual mostrarem les referències.
     * @return Una llista de totes les referències.
     */
    public List<Referencia> llistarReferencias(int idFamilia) {
        return referenciaDAO.LlistarTot(idFamilia);
    }
    
    /**
     * Modifica incrementant en 1 les vegades que ha saltat l'alarma d'estoc i la data de la ultima vegada que va saltar.
     * 
     * @param ref es la referencia de la qual es vol modificar les vegades alarma.
     */
    public void vegadesAlarma(Referencia ref){
        
        Referencia referenciaModificada = ref;
        referenciaModificada.setVegadesAlarma(ref.getVegadesAlarma()+1);
        referenciaModificada.setUltimaDataAlarma(LocalDate.now());
        try{
            referenciaDAO.modificar(referenciaModificada);
        }catch(Exception e){
           
        }
    }
}
