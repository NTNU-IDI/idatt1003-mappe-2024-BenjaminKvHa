package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Ingredient class.
 */
public class Ingredient {

  private final String name;
  private double quantity;
  private final String unit;
  private LocalDate bestBeforeDate;
  private final double pricePerUnit;

  /**
   * Constructs an Ingredient with the specified name, quantity, unit, best-before date, and price
   * per unit.
   */
  public Ingredient(String name, double quantity, String unit, LocalDate bestBeforeDate,
      double pricePerUnit) {
    validateName(name);
    validateQuantity(quantity);
    validateUnit(unit);
    validateBestBeforeDate(bestBeforeDate);
    validatePricePerUnit(pricePerUnit);

    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.bestBeforeDate = bestBeforeDate;
    this.pricePerUnit = pricePerUnit;
  }

  // Getters and setters

  public String getName() {
    return name;
  }

  public double getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the ingredient.
   */
  public void setQuantity(double quantity) {
    validateQuantity(quantity);
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public LocalDate getBestBeforeDate() {
    return bestBeforeDate;
  }

  /**
   * Sets the best-before date.
   */
  public void setBestBeforeDate(LocalDate bestBeforeDate) {
    validateBestBeforeDate(bestBeforeDate);
    this.bestBeforeDate = bestBeforeDate;
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }

  // Validation methods

  private void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
  }

  private void validateQuantity(double quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
  }

  private void validateUnit(String unit) {
    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException("Unit cannot be null or empty.");
    }
  }

  private void validateBestBeforeDate(LocalDate bestBeforeDate) {
    if (bestBeforeDate == null) {
      throw new IllegalArgumentException("Best-before date cannot be null.");
    }
  }

  private void validatePricePerUnit(double pricePerUnit) {
    if (pricePerUnit <= 0) {
      throw new IllegalArgumentException("Price per unit must be positive.");
    }
  }

  @Override
  public String toString() {
    return String.format(
        "Ingredient{name='%s', quantity=%.2f %s, bestBeforeDate=%s, pricePerUnit=%.2f NOK}",
        name, quantity, unit, bestBeforeDate, pricePerUnit);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Ingredient that = (Ingredient) o;
    return name.equals(that.name) && unit.equals(that.unit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, unit);
  }
}

