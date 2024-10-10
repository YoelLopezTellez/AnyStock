/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;


import aplicacio.model.Proveidor;
import dades.ProveidorDAOImpl;
import static dades.ProveidorDAOImpl.getInstance;
import exceptions.BuitException;
import exceptions.CifRepetitException;
import exceptions.DataInvalidaException;
import exceptions.FormatInvalidException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
     * Afegeix un proveidor validant i trucant als metodes corresponents.
     * 
     * @param proveidor 
     */
    public void afegirProveidor(Proveidor proveidor) throws CifRepetitException,BuitException {
        if(proveidor == null){
            throw new IllegalArgumentException("El proveidor no pot ser nul.");
        }
        if(proveidorDAO.existeixCIF(proveidor.getCIF())){
            throw new CifRepetitException("CIF ja existeix " + proveidor.getCIF());
        }
        proveidorDAO.afegir(proveidor);
        System.out.println("Proveïdor agregat correctament.");
        
    }
    /**
     * Modifica un proveidor validant i trucant als metodes corresponents.
     * 
     * @param proveidor 
     */
    public void ModificarProveidor(Proveidor proveidor) throws Exception,BuitException {
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
     * @param id El cif del proveidor a eliminar.
     */
    public void EliminarProveidor(int id){
        if(id <0){
            throw new IllegalArgumentException("El Id del proveidor no pot ser nul.");
        }
       
        proveidorDAO.delete(id); //Aixo passa id al metode eliminar.
    }
    /**
     * Obte el proveidor per el seu CIF.
     * 
     * @param CIF El cif del proveidor a obtenir.
     * @return Proveidor
     */
    public Proveidor ObtenirProveidor(String CIF){
        Proveidor p = proveidorDAO.obtenirProvPerCIF(CIF); //Aixo obte el proveidor per el seu cif.
        
        
        try{
            validarProveidor(p);
        }catch(BuitException e){
            BuitException("Proveidor no obtingut correctament");
        }
        System.out.println("Proveïdor Obtingut correctament.");
        return p;
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
     private void validarProveidor(Proveidor proveidor) throws BuitException {
        if(proveidor.getCIF() == null || proveidor.getCIF().trim().isEmpty() || proveidor.getNom() == null || proveidor.getNom().trim().isEmpty()){
            throw new BuitException("CIF i nom no poden ser camps buit.");
        }
        if (proveidor.getDataAlta() == null) {
            throw new IllegalArgumentException("La data d'alta no pot ser nula.");
        }
    }
     
     public void ExportarCSV(File fitxer){
        //Llista de proveidors a exportar en array de strings per a csv
        List<String> provExpCsv =new ArrayList<String>();
        String cabecera ="cif,dataalta,actiu,motiuinactivitat,nom,valoracio,minimunitats,especialitat";
        provExpCsv.add(cabecera);
        //pasar els proveidors a la llista de strings de proveidors.
        for(Proveidor p : llistarProveidors()){
            String motiuInactivitatNull = ( p.getMotiuInactivitat()==null || p.getMotiuInactivitat().isBlank()) ? null : p.getMotiuInactivitat() ;
            //Posa en un array de strings tots els atributs del proveidor.
            String provString = p.getCIF()+","+p.getDataAlta().toString()+","+p.isActiu()+","+motiuInactivitatNull+","+p.getNom()+","+p.getValoracio()+","+p.getMinimUnitats()+","+p.getEspecialitat();
            
            provExpCsv.add(provString);
        }
        
        //Amb BufferedWriter escrivim l'arxiu
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fitxer))) {
            //Escriu les linies en format CSV
            for (String linea : provExpCsv) {
                
                // Escriu la línea en el fitxer
                writer.write(linea);
                // Afegaix un salt de linea despres de cada linea de proveidor format CSV
                writer.newLine();
            }

            System.out.println("Arxiu CSV exportat amb éxit.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al exportar el arxiu CSV.");
        }
     }
     
     public void importarCSV(File fitxer) throws FormatInvalidException, CifRepetitException, DataInvalidaException, BuitException{
        try(BufferedReader br = new BufferedReader(new FileReader(fitxer))){
            String lineaCabecera = br.readLine(); //llegeix la primera linea on ha d'ahver-hi una capçelera
            String[] cabecera = lineaCabecera.split(","); // separem cada nom de la capçelera
            String linea;
            
            int numLinia = 2; //comencem en 2 perque la linea 1 es la capçalera, utilitzem aixó per poder indicar desprès en quina linea ha saltat l'error
            
            while((linea = br.readLine()) != null){
                String[] dades = linea.split(",");

                Map<String, Object> proveidorData = procesarLineaCSV(cabecera, dades, numLinia);
                
                //si hi ha cif i nom el valida i llavors fará un objecte proveidor amb els camps ja siguin null o no, segons el procesarLinea
                if(validarProveidor(proveidorData,numLinia)){
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
                numLinia++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch(FormatInvalidException e){
            throw new FormatInvalidException(e.getMessage());
        }catch(CifRepetitException e){
            throw new CifRepetitException(e.getMessage());
        }catch(DataInvalidaException e){
            throw new DataInvalidaException(e.getMessage());
        }catch(BuitException e){
            throw new BuitException(e.getMessage());
        }
    }
     
     private Map<String, Object> procesarLineaCSV(String[] cabecera, String[] dades, int linia) throws FormatInvalidException, DataInvalidaException{
         Map<String, Object> proveidorDades = new HashMap();
         
         for(int i = 0; i < dades.length; i++){
            String clau = cabecera[i].trim().toLowerCase(); // per saber en quina capçelera estem
            String dada = dades[i].trim(); //obtenim la dada corresponent
            
            try{
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
                    case "motiuinactivitat":
                        proveidorDades.put("motiuInactivitat", proveidorDades.get("actiu").equals(true) ? "" :dada); //cuando sea actiu no posara res al motiu al importar-lo pero si al exportarlo
                        break;
                    case "minimunitats":
                        proveidorDades.put("minimUnitats", dada.isEmpty() ? null : validarInt(dada));
                        break;
                    case "valoracio":
                        proveidorDades.put("valoracio", dada.isEmpty() ? null : validarFloat(dada));
                        break;
                    case "dataalta":
                        proveidorDades.put("dataAlta", dada.isEmpty() ? LocalDate.now() : validarData(dada));
                        break;
                }
            }catch(FormatInvalidException e){
                proveidorDades.put(clau, null);//possem el valor incorrecte a null
                throw new FormatInvalidException("Error de format en el camp " + clau + " amb la dada " +dada + " en la linia "+linia);
            }catch(DataInvalidaException e){
                proveidorDades.put("dataAlta", LocalDate.now());//possem la data incorrecte a la data d'avui
                throw new DataInvalidaException("Data invàlida en la linia " + linia);
            }
        }
         
         return proveidorDades;
     }
     
     private boolean validarProveidor(Map<String, Object> proveedorData, int linia) throws CifRepetitException,BuitException {
        // Validar los datos del proveedor antes de insertar
        String cif = (String) proveedorData.get("CIF");
        String nom = (String) proveedorData.get("nom");
        
        if(cif == null || cif.isEmpty() || nom == null || nom.isEmpty()){
            throw new BuitException("CIF i nom no poden ser camps buits en la linia " + linia);
        }
        if(proveidorDAO.existeixCIF(cif)){
            throw new CifRepetitException("CIF ja existeix " + cif + " en la linia " + linia);
        }
        return true;
    }
     
     private Float validarFloat(String valor) throws FormatInvalidException{
         try{
             return Float.parseFloat(valor);
         }catch(NumberFormatException e){
             throw new FormatInvalidException("Valor float invàlid: " + valor);
         }
     }
     
     private LocalDate validarData(String valor) throws DataInvalidaException{
         try{
             return LocalDate.parse(valor);
         }catch(NumberFormatException e){
             throw new DataInvalidaException("Data invàlida: " + valor + " es possarà la data d'avui");
         }
     }
     
     private Integer validarInt(String valor) throws FormatInvalidException{
         try{
             return Integer.parseInt(valor);
         }catch(NumberFormatException e){
             throw new FormatInvalidException("Valor enter invàlid: " + valor);
         }
     }

    private void BuitException(String proveidor_No_sa_obtingut_correctament) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
