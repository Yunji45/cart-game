package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
// import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;

public class Main extends Application {
    // Komponen GUI
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;

    // Koneksi ke database
    private DatabaseManager databaseManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        // Membuat MenuBar
        MenuBar menuBar = new MenuBar();

        // Membuat Menu "Navigation" dengan submenu "Login" dan "Register"
        Menu navigationMenu = new Menu("Dashboard");
        MenuItem loginMenuItem = new MenuItem("Login");
        MenuItem registerMenuItem = new MenuItem("Register");
        navigationMenu.getItems().addAll(loginMenuItem, registerMenuItem);

        // Menambahkan Menu ke MenuBar
        menuBar.getMenus().add(navigationMenu);

        // Menambahkan aksi saat menu "Login" dipilih
        loginMenuItem.setOnAction(e -> showLoginScene(primaryStage));

        // Menambahkan aksi saat menu "Register" dipilih
        registerMenuItem.setOnAction(e -> showRegisterScene(primaryStage));

        // Komponen GUI
        Label titleLabel = new Label("Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Label nameLabel = new Label("Njuice");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");

        // Membuat layout grid untuk field username dan password
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Menambahkan label-label dan field-field ke dalam grid
        grid.add(titleLabel, 0, 0, 2, 1);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(loginButton, 0, 2);

        loginButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");

        // Menambahkan jarak antara judul label dengan field username dan password
        GridPane.setMargin(titleLabel, new Insets(0, 0, 20, 0));

        // Membuat layout HBox untuk tombol login
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(loginButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Membuat layout VBox untuk menggabungkan grid dan buttonBox
        VBox vbox = new VBox(10);
        vbox.getChildren().add(menuBar);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(grid, buttonBox);
        vbox.setAlignment(Pos.CENTER);

        // Menambahkan aksi saat tombol login ditekan
        loginButton.setOnAction(e -> login());

        // Membuat scene dan menampilkan stage
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inisialisasi objek DatabaseManager
        databaseManager = new DatabaseManager();
    }

    // Add these methods to handle navigation to login and register scenes
    private void showLoginScene(Stage primaryStage) {
        // Implement the logic to show the login scene
    }

    private void showRegisterScene(Stage primaryStage) {
        Register register = new Register(); // Membuat objek Register
        Stage registerStage = new Stage(); // Membuat stage baru untuk register
        register.start(registerStage); // Memanggil metode start() dari objek Register
        primaryStage.close(); // Menutup stage login (App.java)
    }

//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Login");
//        
//        // Membuat MenuBar
//        MenuBar menuBar = new MenuBar();
//
//        // Membuat Menu "Navigation" dengan submenu "Login" dan "Register"
//        Menu navigationMenu = new Menu("Page");
//        MenuItem loginMenuItem = new MenuItem("Login");
//        MenuItem registerMenuItem = new MenuItem("Register");
//        navigationMenu.getItems().addAll(loginMenuItem, registerMenuItem);
//
//        // Menambahkan Menu ke MenuBar
//        menuBar.getMenus().add(navigationMenu);
//
//        // Menambahkan aksi saat menu "Login" dipilih
////        loginMenuItem.setOnAction(e -> showLoginScene(primaryStage));
//
//        // Menambahkan aksi saat menu "Register" dipilih
////        registerMenuItem.setOnAction(e -> showRegisterScene(primaryStage));
//
//
//        // Komponen GUI
//        Label titleLabel = new Label("Form Login");
//        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
//        Label usernameLabel = new Label("Username:");
//        Label passwordLabel = new Label("Password:");
//        usernameField = new TextField();
//        passwordField = new PasswordField();
//        loginButton = new Button("Login");
//        registerButton = new Button("Register");
//
//        // Membuat layout grid untuk field username dan password
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setPadding(new Insets(10));
//        grid.setVgap(5);
//        grid.setHgap(5);
//
//        // Menambahkan label-label dan field-field ke dalam grid
//        grid.add(titleLabel, 0, 0, 2, 1);
//        grid.add(usernameLabel, 0, 1);
//        grid.add(usernameField, 1, 1);
//        grid.add(passwordLabel, 0, 2);
//        grid.add(passwordField, 1, 2);
//        grid.add(loginButton, 0, 2);
//        grid.add(registerButton, 0, 3);
//
//        loginButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
//        registerButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
//
//        // Menambahkan jarak antara judul label dengan field username dan password
//        GridPane.setMargin(titleLabel, new Insets(0, 0, 20, 0));
//
//        // Membuat layout HBox untuk tombol login dan register
//        HBox buttonBox = new HBox(10);
//        buttonBox.getChildren().addAll(loginButton, registerButton);
//        buttonBox.setAlignment(Pos.CENTER);
//
//        // Membuat layout VBox untuk menggabungkan grid dan buttonBox
//        VBox vbox = new VBox(10);
//        vbox.getChildren().add(menuBar);
//        vbox.setPadding(new Insets(10));
//        vbox.getChildren().addAll(grid, buttonBox);
//        vbox.setAlignment(Pos.CENTER);
//        
//        // Menambahkan aksi saat tombol login ditekan
//        loginButton.setOnAction(e -> login());
//
//        // Menambahkan aksi saat tombol register ditekan
//        registerButton.setOnAction(event -> {
//            Register register = new Register(); // Membuat objek Register
//            Stage registerStage = new Stage(); // Membuat stage baru untuk register
//            register.start(registerStage); // Memanggil metode start() dari objek Register
//            primaryStage.close(); // Menutup stage login (App.java)
//        });
//            
//        // Membuat scene dan menampilkan stage
//        Scene scene = new Scene(vbox, 300, 200);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        // Inisialisasi objek DatabaseManager
//        databaseManager = new DatabaseManager();
//    }

    @Override
    public void stop() {
        // Menutup koneksi database saat aplikasi berhenti
        databaseManager.closeConnection();
    }
    
    private void login() {
        String email = usernameField.getText();
        String password = passwordField.getText();

        try {
            Connection connection = databaseManager.getConnection();

            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // If login is successful, retrieve the user ID
                        int userId = resultSet.getInt("id");

                        // Store the user ID in the UserSession or another session management class
                        UserSession.setCurrentUserId(userId);

                        String role = resultSet.getString("role");
                        if ("admin".equalsIgnoreCase(role)) {
                            System.out.println("Login berhasil sebagai admin");
                            showAdminDashboard();
                        } else if ("user".equalsIgnoreCase(role)) {
                            System.out.println("Login berhasil sebagai user");
                            showUserDashboard();
                        } else {
                            System.out.println("Login berhasil dengan role: " + role);
                        }
                    } else {
                        showAlert(AlertType.ERROR, "Login Gagal", null, "Email atau password salah.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    //admin
    private void showAdminDashboard() {
        AdminDashboard adminDashboard = new AdminDashboard(); // Membuat objek AdminDashboard
        Stage dashboardStage = new Stage(); // Membuat stage baru untuk dashboard admin
        adminDashboard.start(dashboardStage); // Memanggil metode start() dari objek AdminDashboard
    
        // Menutup stage login (App.java)
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }
    
    //User
    private void showUserDashboard() {
        UserDashboard userDashboard = new UserDashboard(); // membuat objek user
        Stage dashboardStage = new Stage(); // Membuat stage baru untuk dashboard admin
        userDashboard.start(dashboardStage); // Memanggil metode start() dari objek 
    
        // Menutup stage login (App.java)
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    //pesan error ketika username dan password salah
    private void pesan() {
        // Contoh: Tampilkan dialog informasi dan tutup aplikasi
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Silahkan Login dengan Username dan password yang benar.");
        alert.show();
    }

}