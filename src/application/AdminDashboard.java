package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;

public class AdminDashboard extends Application {

	private boolean isLoggedIn = true;
    private DatabaseManager databaseManager = new DatabaseManager(); // Deklarasi dan inisialisasi databaseManager
    private int adminId = 1; // Ganti dengan nilai sesuai kebutuhan atau cara Anda mendapatkan adminId

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ADMIN");

        // Membuat MenuBar
        MenuBar menuBar = createMenuBar(primaryStage);

        // Membuat tombol Logout
        Button logoutButton = new Button("Logout");

        // Menambahkan aksi saat tombol Logout diklik
        logoutButton.setOnAction(event -> {
            logout(primaryStage);
        });

        // Membuat layout VBox untuk menyusun elemen secara vertikal
        VBox vbox = new VBox();
        vbox.setSpacing(10); // Memberikan jarak antar elemen
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(menuBar, logoutButton);

        // Menampilkan scene
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Menampilkan alert selamat datang
        showWelcomeAlert();
    }

    // Logika logout
    private void logout(Stage primaryStage) {
        // Menghapus sesi pengguna atau mengatur status logout
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

    // Menampilkan Alert selamat datang saat Admin Dashboard terbuka
    private void showWelcomeAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Selamat Datang");
        alert.setHeaderText("Halo, Admin!");
        alert.setContentText("Selamat datang di Dashboard Admin.");

        alert.showAndWait();
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
        manageProductMenuItem.setOnAction(e -> openManageProductScene(primaryStage));

        viewHistoryMenuItem.setOnAction(e -> {
            // Create an instance of ViewHistoryPage and display it
            ViewPageHistory viewPageHistory = new ViewPageHistory(databaseManager, adminId);
            viewPageHistory.display();
        });


        logoutMenuItem.setOnAction(e -> logout(primaryStage));

        return menuBar;
    }
    


    private void openManageProductScene(Stage primaryStage) {
        if (isLoggedIn) {
            Stage manageProductStage = new Stage();
            ManageProduct manageProduct = new ManageProduct();
            manageProduct.start(manageProductStage);
        }
    }
}
