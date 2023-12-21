package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import application.UserSession;

import application.ManageProduct.Product;


public class UserDashboard extends Application {

    private DatabaseManager databaseManager;
    private MenuBar menuBar;
    private boolean isLoggedIn = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Dashboard");
        
        databaseManager = new DatabaseManager();

        menuBar = createMenuBar();

        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        // Page Menu for User Role
        Menu pageMenu = new Menu("Menu");
        MenuItem homeMenuItem = new MenuItem("Home");
        MenuItem cartMenuItem = new MenuItem("Cart");
        MenuItem historyMenuItem = new MenuItem("History");
        pageMenu.getItems().addAll(homeMenuItem, cartMenuItem, historyMenuItem);

        // Account Menu
        Menu accountMenu = new Menu("Account");
        MenuItem logoutMenuItem = new MenuItem("Logout");
        accountMenu.getItems().add(logoutMenuItem);

        menuBar.getMenus().addAll(pageMenu, accountMenu);

        // Assigning actions to the menu items
        homeMenuItem.setOnAction(e -> {
            // Example action: navigating to the Home page
            navigateToHomePage();
        });

        cartMenuItem.setOnAction(e -> showCartPage()); // Menambahkan aksi untuk menu Cart

        historyMenuItem.setOnAction(e -> navigateToHistoryPage()); // Mengatur aksi untuk menu History


        logoutMenuItem.setOnAction(e -> logout());

        return menuBar;
    }
    
    private void navigateToHistoryPage() {
        int userId = UserSession.getCurrentUserId(); // Gantilah ini sesuai logika aplikasi Anda

        // Membuat objek HistoryPage dan menampilkannya
        HistoryPage historyPage = new HistoryPage(databaseManager, userId);
        historyPage.display();
    }
    
    private void showCartPage() {
        // Retrieve the current user's ID (you need to replace this with your actual logic)
        int userId = UserSession.getCurrentUserId();

        // Create and show the Cart page
        Cart cartPage = new Cart(databaseManager, userId);
        cartPage.display();
    }
    
    private void navigateToHomePage() {
        // Mengambil data produk dari database
        List<ManageProduct.Product> productList = databaseManager.getAllProducts();

        // Membuat ObservableList untuk menyimpan data produk
        ObservableList<ManageProduct.Product> products = FXCollections.observableArrayList(productList);

        // Membuat TableView baru untuk menampilkan daftar produk
        TableView<ManageProduct.Product> tableView = createProductTable();

        // Menambahkan produk ke dalam TableView
        tableView.setItems(products);

        // Membuat label untuk informasi rinci produk
        Label productDetailLabel = new Label("Detail Produk:");
        Label productNameLabel = new Label("Nama Produk:");
        Label productBrandLabel = new Label("Merek Produk:");
        Label productStockLabel = new Label("Stok Produk:");
        Label productPriceLabel = new Label("Harga Produk:");

        // Membuat Spinner untuk memilih jumlah
        Spinner<Integer> quantitySpinner = new Spinner<>(1, Integer.MAX_VALUE, 1);

        // Membuat tombol untuk menambahkan item ke keranjang
        Button addToCartButton = new Button("Tambah ke Keranjang");

        // Membuat VBox untuk menyimpan informasi rinci produk
        VBox productDetailLayout = new VBox(5); // 5 adalah jarak antar komponen
        productDetailLayout.getChildren().addAll(
                productDetailLabel, productNameLabel, productBrandLabel,
                productStockLabel, productPriceLabel, quantitySpinner, addToCartButton
        );
        productDetailLayout.setAlignment(Pos.CENTER);

        // Membuat GridPane baru untuk halaman utama dengan dua kolom
        GridPane homeLayout = new GridPane();
        homeLayout.setHgap(10); // Mengatur jarak horizontal antar kolom
        homeLayout.add(tableView, 0, 0); // Menambahkan tabel ke kolom kiri (kolom 0)
        homeLayout.add(productDetailLayout, 1, 0); // Menambahkan informasi rinci ke kolom kanan (kolom 1)
        homeLayout.setAlignment(Pos.CENTER);

        // Membuat Scene baru dengan layout GridPane
        Scene homeScene = new Scene(homeLayout, 800, 600);

        // Menetapkan Scene baru pada primary stage
        Stage primaryStage = (Stage) menuBar.getScene().getWindow();
        primaryStage.setScene(homeScene);

        // Menambahkan handler acara klik untuk menangani klik pada baris
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Mendeteksi klik tunggal
                // Mendapatkan item yang dipilih dari tabel
                ManageProduct.Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

                if (selectedProduct != null) {
                    // Memperbarui informasi rinci produk
                    productNameLabel.setText("Nama Produk: " + selectedProduct.getName());
                    productBrandLabel.setText("Merek Produk: " + selectedProduct.getBrand());
                    productStockLabel.setText("Stok Produk: " + selectedProduct.getStock());
                    productPriceLabel.setText("Harga Produk: " + selectedProduct.getPrice());

                    // Menetapkan aksi untuk tombol "Tambah ke Keranjang"
                    addToCartButton.setOnAction(e -> addToCart(selectedProduct, quantitySpinner.getValue()));
                }
            }
        });
    }


    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    
    

    private TableView<Product> createProductTable() {
        TableView<Product> table = new TableView<>();
        table.setEditable(true);

        // Define columns for the TableView
        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Add columns to the TableView
        table.getColumns().addAll(nameCol, brandCol, priceCol, stockCol);

        // Adding click event handler to handle row clicks
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Detect single clicks
                // Get the selected item from the table
                ManageProduct.Product selectedProduct = table.getSelectionModel().getSelectedItem();

                if (selectedProduct != null) {
                    // Handle the selected product (e.g., display details or navigate to update form)
                    System.out.println("Selected Product: " + selectedProduct.getName());
                    // You can add logic here to show details or navigate to update form
                }
            }
        });

        return table;
    }
    
    private void addToCart(ManageProduct.Product product, int quantity) {
        // Get the user ID from the session
        int userId = UserSession.getCurrentUserId();

        if (userId > 0) {
            // User is logged in, proceed with addToCart
            databaseManager.addToCart(userId, product.getId(), quantity);

            // Show information alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add to Cart");
            alert.setHeaderText(null);
            alert.setContentText("Added to Cart: " + product.getName() + " Quantity: " + quantity);
            alert.show();
            start(new Stage());
        } else {
            // User is not logged in, show an error alert or redirect to login page
            showAlert(Alert.AlertType.ERROR, "Error", null, "User not logged in.");
        }
    }

    private void logout() {
        // Menghapus sesi pengguna atau mengatur status logout
        isLoggedIn = false;

        // Mendapatkan referensi ke Stage yang menaungi UserDashboard
        Stage primaryStage = (Stage) menuBar.getScene().getWindow();

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

            // Tutup aplikasi setelah dialog ditutup
            primaryStage.close();
        });

        alert.show();
    }
}
