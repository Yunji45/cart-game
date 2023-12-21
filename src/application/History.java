package application;

import java.sql.Timestamp;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class History {
    private final SimpleIntegerProperty transactionId;
    private final SimpleIntegerProperty userId;
    private final SimpleIntegerProperty productId;
    private final SimpleIntegerProperty quantity;
    private final SimpleIntegerProperty totalPrice;
    private final SimpleStringProperty transactionDate;

    public History(int userId, int productId, int quantity, int totalPrice, Timestamp transactionDate) {
        this.transactionId = new SimpleIntegerProperty(0);  // Default value, bisa diabaikan jika diisi oleh database (Auto Increment)
        this.userId = new SimpleIntegerProperty(userId);
        this.productId = new SimpleIntegerProperty(productId);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.totalPrice = new SimpleIntegerProperty(totalPrice);
        this.transactionDate = new SimpleStringProperty(transactionDate.toString());
    }

    // Getter dan setter lainnya

    public int getTransactionId() {
        return transactionId.get();
    }

    public void setTransactionId(int transactionId) {
        this.transactionId.set(transactionId);
    }

    public int getUserId() {
        return userId.get();
    }

    public int getProductId() {
        return productId.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public int getTotalPrice() {
        return totalPrice.get();
    }

    public String getTransactionDate() {
        return transactionDate.get();
    }

    public SimpleIntegerProperty transactionIdProperty() {
        return transactionId;
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public SimpleIntegerProperty productIdProperty() {
        return productId;
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public SimpleIntegerProperty totalPriceProperty() {
        return totalPrice;
    }

    public SimpleStringProperty transactionDateProperty() {
        return transactionDate;
    }
}


//public class History {
//    private int transactionId;
//    private final int userId;
//    private final int productId;
//    private final int quantity;
//    private final int totalPrice;
//    private final Timestamp transactionDate;
//
//    public History(int userId, int productId, int quantity, int totalPrice, Timestamp transactionDate) {
//        this.userId = userId;
//        this.productId = productId;
//        this.quantity = quantity;
//        this.totalPrice = totalPrice;
//        this.transactionDate = transactionDate;
//    }
//
//    public int getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(int transactionId) {
//        this.transactionId = transactionId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public int getProductId() {
//        return productId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public int getTotalPrice() {
//        return totalPrice;
//    }
//
//    public Timestamp getTransactionDate() {
//        return transactionDate;
//    }
//}


//package application;
//
//import java.sql.Timestamp;
//
//public class History {
//    private int transactionId; // Perubahan: tidak menggunakan kata kunci 'final'
//    private final int userId;
//    private final int productId;
//    private final int quantity;
//    private final int totalPrice;
//    private final Timestamp transactionDate;
//
//    public History(int userId, int productId, int quantity, int totalPrice, Timestamp transactionDate) {
//        this.userId = userId;
//        this.productId = productId;
//        this.quantity = quantity;
//        this.totalPrice = totalPrice;
//        this.transactionDate = transactionDate;
//    }
//
//    // Getter untuk atribut-atribut history entry
//    public int getTransactionId() {
//        return transactionId;
//    }
//
//    // Perubahan: tambahkan metode setTransactionId
//    public void setTransactionId(int transactionId) {
//        this.transactionId = transactionId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public int getProductId() {
//        return productId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public int getTotalPrice() {
//        return totalPrice;
//    }
//
//    public Timestamp getTransactionDate() {
//        return transactionDate;
//    }
//}
