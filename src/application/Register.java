package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Register extends Application {
    // Komponen GUI
    private TextField emailField;
    private PasswordField passwordRegisterField;
    private PasswordField confirmPasswordField;
    private ToggleGroup genderToggleGroup;
    private ComboBox<String> nationalityComboBox;
    private Spinner<Integer> ageSpinner;
    private Button registerButton;
    // Objek DatabaseManager
    private DatabaseManager databaseManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Register");

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

        // Komponen GUI untuk Register
        Label registerTitleLabel = new Label("Register Form");
        registerTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        Label emailLabel = new Label("Email:");
        Label passwordRegisterLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");
        Label genderLabel = new Label("Gender:");
        Label nationalityLabel = new Label("Nationality:");
        Label ageLabel = new Label("Age:");

        emailField = new TextField();
        passwordRegisterField = new PasswordField();
        confirmPasswordField = new PasswordField();

        genderToggleGroup = new ToggleGroup();
        RadioButton maleRadioButton = new RadioButton("Male");
        maleRadioButton.setToggleGroup(genderToggleGroup);
        RadioButton femaleRadioButton = new RadioButton("Female");
        femaleRadioButton.setToggleGroup(genderToggleGroup);

        nationalityComboBox = new ComboBox<>();
        nationalityComboBox.getItems().addAll("USA", "UK", "Canada", "Australia", "Other");

        SpinnerValueFactory<Integer> ageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 120, 18);
        ageSpinner = new Spinner<>(ageValueFactory);

        registerButton = new Button("Register");

        // Membuat layout grid untuk field register
        GridPane registerGrid = new GridPane();
        registerGrid.setAlignment(Pos.CENTER);
        registerGrid.setPadding(new Insets(10));
        registerGrid.setVgap(5);
        registerGrid.setHgap(5);

        // Menambahkan label-label dan field-field ke dalam grid
        registerGrid.add(registerTitleLabel, 0, 0, 2, 1);
        registerGrid.add(emailLabel, 0, 1);
        registerGrid.add(emailField, 1, 1);
        registerGrid.add(passwordRegisterLabel, 0, 2);
        registerGrid.add(passwordRegisterField, 1, 2);
        registerGrid.add(confirmPasswordLabel, 0, 3);
        registerGrid.add(confirmPasswordField, 1, 3);
        registerGrid.add(genderLabel, 0, 4);
        registerGrid.add(maleRadioButton, 1, 4);
        registerGrid.add(femaleRadioButton, 2, 4);
        registerGrid.add(nationalityLabel, 0, 5);
        registerGrid.add(nationalityComboBox, 1, 5);
        registerGrid.add(ageLabel, 0, 6);
        registerGrid.add(ageSpinner, 1, 6);
        registerGrid.add(registerButton, 0, 7);

        registerButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white;");

        // Menambahkan jarak antara judul label dengan field-field register
        GridPane.setMargin(registerTitleLabel, new Insets(0, 0, 20, 0));

        // Membuat layout VBox untuk menggabungkan grid register
        VBox registerVBox = new VBox(10);
        registerVBox.setPadding(new Insets(10));
        registerVBox.getChildren().addAll(registerGrid);
        registerVBox.setAlignment(Pos.CENTER);

        registerButton.setOnAction(e -> register());

        // Membuat scene dan menampilkan stage
        Scene registerScene = new Scene(registerVBox, 300, 350);
        VBox vbox = new VBox(10);
        vbox.getChildren().add(menuBar);
        vbox.getChildren().add(registerVBox);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 300, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Membuat objek DatabaseManager
        databaseManager = new DatabaseManager();
    }

//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Register");
//        
//     // Membuat MenuBar
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
//        loginMenuItem.setOnAction(e -> showLoginScene(primaryStage));
//
//        // Komponen GUI untuk Register
//        Label registerTitleLabel = new Label("Register Form");
//        registerTitleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
//        Label emailLabel = new Label("Email:");
//        Label passwordRegisterLabel = new Label("Password:");
//        Label confirmPasswordLabel = new Label("Confirm Password:");
//        Label genderLabel = new Label("Gender:");
//        Label nationalityLabel = new Label("Nationality:");
//        Label ageLabel = new Label("Age:");
//
//        emailField = new TextField();
//        passwordRegisterField = new PasswordField();
//        confirmPasswordField = new PasswordField();
//
//        genderToggleGroup = new ToggleGroup();
//        RadioButton maleRadioButton = new RadioButton("Male");
//        maleRadioButton.setToggleGroup(genderToggleGroup);
//        RadioButton femaleRadioButton = new RadioButton("Female");
//        femaleRadioButton.setToggleGroup(genderToggleGroup);
//
//        nationalityComboBox = new ComboBox<>();
//        nationalityComboBox.getItems().addAll("USA", "UK", "Canada", "Australia", "Other");
//
//        SpinnerValueFactory<Integer> ageValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 120, 18);
//        ageSpinner = new Spinner<>(ageValueFactory);
//
//        registerButton = new Button("Register");
//
//        // Membuat layout grid untuk field register
//        GridPane registerGrid = new GridPane();
//        registerGrid.setAlignment(Pos.CENTER);
//        registerGrid.setPadding(new Insets(10));
//        registerGrid.setVgap(5);
//        registerGrid.setHgap(5);
//
//        // Menambahkan label-label dan field-field ke dalam grid
//        registerGrid.add(registerTitleLabel, 0, 0, 2, 1);
//        registerGrid.add(emailLabel, 0, 1);
//        registerGrid.add(emailField, 1, 1);
//        registerGrid.add(passwordRegisterLabel, 0, 2);
//        registerGrid.add(passwordRegisterField, 1, 2);
//        registerGrid.add(confirmPasswordLabel, 0, 3);
//        registerGrid.add(confirmPasswordField, 1, 3);
//        registerGrid.add(genderLabel, 0, 4);
//        registerGrid.add(maleRadioButton, 1, 4);
//        registerGrid.add(femaleRadioButton, 2, 4);
//        registerGrid.add(nationalityLabel, 0, 5);
//        registerGrid.add(nationalityComboBox, 1, 5);
//        registerGrid.add(ageLabel, 0, 6);
//        registerGrid.add(ageSpinner, 1, 6);
//        registerGrid.add(registerButton, 0, 7);
//
//        registerButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
//
//        // Menambahkan jarak antara judul label dengan field-field register
//        GridPane.setMargin(registerTitleLabel, new Insets(0, 0, 20, 0));
//
//        // Membuat layout VBox untuk menggabungkan grid register
//        VBox registerVBox = new VBox(10);
//        registerVBox.setPadding(new Insets(10));
//        registerVBox.getChildren().addAll(registerGrid);
//        registerVBox.setAlignment(Pos.CENTER);
//        
//        registerButton.setOnAction(e -> register());
//
//
//        // Membuat scene dan menampilkan stage
//        Scene registerScene = new Scene(registerVBox, 300, 350);
//        primaryStage.setScene(registerScene);
//        primaryStage.show();
//
//        // Membuat objek DatabaseManager
//        databaseManager = new DatabaseManager();
//    }
    
    private void showLoginScene(Stage primaryStage) {
        Main login = new Main(); // Assuming Main is the class containing your login logic
        Stage loginStage = new Stage(); // Create a new Stage for the login
        login.start(loginStage); // Call the start() method from the Main class
        primaryStage.close(); // Close the current stage (Register form)
    }

    @Override
    public void stop() {
        // Menutup koneksi database saat aplikasi berhenti
        if (databaseManager != null) {
            databaseManager.closeConnection();
        }
    }

    // Metode untuk mendaftar pengguna
 // Metode untuk mendaftar pengguna
    private void register() {
        String email = emailField.getText();
        String password = passwordRegisterField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String gender = ((RadioButton) genderToggleGroup.getSelectedToggle()).getText();
        String nationality = nationalityComboBox.getValue();
        int age = ageSpinner.getValue();
        String role = "user";

        if (password.equals(confirmPassword)) {
            try {
                // Panggil metode insertUser dengan informasi pengguna
                databaseManager.insertUser(email, password, gender, nationality, age, role);

                // Menampilkan alert setelah registrasi berhasil
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("User registered successfully!");
                alert.showAndWait();

                // Mengarahkan pengguna ke halaman login
                Main login = new Main();
                Stage loginStage = new Stage();
                login.start(loginStage);

                // Menutup stage register (Register.java)
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                // Menangani pengecualian jika email sudah terdaftar
                if (e.getErrorCode() == 1062) { // 1062 adalah kode error untuk duplicate entry
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Registration Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Email is already registered. Please use a different email.");
                    alert.showAndWait();
                } else {
                    System.out.println("Failed to register user: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Password and Confirm Password do not match.");
        }
    }

}