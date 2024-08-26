package org.example;

public class Phone {
    private String modelName;
    private String brand;
    private double price;
    private int quantity;
    private String specifications;

    // Constructor
    public Phone(String modelName, String brand, double price, int quantity, String specifications) {
        this.modelName = modelName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.specifications = specifications;
    }

    // Getters and Setters
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }

    @Override
    public String toString() {
        return "Model: " + modelName + ", Brand: " + brand + ", Price: $" + price +
                ", Quantity: " + quantity + ", Specifications: " + specifications;
    }
}

