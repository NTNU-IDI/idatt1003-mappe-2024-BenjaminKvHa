package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Ingredient} class.
 */
class IngredientTest {

  private Ingredient ingredient;
  private LocalDate bestBeforeDate;

  /**
   * Sets up a sample ingredient before each test.
   */
  @BeforeEach
  void setUp() {
    bestBeforeDate = LocalDate.now().plusDays(5);
    ingredient = new Ingredient("Milk", 2.0, Unit.LITER, bestBeforeDate, 20.0);
  }

  @DisplayName("Test that the constructor creates an ingredient with valid inputs")
  @Test
  void testValidConstructor() {
    String name = "Sugar";
    double quantity = 1.0;
    Unit unit = Unit.KILOGRAM;
    LocalDate bestBeforeDate = LocalDate.now().plusDays(30);
    double pricePerUnit = 15.0;

    Ingredient sugar = new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);

    assertEquals(name, sugar.getName());
    assertEquals(quantity, sugar.getQuantity());
    assertEquals(unit, sugar.getUnit());
    assertEquals(bestBeforeDate, sugar.getBestBeforeDate());
    assertEquals(pricePerUnit, sugar.getPricePerUnit());
  }

  @DisplayName("Test that creating an ingredient with an invalid name throws an exception")
  @Test
  void testConstructorInvalidName() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("", 1.0, Unit.GRAM, LocalDate.now().plusDays(10), 5.0);
    });

    assertEquals("Ingredient name cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that setting an invalid quantity throws an exception")
  @Test
  void testSetInvalidQuantity() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setQuantity(-5.0);
    });

    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  @DisplayName("Test that setting a null unit throws an exception")
  @Test
  void testSetNullUnit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setUnit(null);
    });

    assertEquals("Unit cannot be null.", exception.getMessage());
  }

  @DisplayName("Test the equals method for ingredients with the same name and unit")
  @Test
  void testEqualsSameNameAndUnit() {
    Ingredient anotherMilk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(3), 18.0);

    assertEquals(ingredient, anotherMilk);
  }

  @DisplayName("Test the equals method for ingredients with different units")
  @Test
  void testEqualsDifferentUnit() {
    Ingredient powderedMilk = new Ingredient("Milk", 500, Unit.GRAM, LocalDate.now().plusDays(20), 25.0);

    assertNotEquals(ingredient, powderedMilk);
  }

  @DisplayName("Test the toString method")
  @Test
  void testToString() {
    String expected = String.format("%s: %.2f %s (Best before: %s)", "Milk", 2.0, "L", bestBeforeDate);

    String actual = ingredient.toString();

    assertEquals(expected, actual);
  }
}
