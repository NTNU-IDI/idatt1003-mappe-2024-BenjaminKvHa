package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link IngredientRequirement} class.
 */
class IngredientRequirementTest {

  private IngredientRequirement ingredientRequirement;

  /**
   * Sets up a valid IngredientRequirement before each test.
   */
  @BeforeEach
  void setUp() {
    ingredientRequirement = new IngredientRequirement(200, Unit.GRAM);
  }

  @DisplayName("Test that the constructor creates an IngredientRequirement with valid inputs")
  @Test
  void testValidConstructor() {
    double quantity = 500;
    Unit unit = Unit.MILLILITER;

    IngredientRequirement requirement = new IngredientRequirement(quantity, unit);

    assertEquals(quantity, requirement.getQuantity());
    assertEquals(unit, requirement.getUnit());
  }

  @DisplayName("Test that creating an IngredientRequirement with zero quantity throws an exception")
  @Test
  void testConstructorZeroQuantity() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new IngredientRequirement(0, Unit.GRAM);
    });

    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  @DisplayName("Test that creating an IngredientRequirement with negative quantity throws an exception")
  @Test
  void testConstructorNegativeQuantity() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new IngredientRequirement(-100, Unit.GRAM);
    });

    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  @DisplayName("Test that creating an IngredientRequirement with null unit throws an exception")
  @Test
  void testConstructorNullUnit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new IngredientRequirement(100, null);
    });

    assertEquals("Unit cannot be null.", exception.getMessage());
  }

  @DisplayName("Test getQuantity method returns the correct quantity")
  @Test
  void testGetQuantity() {
    assertEquals(200, ingredientRequirement.getQuantity());
  }

  @DisplayName("Test getUnit method returns the correct unit")
  @Test
  void testGetUnit() {
    assertEquals(Unit.GRAM, ingredientRequirement.getUnit());
  }

  @DisplayName("Test that two IngredientRequirements with the same quantity and unit are equal")
  @Test
  void testEqualsAndHashCode() {
    IngredientRequirement requirement1 = new IngredientRequirement(200, Unit.GRAM);
    IngredientRequirement requirement2 = new IngredientRequirement(200, Unit.GRAM);

    assertEquals(requirement1, requirement2);
    assertEquals(requirement1.hashCode(), requirement2.hashCode());
  }

  @DisplayName("Test that toString method returns the correct string representation")
  @Test
  void testToString() {
    String expectedString = "200,00 g";
    assertEquals(expectedString, ingredientRequirement.toString());
  }
}