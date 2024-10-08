/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;


import aplicacio.model.Proveidor;
import dades.ProveidorDAOImpl;
import static dades.ProveidorDAOImpl.getInstance;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;



/**
 *
 * @author david
 */
public class ProveidorLogica {
    private final ProveidorDAOImpl proveidorDAO;
    
    /**
     * Constructor per instanciar el ProveidorsDAOImpl().
     */
    public ProveidorLogica() {
        this.proveidorDAO = getInstance();
    }
    
    /**
     * Afegeizx un proveidor validant i trucant als metodes corresponents.
     * 
     * @param proveidor 
     */
    public void afegirProveidor(Proveidor proveidor){
        if(proveidor == null){
            throw new IllegalArgumentException("El proveidor no pot ser nul.");
        }
        validarProveidor(proveidor);
        proveidorDAO.afegir(proveidor);
        System.out.println("Proveïdor agregat correctament.");
        
    }
    /**
     * Modifica un proveidor validant i trucant als metodes corresponents.
     * 
     * @param proveidor 
     */
    public void ModificarProveidor(Proveidor proveidor){
        if(proveidor == null){
            throw new IllegalArgumentException("El proveidor no pot ser nul.");
        }
        validarProveidor(proveidor);
        proveidorDAO.modificar(proveidor);
        System.out.println("Proveïdor Modificat correctament.");
        
    }
    /**
     * Elimina un proveidor per el CIF trucant als metodes corresponents.
     * 
     * @param CIF El cif del proveidor a eliminar.
     */
    public void EliminarProveidor(String CIF){
        if(CIF == null){
            throw new IllegalArgumentException("El CIF del proveidor no pot ser nul.");
        }
        
        proveidorDAO.delete((proveidorDAO.obtenirProvPerCIF(CIF)).getId()); //Aixo aconsegeix el cif del proveidor donat, i busca el id a la bbdd per pasar-li la id al metode eliminar.
        System.out.println("Proveïdor Modificat correctament.");
        
    }
    /**
     * Obte el proveidor per el seu CIF.
     * 
     * @param CIF El cif del proveidor a obtenir.
     */
    public void ObtenirProveidor(String CIF){
        if(CIF == null){
            throw new IllegalArgumentException("El CIF del proveidor no pot ser nul.");
        }
        
        proveidorDAO.obtenirProvPerCIF(CIF); //Aixo obte el proveidor per el seu cif.
        System.out.println("Proveïdor Modificat correctament.");
        
    }
    
    /**
     * Llista tots els proveidors..
     *
     * @return Una llista de tots els proveidors.
     */
    public List<Proveidor> llistarProveidors() {
        return proveidorDAO.LlistarTot();
    }

    /**
     * Valida el Proveidor asegurantse de que els seus camps son valids i llançant una excepcio en cas negatiu.
     * 
     * @param proveidor 
     */
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
     
     /**
      * Aquest metode verifica si un CIF es correcte
      * 
      * @param CIF El cif del proveidor
      * @return Un boolean true si es correcte false si no ho es.
      */
     private boolean ValidadorCIF(String CIF){
         
        // Expresio regex per a validar el CIF
        String regexCIF = "^[A-HJ-NP-SUVW][0-9]{7}[0-9A-J]$";
        Pattern pattern = Pattern.compile(regexCIF);
        pattern.matcher(CIF).matches();
    
         return pattern.matcher(CIF).matches();
     }
     
     public void importarCSV(File fitxer){
        try(BufferedReader br = new BufferedReader(new FileReader(fitxer))){
            String lineaCabecera = br.readLine(); //llegeix la primera linea on ha d'ahver-hi una capçelera
            String[] cabecera = lineaCabecera.split(","); // separem cada nom de la capçelera
            String linea;
            while((linea = br.readLine()) != null){
                String[] dades = linea.split(",");

                Map<String, Object> proveidorData = procesarLineaCSV(cabecera, dades);
                
                //si hi ha cif i nom el valida i llavors fará un objecte proveidor amb els camps ja siguin null o no, segons el procesarLinea
                if(validarProveidor(proveidorData)){
                    Proveidor proveidor = new Proveidor(
                        (String) proveidorData.get("CIF"),
                        (LocalDate) proveidorData.get("dataAlta"),
                        (Boolean) proveidorData.get("actiu"),
                        (String) proveidorData.get("motiuInactivitat"),
                        (String) proveidorData.get("nom"),
                        (Float) proveidorData.get("valoracio"),
                        (Integer) proveidorData.get("minimUnitats"),
                        (String) proveidorData.get("especialitat"));
                    
                    proveidorDAO.afegir(proveidor);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
     
     private Map<String, Object> procesarLineaCSV(String[] cabecera, String[] dades){
         Map<String, Object> proveidorDades = new HashMap();
         
         for(int i = 0; i < dades.length; i++){
            String clau = cabecera[i].trim().toLowerCase(); // per saber en quina capçelera estem
            String dada = dades[i].trim(); //obtenim la dada corresponent
            
            switch(clau){
                case "cif":
                    proveidorDades.put("CIF", dada.isEmpty() ? null : dada);
                    break;
                case "actiu":
                    proveidorDades.put("actiu", dada.equalsIgnoreCase("true"));
                    break;
                case "nom":
                    proveidorDades.put("nom", dada.isEmpty() ? null : dada);
                    break;
                case "minimunitats":
                    proveidorDades.put("minimUnitats", dada.isEmpty() ? null : Integer.parseInt(dada));
                    break;
                case "valoracio":
                    proveidorDades.put("valoracio", dada.isEmpty() ? null : Float.parseFloat(dada));
                    break;
                case "dataalta":
                    proveidorDades.put("dataAlta", dada.isEmpty() ? LocalDate.now() : LocalDate.parse(dada));
                    break;
            }
        }
         
         return proveidorDades;
     }
     
     private boolean validarProveidor(Map<String, Object> proveedorData) {
        // Validar los datos del proveedor antes de insertar
        String cif = (String) proveedorData.get("CIF");
        String nom = (String) proveedorData.get("nom");
        return cif != null && !cif.isEmpty() && nom != null && !nom.isEmpty();
    }
}
