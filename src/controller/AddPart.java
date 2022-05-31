package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Add part.
 */
public class AddPart implements Initializable {

    @FXML
    private Label toggle_id_company;
    @FXML
    private final ToggleGroup radioToggle = new ToggleGroup();
    @FXML
    private TextField add_part_id;
    @FXML
    private TextField add_part_name;
    @FXML
    private TextField add_part_price;
    @FXML
    private TextField add_part_mach_comp_id;
    @FXML
    private TextField add_part_inv_current;
    @FXML
    private TextField add_part_inv_min;
    @FXML
    private TextField add_part_inv_max;
    @FXML
    private Label add_part_error;
    @FXML
    private RadioButton radio_in_house;
    @FXML
    private RadioButton radio_outsourced;

    /**
     * Sets Radio Toggle to In House
     * @param actionEvent
     *
     */
    @FXML
    private void radioInHouse(ActionEvent actionEvent) {
        toggle_id_company.setText("Machine ID:");
        add_part_error.setText("");
    }

    /**
     * Sets Radio Toggle to Outsourced
     * @param actionEvent
     *
     */
    @FXML
    private void radioOutSourced(ActionEvent actionEvent) {
        toggle_id_company.setText("Company Name:");
        add_part_error.setText("");

    }

    /**
     * Commits part to Parts Inventory List
     *
     * RUNTIME ERROR Not having check to avoid Passing string as machine ID
     *
     */
    @FXML
    private void addPartSave(ActionEvent actionEvent) throws Exception{

        try {

            if(!radio_in_house.isSelected() && !radio_outsourced.isSelected()) {
                add_part_error.setText("ERROR: In-House or Outsourced Must Be Selected");
                return;
            }

            if (add_part_name.getText().isEmpty()) {
                Utilities.error(8);
                return;
            }
            if (!Utilities.inputValidator(add_part_price.getText(), add_part_inv_current.getText(),
                                          add_part_inv_min.getText(), add_part_inv_max.getText())) {
                return;
            }

            int id = Inventory.generateIdHash();
            String name = add_part_name.getText();
            double price = Double.parseDouble((add_part_price.getText()));
            int stock = Integer.parseInt(add_part_inv_current.getText());
            int min = Integer.parseInt(add_part_inv_min.getText());
            int max = Integer.parseInt(add_part_inv_max.getText());

            if (radio_in_house.isSelected()) {
                if (!Utilities.isInteger(add_part_mach_comp_id.getText())) {
                    Utilities.error(1);
                    return;
                }
                int machineId = Integer.parseInt(add_part_mach_comp_id.getText());
                InHouse savedPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.addPart(savedPart);
                MainScreen main = new MainScreen();
                main.exitToMain(actionEvent);
            } else if (radio_outsourced.isSelected()) {
                String companyName = add_part_mach_comp_id.getText();
                Outsourced savedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(savedPart);
                MainScreen main = new MainScreen();
                main.exitToMain(actionEvent);
            }
        }
        catch (Exception e) {
            Utilities.error(7);
        }
    }

    /**
     * Exit to main.
     *
     * @param actionEvent the action event
     * @throws Exception the exception
     */
    @FXML
    public void exitToMain(ActionEvent actionEvent) throws Exception {
        if(Utilities.confirm(2)) {
            MainScreen main = new MainScreen();
            main.exitToMain(actionEvent);
        }
    }


    /**
     * Initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
