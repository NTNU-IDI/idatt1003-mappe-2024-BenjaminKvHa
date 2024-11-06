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
   * Constructs an Ingredient with the specified name, quantity, unit, best-before date, and price per unit.
   *
   * @param name           the name of the ingredient; cannot be null or empty
   * @param quantity       the quantity of the ingredient; must be positive
   * @param unit           the unit of measurement; cannot be null
   * @param bestBeforeDate the best-before date; cannot be null
   * @param pricePerUnit   the price per unit; must be positive
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Ingredient(String name, double quantity, Unit unit, LocalDate bestBeforeDate, double pricePerUnit) {
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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    validateName(name);
    this.name = name;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    validateQuantity(quantity);
    this.quantity = quantity;
  }

  public Unit getUnit() {
    return unit;
  }

  public void setUnit(Unit unit) {
    validateUnit(unit);
    this.unit = unit;
  }

  public LocalDate getBestBeforeDate() {
    return bestBeforeDate;
  }

  public void setBestBeforeDate(LocalDate bestBeforeDate) {
    validateBestBeforeDate(bestBeforeDate);
    this.bestBeforeDate = bestBeforeDate;
  }

  public double getPricePerUnit() {
    return pricePerUnit;
  }

  public void setPricePerUnit(double pricePerUnit) {
    validatePricePerUnit(pricePerUnit);
    this.pricePerUnit = pricePerUnit;
  }


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

  private void validateUnit(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
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

  // equals and hashCode based on name and unit

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Ingredient that = (Ingredient) o;
    return name.equalsIgnoreCase(that.name) && unit == that.unit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name.toLowerCase(), unit);
  }

  @Override
  public String toString() {
    return String.format("%s: %.2f %s (Best before: %s)", name, quantity, unit.getAbbreviation(), bestBeforeDate);
  }
}
