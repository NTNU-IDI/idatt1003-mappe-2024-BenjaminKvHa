package edu.ntnu.idi.idatt.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a recipe with a name, description, preparation method,
 * a list of ingredients with required quantities and units, and the number of servings.
 */
public class Recipe {

  private String name;
  private String description;
  private String preparationMethod;
  private Map<String, IngredientRequirement> ingredients;
  private int servings;

  /**
   * Constructs a Recipe with the specified name, description, preparation method,
   * and number of servings.
   *
   * @param name              the name of the recipe; cannot be null or empty
   * @param description       a short description; cannot be null or empty
   * @param preparationMethod the preparation method; cannot be null or empty
   * @param servings          the number of servings; must be positive
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Recipe(String name, String description, String preparationMethod, int servings) {
    validateName(name);
    validateDescription(description);
    validatePreparationMethod(preparationMethod);
    validateServings(servings);

    this.name = name;
    this.description = description;
    this.preparationMethod = preparationMethod;
    this.servings = servings;
    this.ingredients = new HashMap<>();
  }

  /**
   * Adds an ingredient requirement to the recipe.
   *
   * @param ingredientName the name of the ingredient; cannot be null or empty
   * @param quantity       the required quantity; must be positive
   * @param unit           the unit of measurement; cannot be null
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public void addIngredient(String ingredientName, double quantity, Unit unit) {
    validateIngredientName(ingredientName);
    validateQuantity(quantity);
    validateUnit(unit);

    String key = ingredientName.toLowerCase();
    ingredients.put(key, new IngredientRequirement(quantity, unit));
  }

  /**
   * Returns an unmodifiable map of ingredients and their requirements.
   *
   * @return a map of ingredient names to their requirements
   */
  public Map<String, IngredientRequirement> getIngredients() {
    return Collections.unmodifiableMap(ingredients);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    validateName(name);
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    validateDescription(description);
    this.description = description;
  }

  public String getPreparationMethod() {
    return preparationMethod;
  }

  public void setPreparationMethod(String preparationMethod) {
    validatePreparationMethod(preparationMethod);
    this.preparationMethod = preparationMethod;
  }

  public int getServings() {
    return servings;
  }

  public void setServings(int servings) {
    validateServings(servings);
    this.servings = servings;
  }

  private void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
  }

  private void validateDescription(String description) {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty.");
    }
  }

  private void validatePreparationMethod(String preparationMethod) {
    if (preparationMethod == null || preparationMethod.trim().isEmpty()) {
      throw new IllegalArgumentException("Preparation method cannot be null or empty.");
    }
  }

  private void validateServings(int servings) {
    if (servings <= 0) {
      throw new IllegalArgumentException("Servings must be positive.");
    }
  }

  private void validateIngredientName(String ingredientName) {
    if (ingredientName == null || ingredientName.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
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

  // equals and hashCode methods based on name

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Recipe recipe = (Recipe) o;
    return name.equalsIgnoreCase(recipe.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name.toLowerCase());
  }

  /**
   * Checks if the required ingredients are available in the provided inventory.
   * Performs unit conversions where necessary.
   *
   * @param inventory the food inventory to check against; cannot be null
   * @return true if all ingredients are available in sufficient quantities, false otherwise
   * @throws IllegalArgumentException if the inventory is null
   */
  public boolean canBeMadeFromInventory(FoodInventory inventory) {
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null.");
    }

    for (Map.Entry<String, IngredientRequirement> entry : ingredients.entrySet()) {
      String ingredientName = entry.getKey();
      IngredientRequirement requirement = entry.getValue();

      Ingredient ingredient = inventory.findIngredientByName(ingredientName);
      if (ingredient == null) {
        return false;
      }

      if (!requirement.getUnit().equals(ingredient.getUnit()) && !areUnitsCompatible(requirement.getUnit(), ingredient.getUnit())) {
        return false;
      }

      double requiredQuantityInBaseUnit = convertToBaseUnit(requirement.getQuantity(), requirement.getUnit());
      double availableQuantityInBaseUnit = convertToBaseUnit(ingredient.getQuantity(), ingredient.getUnit());

      if (availableQuantityInBaseUnit < requiredQuantityInBaseUnit) {
        return false;
      }
    }
    return true;
  }

  /**
   * Converts a quantity to its base unit (grams for mass, liters for volume).
   *
   * @param quantity the quantity to convert
   * @param unit     the unit of the quantity
   * @return the quantity converted to the base unit
   */
  private double convertToBaseUnit(double quantity, Unit unit) {
    switch (unit) {
      case GRAM:
        return quantity;
      case KILOGRAM:
        return quantity * 1000;
      case DECILITER:
        return quantity * 0.1;
      case LITER:
        return quantity;
      case PIECE:
        return quantity;
      default:
        throw new IllegalArgumentException("Unsupported unit: " + unit);
    }
  }

  /**
   * Checks if two units are compatible (mass with mass, volume with volume, count with count).
   *
   * @param unit1 the first unit
   * @param unit2 the second unit
   * @return true if units are compatible, false otherwise
   */
  private boolean areUnitsCompatible(Unit unit1, Unit unit2) {
    if (unit1 == unit2) {
      return true;
    }
    return (isMassUnit(unit1) && isMassUnit(unit2)) ||
        (isVolumeUnit(unit1) && isVolumeUnit(unit2)) ||
        (isCountUnit(unit1) && isCountUnit(unit2));
  }

  private boolean isMassUnit(Unit unit) {
    return unit == Unit.GRAM || unit == Unit.KILOGRAM;
  }

  private boolean isVolumeUnit(Unit unit) {
    return unit == Unit.LITER || unit == Unit.DECILITER;
  }

  private boolean isCountUnit(Unit unit) {
    return unit == Unit.PIECE;
  }
}
