package com.google.codelabs.mdc.java.jionow;

public class FoodItems {
    private String foodName;
    private String quantity;
    private String price;
    private String total;

    public FoodItems(String foodName, String quantity, String price, String total) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}

