package edu.ntnu.idi.idatt.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a recipe with a name, description, preparation method, a list of ingredients with
 * required quantities and units, and the number of servings.
 */
public class Recipe {

  private final String name;
  private String description;
  private String preparationMethod;
  private final Map<String, IngredientRequirement> ingredients;
  private int servings;

  /**
   * Constructs a Recipe with the specified name, description, preparation method, and number of
   * servings.
   *
   * @param name              the name of the recipe; cannot be null or empty
   * @param description       a short description; cannot be null or empty
   * @param preparationMethod the preparation method; cannot be null or empty
   * @param servings          the number of servings; must be positive
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public Recipe(String name, String description, String preparationMethod, int servings) {
    this.name = validateName(name);
    this.description = validateDescription(description);
    this.preparationMethod = validatePreparationMethod(preparationMethod);
    this.servings = validateServings(servings);
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
    String validatedName = validateIngredientName(ingredientName);
    double validatedQuantity = validateQuantity(quantity);
    Unit validatedUnit = validateUnit(unit);

    String key = validatedName.toLowerCase();
    ingredients.put(key, new IngredientRequirement(validatedQuantity, validatedUnit));
  }

  // Getters and setters

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = validateDescription(description);
  }

  public String getPreparationMethod() {
    return preparationMethod;
  }

  public void setPreparationMethod(String preparationMethod) {
    this.preparationMethod = validatePreparationMethod(preparationMethod);
  }

  public int getServings() {
    return servings;
  }

  public void setServings(int servings) {
    this.servings = validateServings(servings);
  }

  // Validation methods returning values

  private String validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Recipe name cannot be null or empty.");
    }
    return name.trim();
  }

  private String validateDescription(String description) {
    if (description == null || description.trim().isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty.");
    }
    return description.trim();
  }

  private String validatePreparationMethod(String preparationMethod) {
    if (preparationMethod == null || preparationMethod.trim().isEmpty()) {
      throw new IllegalArgumentException("Preparation method cannot be null or empty.");
    }
    return preparationMethod.trim();
  }

  private int validateServings(int servings) {
    if (servings <= 0) {
      throw new IllegalArgumentException("Servings must be positive.");
    }
    return servings;
  }

  private String validateIngredientName(String ingredientName) {
    if (ingredientName == null || ingredientName.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
    }
    return ingredientName.trim();
  }

  private double validateQuantity(double quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
    return quantity;
  }

  private Unit validateUnit(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
    return unit;
  }

  // Other methods, including equals, hashCode, and canBeMadeFromInventory...

  /**
   * Checks if the required ingredients are available in the provided inventory. Performs unit
   * conversions where necessary.
   *
   * @param inventory the food inventory to check against; cannot be null
   * @return true if all ingredients are available in sufficient quantities, false otherwise
   * @throws IllegalArgumentException if the inventory is null
   */
  public boolean canBeMadeFromInventory(FoodInventory inventory) {
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null.");
    }

    return ingredients.entrySet().stream()
        .allMatch(entry -> isIngredientAvailable(entry.getKey(), entry.getValue(), inventory));
  }


  private boolean isIngredientAvailable(String ingredientName, IngredientRequirement requirement,
      FoodInventory inventory) {
    Ingredient ingredient = inventory.findIngredientByName(ingredientName);
    if (ingredient == null) {
      return false;
    }

    if (!requirement.getUnit().isCompatibleWith(ingredient.getUnit())) {
      return false;
    }

    double requiredQuantityInBaseUnit = requirement.getUnit().toBaseUnit(requirement.getQuantity());
    double availableQuantityInBaseUnit = ingredient.getUnit().toBaseUnit(ingredient.getQuantity());

    return availableQuantityInBaseUnit >= requiredQuantityInBaseUnit;
  }

  // toString, equals and hashCode methods based on name

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Recipe recipe = (Recipe) o;
    return name.equalsIgnoreCase(recipe.name);
  }

  @Override
  public String toString() {
    return String.format("Recipe{name='%s', servings=%d}", name, servings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name.toLowerCase());
  }
}
