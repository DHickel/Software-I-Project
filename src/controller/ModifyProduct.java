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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Modify product.
 */
public class ModifyProduct implements Initializable {



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
    private TextField modify_product_name;
    @FXML
    private TextField modify_product_price;
    @FXML
    private TextField modify_product_inv_current;
    @FXML
    private TextField modify_product_inv_min;
    @FXML
    private TextField modify_product_inv_max;
    @FXML
    public TextField modify_product_id;
    @FXML
    public TextField modify_product_search_parts_field;
    @FXML
    private ObservableList<Part>  associatedPartsStage =  FXCollections.observableArrayList();

    private static ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();
    private Product productToModify;


    /**
     * Initializes table data
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



        associated_part_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        associated_part_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        associated_part_inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associated_part_unit_cost.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Imports product object reference from MainScreen for modification
     *
     * RUNTIME ERROR arrays are passed by reference and not value. Had issue with temp staged edits being committed to product array on cancel
     *
     * @param productToModify the product to modify
     */
    public void importProductReference(Product productToModify) {
        this.productToModify = productToModify;

        modify_product_id.setText(Integer.toString(productToModify.getId()));
        modify_product_name.setText(productToModify.getName());
        modify_product_price.setText(Double.toString(productToModify.getPrice()));
        modify_product_inv_current.setText(Integer.toString(productToModify.getStock()));
        modify_product_inv_min.setText(Integer.toString(productToModify.getMin()));
        modify_product_inv_max.setText(Integer.toString(productToModify.getMax()));

        associatedPartsStage =  FXCollections.observableArrayList(productToModify.getAllAssociatedParts());
        associated_parts_stage.setItems(associatedPartsStage);
        System.out.println("init");
        System.out.println(productToModify.getAllAssociatedParts());
        System.out.println(associatedPartsStage);
    }

    /**
     * Exits to MainScreen.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void exitToMain (ActionEvent actionEvent) throws IOException {
        if (Utilities.confirm(2)) {
            MainScreen main = new MainScreen();
            main.exitToMain(actionEvent);
        }
    }

    /**
     * Adds part to product's staging list.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void addPartToProduct(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part)parts_table.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        associatedPartsStage.add(selectedPart);
    }

    /**
     * Removes part to product's staging list.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void removePartFromProduct(ActionEvent actionEvent) throws IOException {
        if (Utilities.confirm(3)) {
            Part selectedPart = (Part) associated_parts_stage.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }
            associatedPartsStage.remove(selectedPart);
        }
    }

    /**
     * Commits change to product to Inventory's Product List
     *
     * @param actionEvent the action event
     * @throws Exception the exception
     */
    @FXML
    public void saveProduct(ActionEvent actionEvent) throws Exception {
        try {

            if (modify_product_name.getText().isEmpty()) {
                Utilities.error(8);
                return;
            }
            if (!Utilities.inputValidator(modify_product_price.getText(), modify_product_inv_current.getText(),
                                          modify_product_inv_min.getText(), modify_product_inv_max.getText())) {
                return;
            }
            if (associatedPartsStage.isEmpty()) {
                if (!Utilities.confirm(6)){
                    return;
                }
            }
            int stock = Integer.parseInt(modify_product_inv_current.getText());
            int min = Integer.parseInt(modify_product_inv_min.getText());
            int max = Integer.parseInt(modify_product_inv_max.getText());

            productToModify.setName(modify_product_name.getText());
            productToModify.setPrice(Double.parseDouble(modify_product_price.getText()));
            productToModify.setStock(stock);
            productToModify.setMin(min);
            productToModify.setMax(max);
            productToModify.setAllAssociatedParts(associatedPartsStage);

            MainScreen main = new MainScreen();
            main.exitToMain(actionEvent);
        }
        catch (Exception e) {
            Utilities.error(7);
        }
    }

    /**
     * Search Inventory Parts lists.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void searchParts(ActionEvent actionEvent) {
        ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();
        partsSearchResults = Utilities.searchParts(modify_product_search_parts_field.getText());
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
     * Reset parts search results.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void resetSearchParts(ActionEvent actionEvent) {
        parts_table.setItems(Inventory.getAllParts());
        modify_product_search_parts_field.clear();
        parts_table.getSelectionModel().clearSelection();

    }
}


