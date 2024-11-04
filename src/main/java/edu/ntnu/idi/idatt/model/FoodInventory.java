package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages a collection of ingredients representing the food inventory.
 */
public class FoodInventory {

  private Map<String, Ingredient> inventory;

  /**
   * Constructs an empty FoodInventory.
   */
  public FoodInventory() {
    this.inventory = new HashMap<>();
  }

  /**
   * Adds an ingredient to the inventory. If the ingredient already exists, increases the quantity
   * accordingly.
   *
   * @param ingredient the ingredient to add; cannot be null
   * @throws IllegalArgumentException if the ingredient is null
   */
  public void addIngredient(Ingredient ingredient) {
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient cannot be null.");
    }

    String key = ingredient.getName().toLowerCase();
    if (inventory.containsKey(key)) {
      Ingredient existingIngredient = inventory.get(key);
      double newQuantity = existingIngredient.getQuantity() + ingredient.getQuantity();
      existingIngredient.setQuantity(newQuantity);
      // Optionally update best-before date and price per unit
    } else {
      inventory.put(key, ingredient);
    }
  }

  /**
   * Finds an ingredient by name.
   *
   * @param name the name of the ingredient; cannot be null or empty
   * @return the ingredient if found, or null if not found
   * @throws IllegalArgumentException if the name is null or empty
   */
  public Ingredient findIngredientByName(String name) {
    validateName(name);
    return inventory.get(name.toLowerCase());
  }

  /**
   * Removes a specified quantity of an ingredient. If the quantity becomes zero or less, removes
   * the ingredient from the inventory.
   *
   * @param name     the name of the ingredient; cannot be null or empty
   * @param quantity the quantity to remove; must be positive
   * @return true if the removal was successful, false if the ingredient was not found
   * @throws IllegalArgumentException if the name is null or empty, or if quantity is not positive
   */
  public boolean removeQuantity(String name, double quantity) {
    validateName(name);
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity to remove must be positive.");
    }

    String key = name.toLowerCase();
    Ingredient ingredient = inventory.get(key);
    if (ingredient != null) {
      double newQuantity = ingredient.getQuantity() - quantity;
      if (newQuantity > 0) {
        ingredient.setQuantity(newQuantity);
      } else {
        inventory.remove(key);
      }
      return true;
    }
    return false;
  }

  /**
   * Returns a list of ingredients with best-before dates before the specified date.
   *
   * @param date the date to compare against; cannot be null
   * @return a list of ingredients expiring before the given date
   * @throws IllegalArgumentException if the date is null
   */
  public List<Ingredient> getIngredientsExpiringBefore(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }

    List<Ingredient> expiringIngredients = new ArrayList<>();
    for (Ingredient ingredient : inventory.values()) {
      if (ingredient.getBestBeforeDate().isBefore(date)) {
        expiringIngredients.add(ingredient);
      }
    }
    return expiringIngredients;
  }

  /**
   * Returns a list of all ingredients sorted alphabetically by name.
   *
   * @return a sorted list of ingredients
   */
  public List<Ingredient> getAllIngredientsSortedByName() {
    List<Ingredient> sortedIngredients = new ArrayList<>(inventory.values());
    sortedIngredients.sort(
        Comparator.comparing(Ingredient::getName, String.CASE_INSENSITIVE_ORDER));
    return sortedIngredients;
  }

  /**
   * Validates that the name is not null or empty.
   *
   * @param name the name to validate
   * @throws IllegalArgumentException if the name is null or empty
   */
  private void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
  }
}
