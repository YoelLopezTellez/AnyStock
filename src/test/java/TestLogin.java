/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import aplicacio.model.TIPUSROL;
import aplicacio.model.Usuari;
import java.io.IOException;
import java.io.InputStream;
import logica.Login;
import logica.Sessio;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author reyes
 */
public class TestLogin {
    
    private Login login;
    
    //utilizamos el beforeEach para que utilice el setUp y así antes de cada test cargue la lista de usuarios
    @BeforeEach
    public void setUp() throws IOException {
        // Inicializamos el objeto login con el archivo de usuarios
        InputStream rutaUsuaris = getClass().getClassLoader().getResourceAsStream("usuaris.txt");
        assertNotNull(rutaUsuaris, "El archivo de usuarios no se pudo cargar correctamente.");
        login = new Login(rutaUsuaris);
    }
    
    //comprova que l'usuari que hem ficat existeix en el fitxer amb el not null i també que el nom sigui correcte
    @Test
    public void testAutentificacio(){
        Usuari usuari = login.autentificacio("reyes", "123");
        assertNotNull(usuari);
        assertEquals("reyes", usuari.getNomUsuari());
    }
    
    //ponemos una contraseña incorrecta a un usuario que si existe para que nos devuelva un null
    @Test
    public void testPassIncorrecta(){
        Usuari usuari = login.autentificacio("reyes", "unodostres");
        assertNull(usuari);
    }
    
    //posem un usuari que no existeix pero si una pass válida i ens ha de donar un null
    @Test
    public void testNomIncorrecte(){
        Usuari usuari = login.autentificacio("sara", "123");
        assertNull(usuari);
    }
    
    //Assegurem que el metode de iniciar sessio retorni bé l'usuari ja que ha de ser igual un de l'altre
    @Test
    public void testIniciarSesion() {
        Usuari usuario = new Usuari("reyes", "123", TIPUSROL.RESPONSABLE);
        Sessio.getInstancia().iniciarSessio(usuario);
        assertEquals(usuario, Sessio.getInstancia().getUsuari());
    }
    
    //Testejem que cridant a tancar sessió ens retorni un null, així comprovem que funciona correctament el metode
    @Test
    public void testCerrarSesion() {
        Sessio.getInstancia().tancarSessio();
        assertNull(Sessio.getInstancia().getUsuari());
    }
    
    //verifiquem que la sessio es la mateixa
    @Test
    public void testSingletonSessio(){
        Sessio sessio1 = Sessio.getInstancia();
        Sessio sessio2 = Sessio.getInstancia();
        assertSame(sessio1,sessio2);
    }
}
