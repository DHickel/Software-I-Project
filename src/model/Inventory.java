package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.Locale;
import java.util.UUID;

/**
 * The type Inventory.
 */
public class Inventory {


    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Generate Unique Identifier
     *
     * @return int identifier
     */
    public static int generateIdHash() {
        UUID uuid = UUID.randomUUID();
        int id = uuid.hashCode() & 0xfffffff;
        return id ;
    }

    /**
     * Generates test items.
     */
    public static void generateTestItems() {
        InHouse test_part_1 = new InHouse(generateIdHash(), "Test InHouse 1", 19.99,99,0,100,999);
        InHouse test_part_2 = new InHouse(generateIdHash(), "Test Part 1", 99.99,1,0,100,999);
        Outsourced test_part_3 = new Outsourced(generateIdHash(), "Test Outsourced 1", 19.99,99,0,100,"ACME");
        Outsourced test_part_4 = new Outsourced(generateIdHash(), "Part Test 2", 99.99,1,0,100,"EMCA");

        addPart(test_part_1);
        addPart(test_part_2);
        addPart(test_part_3);
        addPart(test_part_4);

        Product test_product_1 = new Product(generateIdHash(), "Test Product 1", 9999.99,10,0,999);

        test_product_1.addAssociatedPart(test_part_1);
        test_product_1.addAssociatedPart(test_part_4);

        Product test_product_2 = new Product(generateIdHash(), "Product Test 2", 9999.99,10,0,999);

        test_product_2.addAssociatedPart(test_part_2);
        test_product_2.addAssociatedPart(test_part_3);


        addProduct(test_product_1);
        addProduct(test_product_2);

    }


    /**
     * Adds  part.
     *
     * @param newPart Part to add to list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product.
     *
     * @param newProduct Product to add to list
     */

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Lookup part by part ID.
     *
     * @param partId the part id
     * @return the part found
     */
    public static Part lookupPart(int partId) {
        for(Part currPart : allParts) {
            if (currPart.getId() == partId) {
                return currPart;
            }
        }
        return null;
    }

    /**
     * Lookup part by string
     *
     * @param partName the part name
     * @return the observable list of found parts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for(Part currPart : allParts) {
            if (currPart.getName().toLowerCase().contains(partName.toLowerCase())) {
                foundParts.add(currPart);
            }
        }
        return foundParts;
    }

    /**
     * Lookup product by product ID.
     *
     * @param productId the product id
     * @return the product found
     */
//change int to just return object not array
   public static Product lookupProduct(int productId) {
        for(Product currProduct : allProducts) {
            if (currProduct.getId() == productId) {
                return currProduct;
            }
        }
       return null;
   }

    /**
     * Lookup product by string.
     *
     * @param productName the product name
     * @return the observable list of found products
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        for(Product currProduct : allProducts) {
            if (currProduct.getName().toLowerCase().contains(productName.toLowerCase())) {
                foundProducts.add(currProduct);
            }
        }
        return foundProducts;
    }


    /**
     * Update part.
     *
     * @param index        the index of part in list
     * @param selectedPart the selected part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update product.
     *
     * @param index           the index of product in list
     * @param selectedProduct the selected product
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Delete part boolean.
     *
     * @param selectedPart the selected part
     * @return boolean successful delete
     */
    public static boolean deletePart(Part selectedPart) {
        for(Part currPart : allParts) {
            if (currPart.equals(selectedPart)) {
                allParts.remove(selectedPart);
                return true;
            }

        }
        return false;
    }

    /**
     * Delete product boolean.
     *
     * @param selectedProduct the selected product
     * @return the boolean of successful delete
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for(Product currProduct : allProducts) {
            if (currProduct.equals(selectedProduct)) {
                allProducts.remove(selectedProduct);
                return true;
            }

        }
        return false;
    }

    /**
     * Gets all parts.
     *
     * @return the all parts lists
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products.
     *
     * @return the all products list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }








}