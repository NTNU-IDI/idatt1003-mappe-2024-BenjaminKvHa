package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Recipe} class.
 * <p>
 * This class tests the functionality of the Recipe class, including adding ingredients,
 * checking if a recipe can be made from an inventory, and handling invalid inputs.
 * </p>
 */
class RecipeTest {

  private Recipe recipe;

  /**
   * Sets up a new Recipe before each test.
   */
  @BeforeEach
  void setUp() {
    recipe = new Recipe(
        "Pancakes",
        "Fluffy pancakes",
        "Mix ingredients and cook on a skillet.",
        4
    );
  }

  /**
   * Tests that a recipe is created with valid inputs.
   */
  @Test
  void testValidConstructor() {
    // Arrange
    // Done in setUp()

    // Act & Assert
    assertEquals("Pancakes", recipe.getName());
    assertEquals("Fluffy pancakes", recipe.getDescription());
    assertEquals("Mix ingredients and cook on a skillet.", recipe.getPreparationMethod());
    assertEquals(4, recipe.getServings());
    assertTrue(recipe.getIngredients().isEmpty());
  }

  /**
   * Tests that creating a recipe with an invalid name throws an exception.
   */
  @Test
  void testConstructorInvalidName() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("", "Description", "Method", 4);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that adding a valid ingredient works correctly.
   */
  @Test
  void testAddIngredient() {
    // Arrange
    // Done in setUp()

    // Act
    recipe.addIngredient("Flour", 200);

    // Assert
    Map<String, Double> ingredients = recipe.getIngredients();
    assertEquals(1, ingredients.size());
    assertTrue(ingredients.containsKey("flour"));
    assertEquals(200, ingredients.get("flour"));
  }

  /**
   * Tests that adding an ingredient with an invalid name throws an exception.
   */
  @Test
  void testAddIngredientInvalidName() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient("", 100);
    });
    assertEquals("Ingredient name cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that adding an ingredient with an invalid quantity throws an exception.
   */
  @Test
  void testAddIngredientInvalidQuantity() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient("Sugar", -50);
    });
    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  /**
   * Tests that the recipe can correctly identify if it can be made from a given inventory.
   */
  @Test
  void testCanBeMadeFromInventory() {
    // Arrange
    recipe.addIngredient("Flour", 200);
    recipe.addIngredient("Milk", 300);
    recipe.addIngredient("Eggs", 2);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 500, "grams", LocalDate.now().plusDays(5), 10));
    inventory.addIngredient(new Ingredient("Milk", 1000, "ml", LocalDate.now().plusDays(5), 15));
    inventory.addIngredient(new Ingredient("Eggs", 6, "pieces", LocalDate.now().plusDays(5), 20));

    // Act
    boolean canBeMade = recipe.canBeMadeFromInventory(inventory);

    // Assert
    assertTrue(canBeMade);
  }

  /**
   * Tests that the recipe correctly identifies when it cannot be made due to insufficient quantities.
   */
  @Test
  void testCannotBeMadeFromInventoryInsufficientQuantity() {
    // Arrange
    recipe.addIngredient("Flour", 200);
    recipe.addIngredient("Milk", 300);
    recipe.addIngredient("Eggs", 2);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 100, "grams", LocalDate.now().plusDays(5), 10));
    inventory.addIngredient(new Ingredient("Milk", 1000, "ml", LocalDate.now().plusDays(5), 15));
    inventory.addIngredient(new Ingredient("Eggs", 1, "pieces", LocalDate.now().plusDays(5), 20));

    // Act
    boolean canBeMade = recipe.canBeMadeFromInventory(inventory);

    // Assert
    assertFalse(canBeMade);
  }

  /**
   * Tests that the recipe correctly identifies when it cannot be made due to missing ingredients.
   */
  @Test
  void testCannotBeMadeFromInventoryMissingIngredient() {
    // Arrange
    recipe.addIngredient("Flour", 200);
    recipe.addIngredient("Milk", 300);
    recipe.addIngredient("Eggs", 2);
    recipe.addIngredient("Sugar", 50);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 500, "grams", LocalDate.now().plusDays(5), 10));
    inventory.addIngredient(new Ingredient("Milk", 1000, "ml", LocalDate.now().plusDays(5), 15));
    inventory.addIngredient(new Ingredient("Eggs", 6, "pieces", LocalDate.now().plusDays(5), 20));

    // Act
    boolean canBeMade = recipe.canBeMadeFromInventory(inventory);

    // Assert
    assertFalse(canBeMade);
  }

  /**
   * Tests that passing a null inventory to canBeMadeFromInventory throws an exception.
   */
  @Test
  void testCanBeMadeFromInventoryNullInventoryThrowsException() {
    // Arrange
    recipe.addIngredient("Flour", 200);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.canBeMadeFromInventory(null);
    });
    assertEquals("Inventory cannot be null.", exception.getMessage());
  }
}
