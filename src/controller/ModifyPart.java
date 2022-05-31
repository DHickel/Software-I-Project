package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.InHouse;
import model.Outsourced;
import model.Part;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Modify part.
 */
public class ModifyPart implements Initializable {
    @FXML
    private Label toggle_id_company;
    @FXML
    private final ToggleGroup radioToggle = new ToggleGroup();
    @FXML
    private TextField modify_part_id;
    @FXML
    private TextField modify_part_name;
    @FXML
    private TextField modify_part_price;
    @FXML
    private TextField modify_part_mach_comp_id;
    @FXML
    private TextField modify_part_inv_current;
    @FXML
    private TextField modify_part_inv_min;
    @FXML
    private TextField modify_part_inv_max;
    @FXML
    private Label modify_part_error;
    @FXML
    private RadioButton radio_in_house;
    @FXML
    private RadioButton radio_outsourced;

    private Part partToModify;

    @FXML
    /**
     * Sets radio button to InHouse On click
     */
    private void radioInHouse(ActionEvent actionEvent) {
        toggle_id_company.setText("Machine ID:");
        modify_part_error.setText("");
    }
    /**
     * Sets radio button to InHouse
     */
    @FXML
    public void radioInHouse() {
        toggle_id_company.setText("Machine ID:");
        modify_part_error.setText("");
    }
    /**
     * Sets radio button to OutSourced On click
     */
    @FXML
    private void radioOutSourced(ActionEvent actionEvent) {
        toggle_id_company.setText("Company Name:");
        modify_part_error.setText("");
    }
    /**
     * Sets radio button to OutSourced
     */
    @FXML
    private void radioOutSourced() {
        toggle_id_company.setText("Company Name:");
        modify_part_error.setText("");
    }

    /**
     * Imports part object reference from MainScreen for modification
     *
     * @param partToModify the part to modify
     */
    public void importPartReference(Part partToModify) {
        this.partToModify = partToModify;

        modify_part_id.setText(Integer.toString(partToModify.getId()));
        modify_part_name.setText(partToModify.getName());
        modify_part_price.setText(Double.toString(partToModify.getPrice()));
        modify_part_inv_current.setText(Integer.toString(partToModify.getStock()));
        modify_part_inv_min.setText(Integer.toString(partToModify.getMin()));
        modify_part_inv_max.setText(Integer.toString(partToModify.getMax()));

        if ((partToModify instanceof InHouse)) {
            radioInHouse();
            radio_in_house.setSelected(true);
            modify_part_mach_comp_id.setText(Integer.toString(((InHouse) partToModify).getMachineId()));
        } else {
            radioOutSourced();
            radio_outsourced.setSelected(true);
            modify_part_mach_comp_id.setText(((Outsourced)partToModify).getCompanyName());
        }
    }

    /**
     *
     * Commits part object modifications to Inventory's Parts List.
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    private void modifyPartSave(ActionEvent actionEvent) throws Exception {
        try {

            if(!radio_in_house.isSelected() && !radio_outsourced.isSelected()) {
                modify_part_error.setText("ERROR: In-House or Outsourced Must Be Selected");
                return;
            }
            if (modify_part_name.getText().isEmpty()) {
                Utilities.error(8);
                return;
            }
            if (!Utilities.inputValidator(modify_part_price.getText(), modify_part_inv_current.getText(),
                                          modify_part_inv_min.getText(), modify_part_inv_max.getText())) {
                return;
            }

            int stock = Integer.parseInt(modify_part_inv_current.getText());
            int min = Integer.parseInt(modify_part_inv_min.getText());
            int max = Integer.parseInt(modify_part_inv_max.getText());

            partToModify.setName(modify_part_name.getText());
            partToModify.setPrice(Double.parseDouble((modify_part_price.getText())));
            partToModify.setStock(stock);
            partToModify.setMin(min);
            partToModify.setMax(max);

            if (radio_in_house.isSelected()) {
                if (!Utilities.isInteger(modify_part_mach_comp_id.getText())) {
                    Utilities.error(1);
                    return;
                }
                ((InHouse) partToModify).setMachineId(Integer.parseInt(modify_part_mach_comp_id.getText()));
            }
            else {
                ((Outsourced) partToModify).setCompanyName(modify_part_mach_comp_id.getText());
            }

            MainScreen main = new MainScreen();
            main.exitToMain(actionEvent);
        }
        catch (Exception e) {
            Utilities.error(7);
        }
    }

    /**
     * Exits to MainScreen.
     *
     * @param actionEvent the action event
     * @throws Exception the exception
     */
    @FXML
    public void exitToMain(ActionEvent actionEvent) throws Exception {
        if (Utilities.confirm(2)) {
            MainScreen main = new MainScreen();
            main.exitToMain(actionEvent);
        }
    }

    /**
     * Initialize
     * @param url
     * @param resourceBundle
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


}

