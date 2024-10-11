package presentacio;

import logica.Login;
import aplicacio.model.Usuari;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logica.Sessio;

/**
 * Controlador de la pantalla login, el que fa es cridar al login de logica per comprovar si existeix l'usuari 
 * si existeix canvia de pantalla al menu
 *
 * @author Reyes
 * @version 0.4
 */

public class LoginController {

    @FXML
    private Button btn_1;
    
    @FXML
    private Label l_error;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_pass;

    private Login login;

    /**
     * al carregar la escena ja executa els usuaris que hi ha al fitxer
     */
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
        //amb aquest metode seleccionem que si presionem intro activi el botó btn_1 que es el login
        btn_1.setDefaultButton(true);
    }
    
    /**
     * Gestiona el clic al botó 1, realitza la autentificació comprovant amb els
     * camps introduits en el tf_nom i tf_pass si la autentificació es un éxit, 
     * canvia de pantalla, sino informa a l'usuari de que algún dels dos camps es incorrecte
     * @param event un event d'acció quan l'usuari prem el botó
     * @throws IOException si hi ha un error en el canvi de pantalla
     */
    @FXML
    private void onBtn1_action(ActionEvent event) throws IOException{
        String nom = tf_nom.getText();
        String pass = tf_pass.getText();
        
        Usuari usuari = login.autentificacio(nom, pass);
        
        if(usuari != null){
            Sessio.getInstancia().iniciarSessio(usuari);
            CanviPantalla.canviarPantalla(tf_nom.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
            //cambiarPantalla();
        }else{
            l_error.setText("Nom d'usuari o contrasenya incorrectes");
            l_error.setStyle("-fx-text-fill: red;"); //canvia el color del text a vermell
        }
    }
    
    /*private void cambiarPantalla() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cat/copernic/projecte_grup4/Menu.fxml"));
    Parent nuevaPantalla = loader.load();
    Scene currentScene = tf_nom.getScene();  // O cualquier nodo en la escena actual
    currentScene.setRoot(nuevaPantalla);
    }*/
    
}
