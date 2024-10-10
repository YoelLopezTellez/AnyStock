/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import aplicacio.model.Referencia;
import dades.ReferenciaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import logica.ReferenciaLogica;

import static org.junit.jupiter.api.Assertions.*;

class TestReferenciaLogica{ 

    // Clase interna para simular el comportamiento del DAO
    static class TestReferenciaDAOImpl extends ReferenciaDAOImpl {
        private final List<Referencia> referencias = new ArrayList<>();
        private int nextId = 1;

        @Override
        public void afegir(Referencia referencia) {
            referencia.setId(nextId++);
            referencias.add(referencia);
        }

        @Override
        public void modificar(Referencia referencia) {
            for (int i = 0; i < referencias.size(); i++) {
                if (referencias.get(i).getId() == referencia.getId()) {
                    referencias.set(i, referencia);
                    return;
                }
            }
            throw new IllegalArgumentException("Referencia no encontrada para modificar.");
        }

        @Override
        public void delete(int id) {
            referencias.removeIf(referencia -> referencia.getId() == id);
        }

        @Override
        public Referencia obtenir(int id) {
            return referencias.stream()
                    .filter(referencia -> referencia.getId() == id)
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Referencia> LlistarTot(int idFamilia) {
            return new ArrayList<>(referencias);
        }
    }

    private ReferenciaLogica referenciaLogica;

    @BeforeEach
    void setUp() {
        // Usa el DAO de prueba en lugar del real
        referenciaLogica = new ReferenciaLogica(new TestReferenciaDAOImpl());
    }

    @Test
    void testAfegirReferencia_ReferencaNull_ThrowsException() {
        // Verifica que se lanza una excepción si la referencia es nula
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            referenciaLogica.afegirReferencia(null);
        });
        assertEquals("La referencia no puede ser nula.", exception.getMessage());
    }

    @Test
    void testAfegirReferencia_ReferencaValida_CallsDAO() {
        Referencia referencia = new Referencia();
        referencia.setNom("Referencia 1"); // Ajusta según los campos de Referencia

        referenciaLogica.afegirReferencia(referencia);
        // Verifica que se haya agregado correctamente
        assertEquals(1, referencia.getId());
    }

    @Test
    void testModificarReferencia_ReferencaNull_ThrowsException() {
        // Verifica que se lanza una excepción si la referencia es nula
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            referenciaLogica.modificarReferencia(null);
        });
        assertEquals("La referencia no puede ser nula.", exception.getMessage());
    }

    @Test
    void testModificarReferencia_IdNoPositivo_ThrowsException() {
        Referencia referencia = new Referencia();
        referencia.setId(-1); // Asigna un ID negativo para la prueba

        // Verifica que se lanza una excepción si el ID no es positivo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            referenciaLogica.modificarReferencia(referencia);
        });
        assertEquals("El ID de la referencia debe ser positivo.", exception.getMessage());
    }

    @Test
    void testModificarReferencia_ValidReference_CallsDAO() throws Exception {
        Referencia referencia = new Referencia();
        referencia.setNom("Referencia 1");
        referenciaLogica.afegirReferencia(referencia);
        
        // Ahora modificamos la referencia
        referencia.setNom("Referencia 1 Modificada");
        referenciaLogica.modificarReferencia(referencia);

        // Verificamos que el cambio se ha realizado
        Referencia modified = referenciaLogica.obtenirReferencia(referencia.getId());
        assertEquals("Referencia 1 Modificada", modified.getNom());
    }

    @Test
    void testEliminarReferencia_IdNoPositivo_ThrowsException() {
        // Verifica que se lanza una excepción si el ID no es positivo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            referenciaLogica.eliminarReferencia(-1);
        });
        assertEquals("El ID de la referencia debe ser positivo.", exception.getMessage());
    }

    @Test
    void testEliminarReferencia_ValidId_CallsDAO() {
        Referencia referencia = new Referencia();
        referencia.setNom("Referencia 1");
        referenciaLogica.afegirReferencia(referencia);

        // Eliminar la referencia
        referenciaLogica.eliminarReferencia(referencia.getId());

        // Verificar que ya no existe
        assertNull(referenciaLogica.obtenirReferencia(referencia.getId()));
    }

    @Test
    void testObtenirReferencia_IdNoPositivo_ThrowsException() {
        // Verifica que se lanza una excepción si el ID no es positivo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            referenciaLogica.obtenirReferencia(-1);
        });
        assertEquals("El ID de la referencia debe ser positivo.", exception.getMessage());
    }

    @Test
    void testObtenirReferencia_ValidId_ReturnsReference() {
        Referencia referencia = new Referencia();
        referencia.setNom("Referencia 1");
        referenciaLogica.afegirReferencia(referencia);

        Referencia result = referenciaLogica.obtenirReferencia(referencia.getId());
        assertNotNull(result);
        assertEquals(referencia.getNom(), result.getNom());
    }

    @Test
    void testLlistarReferencias_CallsDAO() {
        referenciaLogica.afegirReferencia(new Referencia());
        referenciaLogica.afegirReferencia(new Referencia());

        List<Referencia> result = referenciaLogica.llistarReferencias(1);
        assertEquals(2, result.size());
    }
}