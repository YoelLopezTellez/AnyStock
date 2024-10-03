package aplicacio;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button btn_1;
    
    @FXML
    private TextField tf_nom;
    
    @FXML
    private TextField tf_pass;
    
    private Login login;
    
    @FXML
    public void initialize() {
    try {
        // Obtener la ruta del archivo en resources y pasarlo a la clase Login
        //getClass retorna la clase, getClassLoader serveix per cargar fitxers de recursos, l'ultim localitza si existeix el fitxer
        //getResourceAsStream retorna inputStream per poder passar-lo al metode cargarUsuarios de Login
        login = new Login(getClass().getClassLoader().getResourceAsStream("usuarios.txt"));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
