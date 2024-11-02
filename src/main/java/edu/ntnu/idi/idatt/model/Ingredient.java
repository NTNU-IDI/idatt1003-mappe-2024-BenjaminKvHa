package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an ingredient with a name, quantity, unit of measurement,
 * best-before date, and price per unit in NOK.
 * <p>
 * This class models the properties and behaviors of a grocery item
 * in the inventory system.
 * </p>
 */
public class Ingredient {

  private final String name;
  private double quantity;
  private final String unit;
  private LocalDate bestBeforeDate;
  private final double pricePerUnit;

  /**
   * Constructs an {@code Ingredient} with the specified name, quantity, unit,
   * best-before date, and price per unit.
   *
   * @param name            the name of the ingredient; cannot be null or empty
   * @param quantity        the quantity of the ingredient; must be positive
   * @param unit            the unit of measurement; cannot be null or empty
   * @param bestBeforeDate  the best-before date; cannot be null
   * @param pricePerUnit    the price per unit in NOK; must be positive
   * @throws IllegalArgumentException if any validation rules are violated
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

  /**
   * Returns the name of the ingredient.
   *
   * @return the name of the ingredient
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the quantity of the ingredient.
   *
   * @return the quantity of the ingredient
   */
  public double getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the ingredient.
   *
   * @param quantity the new quantity; must be positive
   * @throws IllegalArgumentException if the quantity is not positive
   */
  public void setQuantity(double quantity) {
    validateQuantity(quantity);
    this.quantity = quantity;
  }

  /**
   * Returns the unit of measurement for the ingredient.
   *
   * @return the unit of measurement
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Returns the best-before date of the ingredient.
   *
   * @return the best-before date
   */
  public LocalDate getBestBeforeDate() {
    return bestBeforeDate;
  }

  /**
   * Sets the best-before date of the ingredient.
   *
   * @param bestBeforeDate the new best-before date; cannot be null
   * @throws IllegalArgumentException if {@code bestBeforeDate} is null
   */
  public void setBestBeforeDate(LocalDate bestBeforeDate) {
    validateBestBeforeDate(bestBeforeDate);
    this.bestBeforeDate = bestBeforeDate;
  }

  /**
   * Returns the price per unit of the ingredient in NOK.
   *
   * @return the price per unit in NOK
   */
  public double getPricePerUnit() {
    return pricePerUnit;
  }

  // Validation methods

  /**
   * Validates the name of the ingredient.
   *
   * @param name the name to validate
   * @throws IllegalArgumentException if {@code name} is null or empty
   */
  private void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
  }

  /**
   * Validates the quantity of the ingredient.
   *
   * @param quantity the quantity to validate
   * @throws IllegalArgumentException if {@code quantity} is not positive
   */
  private void validateQuantity(double quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
  }

  /**
   * Validates the unit of measurement.
   *
   * @param unit the unit to validate
   * @throws IllegalArgumentException if {@code unit} is null or empty
   */
  private void validateUnit(String unit) {
    if (unit == null || unit.trim().isEmpty()) {
      throw new IllegalArgumentException("Unit cannot be null or empty.");
    }
  }

  /**
   * Validates the best-before date.
   *
   * @param bestBeforeDate the date to validate
   * @throws IllegalArgumentException if {@code bestBeforeDate} is null
   */
  private void validateBestBeforeDate(LocalDate bestBeforeDate) {
    if (bestBeforeDate == null) {
      throw new IllegalArgumentException("Best-before date cannot be null.");
    }
  }

  /**
   * Validates the price per unit.
   *
   * @param pricePerUnit the price to validate
   * @throws IllegalArgumentException if {@code pricePerUnit} is not positive
   */
  private void validatePricePerUnit(double pricePerUnit) {
    if (pricePerUnit <= 0) {
      throw new IllegalArgumentException("Price per unit must be positive.");
    }
  }

  /**
   * Returns a string representation of the ingredient.
   *
   * @return a formatted string containing the ingredient's details
   */
  @Override
  public String toString() {
    return String.format(
        "Ingredient{name='%s', quantity=%.2f %s, bestBeforeDate=%s, pricePerUnit=%.2f NOK}",
        name, quantity, unit, bestBeforeDate, pricePerUnit);
  }

  /**
   * Compares this ingredient to the specified object.
   *
   * @param o the object to compare with
   * @return {@code true} if the names and units are equal; {@code false} otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Ingredient)) {
      return false;
    }

    Ingredient that = (Ingredient) o;
    return name.equals(that.name) && unit.equals(that.unit);
  }

  /**
   * Returns a hash code value for the ingredient.
   *
   * @return a hash code based on the name and unit
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, unit);
  }
}
