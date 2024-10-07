/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;


import aplicacio.model.Proveidor;
import dades.ProveidorDAOImpl;
import static dades.ProveidorDAOImpl.getInstance;



/**
 *
 * @author david
 */
public class ProveidorLogica {
    private final ProveidorDAOImpl proveidorDAO;

    public ProveidorLogica() {
        this.proveidorDAO = getInstance();
    }
    
    public void afegirProveidor(Proveidor proveidor){
        if(proveidor == null){
            throw new IllegalArgumentException("El proveidor no pot ser nul.");
        }
        validarProveidor(proveidor);
        proveidorDAO.afegir(proveidor);
        System.out.println("Proveïdor agregat correctament.");
        
    }
     private void validarProveidor(Proveidor proveidor) {
        if (proveidor.getNom() == null || proveidor.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("El nom del proveïdor no pot estar buit.");
        }
        if (proveidor.getCIF()== null || proveidor.getCIF().trim().isEmpty()) {
            throw new IllegalArgumentException("El CIF del proveïdor no pot estar buit.");
        } 
        if (!(ValidadorCIF(proveidor.getCIF()))) {
            throw new IllegalArgumentException("El CIF del proveïdor es incorrecte.");
        }
        if (proveidor.getDataAlta() == null) {
            throw new IllegalArgumentException("La data d'alta no pot ser nula.");
        }
    }
     
     private boolean ValidadorCIF(String CIF){
         boolean cifValid=true;
         
         
         return cifValid;
     }
}
