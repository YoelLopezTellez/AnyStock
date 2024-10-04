package presentacio;

import logica.Login;
import aplicacio.model.Usuari;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logica.CanviPantalla;

/**
 * Controlador de la pantalla login, el que fa es cridar al login de logica per comprovar si existeix l'usuari 
 * si existeix canvia de pantalla al menu
 *
 * @author Reyes
 */

public class LoginController {

    @FXML
    private Button btn_1;
    
    @FXML
    private TextField tf_nom;
    
    @FXML
    private TextField tf_pass;
    
    private Login login;
    
    //al carregar la escena ja executa els usuaris que hi ha al fitxer
    @FXML
    public void initialize() {
        try {
            // Obtener la ruta del archivo en resources y pasarlo a la clase Login
            //getClass retorna la clase, getClassLoader serveix per cargar fitxers de recursos, l'ultim localitza si existeix el fitxer
            //getResourceAsStream retorna inputStream per poder passar-lo al metode cargarUsuarios de Login
            login = new Login(getClass().getClassLoader().getResourceAsStream("usuaris.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onBtn1_action(ActionEvent event) throws IOException{
        String nom = tf_nom.getText();
        String pass = tf_pass.getText();
        
        Usuari usuari = login.autentificacio(nom, pass);
        
        if(usuari != null){
            CanviPantalla.canviarPantalla(tf_nom.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
        }
    }
}
