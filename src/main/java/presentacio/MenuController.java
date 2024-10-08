/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logica.ProveidorLogica;
import logica.Sessio;

/**
 * FXML Controller class
 *
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onBtn_Fam_Action(ActionEvent event) throws IOException{
        CanviPantalla.canviarPantalla(Btn_Fam.getScene(), "/cat/copernic/projecte_grup4/ConsultaFamilia.fxml");
    }
    
    @FXML
    private void onBtn_Prov_Action(ActionEvent event) throws IOException{
        CanviPantalla.canviarPantalla(Btn_Fam.getScene(), "/cat/copernic/projecte_grup4/ConsultaProveidor.fxml");
    }
    
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
            ProveidorLogica logica = new ProveidorLogica();
            logica.importarCSV(fitxerSeleccionat);
        }
    }
    
    @FXML
    private void onBtn_Tancar_Action(ActionEvent event) throws IOException{
        Sessio.getInstancia().tancarSessio();
        CanviPantalla.canviarPantalla(Btn_Fam.getScene(), "/cat/copernic/projecte_grup4/login.fxml");
    }
    
}
