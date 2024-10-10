/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import aplicacio.model.Proveidor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import logica.ProveidorLogica;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




/**
 *
 * @author david
 */
public class TestProveidorLogica {
    
    ProveidorLogica provLogica = new ProveidorLogica();
    
    public TestProveidorLogica() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    @Test
    public void Test_afegirProveidor(){
        
        
        //llista de proveidors que no son null -> CORRECTES.
        List<Proveidor> correctes = new ArrayList<>(Arrays.asList(
        new Proveidor("B12345678", LocalDate.of(2020, 1, 10), true, null, "Proveedor 1", 4.5f, 100, "Electrónica"),
        new Proveidor("C23456789", LocalDate.of(2019, 3, 15), false, "Baja temporal", "Proveedor 2", 3.8f, 150, "Textil"),
        new Proveidor("A34567890", LocalDate.of(2021, 5, 20), true, null, "Proveedor 3", 4.7f, 200, "Alimentación")
        ));
        
        // CORRECTES
        System.out.println("CORRECTES:");
        for (Proveidor p : correctes)
        {
            System.out.println(p);
            assertDoesNotThrow(() -> { //sera true si no es llença ninguna excepcio.
            provLogica.afegirProveidor(p); // Pasem un proveidor valid no null.
            });
        }
        System.out.println();
        
        // INCORRECTES
        System.out.println("INCORRECTES:");
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> { // Insanciem el IllegalArgumentException i amb assertTrue verifiquem si es crida
            provLogica.afegirProveidor(null); // Pasem un proveidor null.
        });
            
        // Verifica el missatge de la excepcio.
        assertEquals("El proveidor no pot ser nul.", e.getMessage());
        
    }
    
    @Test
    public void Test_ModificarProveidor(){
        //llista de proveidors que no son null -> CORRECTES.
        List<Proveidor> correctes = new ArrayList<>(Arrays.asList(
        new Proveidor("B12345678", LocalDate.of(2020, 1, 10), true, null, "Proveedor 1", 4.5f, 100, "Electrónica"),
        new Proveidor("C23456789", LocalDate.of(2019, 3, 15), false, "Baja temporal", "Proveedor 2", 3.8f, 150, "Textil"),
        new Proveidor("A34567890", LocalDate.of(2021, 5, 20), true, null, "Proveedor 3", 4.7f, 200, "Alimentación")
        ));
        
        // CORRECTES
        System.out.println("CORRECTES:");
        for (Proveidor p : correctes)
        {
            System.out.println(p);
            assertDoesNotThrow(() -> { //sera true si no es llença ninguna excepcio.
            provLogica.ModificarProveidor(p); // Pasem un proveidor valid no null.
            });
        }
        System.out.println();
        
        // INCORRECTES
        System.out.println("INCORRECTES:");
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> { // Insanciem el IllegalArgumentException i amb assertTrue verifiquem si es crida
            provLogica.ModificarProveidor(null); // Pasem un proveidor null.
        });
            
        // Verifica el missatge de la excepcio.
        assertEquals("El proveidor no pot ser nul.", e.getMessage());
    }
    @Test
    public void Test_EliminarProveidorr(){
        
        Random random = new Random();
       
        // CORRECTES
        System.out.println("CORRECTES:");
        for (int i = 1; i<100; i++)
        {
            Integer n = random.nextInt(100);
            System.out.println(n);
            assertDoesNotThrow(() -> { //sera true si no es llença ninguna excepcio.
            provLogica.EliminarProveidor(n); 
            });
        
        System.out.println();
        
        }
        // INCORRECTES
        System.out.println("INCORRECTES:");
        for (int i = 1; i<100; i++){
            
            Integer n = random.nextInt(100) -100;
            System.out.println(n);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> { // Insanciem el IllegalArgumentException i amb assertTrue verifiquem si es crida
            provLogica.EliminarProveidor(n); 
        });
            
            // Verifica el missatge de la excepcio.
            assertEquals("El Id del proveidor no pot ser nul.", e.getMessage());
        }
    }
    
    
    @Test
    public void Test_ObtenirProveidor(){
        //llista cifs correctes.
        List<String> correctes = new ArrayList<>(Arrays.asList(
            "B12345678",
            "C23456789",
            "A34567890",
            "B45678901",
            "C56789012",
            "A67890123",
            "B78901234",
            "C89012345",
            "A90123456",
            "B01234567",
            "B12345679",
            "C23456780",
            "A34567891",
            "B45678902",
            "C56789013",
            "A67890124",
            "B78901235",
            "C89012346",
            "A90123457",
            "B01234568"
        ));
        //llista cifs incorrectes.
        List<String> incorrectes = new ArrayList<>(Arrays.asList(
            "B12345",
            "C2345678A",
            "A345678901",
            "B456789",
            "C567890123",
            "A67890B23",
            "B78901234X",
            "  ",
            "A9012345Z",
            "B01234",
            null,
            "C234567890",
            "A34567",
            "B4567890",
            "C567890",
            "A67890123Z",
            "B78901",
            "",
            "A901234A",
            "B0123"
        ));

        // CORRECTES
        System.out.println("CORRECTES:");
        for (String s : correctes){
            System.out.println();
            assertDoesNotThrow(() -> { //sera true si no es llença ninguna excepcio.
            Proveidor p = provLogica.ObtenirProveidor(s); 
            });
            
        }
        
        // INCORRECTES
        System.out.println("INCORRECTES:");
        for (String s : incorrectes){
            System.out.println(s);
            assertThrows(IllegalArgumentException.class, () -> { // Insanciem el IllegalArgumentException i amb assertTrue verifiquem si es crida
            provLogica.ObtenirProveidor(s); 
            });
        }
    }
  
    @Test
    public void Test_ValidarProveidor(){
        //llista de proveidors CORRECTES.
        List<Proveidor> correctes = new ArrayList<>(Arrays.asList(
        new Proveidor("B12345678", LocalDate.of(2020, 1, 10), true, null, "Proveedor 1", 4.5f, 100, "Electrónica"),
        new Proveidor("C23456789", LocalDate.of(2019, 3, 15), false, "Baja temporal", "Proveedor 2", 3.8f, 150, "Textil"),
        new Proveidor("A34567890", LocalDate.of(2021, 5, 20), true, null, "Proveedor 3", 4.7f, 200, "Alimentación")
        ));
        //llista de proveidors INCORRECTES.
        List<Proveidor> incorrectes = new ArrayList<>(Arrays.asList(
        new Proveidor("Z10345678", LocalDate.of(2020, 1, 10), true, null, "Proveedor 1", 4.5f, 100, "Electrónica"),
        new Proveidor("C23456789",null, false, "Baja temporal", "Proveedor 2", 3.8f, 150, "Textil"),
        new Proveidor("", LocalDate.of(2021, 5, 20), true, null, "Proveedor 3", 4.7f, 200, "Alimentación"),
        new Proveidor(null, LocalDate.of(2012, 11, 13), true, null, "Proveedor 3", 4.7f, 200, "Construccion")
        ));    
    }
    
    @Test
    public void Test_ValidarProveidorMap(){
        
    }
    
    @Test
    public void Test_ProcesarLineaCSVp(){
        
    }
    
    @Test
    public void Test_ImportarCSV(){
        
    }
    
    @Test
    public void Test_ExportarCSV(){
        
    }
    
        @Test
    public void Test_ValidadorCIF(){
        
    }
    
}
