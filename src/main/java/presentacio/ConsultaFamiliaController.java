package presentacio;

import java.time.format.DateTimeFormatter;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import logica.FamiliaLogica;
import aplicacio.model.Familia;
import logica.CanviPantalla;

public class ConsultaFamiliaController {

    @FXML
    private Button Btn_Eliminar, Btn_Modificar, Btn_Nova, Btn_Productes, Btn_Sortir;

    @FXML
    private TableColumn<Familia, Integer> col_IdFamilia;
    @FXML
    private TableColumn<Familia, String> col_Nom, col_Descripcio, col_Proveidor;
    @FXML
    private TableColumn<Familia, LocalDate> col_DataAlta;
    @FXML
    private TableColumn<Familia, String> col_observacions;

    @FXML
    private TextArea ta_Descripcio, ta_Observacions;

    @FXML
    private TableView<Familia> tb_Familia;

    @FXML
    private TextField tf_DataAlta, tf_ID, tf_Nom, tf_Proveidor;

    private FamiliaLogica familiaLogica = new FamiliaLogica();

    private ObservableList<Familia> familiasObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        col_IdFamilia.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_Descripcio.setCellValueFactory(new PropertyValueFactory<>("descripcio"));
        col_DataAlta.setCellValueFactory(new PropertyValueFactory<>("dataAlta"));
        col_observacions.setCellValueFactory(new PropertyValueFactory<>("observacions"));
        col_Proveidor.setCellValueFactory(new PropertyValueFactory<>("proveidorPerDefecte"));

        tb_Familia.setItems(familiasObservableList);
        listarFamilias();
    }

    @FXML
    private void onBtn_Nova_Clicked() {
        Familia nuevaFamilia = new Familia();
        nuevaFamilia.setNom(tf_Nom.getText());
        nuevaFamilia.setDescripcio(ta_Descripcio.getText());
        nuevaFamilia.setObservacions(ta_Observacions.getText());
        nuevaFamilia.setDataAlta(LocalDate.now());
        nuevaFamilia.setProveidorPerDefecte(tf_Proveidor.getText());

        familiaLogica.afegirFamilia(nuevaFamilia);
        familiasObservableList.add(nuevaFamilia);
        limpiarCampos();
    }

    @FXML
private void onBtn_Modificar_Clicked() {
    Familia familiaSeleccionada = tb_Familia.getSelectionModel().getSelectedItem();
    if (familiaSeleccionada != null) {
        familiaSeleccionada.setNom(tf_Nom.getText());
        familiaSeleccionada.setDescripcio(ta_Descripcio.getText());
        familiaSeleccionada.setObservacions(ta_Observacions.getText());

        // Validar y convertir la fecha
        if (!tf_DataAlta.getText().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                familiaSeleccionada.setDataAlta(LocalDate.parse(tf_DataAlta.getText(), formatter));
            } catch (Exception e) {
                System.out.println("Formato de fecha incorrecto: " + e.getMessage());
                return; // No continuar si la fecha es inválida
            }
        }

        familiaSeleccionada.setProveidorPerDefecte(tf_Proveidor.getText());

        try {
            familiaLogica.modificarFamilia(familiaSeleccionada);
            listarFamilias(); // Actualiza la lista después de modificar
            tb_Familia.refresh(); // Refresca la tabla
            limpiarCampos(); // Limpia los campos
        } catch (Exception e) {
            System.out.println("Error al modificar la familia: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("Por favor, selecciona una familia para modificar.");
    }
}


    @FXML
    private void onBtn_Eliminar_Clicked() {
        Familia familiaSeleccionada = tb_Familia.getSelectionModel().getSelectedItem();
        if (familiaSeleccionada != null) {
            familiaLogica.eliminarFamilia(familiaSeleccionada.getId());
            familiasObservableList.remove(familiaSeleccionada);
            limpiarCampos();
        } else {
            System.out.println("Por favor, selecciona una familia para eliminar.");
        }
    }

    @FXML
    private void onBtn_Productes_Clicked() {
        try {
            CanviPantalla.canviarPantalla(Btn_Productes.getScene(), "/cat/copernic/projecte_grup4/ConsultaReferencia.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtn_Sortir_Clicked() {
        try {
            CanviPantalla.canviarPantalla(Btn_Sortir.getScene(), "/cat/copernic/projecte_grup4/Menu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarFamilias() {
        familiasObservableList.clear();
        familiasObservableList.addAll(familiaLogica.llistarFamilias());
    }

    @FXML
    private void onTb_FamiliaMouseClicked(MouseEvent event) {
        Familia familiaSeleccionada = tb_Familia.getSelectionModel().getSelectedItem();
        if (familiaSeleccionada != null) {
            tf_ID.setText(String.valueOf(familiaSeleccionada.getId()));
            tf_Nom.setText(familiaSeleccionada.getNom());
            ta_Descripcio.setText(familiaSeleccionada.getDescripcio());
            ta_Observacions.setText(familiaSeleccionada.getObservacions());

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            tf_DataAlta.setText((familiaSeleccionada.getDataAlta().format(format)));
            tf_Proveidor.setText(familiaSeleccionada.getProveidorPerDefecte());
        }
    }

    private void limpiarCampos() {
        tf_ID.clear();
        tf_Nom.clear();
        ta_Descripcio.clear();
        ta_Observacions.clear();
        tf_DataAlta.clear();
        tf_Proveidor.clear();
    }
}
