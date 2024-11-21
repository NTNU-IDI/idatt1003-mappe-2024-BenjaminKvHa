package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an ingredient with a name, quantity, unit, best-before date, and price per unit.
 */
public class Ingredient {

  private String name;
  private double quantity;
  private Unit unit;
  private LocalDate bestBeforeDate;
  private double pricePerUnit;

  /**
   * Constructs an Ingredient with the specified name, quantity, unit, best-before date, and price
   * per unit.
   *
   * @param name           the name of the ingredient; cannot be null or empty
   * @param quantity       the quantity of the ingredient; must be positive
   * @param unit           the unit of measurement; cannot be null
   * @param bestBeforeDate the best-before date; cannot be null
   * @param pricePerUnit   the price per unit; must be positive
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Ingredient(String name, double quantity, Unit unit, LocalDate bestBeforeDate,
      double pricePerUnit) {
    this.name = validateName(name);
    this.quantity = validatePositiveValue(quantity, "Quantity must be positive.");
    this.unit = validateUnit(unit);
    this.bestBeforeDate = validateBestBeforeDate(bestBeforeDate);
    this.pricePerUnit = validatePositiveValue(pricePerUnit, "Price per unit must be positive.");
  }

  // Getters and setters with validation

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = validateName(name);
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = validatePositiveValue(quantity, "Quantity must be positive.");
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    this.unit = validateUnit(unit);
  }

  public LocalDate getBestBeforeDate() {
    return bestBeforeDate;
  }

  public void setBestBeforeDate(LocalDate bestBeforeDate) {
    this.bestBeforeDate = validateBestBeforeDate(bestBeforeDate);
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }

  public void setPricePerUnit(double pricePerUnit) {
    this.pricePerUnit = validatePositiveValue(pricePerUnit, "Price per unit must be positive.");
  }

  // Validation methods

  private String validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
    }
    return name.trim();
  }

  private double validatePositiveValue(double value, String errorMessage) {
    if (value <= 0) {
      throw new IllegalArgumentException(errorMessage);
    }
    return value;
  }

  private Unit validateUnit(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
    return unit;
  }

  private LocalDate validateBestBeforeDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Best-before date cannot be null.");
    }
    return date;
  }

  // equals and hashCode methods based on name and unit

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Ingredient that = (Ingredient) o;
    return name.equalsIgnoreCase(that.name) && unit == that.unit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name.toLowerCase(), unit);
  }

  @Override
  public String toString() {
    return String.format("%s: %.2f %s (Best before: %s)", name, quantity, unit.getAbbreviation(),
        bestBeforeDate);
  }
}
