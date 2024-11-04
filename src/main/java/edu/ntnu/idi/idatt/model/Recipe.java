package edu.ntnu.idi.idatt.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a recipe with a name, description, preparation method,
 * a list of ingredients with required quantities, and the number of servings.
 */
public class Recipe {

  private String name;
  private String description;
  private String preparationMethod;
  private Map<String, Double> ingredients;
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
   * Adds an ingredient with the required quantity to the recipe.
   *
   * @param ingredientName the name of the ingredient; cannot be null or empty
   * @param quantity       the required quantity; must be positive
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public void addIngredient(String ingredientName, double quantity) {
    validateIngredientName(ingredientName);
    validateQuantity(quantity);

    String key = ingredientName.toLowerCase();
    ingredients.put(key, quantity);
  }

  /**
   * Returns an unmodifiable map of ingredients and their required quantities.
   *
   * @return a map of ingredient names to quantities
   */
  public Map<String, Double> getIngredients() {
    return Collections.unmodifiableMap(ingredients);
  }

  // Getters and setters with validation

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

  // Validation methods

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
   *
   * @param inventory the food inventory to check against; cannot be null
   * @return true if all ingredients are available in sufficient quantities, false otherwise
   * @throws IllegalArgumentException if the inventory is null
   */
  public boolean canBeMadeFromInventory(FoodInventory inventory) {
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null.");
    }

    for (Map.Entry<String, Double> entry : ingredients.entrySet()) {
      String ingredientName = entry.getKey();
      double requiredQuantity = entry.getValue();

      Ingredient ingredient = inventory.findIngredientByName(ingredientName);
      if (ingredient == null || ingredient.getQuantity() < requiredQuantity) {
        return false;
      }
    }
    return true;
  }
}
