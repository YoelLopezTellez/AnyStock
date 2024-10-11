/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import aplicacio.model.Proveidor;
import aplicacio.model.TIPUSROL;
import aplicacio.model.Usuari;
import exceptions.BuitException;
import exceptions.DataInvalidaException;
import exceptions.FormatInvalidException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logica.ProveidorLogica;
import logica.Sessio;

/**
 * FXML Controller class
 * Controlador de la pantalla menu principal on hi han 4 botons, segons el rol
 * de l'usuari mostrará el botó importar o no
 * @author Reyes
 */
public class MenuController implements Initializable {

    @FXML
    private Button Btn_Fam;
    
    @FXML
    private Button Btn_Prov;
    
    @FXML
    private Button Btn_Imp;
    
    @FXML
    private Button Btn_Exp;
    
    @FXML
    private Button Btn_Tancar;
    
    private Usuari usuari;
    private Error error;
    
    /**
     * Inicialitza el controlador i la vista, se executa automàticament, carrega
     * l'usuari que actualment hi ha en la sessio i mostra els botons segons
     * el seu rol.
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //carguem l'usuari que tenim guardat en sessio
        usuari = Sessio.getInstancia().getUsuari();
        //si l'usuari es del rol vendedor, oculta el botó importar
        if(usuari.getTipusRol() == TIPUSROL.VENDEDOR)
            Btn_Imp.setVisible(false);
    }    
    
    /**
     * Gestiona el clic al botó que el que fa es canviar de escena a consultar familia
     * @param event un event d'acció quan l'usuari prem el botó
     * @throws IOException 
     */
    @FXML
    private void onBtn_Fam_Action(ActionEvent event) throws IOException{
        CanviPantalla.canviarPantalla(Btn_Fam.getScene(), "/cat/copernic/projecte_grup4/ConsultaFamilia.fxml");
    }
    
    /**
     * Gestiona el clic al botó que el que fa es canviar de escena a consultar proveidor
     * @param event un event d'acció quan l'usuari prem el botó
     * @throws IOException 
     */
    @FXML
    private void onBtn_Prov_Action(ActionEvent event) throws IOException{
        CanviPantalla.canviarPantalla(Btn_Fam.getScene(), "/cat/copernic/projecte_grup4/ConsultaProveidor.fxml");
    }
    
    /**
     * Gestiona el clic al botó d'importar. S'obre una nova finestra de diàleg
     * per poder seleccionar un fitxer csv, apart filtra perque tan sols es pugui
     * veure fitxers csv. També crida a la lógica per poder importar amb el métode
     * importarCSV i si hi ha algún error, saltarà un pop-up informant de l'error
     * @param event un event d'acció quan l'usuari prem el botó
     */
    @FXML
    private void onBtn_Imp_Action (ActionEvent event){
        //creem la classe
        FileChooser seleccionador = new FileChooser();
        
        //fer un filtre perque tan sols mostri fitxers csv
        FileChooser.ExtensionFilter filtre = new FileChooser.ExtensionFilter("Fitxers CSV (*.csv)", "*.csv");
        seleccionador.getExtensionFilters().add(filtre);
        
        //fiquem un titol
        seleccionador.setTitle("Seleccioni un fitxer CSV");
        
        //obtenim el Stage actual on está el botó en comptes de crear un Stage nou utilitzem la referència d'aquest botó
        Stage escenari = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        //obrim el diàleg per la selecció del fitxer
        File fitxerSeleccionat = seleccionador.showOpenDialog(escenari);
        
        if(fitxerSeleccionat != null){
            //creem la la lògica per poder cridar als seus mètodes d'importar
            ProveidorLogica logica = new ProveidorLogica();
            try{
                List<Proveidor> proveidors = logica.importarCSV(fitxerSeleccionat, (proveidorCSV,proveidorBD) -> {
                    boolean actualitzar = preguntarUsuari(proveidorCSV.getCIF());
                    if (actualitzar) {
                        try {
                            logica.ModificarProveidor(proveidorCSV);
                        } catch (Exception e) {
                            error.mostrarError("Error importar", e.getMessage());
                        }
                    }
                });
            }catch(FormatInvalidException e){
                error.mostrarError("Error importar", e.getMessage());
            }catch(DataInvalidaException e){
                error.mostrarError("Error importar", e.getMessage());
            }catch(BuitException e){
                error.mostrarError("Error importar", e.getMessage());
            }
        }
    }
    
    /**
     * Gestiona el clic al botó d'exportar. S'obre una nova finestra de diàleg
     * per seleccionar on vols guardar el fitxer i amb quin nom
     * @param event un event d'acció quan l'usuari prem el botó
     */
    @FXML
    void onBtn_Exp_Action(ActionEvent event) {
        
        //creem la classe
        FileChooser seleccionador = new FileChooser();
        
        //fer un filtre perque tan sols mostri fitxers csv
        FileChooser.ExtensionFilter filtre = new FileChooser.ExtensionFilter("Fitxers CSV (*.csv)", "*.csv");
        seleccionador.getExtensionFilters().add(filtre);
        
        //fiquem un titol
        seleccionador.setTitle("Seleccioni una carpeta per exportar fitxer CSV");
        
        //obtenim el Stage actual on está el botó en comptes de crear un Stage nou utilitzem la referència d'aquest botó
        Stage escenari = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        //obrim el diàleg per seleccionar on es guardara el fitxer
        File fitxerExportar  = seleccionador.showSaveDialog(escenari);
        
        if(fitxerExportar != null){
            ProveidorLogica logica = new ProveidorLogica();
            logica.ExportarCSV(fitxerExportar);
        }
    }
    
    /**
     * Gestiona quan l'usuari dona clic al botó tancar sessio, crida al mètode
     * tancarSessio que possa l'usuari en null i canvia de pantalla a la pantalla login
     * @param event un event d'acció quan l'usuari prem el botó
     * @throws IOException si hi ha un error en el canvi de escena
     */
    @FXML
    private void onBtn_Tancar_Action(ActionEvent event) throws IOException{
        Sessio.getInstancia().tancarSessio();
        CanviPantalla.canviarPantalla(Btn_Fam.getScene(), "/cat/copernic/projecte_grup4/login.fxml");
    }
    
    /**
     * Obre una finestra informant a l'usuari que hi ha un cif repetit hi li dona dos opcions
     * el botó modificar per modificar la dada o el botó omitir per no fer res i continuar
     * @param cif el cif repetit per informar a l'usuari
     * @return true si l'usuari prem el botó Actualitzar
     */
    private boolean preguntarUsuari(String cif){
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("CIF Repetit");
        alerta.setHeaderText("El CIF " + cif + " ja existeix a la base de dades.");
        alerta.setContentText("Vols actualitzar les dades del proveïdor o ometre aquesta entrada?");
        
        ButtonType btnActualizar = new ButtonType("Actualizar");
        ButtonType btnOmitir = new ButtonType("Omitir", ButtonBar.ButtonData.CANCEL_CLOSE);

        alerta.getButtonTypes().setAll(btnActualizar, btnOmitir);

        Optional<ButtonType> result = alerta.showAndWait();
        return result.isPresent() && result.get() == btnActualizar;
    }
    
}
