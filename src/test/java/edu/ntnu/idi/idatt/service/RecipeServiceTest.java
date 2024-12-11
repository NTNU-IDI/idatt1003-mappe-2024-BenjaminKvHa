package edu.ntnu.idi.idatt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.model.Unit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link RecipeService} class.
 */
class RecipeServiceTest {

  private RecipeService recipeService;
  private InventoryService inventoryService;

  @BeforeEach
  void setUp() {
    recipeService = new RecipeService();
    recipeService.populateSampleRecipes();

    inventoryService = new InventoryService();
    inventoryService.populateSampleIngredients();
  }

  @DisplayName("Test adding a new recipe to the cookbook")
  @Test
  void testAddRecipe() {
    Recipe sandwich = new Recipe( //Recipe from ChatGPT with some changes
        "Sandwich",
        "Simple sandwich",
        "Assemble ingredients between slices of bread.",
        1
    );
    sandwich.addIngredient("Bread", 2, Unit.PIECE);
    sandwich.addIngredient("Cheese", 50, Unit.GRAM);
    recipeService.addRecipe(sandwich);

    Recipe retrievedRecipe = recipeService.findRecipeByName("Sandwich");

    assertNotNull(retrievedRecipe);
    assertEquals(sandwich, retrievedRecipe);
  }

  @DisplayName("Test adding a duplicate recipe throws an exception")
  @Test
  void testAddRecipeExisting() {
    Recipe duplicateRecipe = new Recipe(
        "Pancakes",
        "Another pancake recipe",
        "Different method.",
        4
    );
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipeService.addRecipe(duplicateRecipe);
    });

    assertEquals("Recipe already exists in the cookbook.", exception.getMessage());
  }

  @DisplayName("Test finding a recipe that exists in the cookbook")
  @Test
  void testFindRecipeByNameExists() {
    Recipe omelette = recipeService.findRecipeByName("Omelette");

    assertNotNull(omelette);
    assertEquals("Omelette", omelette.getName());
  }

  @DisplayName("Test finding a recipe that does not exist returns null")
  @Test
  void testFindRecipeByNameNotExists() {
    Recipe pizza = recipeService.findRecipeByName("Pizza");

    assertNull(pizza);
  }

  @DisplayName("Test that adding a null recipe throws an exception")
  @Test
  void testAddNullRecipeThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipeService.addRecipe(null);
    });
    assertEquals("Recipe cannot be null.", exception.getMessage());
  }


  @DisplayName("Test retrieving all recipes from the cookbook")
  @Test
  void testGetAllRecipes() {
    List<Recipe> recipes = recipeService.getAllRecipes();

    assertEquals(2, recipes.size());
  }

  @DisplayName("Test checking if a recipe can be made with current inventory (should return true)")
  @Test
  void testCanRecipeBeMadeTrue() {
    Recipe pancakes = recipeService.findRecipeByName("Pancakes");
    assertNotNull(pancakes);

    boolean canBeMade = recipeService.canRecipeBeMade(pancakes,
        inventoryService.getFoodInventory());
    assertTrue(canBeMade);
  }

  @DisplayName("Test checking if a recipe can be made after removing an ingredient (should return false)")
  @Test
  void testCanRecipeBeMadeFalse() {
    inventoryService.removeQuantity("Milk", 2.0, Unit.LITER);

    Recipe pancakes = recipeService.findRecipeByName("Pancakes");
    assertNotNull(pancakes);

    boolean canBeMade = recipeService.canRecipeBeMade(pancakes,
        inventoryService.getFoodInventory());
    assertFalse(canBeMade);
  }

  @DisplayName("Test getting recipes that can be made with the current inventory")
  @Test
  void testGetRecipesCanBeMade() {
    List<Recipe> availableRecipes = recipeService.getRecipesCanBeMade(
        inventoryService.getFoodInventory());

    assertEquals(2, availableRecipes.size());
  }

  @DisplayName("Test getting recipes that can be made after removing an ingredient")
  @Test
  void testGetRecipesCanBeMadeAfterRemovingIngredient() {
    inventoryService.removeQuantity("Flour", 1.0, Unit.KILOGRAM);

    List<Recipe> availableRecipes = recipeService.getRecipesCanBeMade(
        inventoryService.getFoodInventory());

    assertEquals(1, availableRecipes.size());
    assertEquals("Omelette", availableRecipes.get(0).getName());
  }
}
