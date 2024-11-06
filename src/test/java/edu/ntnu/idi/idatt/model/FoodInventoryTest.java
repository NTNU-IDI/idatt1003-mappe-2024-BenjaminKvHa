package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link FoodInventory} class.
 */
class FoodInventoryTest {

  private FoodInventory inventory;

  /**
   * Sets up a new FoodInventory before each test.
   */
  @BeforeEach
  void setUp() {
    inventory = new FoodInventory();
  }

  @Test
  void testRemoveQuantity() {
    // Arrange
    Ingredient sugar = new Ingredient("Sugar", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(365), 10.0);
    inventory.addIngredient(sugar);

    // Act
    boolean result = inventory.removeQuantity("Sugar", 500, Unit.GRAM); // Remove 500 grams

    // Assert
    assertTrue(result);
    Ingredient storedSugar = inventory.findIngredientByName("Sugar");
    assertNotNull(storedSugar);
    assertEquals(0.5, storedSugar.getQuantity(), 0.0001); // Remaining quantity should be 0.5 kg
  }

  /**
   * Tests that removing a quantity removes the ingredient when quantity becomes zero.
   */
  @Test
  void testRemoveQuantityRemovesIngredientWhenZero() {
    // Arrange
    Ingredient sugar = new Ingredient("Sugar", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(365), 10.0);
    inventory.addIngredient(sugar);

    // Act
    boolean result = inventory.removeQuantity("Sugar", 1.0, Unit.KILOGRAM); // Remove 1 kg

    // Assert
    assertTrue(result);
    assertNull(inventory.findIngredientByName("Sugar"));
  }

  /**
   * Tests that removing a quantity with incompatible units throws an exception.
   */
  @Test
  void testRemoveQuantityIncompatibleUnitsThrowsException() {
    // Arrange
    Ingredient eggs = new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0);
    inventory.addIngredient(eggs);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      inventory.removeQuantity("Eggs", 500, Unit.GRAM); // Attempting to remove grams from pieces
    });
    assertEquals("Units are incompatible for ingredient: Eggs", exception.getMessage());
  }

  /**
   * Tests that adding ingredients with compatible units updates the quantity correctly with conversions.
   */
  @Test
  void testAddIngredientWithUnitConversion() {
    // Arrange
    Ingredient milkInLiters = new Ingredient("Milk", 2.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
    Ingredient milkInDeciliters = new Ingredient("Milk", 2.0, Unit.DECILITER, LocalDate.now().plusDays(7), 18.0);

    // Act
    inventory.addIngredient(milkInLiters);
    inventory.addIngredient(milkInDeciliters);

    // Assert
    Ingredient storedMilk = inventory.findIngredientByName("Milk");
    assertNotNull(storedMilk);
    assertEquals(2.2, storedMilk.getQuantity(), 0.0001);
    assertEquals(Unit.LITER, storedMilk.getUnit());
  }

  /**
   * Tests that adding ingredients with compatible mass units updates the quantity correctly with conversions.
   */
  @Test
  void testAddIngredientWithMassUnitConversion() {
    // Arrange
    Ingredient flourInGrams = new Ingredient("Flour", 500, Unit.GRAM, LocalDate.now().plusDays(30), 15.0);
    Ingredient flourInKilograms = new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(25), 14.0);

    // Act
    inventory.addIngredient(flourInGrams);
    inventory.addIngredient(flourInKilograms);

    // Assert
    Ingredient storedFlour = inventory.findIngredientByName("Flour");
    assertNotNull(storedFlour);
    assertEquals(1500, storedFlour.getQuantity(), 0.0001); // Quantity in grams
    assertEquals(Unit.GRAM, storedFlour.getUnit()); // Original unit retained
  }

  /**
   * Tests that adding an ingredient with an incompatible unit throws an exception.
   */
  @Test
  void testAddIngredientIncompatibleUnitsThrowsException() {
    // Arrange
    Ingredient milkInLiters = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
    Ingredient milkInGrams = new Ingredient("Milk", 500, Unit.GRAM, LocalDate.now().plusDays(60), 25.0);

    // Act
    inventory.addIngredient(milkInLiters);

    // Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      inventory.addIngredient(milkInGrams);
    });
    assertEquals("Units are incompatible for ingredient: Milk", exception.getMessage());
  }

  /**
   * Tests that an ingredient can be added to the inventory.
   */
  @Test
  void testAddIngredient() {
    Ingredient milk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);

    inventory.addIngredient(milk);

    assertEquals(1, inventory.getAllIngredientsSortedByName().size());
    assertEquals(milk, inventory.findIngredientByName("Milk"));
  }

  /**
   * Tests that adding a null ingredient throws an exception.
   */
  @Test
  void testAddNullIngredientThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      inventory.addIngredient(null);
    });
    assertEquals("Ingredient cannot be null.", exception.getMessage());
  }

  /**
   * Tests that adding ingredients with compatible units updates the quantity.
   */
  @Test
  void testAddIngredientUpdatesQuantityWithCompatibleUnits() {
    Ingredient flour1 = new Ingredient("Flour", 500, Unit.GRAM, LocalDate.now().plusDays(30), 15.0);
    Ingredient flour2 = new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(25), 14.0);

    inventory.addIngredient(flour1);
    inventory.addIngredient(flour2);

    Ingredient storedFlour = inventory.findIngredientByName("Flour");
    assertNotNull(storedFlour);
    assertEquals(1500, storedFlour.getQuantity()); // Quantity in grams
    assertEquals(Unit.GRAM, storedFlour.getUnit()); // Original unit retained
  }

  /**
   * Tests that getting all ingredients returns a sorted list.
   */
  @Test
  void testGetAllIngredientsSortedByName() {
    Ingredient milk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
    Ingredient bread = new Ingredient("Bread", 1.0, Unit.PIECE, LocalDate.now().plusDays(2), 25.0);
    Ingredient eggs = new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0);

    inventory.addIngredient(milk);
    inventory.addIngredient(bread);
    inventory.addIngredient(eggs);

    List<Ingredient> ingredients = inventory.getAllIngredientsSortedByName();

    assertEquals(3, ingredients.size());
    assertEquals("Bread", ingredients.get(0).getName());
    assertEquals("Eggs", ingredients.get(1).getName());
    assertEquals("Milk", ingredients.get(2).getName());
  }
}
