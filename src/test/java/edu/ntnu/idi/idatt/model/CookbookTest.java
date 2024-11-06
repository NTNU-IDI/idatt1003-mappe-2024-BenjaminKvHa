package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Cookbook} class.
 * <p>
 * This class tests the functionality of the Cookbook class, including adding recipes,
 * finding recipes by name, removing recipes, checking if a recipe exists, and retrieving
 * recipes that can or cannot be made from a given inventory.
 * </p>
 */
class CookbookTest {

  private Cookbook cookbook;
  private Recipe pancakeRecipe;
  private Recipe omeletteRecipe;

  /**
   * Sets up a new Cookbook and sample recipes before each test.
   */
  @BeforeEach
  void setUp() {
    cookbook = new Cookbook();

    pancakeRecipe = new Recipe(
        "Pancakes",
        "Fluffy pancakes",
        "Mix ingredients and cook on a skillet.",
        4
    );
    pancakeRecipe.addIngredient("Flour", 200, Unit.GRAM);
    pancakeRecipe.addIngredient("Milk", 3, Unit.DECILITER);
    pancakeRecipe.addIngredient("Eggs", 2, Unit.PIECE);

    omeletteRecipe = new Recipe(
        "Omelette",
        "Simple omelette",
        "Beat eggs and cook on a pan.",
        2
    );
    omeletteRecipe.addIngredient("Eggs", 3, Unit.PIECE);
    omeletteRecipe.addIngredient("Cheese", 50, Unit.GRAM);
    omeletteRecipe.addIngredient("Milk", 0.5, Unit.DECILITER);
  }

  /**
   * Tests that a recipe can be added to the cookbook.
   */
  @Test
  void testAddRecipe() {
    // Arrange
    // Done in setUp()

    // Act
    cookbook.addRecipe(pancakeRecipe);

    // Assert
    assertEquals(1, cookbook.getAllRecipes().size());
    assertTrue(cookbook.getAllRecipes().contains(pancakeRecipe));
  }

  /**
   * Tests that adding a null recipe throws an exception.
   */
  @Test
  void testAddNullRecipeThrowsException() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.addRecipe(null);
    });
    assertEquals("Recipe cannot be null.", exception.getMessage());
  }

  /**
   * Tests that finding a recipe by name works correctly.
   */
  @Test
  void testFindRecipeByName() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act
    Recipe foundRecipe = cookbook.findRecipeByName("Pancakes");

    // Assert
    assertNotNull(foundRecipe);
    assertEquals(pancakeRecipe, foundRecipe);
  }

  /**
   * Tests that getting recipes that can be made returns the correct list.
   */
  @Test
  void testGetRecipesCanBeMade() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 15.0));
    inventory.addIngredient(new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0));
    inventory.addIngredient(new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0));
    inventory.addIngredient(new Ingredient("Cheese", 100, Unit.GRAM, LocalDate.now().plusDays(15), 50.0));

    // Act
    List<Recipe> availableRecipes = cookbook.getRecipesCanBeMade(inventory);

    // Assert
    assertEquals(2, availableRecipes.size());
    assertTrue(availableRecipes.contains(pancakeRecipe));
    assertTrue(availableRecipes.contains(omeletteRecipe));
  }

  /**
   * Tests that recipes with missing ingredients are not included in the available recipes.
   */
  @Test
  void testGetRecipesCanBeMadeMissingIngredients() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0));
    inventory.addIngredient(new Ingredient("Eggs", 2, Unit.PIECE, LocalDate.now().plusDays(10), 3.0));

    // Act
    List<Recipe> availableRecipes = cookbook.getRecipesCanBeMade(inventory);

    // Assert
    assertEquals(0, availableRecipes.size());
  }

  /**
   * Tests that passing a null inventory to getRecipesCanBeMade throws an exception.
   */
  @Test
  void testGetRecipesCanBeMadeNullInventoryThrowsException() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.getRecipesCanBeMade(null);
    });
    assertEquals("Inventory cannot be null.", exception.getMessage());
  }
}
