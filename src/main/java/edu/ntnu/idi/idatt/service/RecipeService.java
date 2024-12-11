package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.model.Cookbook;
import edu.ntnu.idi.idatt.model.FoodInventory;
import edu.ntnu.idi.idatt.model.Recipe;
import java.util.List;

/**
 * Service class that provides operations related to recipes and the cookbook.
 *
 * <p>
 * The {@code RecipeService} class acts as an intermediary between the user interface and the
 * underlying {@link Cookbook} model. It encapsulates all business logic related to managing
 * recipes, such as adding new recipes, finding recipes by name, and determining which recipes can
 * be made with the current inventory.
 * </p>
 *
 * <p>
 * This class ensures that the {@link Cookbook} is manipulated in a controlled manner, enforcing
 * validation rules and providing a clean API for higher layers of the application.
 * </p>
 */
public class RecipeService {

  private final Cookbook cookbook;

  public RecipeService() {
    this.cookbook = new Cookbook();
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe the recipe to add
   * @throws IllegalArgumentException if the recipe is invalid or already exists
   */
  public void addRecipe(Recipe recipe) {
    cookbook.addRecipe(recipe);
  }

  /**
   * Finds a recipe by name.
   *
   * @param name the name of the recipe
   * @return the recipe if found, or null if not found
   */
  public Recipe findRecipeByName(String name) {
    return cookbook.findRecipeByName(name);
  }

  /**
   * Gets all recipes.
   *
   * @return a list of recipes
   */
  public List<Recipe> getAllRecipes() {
    return cookbook.getAllRecipes();
  }

  /**
   * Checks if a recipe can be made with the given inventory.
   *
   * @param recipe    the recipe to check
   * @param inventory the food inventory
   * @return true if the recipe can be made, false otherwise
   */
  public boolean canRecipeBeMade(Recipe recipe, FoodInventory inventory) {
    return recipe.canBeMadeFromInventory(inventory);
  }

  /**
   * Gets recipes that can be made with the given inventory.
   *
   * @param inventory the food inventory
   * @return a list of recipes that can be made
   */
  public List<Recipe> getRecipesCanBeMade(FoodInventory inventory) {
    return cookbook.getRecipesCanBeMade(inventory);
  }

  /**
   * Populates the cookbook with sample recipes.
   */
  public void populateSampleRecipes() {
    // Pancake recipe. From ChatGPT with some changes
    Recipe pancakeRecipe = new Recipe(
        "Pancakes",
        "Fluffy pancakes",
        "Mix ingredients and cook on a skillet.",
        4
    );
    pancakeRecipe.addIngredient("Flour", 200, edu.ntnu.idi.idatt.model.Unit.GRAM);
    pancakeRecipe.addIngredient("Milk", 3, edu.ntnu.idi.idatt.model.Unit.DECILITER);
    pancakeRecipe.addIngredient("Eggs", 2, edu.ntnu.idi.idatt.model.Unit.PIECE);
    pancakeRecipe.addIngredient("Sugar", 50, edu.ntnu.idi.idatt.model.Unit.GRAM);

    // Omelette recipe. From ChatGPT with some changes
    Recipe omeletteRecipe = new Recipe(
        "Omelette",
        "Simple omelette",
        "Beat eggs and cook on a pan.",
        2
    );
    omeletteRecipe.addIngredient("Eggs", 3, edu.ntnu.idi.idatt.model.Unit.PIECE);
    omeletteRecipe.addIngredient("Cheese", 50, edu.ntnu.idi.idatt.model.Unit.GRAM);
    omeletteRecipe.addIngredient("Milk", 0.5, edu.ntnu.idi.idatt.model.Unit.DECILITER);

    try {
      addRecipe(pancakeRecipe);
      addRecipe(omeletteRecipe);
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding sample recipes: " + e.getMessage());
    }
  }
}
