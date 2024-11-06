package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Ingredient} class.
 */
class IngredientTest {

  private Ingredient ingredient;

  /**
   * Sets up a sample ingredient before each test.
   */
  @BeforeEach
  void setUp() {
    ingredient = new Ingredient("Milk", 2.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
  }

  /**
   * Tests that the constructor creates an ingredient with valid inputs.
   */
  @Test
  void testValidConstructor() {
    // Arrange
    String name = "Sugar";
    double quantity = 1.0;
    Unit unit = Unit.KILOGRAM;
    LocalDate bestBeforeDate = LocalDate.now().plusDays(30);
    double pricePerUnit = 15.0;

    // Act
    Ingredient sugar = new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);

    // Assert
    assertEquals(name, sugar.getName());
    assertEquals(quantity, sugar.getQuantity());
    assertEquals(unit, sugar.getUnit());
    assertEquals(bestBeforeDate, sugar.getBestBeforeDate());
    assertEquals(pricePerUnit, sugar.getPricePerUnit());
  }

  /**
   * Tests that creating an ingredient with an invalid name throws an exception.
   */
  @Test
  void testConstructorInvalidName() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("", 1.0, Unit.GRAM, LocalDate.now().plusDays(10), 5.0);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that setting an invalid quantity throws an exception.
   */
  @Test
  void testSetInvalidQuantity() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setQuantity(-5.0);
    });
    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  /**
   * Tests that setting a null unit throws an exception.
   */
  @Test
  void testSetNullUnit() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setUnit(null);
    });
    assertEquals("Unit cannot be null.", exception.getMessage());
  }

  /**
   * Tests the equals method for ingredients with the same name and unit.
   */
  @Test
  void testEqualsSameNameAndUnit() {
    // Arrange
    Ingredient anotherMilk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(3), 18.0);

    // Act & Assert
    assertEquals(ingredient, anotherMilk);
  }

  /**
   * Tests the equals method for ingredients with different units.
   */
  @Test
  void testEqualsDifferentUnit() {
    // Arrange
    Ingredient powderedMilk = new Ingredient("Milk", 500, Unit.GRAM, LocalDate.now().plusDays(20), 25.0);

    // Act & Assert
    assertNotEquals(ingredient, powderedMilk);
  }

  /**
   * Tests the toString method.
   */
  @Test
  void testToString() {
    // Arrange
    String expected = String.format("%s: %.2f %s (Best before: %s)", "Milk", 2.0, "L", LocalDate.now().plusDays(5));

    // Act
    String actual = ingredient.toString();

    // Assert
    assertEquals(expected, actual);
  }
}
