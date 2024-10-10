/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacio;

import javafx.scene.control.Alert;

/**
 * Classe perque es pugui cridar el mètode en altres classes i no repetir-ho
 * @author reyes
 * @version 0.1
 */
public class Error {
    
    /**
     * Mostra un pop-up informant d'un error que ha sorgit
     * @param titol el titol que volem a la finestra del pop-up
     * @param missatge el contingut que tindrá la finestra
     */
    public static void mostrarError(String titol, String missatge){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titol);
        alerta.setHeaderText(null);
        alerta.setContentText(missatge);
        alerta.showAndWait();
    }
}
