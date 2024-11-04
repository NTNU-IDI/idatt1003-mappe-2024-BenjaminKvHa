package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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
    pancakeRecipe.addIngredient("Flour", 200);
    pancakeRecipe.addIngredient("Milk", 300);
    pancakeRecipe.addIngredient("Eggs", 2);

    omeletteRecipe = new Recipe(
        "Omelette",
        "Simple omelette",
        "Beat eggs and cook on a pan.",
        2
    );
    omeletteRecipe.addIngredient("Eggs", 3);
    omeletteRecipe.addIngredient("Cheese", 50);
  }

  /**
   * Tests that a recipe can be added to the cookbook.
   */
  @Test
  void addRecipe() {
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
  void addNullRecipeThrowsException() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.addRecipe(null);
    });
    assertEquals("Recipe cannot be null.", exception.getMessage());
  }

  /**
   * Tests that adding a duplicate recipe throws an exception.
   */
  @Test
  void addDuplicateRecipeThrowsException() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.addRecipe(pancakeRecipe);
    });
    assertEquals("Recipe already exists in the cookbook.", exception.getMessage());
  }

  /**
   * Tests that a recipe can be found by name.
   */
  @Test
  void findRecipeByName() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act
    Recipe foundRecipe = cookbook.findRecipeByName("Pancakes");

    // Assert
    assertNotNull(foundRecipe);
    assertEquals(pancakeRecipe, foundRecipe);
  }

  /**
   * Tests that searching for a non-existent recipe returns null.
   */
  @Test
  void findRecipeByNameNotFound() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act
    Recipe foundRecipe = cookbook.findRecipeByName("Waffles");

    // Assert
    assertNull(foundRecipe);
  }

  /**
   * Tests that searching with a null name throws an exception.
   */
  @Test
  void findRecipeByNameNullThrowsException() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.findRecipeByName(null);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that getting all recipes returns the correct list.
   */
  @Test
  void getAllRecipes() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    // Act
    List<Recipe> allRecipes = cookbook.getAllRecipes();

    // Assert
    assertEquals(2, allRecipes.size());
    assertTrue(allRecipes.contains(pancakeRecipe));
    assertTrue(allRecipes.contains(omeletteRecipe));
  }

  /**
   * Tests that the returned list from getAllRecipes is unmodifiable.
   */
  @Test
  void getAllRecipesUnmodifiable() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act
    List<Recipe> allRecipes = cookbook.getAllRecipes();

    // Assert
    assertThrows(UnsupportedOperationException.class, () -> {
      allRecipes.add(omeletteRecipe);
    });
  }

  /**
   * Tests that recipes that can be made are correctly identified.
   */
  @Test
  void getRecipesCanBeMade() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 500, "grams", LocalDate.now().plusDays(5), 10));
    inventory.addIngredient(new Ingredient("Milk", 1000, "ml", LocalDate.now().plusDays(5), 15));
    inventory.addIngredient(new Ingredient("Eggs", 6, "pieces", LocalDate.now().plusDays(5), 20));
    inventory.addIngredient(new Ingredient("Cheese", 100, "grams", LocalDate.now().plusDays(5), 25));

    // Act
    List<Recipe> availableRecipes = cookbook.getRecipesCanBeMade(inventory);

    // Assert
    assertEquals(2, availableRecipes.size());
    assertTrue(availableRecipes.contains(pancakeRecipe));
    assertTrue(availableRecipes.contains(omeletteRecipe));
  }

  /**
   * Tests that recipes that cannot be made are correctly identified.
   */
  @Test
  void getRecipesCannotBeMade() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Eggs", 2, "pieces", LocalDate.now().plusDays(5), 20));
    inventory.addIngredient(new Ingredient("Milk", 1000, "ml", LocalDate.now().plusDays(5), 15));

    // Act
    List<Recipe> unavailableRecipes = cookbook.getRecipesCannotBeMade(inventory);

    // Assert
    assertEquals(2, unavailableRecipes.size());
    assertTrue(unavailableRecipes.contains(pancakeRecipe));
    assertTrue(unavailableRecipes.contains(omeletteRecipe));
  }

  /**
   * Tests that removing a recipe works correctly.
   */
  @Test
  void removeRecipe() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);
    cookbook.addRecipe(omeletteRecipe);

    // Act
    boolean removed = cookbook.removeRecipe(pancakeRecipe);

    // Assert
    assertTrue(removed);
    assertEquals(1, cookbook.getAllRecipes().size());
    assertFalse(cookbook.getAllRecipes().contains(pancakeRecipe));
  }

  /**
   * Tests that removing a recipe that doesn't exist returns false.
   */
  @Test
  void removeNonExistentRecipe() {
    // Arrange
    cookbook.addRecipe(omeletteRecipe);

    // Act
    boolean removed = cookbook.removeRecipe(pancakeRecipe);

    // Assert
    assertFalse(removed);
    assertEquals(1, cookbook.getAllRecipes().size());
  }

  /**
   * Tests that checking if a recipe exists works correctly.
   */
  @Test
  void containsRecipe() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act
    boolean contains = cookbook.containsRecipe("Pancakes");

    // Assert
    assertTrue(contains);
  }

  /**
   * Tests that checking for a non-existent recipe returns false.
   */
  @Test
  void containsRecipeNotFound() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act
    boolean contains = cookbook.containsRecipe("Waffles");

    // Assert
    assertFalse(contains);
  }

  /**
   * Tests that passing a null recipe to removeRecipe throws an exception.
   */
  @Test
  void removeNullRecipeThrowsException() {
    // Arrange

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.removeRecipe(null);
    });
    assertEquals("Recipe cannot be null.", exception.getMessage());
  }

  /**
   * Tests that passing a null name to containsRecipe throws an exception.
   */
  @Test
  void containsRecipeNullNameThrowsException() {
    // Arrange

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.containsRecipe(null);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that passing a null inventory to getRecipesCanBeMade throws an exception.
   */
  @Test
  void getRecipesCanBeMadeNullInventoryThrowsException() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.getRecipesCanBeMade(null);
    });
    assertEquals("Inventory cannot be null.", exception.getMessage());
  }

  /**
   * Tests that passing a null inventory to getRecipesCannotBeMade throws an exception.
   */
  @Test
  void getRecipesCannotBeMadeNullInventoryThrowsException() {
    // Arrange
    cookbook.addRecipe(pancakeRecipe);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      cookbook.getRecipesCannotBeMade(null);
    });
    assertEquals("Inventory cannot be null.", exception.getMessage());
  }
}
