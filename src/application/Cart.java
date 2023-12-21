package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import application.ManageProduct.Product;
import java.sql.Timestamp;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.util.List;

public class Cart {

    private final DatabaseManager databaseManager;
    private final int userId;
    
    private TableView<Product> cartTableView;
    private Label totalPriceLabel;



    public Cart(DatabaseManager databaseManager, int userId) {
        this.databaseManager = databaseManager;
        this.userId = userId;
    }
    
    public void display() {
        Stage cartStage = new Stage();
        cartStage.setTitle("Shopping Cart");
        // Konten halaman keranjang belanja
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);  // Menetapkan posisi VBox menjadi di tengah

        // Labels
        Label cartListLabel = new Label("Your Cart List");
        cartListLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalPriceLabel = new Label("Cart’s Total Price");  // Inisialisasi atribut totalPriceLabel
//        Label productDetailLabel = new Label("Product Detail:");

        // TableView untuk menampilkan item keranjang
        cartTableView = createCartTableView();  // Inisialisasi atribut cartTableView

        // Dapatkan item keranjang dari database
        List<Product> cartItems = databaseManager.getCartItems(userId);
        ObservableList<Product> cartItemList = FXCollections.observableArrayList(cartItems);
        cartTableView.setItems(cartItemList);

        // Tambahkan kolom "Total Price" ke dalam TableView
        TableColumn<Product, Integer> totalPriceCol = new TableColumn<>("Total Price");
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        cartTableView.getColumns().add(totalPriceCol);

        // Tombol untuk menghapus dari keranjang
        Button removeButton = new Button("Remove from Cart");
        removeButton.setOnAction(e -> removeFromCart(cartTableView.getSelectionModel().getSelectedItem()));

        // Tombol untuk checkout
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setOnAction(e -> checkout());

        // HBox untuk menempatkan tombol secara sejajar
        HBox buttonsBox = new HBox(10); // Jarak antar elemen 10 piksel
        buttonsBox.setAlignment(Pos.CENTER); // Menetapkan posisi HBox menjadi di tengah
        buttonsBox.getChildren().addAll(removeButton, checkoutButton);

        // Tambahkan elemen ke dalam layout
        layout.getChildren().addAll(cartListLabel, cartTableView, totalPriceLabel, buttonsBox);

        // Hitung dan tampilkan total harga belanja
        double cartTotalPrice = cartItems.stream().mapToDouble(Product::getTotalPrice).sum();
        totalPriceLabel.setText("Cart’s Total Price: Rp." + cartTotalPrice);

        // Setelah menambahkan semua elemen, buat scene dan tampilkan stage
        Scene scene = new Scene(layout, 600, 400);
        cartStage.setScene(scene);
        cartStage.show();
    }

    
//    public void display() {
//        Stage cartStage = new Stage();
//        cartStage.setTitle("Shopping Cart");
//        // Konten halaman keranjang belanja
//        VBox layout = new VBox(10);
//        layout.setAlignment(Pos.CENTER);  // Menetapkan posisi VBox menjadi di tengah
//
//        // Labels
//        Label cartListLabel = new Label("Your Cart List");
//        cartListLabel.setStyle("-fx-font-weight: bold;"); // Mengatur tulisan menjadi tebal
//        totalPriceLabel = new Label("Cart’s Total Price");  // Inisialisasi atribut totalPriceLabel
//        Label productDetailLabel = new Label("Product Detail:");
//
//        // TableView untuk menampilkan item keranjang
//        cartTableView = createCartTableView();  // Inisialisasi atribut cartTableView
//
//        // Dapatkan item keranjang dari database
//        List<Product> cartItems = databaseManager.getCartItems(userId);
//        ObservableList<Product> cartItemList = FXCollections.observableArrayList(cartItems);
//        cartTableView.setItems(cartItemList);
//
//        // Tambahkan kolom "Total Price" ke dalam TableView
//        TableColumn<Product, Integer> totalPriceCol = new TableColumn<>("Total Price");
//        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
//        cartTableView.getColumns().add(totalPriceCol);
//
//        // Tombol untuk menghapus dari keranjang
//        Button removeButton = new Button("Remove from Cart");
//        removeButton.setOnAction(e -> removeFromCart(cartTableView.getSelectionModel().getSelectedItem()));
//
//        // Tombol untuk checkout
//        Button checkoutButton = new Button("Checkout");
//        checkoutButton.setOnAction(e -> checkout());
//
//        // Tambahkan elemen ke dalam layout
//        layout.getChildren().addAll(cartListLabel, cartTableView, totalPriceLabel, productDetailLabel, removeButton, checkoutButton);
//
//        // Menambahkan elemen untuk menampilkan detail produk di keranjang belanja
//        HBox productDetailBox = new HBox(10);
//        Label productNameLabel = new Label("Product Name:");
//        Label productPriceLabel = new Label("Product Price:");
//        Label productQuantityLabel = new Label("Quantity:");
//
//        productDetailBox.getChildren().addAll(productNameLabel, productPriceLabel, productQuantityLabel);
//        layout.getChildren().add(productDetailBox);
//
//        // Hitung dan tampilkan total harga belanja
//        double cartTotalPrice = cartItems.stream().mapToDouble(Product::getTotalPrice).sum();
//        totalPriceLabel.setText("Cart’s Total Price: " + cartTotalPrice);
//
//        // Setelah menambahkan semua elemen, buat scene dan tampilkan stage
//        Scene scene = new Scene(layout, 600, 400);
//        cartStage.setScene(scene);
//        cartStage.show();
//    }
   
    private TableView<Product> createCartTableView() {
        TableView<Product> table = new TableView<>();
        table.setEditable(false);

        // Tentukan kolom-kolom untuk TableView
        TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Product, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(cellData -> cellData.getValue().brandProperty());

        TableColumn<Product, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        TableColumn<Product, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

//        TableColumn<Product, Integer> totalPriceCol = new TableColumn<>("Total Price");
//        totalPriceCol.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        // Tambahkan kolom ke dalam TableView
        table.getColumns().addAll(nameCol, brandCol, quantityCol, priceCol);

        // Menambahkan handler acara klik untuk menangani klik baris
        table.setOnMouseClicked(event -> handleRowClick(table));

        return table;
    }


    private void handleRowClick(TableView<Product> table) {
        if (table.getSelectionModel().isEmpty()) {
            return; // Tidak ada yang dipilih, keluar dari metode
        }

        // Tangani produk yang dipilih di sini
        Product selectedProduct = table.getSelectionModel().getSelectedItem();
        System.out.println("Selected Product: " + selectedProduct.getName());
    }

    private void removeFromCart(Product selectedProduct) {
        if (selectedProduct != null) {
            // Hapus item dari keranjang
            databaseManager.removeFromCart(userId, selectedProduct.getId());

            // Dapatkan item keranjang yang diperbarui dari database
            List<Product> updatedCartItems = databaseManager.getCartItems(userId);
            ObservableList<Product> updatedCartItemList = FXCollections.observableArrayList(updatedCartItems);
            cartTableView.setItems(updatedCartItemList);

            // Hitung dan perbarui total harga belanja
            double cartTotalPrice = updatedCartItems.stream().mapToDouble(Product::getTotalPrice).sum();
            totalPriceLabel.setText("Cart’s Total Price: Rp." + cartTotalPrice);

            // Refresh TableView untuk menampilkan pembaruan
            refreshCartTable();
        }
    }

    private void checkout() {
        // Dapatkan item keranjang yang diperbarui dari database
        List<Product> cartItems = databaseManager.getCartItems(userId);

        // Hanya checkout jika keranjang tidak kosong
        if (!cartItems.isEmpty()) {
            // Implementasikan logika checkout di sini
            // Anda dapat menggunakan data dalam cartItems untuk membuat entri riwayat (history)
            
            // Contoh: Membuat entri riwayat untuk setiap produk dalam keranjang
            for (Product cartItem : cartItems) {
                History historyEntry = new History(userId, cartItem.getId(), cartItem.getQuantity(),
                        cartItem.getTotalPrice(), new Timestamp(System.currentTimeMillis()));

                // Tambahkan entri riwayat ke database
                databaseManager.addHistoryEntry(historyEntry);
            }

            // Hapus semua item dari keranjang
            databaseManager.clearCart(userId);

            // Tampilkan pesan bahwa checkout berhasil
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Checkout successful!", ButtonType.OK);
            alert.showAndWait();

            // Refresh TableView untuk menampilkan pembaruan
            refreshCartTable();
        } else {
            // Tampilkan pesan bahwa keranjang kosong
            Alert alert = new Alert(Alert.AlertType.WARNING, "Your cart is empty!", ButtonType.OK);
            alert.showAndWait();
        }
    }
    

    private void refreshCartTable() {
        TableView<Product> newCartTableView = createCartTableView();
        VBox layout = (VBox) cartTableView.getScene().getRoot();
        layout.getChildren().set(1, newCartTableView);
        cartTableView = newCartTableView;  // Inisialisasi cartTableView dengan yang baru
    }

}
