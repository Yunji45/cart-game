package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import application.History;

import java.util.List;

public class ViewPageHistory {

    private DatabaseManager databaseManager;
    private int adminId;  // Gantilah dengan logika Anda untuk mendapatkan ID Admin

    public ViewPageHistory(DatabaseManager databaseManager, int adminId) {
        this.databaseManager = databaseManager;
        this.adminId = adminId;
    }

    public void display() {
        Stage stage = new Stage();
        stage.setTitle("View History");

        // Membuat kolom-kolom untuk TableView
        TableColumn<History, Integer> transactionIdColumn = new TableColumn<>("Transaction ID");
        transactionIdColumn.setCellValueFactory(cellData -> cellData.getValue().transactionIdProperty().asObject());

        TableColumn<History, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());

        TableColumn<History, Integer> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty().asObject());

        TableColumn<History, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        TableColumn<History, Integer> totalPriceColumn = new TableColumn<>("Total Price");
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());

        TableColumn<History, String> transactionDateColumn = new TableColumn<>("Transaction Date");
        transactionDateColumn.setCellValueFactory(cellData -> cellData.getValue().transactionDateProperty());

        // Membuat TableView
        TableView<History> tableView = new TableView<>();
        tableView.getColumns().addAll(
                transactionIdColumn, userIdColumn, productIdColumn,
                quantityColumn, totalPriceColumn, transactionDateColumn);

        // Mendapatkan semua entri histori dari database
        List<History> historyEntries = databaseManager.getAllHistoryEntries();

        // Membuat ObservableList untuk TableView
        ObservableList<History> historyList = FXCollections.observableArrayList(historyEntries);

        // Menambahkan data ke TableView
        tableView.setItems(historyList);

        // Membuat layout VBox
        VBox vbox = new VBox();
        vbox.setSpacing(10);  // Jarak antar elemen
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(tableView);

        // Membuat Scene
        Scene scene = new Scene(vbox, 800, 600);

        // Menetapkan Scene pada stage
        stage.setScene(scene);

        // Menampilkan stage
        stage.show();
    }
}
