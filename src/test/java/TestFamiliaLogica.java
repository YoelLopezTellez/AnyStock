import dades.FamiliaDAOImpl;
import aplicacio.model.Familia;
import logica.FamiliaLogica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class TestFamiliaLogica {

    private FamiliaLogica familiaLogica;

    @BeforeEach
    public void setUp() {
        familiaLogica = new FamiliaLogica();
        new FamiliaDAOImpl();
        // Aquí puedes inicializar tu mock o datos de prueba
    }

    @Test
    public void testAfegeixFamilia_Nulla() {
        familiaLogica.afegirFamilia(null);
        // Aquí podrías verificar que se muestre el mensaje de error apropiado
    }

    @Test
    public void testAfegeixFamilia_Exito() {
        Familia familia = new Familia(0, LocalDate.now(), "Observacions", "Nom", "Descripcio", 1);
        familiaLogica.afegirFamilia(familia);
        // Aquí podrías verificar que la familia fue añadida correctamente en el DAO
    }

    @Test
    public void testModificarFamilia_Nulla() {
        familiaLogica.modificarFamilia(null);
        // Verificar el mensaje de error
    }

    @Test
    public void testModificarFamilia_IdInvalido() {
        Familia familia = new Familia(-1, LocalDate.now(), "Observacions", "Nom", "Descripcio", 1);
        familiaLogica.modificarFamilia(familia);
        // Verificar el mensaje de error
    }

    @Test
    public void testModificarFamilia_Exito() {
        Familia familia = new Familia(1, LocalDate.now(), "Observacions", "Nom", "Descripcio", 1);
        familiaLogica.modificarFamilia(familia);
        // Verificar que la familia fue modificada correctamente en el DAO
    }

    @Test
    public void testEliminarFamilia_ConReferencias() {
        Exception exception = assertThrows(Exception.class, () -> {
            familiaLogica.eliminarFamilia(1); // Suponiendo que la familia con ID 1 tiene referencias
        });
        assertEquals("No es pot eliminar la família perquè té referències associades.", exception.getMessage());
    }

    @Test
    public void testEliminarFamilia_Exito() throws Exception {
        // Primero, deberías asegurarte de que la familia existe sin referencias
        familiaLogica.eliminarFamilia(2); // Cambia por un ID válido
        // Verificar que la familia fue eliminada correctamente en el DAO
    }

    @Test
    public void testObtenirFamilia_IdInvalido() {
        Familia familia = familiaLogica.obtenirFamilia(-1);
        assertNull(familia); // Debería retornar null
        // Verificar el mensaje de error
    }

    @Test
    public void testObtenirFamilia_Exito() {
        Familia familia = familiaLogica.obtenirFamilia(1); // Cambia por un ID válido
        assertNotNull(familia); // Debería retornar una familia existente
    }

    @Test
    public void testLlistarFamilias() {
        List<Familia> familias = familiaLogica.llistarFamilias();
        assertNotNull(familias); // Debería retornar una lista no nula
        // Aquí puedes hacer más verificaciones sobre el contenido de la lista
    }
}
