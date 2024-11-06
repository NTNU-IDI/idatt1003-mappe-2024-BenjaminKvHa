package edu.ntnu.idi.idatt.model;

/**
 * Represents the required quantity and unit of an ingredient in a recipe.
 */
public class IngredientRequirement {

  private double quantity;
  private Unit unit;

  /**
   * Constructs an IngredientRequirement with the specified quantity and unit.
   *
   * @param quantity the required quantity; must be positive
   * @param unit     the unit of measurement; cannot be null
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public IngredientRequirement(double quantity, Unit unit) {
    validateQuantity(quantity);
    validateUnit(unit);
    this.quantity = quantity;
    this.unit = unit;
  }

  public double getQuantity() {
    return quantity;
  }

  public Unit getUnit() {
    return unit;
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
}
