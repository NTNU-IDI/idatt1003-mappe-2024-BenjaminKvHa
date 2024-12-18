package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a cookbook containing a collection of recipes.
 * <p>
 * This class allows adding recipes to the cookbook, finding recipes by name, retrieving all
 * recipes, and getting recipes that can be made with a given food inventory.
 * </p>
 */
public class Cookbook {

  private final Map<String, Recipe> recipes;

  /**
   * Constructs an empty Cookbook.
   */
  public Cookbook() {
    this.recipes = new HashMap<>();
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe the recipe to add; cannot be null
   * @throws IllegalArgumentException if the recipe is null or already exists in the cookbook
   */
  public void addRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe cannot be null.");
    }
    String key = recipe.getName().toLowerCase();
    if (recipes.containsKey(key)) {
      throw new IllegalArgumentException("Recipe already exists in the cookbook.");
    }
    recipes.put(key, recipe);
  }

  /**
   * Finds a recipe by name.
   *
   * @param name the name of the recipe; cannot be null or empty
   * @return the recipe if found, or null if not found
   * @throws IllegalArgumentException if the name is null or empty
   */
  public Recipe findRecipeByName(String name) {
    validateName(name);
    return recipes.get(name.toLowerCase());
  }

  /**
   * Returns an unmodifiable list of all recipes in the cookbook.
   *
   * @return a list of recipes
   */
  public List<Recipe> getAllRecipes() {
    return Collections.unmodifiableList(new ArrayList<>(recipes.values()));
  }

  /**
   * Returns a list of recipes that can be made with the provided inventory.
   *
   * @param inventory the food inventory to check against; cannot be null
   * @return a list of recipes that can be made
   * @throws IllegalArgumentException if the inventory is null
   */
  public List<Recipe> getRecipesCanBeMade(FoodInventory inventory) {
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null.");
    }

    return recipes.values().stream()
        .filter(recipe -> recipe.canBeMadeFromInventory(inventory))
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
      throw new IllegalArgumentException("Recipe name cannot be null or empty.");
    }
  }

  /**
   * Removes a recipe from the cookbook.
   *
   * @param name the name of the recipe to remove; cannot be null or empty
   * @return true if the recipe was removed, false if it was not found
   * @throws IllegalArgumentException if the name is null or empty
   */
  public boolean removeRecipe(String name) {
    validateName(name);
    return recipes.remove(name.toLowerCase()) != null;
  }

  /**
   * Checks if the cookbook contains a recipe with the given name.
   *
   * @param name the name of the recipe; cannot be null or empty
   * @return true if the recipe exists in the cookbook, false otherwise
   * @throws IllegalArgumentException if the name is null or empty
   */
  public boolean containsRecipe(String name) {
    validateName(name);
    return recipes.containsKey(name.toLowerCase());
  }

  /**
   * Returns a list of recipes that cannot be made with the provided inventory.
   *
   * @param inventory the food inventory to check against; cannot be null
   * @return a list of recipes that cannot be made
   * @throws IllegalArgumentException if the inventory is null
   */
  public List<Recipe> getRecipesCannotBeMade(FoodInventory inventory) {
    if (inventory == null) {
      throw new IllegalArgumentException("Inventory cannot be null.");
    }

    List<Recipe> unavailableRecipes = new ArrayList<>();
    for (Recipe recipe : recipes.values()) {
      if (!recipe.canBeMadeFromInventory(inventory)) {
        unavailableRecipes.add(recipe);
      }
    }
    return unavailableRecipes;
  }
}
