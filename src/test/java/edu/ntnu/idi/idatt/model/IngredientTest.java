package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

  @DisplayName("Test that creating an ingredient with a null best-before date throws an exception")
  @Test
  void testConstructorNullBestBeforeDate() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Eggs", 12, Unit.PIECE, null, 3.0);
    });

    assertEquals("Best-before date cannot be null.", exception.getMessage());
  }

  @DisplayName("Test that creating an ingredient with a past best-before date throws an exception")
  @Test
  void testConstructorPastBestBeforeDate() {
    LocalDate pastDate = LocalDate.now().minusDays(1);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Eggs", 12, Unit.PIECE, pastDate, 3.0);
    });

    assertEquals("Best-before date cannot be in the past.", exception.getMessage());
  }

  @DisplayName("Test that creating an ingredient with non-positive price per unit throws an exception")
  @Test
  void testConstructorNonPositivePricePerUnit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 0);
    });
    assertEquals("Price per unit must be positive.", exception.getMessage());
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

  @DisplayName("Test that setting a null name throws an exception")
  @Test
  void testSetNullName() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setName(null);
    });
    assertEquals("Ingredient name cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that setting an empty name throws an exception")
  @Test
  void testSetEmptyName() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setName("");
    });
    assertEquals("Ingredient name cannot be null or empty.", exception.getMessage());
  }

  @DisplayName("Test that setting a null best-before date throws an exception")
  @Test
  void testSetNullBestBeforeDate() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setBestBeforeDate(null);
    });
    assertEquals("Best-before date cannot be null.", exception.getMessage());
  }

  @DisplayName("Test that setting a past best-before date throws an exception")
  @Test
  void testSetPastBestBeforeDate() {
    LocalDate pastDate = LocalDate.now().minusDays(1);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setBestBeforeDate(pastDate);
    });
    assertEquals("Best-before date cannot be in the past.", exception.getMessage());
  }

  @DisplayName("Test that setting a zero price per unit throws an exception")
  @Test
  void testSetZeroPricePerUnit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setPricePerUnit(0);
    });
    assertEquals("Price per unit must be positive.", exception.getMessage());
  }

  @DisplayName("Test that setting a negative price per unit throws an exception")
  @Test
  void testSetNegativePricePerUnit() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setPricePerUnit(-10.0);
    });
    assertEquals("Price per unit must be positive.", exception.getMessage());
  }

  @DisplayName("Test the equals method for ingredients with the same name and unit")
  @Test
  void testEqualsSameNameAndUnit() {
    Ingredient anotherMilk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(3),
        18.0);

    assertEquals(ingredient, anotherMilk);
  }

  @DisplayName("Test the equals method for ingredients with different units")
  @Test
  void testEqualsDifferentUnit() {
    Ingredient powderedMilk = new Ingredient("Milk", 500, Unit.GRAM, LocalDate.now().plusDays(20),
        25.0);

    assertNotEquals(ingredient, powderedMilk);
  }

  @DisplayName("Test the equals method for ingredients with different names")
  @Test
  void testEqualsDifferentName() {
    Ingredient differentIngredient = new Ingredient("Butter", 2.0, Unit.LITER, bestBeforeDate, 20.0);
    assertNotEquals(ingredient, differentIngredient);
  }

  @DisplayName("Test the equals method comparing with null")
  @Test
  void testEqualsWithNull() {
    assertNotEquals(ingredient, null);
  }

  @DisplayName("Test the equals method comparing with a different class")
  @Test
  void testEqualsWithDifferentClass() {
    String notAnIngredient = "Not an ingredient";
    assertNotEquals(ingredient, notAnIngredient);
  }

  @DisplayName("Test that equal ingredients have the same hash code")
  @Test
  void testHashCode() {
    Ingredient anotherMilk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(3),
        18.0);
    assertEquals(ingredient.hashCode(), anotherMilk.hashCode());
  }

  @DisplayName("Test that different ingredients have different hash codes")
  @Test
  void testHashCodeDifferentIngredients() {
    Ingredient differentIngredient = new Ingredient("Butter", 2.0, Unit.LITER, bestBeforeDate, 20.0);
    assertNotEquals(ingredient.hashCode(), differentIngredient.hashCode());
  }

  @DisplayName("Test getName method returns the correct name")
  @Test
  void testGetName() {
    assertEquals("Milk", ingredient.getName());
  }

  @DisplayName("Test getQuantity method returns the correct quantity")
  @Test
  void testGetQuantity() {
    assertEquals(2.0, ingredient.getQuantity());
  }

  @DisplayName("Test getUnit method returns the correct unit")
  @Test
  void testGetUnit() {
    assertEquals(Unit.LITER, ingredient.getUnit());
  }

  @DisplayName("Test getBestBeforeDate method returns the correct date")
  @Test
  void testGetBestBeforeDate() {
    assertEquals(bestBeforeDate, ingredient.getBestBeforeDate());
  }

  @DisplayName("Test getPricePerUnit method returns the correct price")
  @Test
  void testGetPricePerUnit() {
    assertEquals(20.0, ingredient.getPricePerUnit());
  }

  @DisplayName("Test the toString method")
  @Test
  void testToString() {
    String expected = String.format("%s: %.2f %s (Best before: %s)", "Milk", 2.0, "L",
        bestBeforeDate);

    String actual = ingredient.toString();

    assertEquals(expected, actual);
  }
}
