package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Recipe} class.
 */
class RecipeTest {

  private Recipe recipe;

  /**
   * Sets up a new Recipe before each test.
   */
  @BeforeEach
  void setUp() {
    recipe = new Recipe("Pancakes", "Fluffy pancakes", "Mix ingredients and cook on a skillet.", 4);
  }

  @DisplayName("Test that the recipe is created correctly with valid inputs")
  @Test
  void testValidConstructor() {
    String name = "Omelette";
    String description = "Simple omelette";
    String preparationMethod = "Beat eggs and cook on a pan.";
    int servings = 2;

    Recipe omelette = new Recipe(name, description, preparationMethod, servings);

    assertEquals(name, omelette.getName());
    assertEquals(description, omelette.getDescription());
    assertEquals(preparationMethod, omelette.getPreparationMethod());
    assertEquals(servings, omelette.getServings());
  }

  @DisplayName("Test that adding an ingredient with valid inputs works correctly")
  @Test
  void testAddIngredient() {
    String ingredientName = "Flour";
    double quantity = 200;
    Unit unit = Unit.GRAM;

    recipe.addIngredient(ingredientName, quantity, unit);

    assertTrue(recipe.getIngredients().containsKey(ingredientName.toLowerCase()));
    IngredientRequirement requirement = recipe.getIngredients().get(ingredientName.toLowerCase());
    assertEquals(quantity, requirement.getQuantity());
    assertEquals(unit, requirement.getUnit());
  }

  @DisplayName("Test that adding an ingredient with an invalid name throws an exception")
  @Test
  void testAddIngredientInvalidName() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient("", 200, Unit.GRAM);
    });

    assertEquals("Ingredient name cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that adding an ingredient with an invalid quantity throws an exception")
  @Test
  void testAddIngredientInvalidQuantity() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient("Flour", -100, Unit.GRAM);
    });

    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  @DisplayName("Test that adding an ingredient with a null unit throws an exception")
  @Test
  void testAddIngredientNullUnit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.addIngredient("Flour", 200, null);
    });

    assertEquals("Unit cannot be null.", exception.getMessage());
  }

  @DisplayName("Test that canBeMadeFromInventory returns true when all ingredients are available")
  @Test
  void testCanBeMadeFromInventory() {
    recipe.addIngredient("Flour", 200, Unit.GRAM);
    recipe.addIngredient("Milk", 3, Unit.DECILITER);
    recipe.addIngredient("Eggs", 2, Unit.PIECE);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 15.0));
    inventory.addIngredient(new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0));
    inventory.addIngredient(new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0));

    boolean canBeMade = recipe.canBeMadeFromInventory(inventory);

    assertTrue(canBeMade);
  }

  @DisplayName("Test that canBeMadeFromInventory returns false when ingredients are insufficient")
  @Test
  void testCannotBeMadeFromInventoryInsufficientQuantity() {
    recipe.addIngredient("Flour", 200, Unit.GRAM);
    recipe.addIngredient("Milk", 3, Unit.DECILITER);
    recipe.addIngredient("Eggs", 2, Unit.PIECE);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 150, Unit.GRAM, LocalDate.now().plusDays(30), 15.0));
    inventory.addIngredient(new Ingredient("Milk", 1.0, Unit.DECILITER, LocalDate.now().plusDays(5), 20.0));
    inventory.addIngredient(new Ingredient("Eggs", 1, Unit.PIECE, LocalDate.now().plusDays(10), 3.0));

    boolean canBeMade = recipe.canBeMadeFromInventory(inventory);

    assertFalse(canBeMade);
  }

  @DisplayName("Test that canBeMadeFromInventory returns false when units are incompatible")
  @Test
  void testCannotBeMadeFromInventoryIncompatibleUnits() {
    recipe.addIngredient("Flour", 200, Unit.GRAM);
    recipe.addIngredient("Milk", 3, Unit.DECILITER);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 1.0, Unit.LITER, LocalDate.now().plusDays(30), 15.0)); // Incompatible unit
    inventory.addIngredient(new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0));

    boolean canBeMade = recipe.canBeMadeFromInventory(inventory);

    assertFalse(canBeMade);
  }

  @DisplayName("Test that passing a null inventory to canBeMadeFromInventory throws an exception")
  @Test
  void testCanBeMadeFromInventoryNullInventoryThrowsException() {
    recipe.addIngredient("Flour", 200, Unit.GRAM);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.canBeMadeFromInventory(null);
    });

    assertEquals("Inventory cannot be null.", exception.getMessage());
  }
}
