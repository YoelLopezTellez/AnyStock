/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacio;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Tenim un metode que es comparteix amb moltes classes que es per caviar de pantalla
 * @author reyes
 * @version 0.1
 */
public class CanviPantalla {
    /**
     * Li has de pasar una escena, per exemple un node (node.getscene()) i desrpès passar-li la ruta del fxml
     * @param escenaActual li has de passar un node de l'escena actual
     * @param rutaFxml la ruta on està el fitxer fxml
     * @throws IOException 
     */
    public static void canviarPantalla(Scene escenaActual, String rutaFxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(CanviPantalla.class.getResource(rutaFxml));
        Parent novaPantalla = loader.load();
        escenaActual.setRoot(novaPantalla);
    }
}
