package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    inventory.addIngredient(
        new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 15.0));
    inventory.addIngredient(
        new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0));
    inventory.addIngredient(
        new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0));

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
    inventory.addIngredient(
        new Ingredient("Flour", 150, Unit.GRAM, LocalDate.now().plusDays(30), 15.0));
    inventory.addIngredient(
        new Ingredient("Milk", 1.0, Unit.DECILITER, LocalDate.now().plusDays(5), 20.0));
    inventory.addIngredient(
        new Ingredient("Eggs", 1, Unit.PIECE, LocalDate.now().plusDays(10), 3.0));

    boolean canBeMade = recipe.canBeMadeFromInventory(inventory);

    assertFalse(canBeMade);
  }

  @DisplayName("Test that canBeMadeFromInventory returns false when units are incompatible")
  @Test
  void testCannotBeMadeFromInventoryIncompatibleUnits() {
    recipe.addIngredient("Flour", 200, Unit.GRAM);
    recipe.addIngredient("Milk", 3, Unit.DECILITER);

    FoodInventory inventory = new FoodInventory();
    inventory.addIngredient(new Ingredient("Flour", 1.0, Unit.LITER, LocalDate.now().plusDays(30),
        15.0));
    inventory.addIngredient(
        new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0));

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

  @DisplayName("Test that creating a recipe with null or empty name throws an exception")
  @Test
  void testConstructorInvalidName() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe(null, "Description", "Method", 4);
    });
    assertEquals("Recipe name cannot be null or empty.", exception.getMessage());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("", "Description", "Method", 4);
    });
    assertEquals("Recipe name cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that creating a recipe with null or empty description throws an exception")
  @Test
  void testConstructorInvalidDescription() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("Name", null, "Method", 4);
    });
    assertEquals("Description cannot be null or empty.", exception.getMessage());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("Name", "", "Method", 4);
    });
    assertEquals("Description cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that creating a recipe with null or empty preparation method throws an exception")
  @Test
  void testConstructorInvalidPreparationMethod() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("Name", "Description", null, 4);
    });
    assertEquals("Preparation method cannot be null or empty.", exception.getMessage());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("Name", "Description", "", 4);
    });
    assertEquals("Preparation method cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that creating a recipe with non-positive servings throws an exception")
  @Test
  void testConstructorInvalidServings() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("Name", "Description", "Method", 0);
    });
    assertEquals("Servings must be positive.", exception.getMessage());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      new Recipe("Name", "Description", "Method", -1);
    });
    assertEquals("Servings must be positive.", exception.getMessage());
  }

  @DisplayName("Test that adding an ingredient with the same name updates the requirement")
  @Test
  void testAddIngredientDuplicateName() {
    recipe.addIngredient("Flour", 200, Unit.GRAM);
    recipe.addIngredient("Flour", 300, Unit.GRAM);

    IngredientRequirement requirement = recipe.getIngredients().get("flour");
    assertEquals(300, requirement.getQuantity());
    assertEquals(Unit.GRAM, requirement.getUnit());
  }

  @DisplayName("Test that setDescription updates the description with a valid input")
  @Test
  void testSetDescriptionValid() {
    String newDescription = "Delicious fluffy pancakes";
    recipe.setDescription(newDescription);
    assertEquals(newDescription, recipe.getDescription());
  }

  @DisplayName("Test that setting a null or empty description throws an exception")
  @Test
  void testSetDescriptionInvalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.setDescription(null);
    });
    assertEquals("Description cannot be null or empty.", exception.getMessage());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.setDescription("");
    });
    assertEquals("Description cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that setPreparationMethod updates the method with a valid input")
  @Test
  void testSetPreparationMethodValid() {
    String newMethod = "Mix ingredients thoroughly and cook on a skillet.";
    recipe.setPreparationMethod(newMethod);
    assertEquals(newMethod, recipe.getPreparationMethod());
  }

  @DisplayName("Test that setting a null or empty preparation method throws an exception")
  @Test
  void testSetPreparationMethodInvalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.setPreparationMethod(null);
    });
    assertEquals("Preparation method cannot be null or empty.", exception.getMessage());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.setPreparationMethod("");
    });
    assertEquals("Preparation method cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that setServings updates the servings with a valid input")
  @Test
  void testSetServingsValid() {
    int newServings = 6;
    recipe.setServings(newServings);
    assertEquals(newServings, recipe.getServings());
  }

  @DisplayName("Test that setting non-positive servings throws an exception")
  @Test
  void testSetServingsInvalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.setServings(0);
    });
    assertEquals("Servings must be positive.", exception.getMessage());

    exception = assertThrows(IllegalArgumentException.class, () -> {
      recipe.setServings(-2);
    });
    assertEquals("Servings must be positive.", exception.getMessage());
  }

  @DisplayName("Test getName method returns the correct name")
  @Test
  void testGetName() {
    assertEquals("Pancakes", recipe.getName());
  }

  @DisplayName("Test getDescription method returns the correct description")
  @Test
  void testGetDescription() {
    assertEquals("Fluffy pancakes", recipe.getDescription());
  }

  @DisplayName("Test getPreparationMethod method returns the correct method")
  @Test
  void testGetPreparationMethod() {
    assertEquals("Mix ingredients and cook on a skillet.", recipe.getPreparationMethod());
  }

  @DisplayName("Test getServings method returns the correct number of servings")
  @Test
  void testGetServings() {
    assertEquals(4, recipe.getServings());
  }

  @DisplayName("Test that two recipes with the same name are equal")
  @Test
  void testEqualsSameName() {
    Recipe anotherRecipe = new Recipe("Pancakes", "Different description", "Different method", 2);
    assertEquals(recipe, anotherRecipe);
  }

  @DisplayName("Test that two recipes with different names are not equal")
  @Test
  void testEqualsDifferentName() {
    Recipe differentRecipe = new Recipe("Waffles", "Crispy waffles", "Cook in a waffle iron.", 4);
    assertNotEquals(recipe, differentRecipe);
  }

  @DisplayName("Test that recipe does not equal null")
  @Test
  void testEqualsWithNull() {
    assertNotEquals(recipe, null);
  }

  @DisplayName("Test that recipe does not equal an object of a different class")
  @Test
  void testEqualsWithDifferentClass() {
    String notARecipe = "Not a recipe";
    assertNotEquals(recipe, notARecipe);
  }

  @DisplayName("Test that equal recipes have the same hash code")
  @Test
  void testHashCodeEqualRecipes() {
    Recipe anotherRecipe = new Recipe("Pancakes", "Different description", "Different method", 2);
    assertEquals(recipe.hashCode(), anotherRecipe.hashCode());
  }

  @DisplayName("Test that different recipes have different hash codes")
  @Test
  void testHashCodeDifferentRecipes() {
    Recipe differentRecipe = new Recipe("Waffles", "Crispy waffles", "Cook in a waffle iron.", 4);
    assertNotEquals(recipe.hashCode(), differentRecipe.hashCode());
  }

  @DisplayName("Test that toString method returns the correct string representation")
  @Test
  void testToString() {
    String expectedString = String.format("Recipe{name='%s', servings=%d}", "Pancakes", 4);
    assertEquals(expectedString, recipe.toString());
  }

}
