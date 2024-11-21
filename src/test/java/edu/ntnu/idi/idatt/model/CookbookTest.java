package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

  @DisplayName("Test that a recipe can be added to the cookbook")
  @Test
  void testAddRecipe() {
    cookbook.addRecipe(pancakeRecipe);

    assertEquals(1, cookbook.getAllRecipes().size());
    assertTrue(cookbook.getAllRecipes().contains(pancakeRecipe));
  }

  @DisplayName("Test that adding a null recipe throws an exception")
  @Test
  void testAddNullRecipeThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.addRecipe(null);
    });

    assertEquals("Recipe cannot be null.", exception.getMessage());
  }

  @DisplayName("Test that adding a duplicate recipe throws an exception")
  @Test
  void testAddDuplicateRecipeThrowsException() {
    cookbook.addRecipe(pancakeRecipe);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.addRecipe(pancakeRecipe);
    });

    assertEquals("Recipe already exists in the cookbook.", exception.getMessage());
  }

  @DisplayName("Test that a recipe can be found by name")
  @Test
  void testFindRecipeByName() {
    cookbook.addRecipe(pancakeRecipe);

    Recipe foundRecipe = cookbook.findRecipeByName("Pancakes");

    assertNotNull(foundRecipe);
    assertEquals(pancakeRecipe, foundRecipe);
  }

  @DisplayName("Test that finding a recipe with a null name throws an exception")
  @Test
  void testFindRecipeByNameNullThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.findRecipeByName(null);
    });

    assertEquals("Recipe name cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that all recipes are retrieved from an empty cookbook")
  @Test
  void testGetAllRecipesEmptyCookbook() {
    List<Recipe> allRecipes = cookbook.getAllRecipes();

    assertNotNull(allRecipes);
    assertTrue(allRecipes.isEmpty());
  }

  @DisplayName("Test that recipes that can be made are correctly identified")
  @Test
  void testGetRecipesCanBeMade() {
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 15.0));
    inventory.addIngredient(new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0));
    inventory.addIngredient(new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0));
    inventory.addIngredient(new Ingredient("Cheese", 100, Unit.GRAM, LocalDate.now().plusDays(15), 50.0));

    List<Recipe> availableRecipes = cookbook.getRecipesCanBeMade(inventory);

    assertEquals(2, availableRecipes.size());
    assertTrue(availableRecipes.contains(pancakeRecipe));
    assertTrue(availableRecipes.contains(omeletteRecipe));
  }

  @DisplayName("Test that recipes that cannot be made are correctly identified")
  @Test
  void testGetRecipesCannotBeMade() {
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Milk", 0.5, Unit.LITER, LocalDate.now().plusDays(5), 20.0));

    List<Recipe> cannotMakeRecipes = cookbook.getRecipesCannotBeMade(inventory);

    assertEquals(2, cannotMakeRecipes.size());
    assertTrue(cannotMakeRecipes.contains(pancakeRecipe));
    assertTrue(cannotMakeRecipes.contains(omeletteRecipe));
  }

  @DisplayName("Test that passing a null inventory throws an exception")
  @Test
  void testGetRecipesCanBeMadeNullInventoryThrowsException() {
    cookbook.addRecipe(pancakeRecipe);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.getRecipesCanBeMade(null);
    });
    assertEquals("Inventory cannot be null.", exception.getMessage());
  }

  @DisplayName("Test that a recipe can be removed from the cookbook")
  @Test
  void testRemoveRecipe() {
    cookbook.addRecipe(pancakeRecipe);
    assertTrue(cookbook.containsRecipe("Pancakes"));

    boolean removed = cookbook.removeRecipe("Pancakes");

    assertTrue(removed);
    assertFalse(cookbook.containsRecipe("Pancakes"));
  }

  @DisplayName("Test that removing a recipe with a null name throws an exception")
  @Test
  void testRemoveRecipeNullThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.removeRecipe(null);
    });

    assertEquals("Recipe name cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that removing a non-existent recipe returns false")
  @Test
  void testRemoveNonexistentRecipe() {
    boolean removed = cookbook.removeRecipe("Nonexistent Recipe");

    assertFalse(removed);
  }

  @DisplayName("Test that containsRecipe works correctly")
  @Test
  void testContainsRecipe() {
    cookbook.addRecipe(pancakeRecipe);

    assertTrue(cookbook.containsRecipe("Pancakes"));
    assertFalse(cookbook.containsRecipe("Nonexistent Recipe"));
  }

  @DisplayName("Test that containsRecipe with null name throws exception")
  @Test
  void testContainsRecipeNullThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.containsRecipe(null);
    });

    assertEquals("Recipe name cannot be null or empty.", exception.getMessage());
  }
}
