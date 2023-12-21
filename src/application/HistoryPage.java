package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;


public class HistoryPage {
    private final DatabaseManager databaseManager;
    private final int userId;

    public HistoryPage(DatabaseManager databaseManager, int userId) {
        this.databaseManager = databaseManager;
        this.userId = userId;
    }

    public void display() {
        // Membuat tabel untuk menampilkan data History
        TableView<History> historyTable = new TableView<>();

        // Membuat kolom untuk setiap atribut di kelas History
        TableColumn<History, Integer> transactionIdColumn = new TableColumn<>("Transaction ID");
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));

        TableColumn<History, Integer> productIdColumn = new TableColumn<>("Product ID");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));

        TableColumn<History, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<History, Integer> totalPriceColumn = new TableColumn<>("Total Price");
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<History, String> transactionDateColumn = new TableColumn<>("Transaction Date");
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        // Menambahkan kolom ke dalam tabel
        historyTable.getColumns().addAll(
                transactionIdColumn, productIdColumn, quantityColumn, totalPriceColumn, transactionDateColumn);

        // Mendapatkan daftar history untuk user tertentu
        List<History> historyEntries = databaseManager.getHistoryEntries(userId);

        // Mengonversi list ke ObservableList (dibutuhkan oleh TableView)
        ObservableList<History> observableHistory = FXCollections.observableArrayList(historyEntries);

        // Menetapkan data ObservableList ke dalam tabel
        historyTable.setItems(observableHistory);

        // Membuat layout VBox untuk menempatkan tabel
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(historyTable);

        // Membuat Scene baru dengan layout VBox
        Scene scene = new Scene(vbox, 800, 600);

        // Membuat dan menetapkan Stage
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("History Page");
        stage.show();
    }
}
