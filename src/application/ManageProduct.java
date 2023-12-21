package application;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.List;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageProduct extends Application {
	private TextField productNameField;
    private TextField productBrandField;
    private TextField productPriceField;
    private TextField productStockField;
    private TextField updateProductNameField;
    private Spinner<Integer> quantitySpinner;

    private boolean isLoggedIn = true;
    private DatabaseManager databaseManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Manage Product");

        // Membuat MenuBar
        MenuBar menuBar = createMenuBar(primaryStage);

        // Membuat layout BorderPane sebagai root layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Menambahkan MenuBar ke atas (top) BorderPane
        root.setTop(menuBar);

        // Membuat objek DatabaseManager
        databaseManager = new DatabaseManager();

        // Menambahkan tabel produk ke tengah (center) BorderPane
        TableView<Product> productTable = createProductTable();
        root.setCenter(productTable);

        // Menambahkan form produk ke bawah (bottom) BorderPane
        GridPane productForm = createProductForm();
        root.setBottom(productForm);

        // Menampilkan scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        // Admin Menu
        Menu adminMenu = new Menu("Admin");
        MenuItem manageProductMenuItem = new MenuItem("Manage Product");
        MenuItem viewHistoryMenuItem = new MenuItem("View History");
        adminMenu.getItems().addAll(manageProductMenuItem, viewHistoryMenuItem);

        // Account Menu
        Menu accountMenu = new Menu("Account");
        MenuItem logoutMenuItem = new MenuItem("Logout");
        accountMenu.getItems().add(logoutMenuItem);

        menuBar.getMenus().addAll(adminMenu, accountMenu);

        // Assigning actions to the menu items
        manageProductMenuItem.setOnAction(e -> {
            // Example action: navigating to the Manage Product page
            System.out.println("Navigating to Manage Product page");
        });

        viewHistoryMenuItem.setOnAction(e -> {
            // Example action: navigating to the View History page
            System.out.println("Navigating to View History page");
        });

        logoutMenuItem.setOnAction(e -> logout(primaryStage));

        return menuBar;
    }

    private void logout(Stage primaryStage) {
        // Logika logout
        isLoggedIn = false;

        // Contoh: Tampilkan dialog informasi dan tutup aplikasi
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Anda telah berhasil logout.");

        alert.setOnHidden(evt -> {
            // Buka halaman login
            Main loginWindow = new Main();
            Stage loginStage = new Stage();
            loginWindow.start(loginStage);

            primaryStage.close(); // Tutup aplikasi setelah dialog ditutup
        });

        alert.show();
    }

    private TableView<Product> createProductTable() {
        TableView<Product> table = new TableView<>();
        table.setEditable(true);

        // Mendefinisikan kolom tabel
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> brandCol = new TableColumn<>("Desc");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Menambahkan kolom ke tabel
        table.getColumns().addAll(nameCol, brandCol, priceCol, stockCol);

        // Adding click event handler
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for a single click
                Product selectedProduct = table.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    // Update form fields with selected product details
                    updateProductNameField.setText(selectedProduct.getName());
                    // Update other fields as needed
                }
            }
        });

        // Mengambil data produk dari database dan menambahkannya ke tabel
        List<Product> products = databaseManager.getAllProducts();
        table.getItems().addAll(products);

        return table;
    }
    
    private GridPane createProductForm() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        // Label untuk Product List
        Label productListLabel = new Label("Product List");
        form.add(productListLabel, 0, 0);

        // Label untuk Add Product
        Label addProductLabel = new Label("Add Product:");
        form.add(addProductLabel, 1, 0);

        // Formulir untuk menambah produk baru
        productNameField = new TextField();
        productBrandField = new TextField();
        productPriceField = new TextField();
        productStockField = new TextField(); // New field for stock
        Button addProductButton = new Button("Add Product");

        // Menambahkan elemen-elemen ke dalam form
        form.add(new Label("Product Name:"), 1, 1);
        form.add(productNameField, 2, 1);
        form.add(new Label("Product Desc:"), 1, 2);
        form.add(productBrandField, 2, 2);
        form.add(new Label("Product Price:"), 1, 3);
        form.add(productPriceField, 2, 3);
        form.add(new Label("Product Stock:"), 1, 4); // Label for stock
        form.add(productStockField, 2, 4); // Textfield for stock
        form.add(addProductButton, 2, 5); // Adjusted row

        // Label untuk Update & Delete Product
        Label updateDeleteLabel = new Label("Update & Delete Product:");
        form.add(updateDeleteLabel, 0, 5);

        // Formulir untuk mengupdate produk
        updateProductNameField = new TextField();
        quantitySpinner = new Spinner<>(1, Integer.MAX_VALUE, 1);
        Button updateStockButton = new Button("Update Stock");
        Button deleteProductButton = new Button("Delete Product");

        // Menambahkan elemen-elemen ke dalam form
        form.add(new Label("Product Name for Update:"), 0, 6);
        form.add(updateProductNameField, 1, 6);
        form.add(new Label("Quantity to Add to Stock:"), 0, 7);
        form.add(quantitySpinner, 1, 7);
        form.add(updateStockButton, 2, 7);
        form.add(deleteProductButton, 2, 8);

        // Menambahkan aksi untuk tombol-tombol
        addProductButton.setOnAction(e -> addProduct(productNameField.getText(), productBrandField.getText(), productPriceField.getText(), productStockField.getText()));
        updateStockButton.setOnAction(e -> updateStock(updateProductNameField.getText(), quantitySpinner.getValue()));
        deleteProductButton.setOnAction(e -> deleteProduct(updateProductNameField.getText()));

        return form;
    }

    // Logika untuk menambah produk baru ke dalam tabel
    private void addProduct(String name, String brand, String price, String stock) {
        // Logika untuk menambah produk baru ke dalam tabel
        System.out.println("Adding new product: " + name + " " + brand + " " + price + " " + stock);

        // Memanggil metode addProduct dari DatabaseManager
        databaseManager.addProduct(name, brand, Integer.parseInt(price), Integer.parseInt(stock));

        // Membersihkan input fields
        clearInputFields();
//
//        // Refresh tabel dengan data terbaru dari database
        refreshProductTable();
    }
    
 // Membersihkan input fields
    private void clearInputFields() {
        // Membersihkan input fields
        productNameField.clear();
        productBrandField.clear();
        productPriceField.clear();
        productStockField.clear();  // Clear stock field as well
        updateProductNameField.clear();
        quantitySpinner.getValueFactory().setValue(1);
    }
    
    private void refreshProductTable() {
        // Clear the table and add the updated products
        TableView<Product> productTable = createProductTable();
        BorderPane root = (BorderPane) productNameField.getScene().getRoot();
        root.setCenter(productTable);
    }


    // Logika untuk mengupdate stock produk
    private void updateStock(String name, int quantity) {
        // Logika untuk mengupdate stock produk
        System.out.println("Updating stock for product: " + name + " Quantity: " + quantity);

        // Memanggil metode updateStock dari DatabaseManager
        databaseManager.updateStock(name, quantity);

        // Membersihkan input fields
        clearInputFields();

        // Refresh tabel dengan data terbaru dari database
        refreshProductTable();
    }

    // Logika untuk menghapus produk
    private void deleteProduct(String name) {
        // Logika untuk menghapus produk
        System.out.println("Deleting product: " + name);

        // Memanggil metode deleteProduct dari DatabaseManager
        databaseManager.deleteProduct(name);

        // Membersihkan input fields
        clearInputFields();

        // Refresh tabel dengan data terbaru dari database
        refreshProductTable();
    }
    
    // Kelas model untuk representasi produk
//    public static class Product {
//        private static int nextId = 1; // Menyimpan ID berikutnya untuk diberikan ke produk berikutnya
//
//        private int id;
//        private String name;
//        private String brand;
//        private int price;
//        private int stock;
//        private int quantity;  // Tambahkan variabel quantit
//
//
//
//        public Product(String name, String brand, int price, int stock) {
//            this.id = nextId++;
//            this.name = name;
//            this.brand = brand;
//            this.price = price;
//            this.stock = stock;
//            this.quantity = 0;  // Default quantity is 0
//
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getBrand() {
//            return brand;
//        }
//
//        public void setBrand(String brand) {
//            this.brand = brand;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public void setPrice(int price) {
//            this.price = price;
//        }
//
//        public int getStock() {
//            return stock;
//        }
//
//        public void setStock(int stock) {
//            this.stock = stock;
//        }
//        
//        public int getQuantity() {
//            return quantity;
//        }
//
//        public void setQuantity(int quantity) {
//            this.quantity = quantity;
//        }
//
//        public int getId() {
//            return id;
//        }
//    }
    
    public static class Product {
        private static int nextId = 1;

        private final IntegerProperty id = new SimpleIntegerProperty();
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty brand = new SimpleStringProperty();
        private final IntegerProperty price = new SimpleIntegerProperty();
        private final IntegerProperty stock = new SimpleIntegerProperty();
        private final IntegerProperty quantity = new SimpleIntegerProperty();
        private final SimpleIntegerProperty totalPrice = new SimpleIntegerProperty();


        public Product(String name, String brand, int price, int stock) {
            this.id.set(nextId++);
            this.name.set(name);
            this.brand.set(brand);
            this.price.set(price);
            this.stock.set(stock);
            this.quantity.set(0);  // Default quantity is 0
            calculateTotalPrice();  // Calculate total price initially

        }

        // Getter and Setter methods for all properties

        public IntegerProperty idProperty() {
            return id;
        }

        public int getId() {
            return id.get();
        }

        public StringProperty nameProperty() {
            return name;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public StringProperty brandProperty() {
            return brand;
        }

        public String getBrand() {
            return brand.get();
        }

        public void setBrand(String brand) {
            this.brand.set(brand);
        }

        public IntegerProperty priceProperty() {
            return price;
        }

        public int getPrice() {
            return price.get();
        }

        public void setPrice(int price) {
            this.price.set(price);
        }

        public IntegerProperty stockProperty() {
            return stock;
        }

        public int getStock() {
            return stock.get();
        }

        public void setStock(int stock) {
            this.stock.set(stock);
        }

        public IntegerProperty quantityProperty() {
            return quantity;
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
            calculateTotalPrice();  // Update total price when quantity changes
        }
        
        public SimpleIntegerProperty totalPriceProperty() {
            return totalPrice;
        }

        public int getTotalPrice() {
            return totalPrice.get();
        }

        private void calculateTotalPrice() {
            setTotalPrice(getQuantity() * getPrice());
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice.set(totalPrice);
        }
    }
    

}
