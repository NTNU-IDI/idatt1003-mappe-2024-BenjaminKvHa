package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a cookbook containing a collection of recipes.
 * <p>
 * This class allows adding recipes to the cookbook, finding recipes by name,
 * retrieving all recipes, and getting recipes that can be made with a given
 * food inventory.
 * </p>
 */
public class Cookbook {

  private List<Recipe> recipes;

  /**
   * Constructs an empty Cookbook.
   */
  public Cookbook() {
    this.recipes = new ArrayList<>();
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
    if (recipes.contains(recipe)) {
      throw new IllegalArgumentException("Recipe already exists in the cookbook.");
    }
    recipes.add(recipe);
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
    for (Recipe recipe : recipes) {
      if (recipe.getName().equalsIgnoreCase(name)) {
        return recipe;
      }
    }
    return null;
  }

  /**
   * Returns an unmodifiable list of all recipes in the cookbook.
   *
   * @return a list of recipes
   */
  public List<Recipe> getAllRecipes() {
    return Collections.unmodifiableList(recipes);
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

    List<Recipe> availableRecipes = new ArrayList<>();
    for (Recipe recipe : recipes) {
      if (recipe.canBeMadeFromInventory(inventory)) {
        availableRecipes.add(recipe);
      }
    }
    return availableRecipes;
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

  /**
   * Removes a recipe from the cookbook.
   *
   * @param recipe the recipe to remove; cannot be null
   * @return true if the recipe was removed, false if it was not found
   * @throws IllegalArgumentException if the recipe is null
   */
  public boolean removeRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe cannot be null.");
    }
    return recipes.remove(recipe);
  }

  /**
   * Checks if the cookbook contains a recipe with the given name.
   *
   * @param name the name of the recipe; cannot be null or empty
   * @return true if the recipe exists in the cookbook, false otherwise
   * @throws IllegalArgumentException if the name is null or empty
   */
  public boolean containsRecipe(String name) {
    return findRecipeByName(name) != null;
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
    for (Recipe recipe : recipes) {
      if (!recipe.canBeMadeFromInventory(inventory)) {
        unavailableRecipes.add(recipe);
      }
    }
    return unavailableRecipes;
  }
}
