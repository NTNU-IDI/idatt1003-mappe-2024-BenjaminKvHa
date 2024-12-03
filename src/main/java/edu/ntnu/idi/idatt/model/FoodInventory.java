package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages a collection of ingredients in the food inventory.
 * <p>
 * This class allows adding ingredients to the inventory, removing quantities, finding ingredients
 * by name, and retrieving ingredients based on their best-before dates.
 * </p>
 */
public class FoodInventory {

  private final Map<String, Ingredient> inventory;

  /**
   * Constructs an empty FoodInventory.
   */
  public FoodInventory() {
    this.inventory = new HashMap<>();
  }

  /**
   * Adds an ingredient to the inventory.
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

      if (!existingIngredient.getUnit().isCompatibleWith(ingredient.getUnit())) {
        throw new IllegalArgumentException(
            "Units are incompatible for ingredient: " + ingredient.getName());
      }

      double existingQuantityInBaseUnit = existingIngredient.getUnit()
          .toBaseUnit(existingIngredient.getQuantity());
      double newQuantityInBaseUnit = ingredient.getUnit().toBaseUnit(ingredient.getQuantity());
      double totalQuantityInBaseUnit = existingQuantityInBaseUnit + newQuantityInBaseUnit;

      double totalQuantityInExistingUnit = existingIngredient.getUnit()
          .fromBaseUnit(totalQuantityInBaseUnit);

      existingIngredient.setQuantity(totalQuantityInExistingUnit);

      if (ingredient.getBestBeforeDate().isBefore(existingIngredient.getBestBeforeDate())) {
        existingIngredient.setBestBeforeDate(ingredient.getBestBeforeDate());
      }

      existingIngredient.setPricePerUnit(
          (existingIngredient.getPricePerUnit() + ingredient.getPricePerUnit()) / 2
      );

    } else {
      inventory.put(key, ingredient);
    }
  }

  /**
   * Removes a quantity of an ingredient from the inventory.
   *
   * @param name     the name of the ingredient; cannot be null or empty
   * @param quantity the quantity to remove; must be positive
   * @param unit     the unit of the quantity to remove; cannot be null
   * @return true if the quantity was removed, false if the ingredient was not found
   * @throws IllegalArgumentException if any parameter is invalid, units are incompatible, or if the
   *                                  quantity to remove exceeds the available quantity
   */
  public boolean removeQuantity(String name, double quantity, Unit unit) {
    validateName(name);
    validateQuantity(quantity);
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }

    String key = name.toLowerCase();
    if (!inventory.containsKey(key)) {
      return false;
    }

    Ingredient ingredient = inventory.get(key);

    if (!ingredient.getUnit().isCompatibleWith(unit)) {
      throw new IllegalArgumentException("Units are incompatible for ingredient: " + name);
    }

    double currentQuantityInBaseUnit = ingredient.getUnit().toBaseUnit(ingredient.getQuantity());
    double quantityToRemoveInBaseUnit = unit.toBaseUnit(quantity);

    if (quantityToRemoveInBaseUnit > currentQuantityInBaseUnit) {
      throw new IllegalArgumentException(
          "Insufficient quantity of " + name + " to remove the requested amount."
      );
    } else if (quantityToRemoveInBaseUnit == currentQuantityInBaseUnit) {
      inventory.remove(key);
    } else {
      double newQuantityInBaseUnit = currentQuantityInBaseUnit - quantityToRemoveInBaseUnit;
      double newQuantityInExistingUnit = ingredient.getUnit().fromBaseUnit(newQuantityInBaseUnit);
      ingredient.setQuantity(newQuantityInExistingUnit);
    }
    return true;
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
   * Returns a list of all ingredients sorted by name.
   *
   * @return a list of ingredients
   */
  public List<Ingredient> getAllIngredientsSortedByName() {
    return inventory.values().stream()
        .sorted(Comparator.comparing(Ingredient::getName, String.CASE_INSENSITIVE_ORDER))
        .collect(Collectors.toList());
  }


  /**
   * Returns a list of ingredients that expire before the specified date.
   *
   * @param date the date to compare; cannot be null
   * @return a list of ingredients expiring before the specified date
   * @throws IllegalArgumentException if the date is null
   */
  public List<Ingredient> getIngredientsExpiringBefore(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null.");
    }

    return inventory.values().stream()
        .filter(ingredient -> ingredient.getBestBeforeDate().isBefore(date))
        .sorted(Comparator.comparing(Ingredient::getBestBeforeDate))
        .collect(Collectors.toList());
  }


  /**
   * Validates that the name is not null or empty.
   *
   * @param name the name to validate
   * @throws IllegalArgumentException if the name is null or empty
   */
  private void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or empty.");
    }
  }

  /**
   * Validates that the quantity is positive.
   *
   * @param quantity the quantity to validate
   * @throws IllegalArgumentException if the quantity is not positive
   */
  private void validateQuantity(double quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
  }
}