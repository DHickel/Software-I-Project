package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;
import model.Product;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Add product.
 */
public class AddProduct implements Initializable {

    @FXML
    private TextField add_product_search_parts_field;
    @FXML
    private TableView<Part> parts_table;
    @FXML
    private TableColumn<Part, Integer> part_id;
    @FXML
    private TableColumn<Part, String> part_name;
    @FXML
    private TableColumn<Part, Integer> part_inventory;
    @FXML
    private TableColumn<Part, Integer> part_unit_cost;
    @FXML
    private TableView<Part> associated_parts_stage;
    @FXML
    private TableColumn<Part, Integer> associated_part_id;
    @FXML
    private TableColumn<Part, String> associated_part_name;
    @FXML
    private TableColumn<Part, Integer> associated_part_inventory;
    @FXML
    private TableColumn<Part, Integer> associated_part_unit_cost;
    @FXML
    private TextField add_product_name;
    @FXML
    private TextField add_product_price;
    @FXML
    private TextField add_product_inv_current;
    @FXML
    private TextField add_product_inv_min;
    @FXML
    private TextField add_product_inv_max;

    private ObservableList<Part> associatedPartsStage= FXCollections.observableArrayList();
    private static ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();

    /**
     *  Initializes table data
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        parts_table.setItems(Inventory.getAllParts());
        part_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        part_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        part_inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        part_unit_cost.setCellValueFactory(new PropertyValueFactory<>("price"));

        associated_parts_stage.setItems(associatedPartsStage);
        associated_part_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        associated_part_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        associated_part_inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associated_part_unit_cost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Exits to MainScreen.
     *
     * @param actionEvent the action event
     * @throws Exception the exception
     */
    @FXML
    public void exitToMain (ActionEvent actionEvent) throws Exception {
        if(Utilities.confirm(2)) {
            MainScreen main = new MainScreen();
            main.exitToMain(actionEvent);
        }
    }

    /**
     * Add part to product's staging list.
     *
     * @param actionEvent the action event
     * @throws Exception the exception
     */
    @FXML
    public void addPartToProduct(ActionEvent actionEvent) throws Exception {
        Part selectedPart = (Part)parts_table.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        associatedPartsStage.add(selectedPart);
    }

    /**
     * Remove part from product's staging list.
     *
     * @param actionEvent the action event
     * @throws Exception the exception
     */
    @FXML
    public void removePartFromProduct(ActionEvent actionEvent) throws Exception {
        if (Utilities.confirm(3)) {
            Part selectedPart = (Part) associated_parts_stage.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }
            associatedPartsStage.remove(selectedPart);
        }
    }

    /**
     * Save product to Inventory's products list.
     * Commits Parts from staging list to product's parts list.
     *
     * @param actionEvent the action event
     * @throws Exception the exception
     */
    @FXML
    public void saveProduct(ActionEvent actionEvent) throws Exception {

        try {

            if (add_product_name.getText().isEmpty()) {
                Utilities.error(8);
                return;
            }

            if (!Utilities.inputValidator(add_product_price.getText(), add_product_inv_current.getText(),
                    add_product_inv_min.getText(), add_product_inv_max.getText())) {
                return;
            }
            if (associatedPartsStage.isEmpty()) {
                if (!Utilities.confirm(6)){
                    return;
                }
            }
            int id = Inventory.generateIdHash();
            String name = add_product_name.getText();
            double price = Double.parseDouble(add_product_price.getText());
            int stock = Integer.parseInt(add_product_inv_current.getText());
            int min = Integer.parseInt(add_product_inv_min.getText());
            int max = Integer.parseInt(add_product_inv_max.getText());


            Product savedProduct = new Product(id, name, price, stock, min, max);

            for (Part stagedPart : associatedPartsStage) {
                savedProduct.addAssociatedPart(stagedPart);
            }

            Inventory.addProduct(savedProduct);
            MainScreen main = new MainScreen();
            main.exitToMain(actionEvent);

        }
        catch (Exception e) {
            Utilities.error(7);
        }
    }

    /**
     * Searches parts_table for input in add_product_search_parts_field.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void searchParts(ActionEvent actionEvent) {
        ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();
        partsSearchResults = Utilities.searchParts(add_product_search_parts_field.getText());
        parts_table.getSelectionModel().clearSelection();

        if (!partsSearchResults.isEmpty()) {
            parts_table.setItems(partsSearchResults);
            if (partsSearchResults.size() == 1) {
                parts_table.getSelectionModel().selectFirst();
            }
        }
        else {
            Utilities.info(1);
        }
    }

    /**
     * Resets parts search results.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void resetSearchParts(ActionEvent actionEvent) {
        parts_table.setItems(Inventory.getAllParts());
        add_product_search_parts_field.clear();
    }
}
