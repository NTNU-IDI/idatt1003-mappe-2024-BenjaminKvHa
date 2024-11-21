package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

  @DisplayName("Test removing a quantity from an ingredient updates the quantity correctly")
  @Test
  void testRemoveQuantity() {
    Ingredient sugar = new Ingredient("Sugar", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(365), 10.0);
    inventory.addIngredient(sugar);

    boolean result = inventory.removeQuantity("Sugar", 500, Unit.GRAM); // Remove 500 grams

    assertTrue(result);
    Ingredient storedSugar = inventory.findIngredientByName("Sugar");
    assertNotNull(storedSugar);
    assertEquals(0.5, storedSugar.getQuantity(), 0.0001); // Remaining quantity should be 0.5 kg
  }

  @DisplayName("Test removing a quantity that results in zero removes the ingredient from inventory")
  @Test
  void testRemoveQuantityRemovesIngredientWhenZero() {
    Ingredient sugar = new Ingredient("Sugar", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(365), 10.0);
    inventory.addIngredient(sugar);

    boolean result = inventory.removeQuantity("Sugar", 1.0, Unit.KILOGRAM); // Remove 1 kg

    assertTrue(result);
    assertNull(inventory.findIngredientByName("Sugar"));
  }

  @DisplayName("Test removing a quantity with incompatible units throws an exception")
  @Test
  void testRemoveQuantityIncompatibleUnitsThrowsException() {
    Ingredient eggs = new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0);
    inventory.addIngredient(eggs);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      inventory.removeQuantity("Eggs", 500, Unit.GRAM);
    });

    assertEquals("Units are incompatible for ingredient: Eggs", exception.getMessage());
  }

  @DisplayName("Test adding ingredients with compatible volume units updates quantity with conversion")
  @Test
  void testAddIngredientWithUnitConversion() {
    Ingredient milkInLiters = new Ingredient("Milk", 2.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
    Ingredient milkInDeciliters = new Ingredient("Milk", 2.0, Unit.DECILITER, LocalDate.now().plusDays(7), 18.0);

    inventory.addIngredient(milkInLiters);
    inventory.addIngredient(milkInDeciliters);

    Ingredient storedMilk = inventory.findIngredientByName("Milk");
    assertNotNull(storedMilk);
    assertEquals(2.2, storedMilk.getQuantity(), 0.0001);
    assertEquals(Unit.LITER, storedMilk.getUnit());
  }

  @DisplayName("Test adding ingredients with compatible mass units updates quantity with conversion")
  @Test
  void testAddIngredientWithMassUnitConversion() {
    Ingredient flourInGrams = new Ingredient("Flour", 500, Unit.GRAM, LocalDate.now().plusDays(30), 15.0);
    Ingredient flourInKilograms = new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(25), 14.0);

    inventory.addIngredient(flourInGrams);
    inventory.addIngredient(flourInKilograms);

    Ingredient storedFlour = inventory.findIngredientByName("Flour");
    assertNotNull(storedFlour);
    assertEquals(1500, storedFlour.getQuantity(), 0.0001); // Quantity in grams
    assertEquals(Unit.GRAM, storedFlour.getUnit()); // Original unit retained
  }

  @DisplayName("Test adding an ingredient with incompatible units throws an exception")
  @Test
  void testAddIngredientIncompatibleUnitsThrowsException() {
    Ingredient milkInLiters = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
    Ingredient milkInGrams = new Ingredient("Milk", 500, Unit.GRAM, LocalDate.now().plusDays(60), 25.0);

    inventory.addIngredient(milkInLiters);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      inventory.addIngredient(milkInGrams);
    });
    assertEquals("Units are incompatible for ingredient: Milk", exception.getMessage());
  }

  @DisplayName("Test adding a valid ingredient to the inventory")
  @Test
  void testAddIngredient() {
    Ingredient milk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);

    inventory.addIngredient(milk);

    assertEquals(1, inventory.getAllIngredientsSortedByName().size());
    assertEquals(milk, inventory.findIngredientByName("Milk"));
  }

  @DisplayName("Test adding a null ingredient throws an exception")
  @Test
  void testAddNullIngredientThrowsException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      inventory.addIngredient(null);
    });

    assertEquals("Ingredient cannot be null.", exception.getMessage());
  }

  @DisplayName("Test adding ingredients with compatible units updates the quantity correctly")
  @Test
  void testAddIngredientUpdatesQuantityWithCompatibleUnits() {
    Ingredient flour1 = new Ingredient("Flour", 500, Unit.GRAM, LocalDate.now().plusDays(30), 15.0);
    Ingredient flour2 = new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(25), 14.0);

    inventory.addIngredient(flour1);
    inventory.addIngredient(flour2);

    Ingredient storedFlour = inventory.findIngredientByName("Flour");
    assertNotNull(storedFlour);
    assertEquals(1500, storedFlour.getQuantity());
    assertEquals(Unit.GRAM, storedFlour.getUnit());
  }

  @DisplayName("Test retrieving all ingredients returns a sorted list by name")
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
