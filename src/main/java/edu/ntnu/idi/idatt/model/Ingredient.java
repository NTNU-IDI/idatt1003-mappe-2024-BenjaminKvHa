package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;

public class Ingredient {

  private final String name;
  private double quantity;
  private final String unit;
  private LocalDate bestBeforeDate;
  private final double pricePerUnit;

  public Ingredient(String name, double quantity, String unit, LocalDate bestBeforeDate,
      double pricePerUnit) {
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

  @Override
  public String toString() {
    return "Ingredient{" +
        "name='" + name + '\'' +
        ", quantity=" + quantity +
        ", unit='" + unit + '\'' +
        ", bestBeforeDate=" + bestBeforeDate +
        ", pricePerUnit=" + pricePerUnit +
        '}';
  }
}
