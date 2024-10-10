/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacio;

import javafx.scene.control.Alert;

/**
 *
 * @author reyes
 */
public class Error {

    public static void mostrarError(String titol, String missatge) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titol);
        alerta.setHeaderText(null);
        alerta.setContentText(missatge);
        alerta.showAndWait();
    }
}
