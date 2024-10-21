package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;

public class Ingredient {

  private final String name;
  private double quantity;
  private final String unit;
  private LocalDate bestBeforeDate;
  private final double pricePerUnit;

  public Ingredient(String name, double quantity, String unit, LocalDate bestBeforeDate, double pricePerUnit) {
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.bestBeforeDate = bestBeforeDate;
    this.pricePerUnit = pricePerUnit;
  }

  public String getName() {
    return name;
  }

  public double getQuantity() {
    return quantity;
  }

  public String getUnit() {
    return unit;
  }

  public LocalDate getBestBeforeDate() {
    return bestBeforeDate;
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }
  //For testing
  public static void main(String[] args) {
    Ingredient ingredient = new Ingredient(
        "Milk",
        1.5,
        "liter",
        LocalDate.of(2024, 12, 31),
        20.0
    );

    System.out.println("Ingredient Details:");
    System.out.println("Name: " + ingredient.getName());
    System.out.println("Quantity: " + ingredient.getQuantity() + " " + ingredient.getUnit());
    System.out.println("Best Before Date: " + ingredient.getBestBeforeDate());
    System.out.println("Price per Unit: " + ingredient.getPricePerUnit() + " NOK");
  }

}
