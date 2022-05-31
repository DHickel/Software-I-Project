package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Inventory;
import model.Part;
import model.Product;
import java.util.Optional;

/**
 * The type Utilities.
 */
public class Utilities {

    /**
     * Is integer boolean.
     *
     * @param input string to test for integer
     * @return the boolean of test result
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Is double boolean.
     *
     * @param input the string to test for double
     * @return the boolean of test result
     */
    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check for valid input for Part/Product objects
     *
     * @param price the price input
     * @param stock the stock input
     * @param min   the min input
     * @param max   the max input
     * @return the boolean return of validation
     */
    public static boolean inputValidator(String price, String stock, String min, String max) {
        if (price.isEmpty() || stock.isEmpty() || min.isEmpty() || max.isEmpty()) {
            Utilities.error(1);
            return false;
        }
        if (!isDouble(price)) {
            error(1);
            return false;
        }
        if (!(isInteger(stock) || isInteger(stock) || isInteger(min) || isInteger(max))) {
            error(1);
            return false;
        }
        return invLogicCheck(Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max));
    }

    /**
     * Check logic of inventory input, stock more than min, stock less than max, min less than max
     *
     * @param stock the stock input
     * @param min   the min input
     * @param max   the max input
     * @return the boolean return of check
     */
    public static boolean invLogicCheck(int stock, int min, int max) {
        if ((stock >= min) && (stock <= max)) {
            return true;
        }
        else if (min > max) {
        error(2);
        return false;
        }
        else if(stock > max) {
            error(3);
        }
        else if (stock < min) {
            error(4);
        }
        return false;
    }


    /**
     * Search Parts observable list for search term.
     *
     * @param searchTerm the search term
     * @return the observable list fo results
     */
    public static ObservableList<Part> searchParts(String searchTerm) {
        ObservableList<Part> partsSearchResults = FXCollections.observableArrayList();
        partsSearchResults.clear();

        partsSearchResults = (Inventory.lookupPart(searchTerm));
        if (isInteger(searchTerm)) {
            Part idResult = Inventory.lookupPart(Integer.parseInt(searchTerm));
            if (idResult != null) {
                partsSearchResults.add(idResult);
            }
        }
        return partsSearchResults;

    }

    /**
     * Search Products observable list for search term.
     *
     * @param searchTerm the search term
     * @return the observable list of results
     */
    public static ObservableList<Product> searchProducts(String searchTerm) {
        ObservableList<Product> productsSearchResults = FXCollections.observableArrayList();
        productsSearchResults.clear();

        productsSearchResults = (Inventory.lookupProduct(searchTerm));
        if (isInteger(searchTerm)) {
            Product idResult = Inventory.lookupProduct(Integer.parseInt(searchTerm));
            if (idResult != null) {
                productsSearchResults.add(idResult);
            }
        }
        return productsSearchResults;

    }

    /**
     * Error Alerts
     *
     * @param n The Alert to show
     */
    public static void error(int n) {

        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        switch (n) {

            case 1:
                error.setContentText("Non-Numeric or Empty Input In Numeric Field");
                break;
            case 2:
                error.setContentText("Min Must Be Less Than Max");
                break;
            case 3:
                error.setContentText("Current Inventory Cant Exceed Max");
                break;
            case 4:
                error.setContentText("Current Inventory Can't Be Less Than Min");
                break;
            case 5:
                error.setContentText("Can't Delete Product With Parts Assigned");
                break;
            case 6:
                error.setContentText("One or More Fields Are Empty");
                break;
            case 7:
                error.setContentText("Undefined Error Encountered");
                break;
            case 8:
                error.setContentText("Name Cannot Be Empty");
        }
        error.showAndWait();
    }

    /**
     * Confirmation Alerts
     *
     * @param n the confirmation to show
     * @return the boolean Yes or No
     */
    public static boolean confirm(int n) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm");

        switch (n) {
            case 1:
                confirm.setContentText("Exit?");
                break;
            case 2:
                confirm.setContentText("Cancel? All Changes Will Be Lost");
                break;
            case 3:
                confirm.setContentText("Remove Part?");
                break;
            case 4:
                confirm.setContentText("Delete Part?");
                break;
            case 5:
                confirm.setContentText("Delete Product?");
                break;
            case 6:
                confirm.setContentText("Product Has No Parts Attached, Continue?");
                break;
        }

        Optional<ButtonType> yesNo = confirm.showAndWait();
        return yesNo.isPresent() && yesNo.get() == ButtonType.OK;


    }

    /**
     * Information Alerts
     *
     * @param n the Alert to show
     */
    public static void info(int n) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Search");
        switch (n) {
            case 1:
                info.setContentText("Part Not Found");
                break;
            case 2:
                info.setContentText("Product Not Found");
                break;
            case 3:
                info.setContentText("Delete Successful");
        }
        info.showAndWait();
    }
}