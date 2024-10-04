/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logica.CanviPantalla;

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
    
}
