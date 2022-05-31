package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Main screen.
 */
public class MainScreen implements Initializable {

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
    private TableView<Product> products_table;
    @FXML
    private TableColumn<Product, Integer> product_id;
    @FXML
    private TableColumn<Product, String> product_name;
    @FXML
    private TableColumn<Product, Integer> product_inventory;
    @FXML
    private TableColumn<Product, Integer> product_unit_cost;
    @FXML
    private TextField mainscreen_search_parts_field;
    @FXML
    private TextField mainscreen_search_products_field;
    @FXML
    private static ObservableList<Product> productSearchResults = FXCollections.observableArrayList();

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

        products_table.setItems(Inventory.getAllProducts());
        product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_inventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        product_unit_cost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    /**
     * To add part view.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1024, 762);
        stage.setTitle("Add Part");

        stage.setScene(scene);
        stage.show();

    }

    /**
     * To modify part view.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML // null pointer from setting textfield  for passed pbject in initialize method of modifyParts
    public void toModifyPart(ActionEvent actionEvent) throws IOException {

        Part selectedPart = (Part)parts_table.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/view/modifyPart.fxml"));
        loader.load();

        ModifyPart modifyPartController = loader.getController();
        modifyPartController.importPartReference(selectedPart);
        Parent root = loader.getRoot();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1024, 762);
        stage.setTitle("Modify Part");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * To add product view.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1024, 762);
        stage.setTitle("Add Product");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * To modify product view.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {

        Product selectedProduct = products_table.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/view/modifyProduct.fxml"));
        loader.load();

        ModifyProduct modifyProductController = loader.getController();
        modifyProductController.importProductReference(selectedProduct);
        Parent root = loader.getRoot();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1024, 762);
        stage.setTitle("Modify Product");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();



    }

    /**
     * Exit to main screen view.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void exitToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1024, 762);
        stage.setTitle("Inventory Manager");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Delete product from Inventory Product List.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void deleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = (Product) products_table.getSelectionModel().getSelectedItem();
        if (Utilities.confirm(5)) {
            if (selectedProduct.getAllAssociatedParts().isEmpty()) {
                 if (Inventory.deleteProduct(selectedProduct)) {
                     Utilities.info(3);
                }
            }
            else {
                Utilities.error(5);
            }
        }
    }

    /**
     * Delete part from Inventory Part List.
     *
     * @param actionEvent the action event
     */
    @FXML
    // FIXME
    public void deletePart(ActionEvent actionEvent) {
        Part selectedPart = (Part)parts_table.getSelectionModel().getSelectedItem();
        if(Utilities.confirm(4)) {
            if (Inventory.deletePart(selectedPart)) {
                Utilities.info(3);
            }
        }
    }

    /**
     * Exits program with confirmation prompt.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void mainscreenOnExit(ActionEvent actionEvent) {
        if(Utilities.confirm(1)) {
            System.exit(0);
        }
    }

    /**
     * Searches Inventory Parts List.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void searchParts(ActionEvent actionEvent) {
        ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();
        partsSearchResults = Utilities.searchParts(mainscreen_search_parts_field.getText());
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
     * Searches Inventory Products List.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void searchProducts(ActionEvent actionEvent) {
        ObservableList<Product> productsSearchResults = FXCollections.observableArrayList();
        productsSearchResults = Utilities.searchProducts(mainscreen_search_products_field.getText());
        products_table.getSelectionModel().clearSelection();

        if (!productsSearchResults.isEmpty()) {
            products_table.setItems(productsSearchResults);
            if (productsSearchResults.size() == 1) {
                products_table.getSelectionModel().selectFirst();
            }
        }
        else {
            Utilities.info(2);
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
        mainscreen_search_parts_field.clear();
        parts_table.getSelectionModel().clearSelection();
    }

    /**
     * Reset products search results.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void resetSearchProducts(ActionEvent actionEvent) {
        products_table.setItems(Inventory.getAllProducts());
        mainscreen_search_products_field.clear();
        products_table.getSelectionModel().clearSelection();
    }


}
