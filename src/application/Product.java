package application;

import javafx.beans.property.*;

public class Product {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty brand = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final DoubleProperty totalPrice = new SimpleDoubleProperty();

    // Constructor dan metode lainnya
    public Product(String name, String brand, double price, int quantity) {
        setName(name);
        setBrand(brand);
        setPrice(price);
        setQuantity(quantity);
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

    public DoubleProperty priceProperty() {
        return price;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
        calculateTotalPrice();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
        calculateTotalPrice();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    private void calculateTotalPrice() {
        setTotalPrice(getQuantity() * getPrice());
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public String getProductDetails() {
        return "Name: " + getName() + "\nBrand: " + getBrand() + "\nPrice: " + getPrice();
    }
}
