/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import aplicacio.model.Proveidor;
import exceptions.BuitException;
import exceptions.CifRepetitException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import logica.ProveidorLogica;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




/**
 *
 * @author david
 */
public class TestProveidorLogica {
    
    ProveidorLogica provLogica = new ProveidorLogica();
    
    
    @Test
    public void Test_afegirProveidor(){ //fail
        System.out.println("TEST AFEGIR PROVEIDOR");
        
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
        // Proveidor amb CIF duplicat
        assertThrows(CifRepetitException.class, () -> {
        // Simula l'afegiment d'un proveidor amb un CIF ja existent
            provLogica.afegirProveidor(correctes.get(0)); // Reutilitzem un proveidor amb el mateix CIF
        });
        
        //elimina cada insercio a la base de dades despres de ferla.
        for (Proveidor p : correctes){
        provLogica.EliminarProveidor((provLogica.ObtenirProveidor(p.getCIF())).getId());
        }
        
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> { // Insanciem el IllegalArgumentException i amb assertThrow verifiquem si es crida
            provLogica.afegirProveidor(null); // Pasem un proveidor null.
        });
            
        // Verifica el missatge de la excepcio.
        assertEquals("El proveidor no pot ser nul.", e.getMessage());

        
        
        
    }

    
    @Test
    public void Test_ModificarProveidor(){
        System.out.println("TEST MODIFICAR PROVEIDOR");
        
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
    public void Test_EliminarProveidor(){ //fail
        System.out.println("TEST ELIMINAR PROVEIDOR");
        Random random = new Random();
        // Llista de proveidors correctes, els inserarem per a eliminarlos psoteriorment.
        List<Proveidor> correctes = new ArrayList<>(Arrays.asList(
        new Proveidor("B12345678", LocalDate.of(2020, 1, 10), true, null, "Proveedor 1", 4.5f, 100, "Electrónica"),
        new Proveidor("C23456789", LocalDate.of(2019, 3, 15), false, "Baja temporal", "Proveedor 2", 3.8f, 150, "Textil"),
        new Proveidor("A34567890", LocalDate.of(2021, 5, 20), true, null, "Proveedor 3", 4.7f, 200, "Alimentación")
        ));

        // CORRECTES
        System.out.println("CORRECTES:");
        for(Proveidor p : correctes){
            try{
            provLogica.afegirProveidor(p);// insertar els proveidors per eliminarlos
            }catch(BuitException e){
            }catch(CifRepetitException ex){}
                
            int id = (provLogica.ObtenirProveidor(p.getCIF())).getId(); // extreure el id dels proveidors insertats a partir del seu cif.
            assertDoesNotThrow(() -> { //sera true si no es llença ninguna excepcio.
            provLogica.EliminarProveidor(id); //eliminarlos
            });
        }    
        // INCORRECTES
        System.out.println("INCORRECTES:");
        //proveidores con id negativos
        for (int i = 1; i<100; i++){
            
            Integer n = random.nextInt(100) -100;
            System.out.println(n);
            IllegalArgumentException e1 = assertThrows(IllegalArgumentException.class, () -> { // Insanciem el IllegalArgumentException i amb assertTrue verifiquem si es crida
                provLogica.EliminarProveidor(n); 
            });
            assertEquals("El Id del proveidor no pot ser nul.", e1.getMessage());
        }
        
        // Cas 1: el id del proveidor no existeix
        IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class, () -> {
            provLogica.EliminarProveidor(1000); // Intentem eliminar un proveidor amb id inexistent.
        });
        assertEquals("Error: El proveidor con ID 1000 no existeix.", e2.getMessage());
    }
    
    
    @Test
    public void Test_ObtenirProveidor(){ //fail
        System.out.println("TEST OBTENIT PROVEIDOR");
        
        List<String> incorrectes = new ArrayList<>(Arrays.asList("","B6B00","F44444","12213V"));
        
        // CORRECTES
        Proveidor p = new Proveidor("B12345678", LocalDate.of(2020, 1, 10), true, null, "Proveedor 1", 4.5f, 100, "Electrónica");
        try{
            provLogica.afegirProveidor(p);// insertar el proveidor per OBTENILO
            }catch(BuitException e){
            }catch(CifRepetitException ex){}
            
            
            assertDoesNotThrow(() -> { //sera true si no es llença ninguna excepcio.
               provLogica.ObtenirProveidor("B12345678"); 
            });
            //obtenir id del proveidor de prova insertat a la base de dades
            int id = (provLogica.ObtenirProveidor(p.getCIF())).getId(); 
            //eliminar proveidor insertat anteriorment a la bbdd
            provLogica.EliminarProveidor(id);
        
        // INCORRECTES
        System.out.println("INCORRECTES:");
        for (String s : incorrectes){
            System.out.println(s);
            
            assertThrows(IllegalArgumentException.class, () -> {// Insanciem el IllegalArgumentException i amb assertTrue verifiquem si es crida
                provLogica.ObtenirProveidor(s); 
            });
        }
    }
    
    
    @Test
    public void Test_LlistarProveidors(){
        System.out.println("TEST LLISTAR PROVEIDORS");
        
         // Llista de proveidors correctes, els insertarem per a llistarlos i eliminarlos psoteriorment.
        List<Proveidor> provLlista = new ArrayList<>(Arrays.asList(
        new Proveidor("B12345678", LocalDate.of(2020, 1, 10), true, null, "Proveedor 1", 4.5f, 100, "Electrónica"),
        new Proveidor("C23456789", LocalDate.of(2019, 3, 15), false, "Baja temporal", "Proveedor 2", 3.8f, 150, "Textil"),
        new Proveidor("A34567890", LocalDate.of(2021, 5, 20), true, null, "Proveedor 3", 4.7f, 200, "Alimentación")
        ));

        // CORRECTES
        System.out.println("CORRECTES:");
        for(Proveidor p : provLlista){
            try{
            provLogica.afegirProveidor(p);// insertar els proveidors per llistar.
            }catch(BuitException e){
            }catch(CifRepetitException ex){}
        }     
        
        assertDoesNotThrow(() -> { //sera true si no es llença ninguna excepcio.
            provLogica.llistarProveidors(); //llistarlos
        });
         for(Proveidor p : provLlista){
           int id = (provLogica.ObtenirProveidor(p.getCIF())).getId(); 
           provLogica.EliminarProveidor(id);
        }
        
    }  
}
