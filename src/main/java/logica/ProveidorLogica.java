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
import java.util.function.BiConsumer;
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
        Proveidor p = new Proveidor();
       
        try{
        p = proveidorDAO.obtenirProvPerCIF(CIF); //Aixo obte el proveidor per el seu cif.
        }catch(Exception e){
        }
        
        try{
            validarProveidor(p);
        }catch(BuitException e){
            BuitException("Proveidor no obtingut correctament");
        }
        System.out.println("Proveïdor Obtingut correctament.");
        return p;
    }
    
    /**
     * Obte el proveidor per la seva Id.
     * @param id
     * @return 
     */
    public Proveidor ObtenirProveidorPerId(int id){
        Proveidor p = proveidorDAO.obtenir(id); //Aixo obte el proveidor per el seu cif.
        
        
        try{
            validarProveidor(p);
        }catch(BuitException e){
            BuitException("Proveidor no obtingut correctament");
        }
        System.out.println("Proveïdor Obtingut correctament.");
        return p;
    }
    
    /**
     * Llista tots els proveidors.
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
        if(proveidor != null){
            if(proveidor.getCIF() == null || proveidor.getCIF().trim().isEmpty() || proveidor.getNom() == null || proveidor.getNom().trim().isEmpty()){
                throw new BuitException("CIF i nom no poden ser camps buit.");
            }
            if (proveidor.getDataAlta() == null) {
                throw new IllegalArgumentException("La data d'alta no pot ser nula.");
            }
        }else{
            throw new IllegalArgumentException("proveidor proporcionat es nul");
        }
    }
     
     /**
      * Exporta la lista de proveïdors a un fitxer CSV.
      * Aquest mètode crea un fitxer CSV amb una capçalera i les dades de cada proveïdor.
      * Si un proveïdor té el motiu d'inactivitat com a null o buit, s'escriurà com a null al CSV.
      * Les dades es separen per comes (,) i s'escriuen línia per línia en el fitxer especificat.
      * @param fitxer 
      */
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
     
     /**
      * Importa proveïdors des d'un fitxer CSV.
      * 
      * Aquest mètode llegeix un fitxer CSV amb les dades dels proveïdors i crea una llista de
      * proveïdors. Si un proveïdor amb el mateix CIF ja existeix a la base de dades, es crida a 
      * la funció de consum de bi-consumer amb el nou proveidor i el
      * proveidor existent.
      * 
      * @param fitxer El fitxer CSV que conté les dades dels proveïdors a importar.
      * @param CIFRepetit Una funció que accepta dos proveïdors: el nou proveidor del fitxer
 *                   i el proveidor existent amb el mateix CIF, per manejar el cas
 *                   en què es detecta un CIF repetit.
      * @return un llistat dels proveïdors
      * @throws FormatInvalidException si el fitxer hi te algún format incorrecte
      * @throws DataInvalidaException si el fitxer hi te una data incorrecte
      * @throws BuitException si en el fitxer hi ha un cif buit
      */
     public List<Proveidor> importarCSV(File fitxer, BiConsumer<Proveidor,Proveidor> CIFRepetit) throws FormatInvalidException, DataInvalidaException, BuitException {
        List<Proveidor> proveidors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fitxer))) {
            String lineaCabecera = br.readLine(); //llegeix la primera linea on ha d'ahver-hi una capçelera
            String[] cabecera = lineaCabecera.split(","); // separem cada nom de la capçelera
            String linea;

            int numLinia = 2; //comencem en 2 perque la linea 1 es la capçalera, utilitzem aixó per poder indicar desprès en quina linea ha saltat l'error

            while ((linea = br.readLine()) != null) {
                String[] dades = linea.split(",");

                Map<String, Object> proveidorData = procesarLineaCSV(cabecera, dades, numLinia);

                //fem un try aquí amb els seus respectius catch perque així llegeixi cada linea i no interrompi tota la lectura del fitxer
                try {
                    //si hi ha cif i nom el valida i llavors fará un objecte proveidor amb els camps ja siguin null o no, segons el procesarLinea
                    if (validarProveidor(proveidorData, numLinia)) {
                        Proveidor proveidor = new Proveidor(
                                (String) proveidorData.get("CIF"),
                                (LocalDate) proveidorData.get("dataAlta"),
                                (Boolean) proveidorData.get("actiu"),
                                (String) proveidorData.get("motiuInactivitat"),
                                (String) proveidorData.get("nom"),
                                (Float) proveidorData.get("valoracio"),
                                (Integer) proveidorData.get("minimUnitats"),
                                (String) proveidorData.get("especialitat"));
                        Proveidor proveidorRepetit = proveidorDAO.obtenirProvPerCIF(proveidor.getCIF());
                        if(proveidorRepetit != null)
                            CIFRepetit.accept(proveidor, proveidorRepetit);
                        else{
                        proveidors.add(proveidor); //afegeix a la llista per poder-li passar al controlador
                        proveidorDAO.afegir(proveidor); //afegeix a la BBDD
                        }
                    }
                } catch (BuitException e) {
                    throw new BuitException(e.getMessage());
                }
                numLinia++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataInvalidaException e) {
            throw new DataInvalidaException(e.getMessage());
        } catch (FormatInvalidException e) {
            throw new FormatInvalidException(e.getMessage());
        }
        return proveidors;
    }
     
     /**
      * Procesa una línia del fitxer CSV per a extreure les dades del proveidor.
      * 
      * Aquest mètode pren una línia del fitxer CSV i la divideix en les dades corresponents
      * a cada capçalera. Les dades es validen i es guarden en un mapa per a ser utilitzades
      * més tard en la creació de l'objecte
      * @param cabecera un array de Strings que conté els noms de les capçaleres del fitxer csv
      * @param dades Un array de Strings que conté les dades corresponents a les capçaleres de la línia actual
      * @param linia El número de línia actual del fitxer CSV, utilitzat per proporcionar 
      *         informació d'error en cas que es detectin dades invàlides
      * @return Un mapa que associa les capçaleres a les dades corresponents del proveidor
      * @throws FormatInvalidException Si es detecta un error de format en alguna dada.
      * @throws DataInvalidaException  Si la data proporcionada és invàlida.
      */
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
     
     /**
      * Valida les dades del proveïdor abans de ser insertada a la BBDD
      * 
      * El metòde comprova que tant el cif com el nom no siguin nulls ni estiguin buits, sino llançarà una excepció
      * @param proveedorData un mapa que conté les dades a verificar
      * @param linia El número de línia actual del fitxer CSV, utilitzat per proporcionar 
      *         informació d'error en cas que es detectin dades invàlides
      * @return true si les dades son correctes
      * @throws BuitException Si el camp del nom o CIF están buits
      */
     private boolean validarProveidor(Map<String, Object> proveedorData, int linia) throws BuitException {
        // Validar los datos del proveedor antes de insertar
        String cif = (String) proveedorData.get("CIF");
        String nom = (String) proveedorData.get("nom");
        
        if(cif == null || cif.isEmpty() || nom == null || nom.isEmpty()){
            throw new BuitException("CIF i nom no poden ser camps buits en la linia " + linia);
        }
        if(proveidorDAO.existeixCIF(cif)){
            return false;
        }
        return true;
    }
     
     /**
      * Valida i converteix un String a float, si la conversió falla llença una excepció
      * @param valor el String a convertir
      * @return el nombre float convertit
      * @throws FormatInvalidException si no es pot convertir a float
      */
     private Float validarFloat(String valor) throws FormatInvalidException{
         try{
             return Float.parseFloat(valor);
         }catch(NumberFormatException e){
             throw new FormatInvalidException("Valor float invàlid: " + valor);
         }
     }
     
     /**
      * Valida i converteix un String a LocalDate, si la conversió falla llença una excepció
      * @param valor el String a convertir
      * @return la data convertida
      * @throws DataInvalidaException si no es pot convertir a LocalDate
      */
     private LocalDate validarData(String valor) throws DataInvalidaException{
         try{
             return LocalDate.parse(valor);
         }catch(NumberFormatException e){
             throw new DataInvalidaException("Data invàlida: " + valor + " es possarà la data d'avui");
         }
     }
     
     /**
      * Valida i converteix un String a integer, si la conversió falla llença una excepció
      * @param valor el String a convertir
      * @return el nombre integer convertit
      * @throws FormatInvalidException si no es pot convertir a integer
      */
     private Integer validarInt(String valor) throws FormatInvalidException{
         try{
             return Integer.parseInt(valor);
         }catch(NumberFormatException e){
             throw new FormatInvalidException("Valor enter invàlid: " + valor);
         }
    }
    
    /**
     * Es un metode que llença una excepcio tipus UnsupportedOperationException
     * 
     * @param proveidor_No_sa_obtingut_correctament 
     */
    private void BuitException(String proveidor_No_sa_obtingut_correctament) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
