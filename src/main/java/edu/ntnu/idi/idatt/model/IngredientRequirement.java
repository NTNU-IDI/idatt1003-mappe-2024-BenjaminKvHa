package edu.ntnu.idi.idatt.model;

/**
 * Represents the required quantity and unit of an ingredient in a recipe.
 */
public class IngredientRequirement {

  private final double quantity;
  private final Unit unit;

  /**
   * Constructs an IngredientRequirement with the specified quantity and unit.
   *
   * @param quantity the required quantity; must be positive
   * @param unit     the unit of measurement; cannot be null
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public IngredientRequirement(double quantity, Unit unit) {
    this.quantity = validateQuantity(quantity);
    this.unit = validateUnit(unit);
  }

  /**
   * Gets the required quantity of the ingredient.
   *
   * @return the required quantity
   */
  public double getQuantity() {
    return quantity;
  }

  /**
   * Gets the unit of measurement for the quantity.
   *
   * @return the unit of measurement
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   * Validates that the quantity is positive.
   *
   * @param quantity the quantity to validate
   * @return the validated quantity
   * @throws IllegalArgumentException if the quantity is not positive
   */
  private double validateQuantity(double quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
    return quantity;
  }

  /**
   * Validates that the unit is not null.
   *
   * @param unit the unit to validate
   * @return the validated unit
   * @throws IllegalArgumentException if the unit is null
   */
  private Unit validateUnit(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
    return unit;
  }
}
