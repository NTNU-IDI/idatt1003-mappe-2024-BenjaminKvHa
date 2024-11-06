package edu.ntnu.idi.idatt.model;

import java.time.LocalDate;
import java.util.*;

/**
 * Manages a collection of ingredients in the food inventory.
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

      if (!areUnitsCompatible(existingIngredient.getUnit(), ingredient.getUnit())) {
        throw new IllegalArgumentException("Units are incompatible for ingredient: " + ingredient.getName());
      }

      double existingQuantityInBaseUnit = convertToBaseUnit(existingIngredient.getQuantity(), existingIngredient.getUnit());
      double newQuantityInBaseUnit = convertToBaseUnit(ingredient.getQuantity(), ingredient.getUnit());
      double totalQuantityInBaseUnit = existingQuantityInBaseUnit + newQuantityInBaseUnit;

      double totalQuantityInExistingUnit = convertFromBaseUnit(totalQuantityInBaseUnit, existingIngredient.getUnit());

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
   * @throws IllegalArgumentException if any parameter is invalid or units are incompatible
   */
  public boolean removeQuantity(String name, double quantity, Unit unit) {
    validateName(name);
    validateQuantity(quantity);
    validateUnit(unit);

    String key = name.toLowerCase();
    if (!inventory.containsKey(key)) {
      return false;
    }

    Ingredient ingredient = inventory.get(key);

    if (!areUnitsCompatible(ingredient.getUnit(), unit)) {
      throw new IllegalArgumentException("Units are incompatible for ingredient: " + name);
    }

    double currentQuantityInBaseUnit = convertToBaseUnit(ingredient.getQuantity(), ingredient.getUnit());
    double quantityToRemoveInBaseUnit = convertToBaseUnit(quantity, unit);

    if (currentQuantityInBaseUnit <= quantityToRemoveInBaseUnit) {
      inventory.remove(key);
    } else {
      double newQuantityInBaseUnit = currentQuantityInBaseUnit - quantityToRemoveInBaseUnit;
      double newQuantityInExistingUnit = convertFromBaseUnit(newQuantityInBaseUnit, ingredient.getUnit());
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
    List<Ingredient> ingredients = new ArrayList<>(inventory.values());
    ingredients.sort(Comparator.comparing(Ingredient::getName, String.CASE_INSENSITIVE_ORDER));
    return ingredients;
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

    List<Ingredient> expiringIngredients = new ArrayList<>();
    for (Ingredient ingredient : inventory.values()) {
      if (ingredient.getBestBeforeDate().isBefore(date)) {
        expiringIngredients.add(ingredient);
      }
    }

    expiringIngredients.sort(Comparator.comparing(Ingredient::getBestBeforeDate));
    return expiringIngredients;
  }

  // Validation methods

  private void validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty.");
    }
  }

  private void validateQuantity(double quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity to remove must be positive.");
    }
  }

  private void validateUnit(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
  }

  // Unit conversion and compatibility methods

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

  private double convertFromBaseUnit(double quantityInBaseUnit, Unit unit) {
    switch (unit) {
      case GRAM:
        return quantityInBaseUnit;
      case KILOGRAM:
        return quantityInBaseUnit / 1000;
      case DECILITER:
        return quantityInBaseUnit * 10;
      case LITER:
        return quantityInBaseUnit;
      case PIECE:
        return quantityInBaseUnit;
      default:
        throw new IllegalArgumentException("Unsupported unit: " + unit);
    }
  }

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
