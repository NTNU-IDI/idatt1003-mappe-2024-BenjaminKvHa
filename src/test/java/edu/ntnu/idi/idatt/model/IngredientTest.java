package edu.ntnu.idi.idatt.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Ingredient} class.
 * <p>
 * This class contains test methods that verify the functionality of the
 * {@code Ingredient} class, including both positive and negative test cases.
 * </p>
 */
class IngredientTest {

  /**
   * Tests that a valid {@code Ingredient} object is created when provided with valid inputs.
   */
  @Test
  void testValidConstructor() {
    String name = "Milk";
    double quantity = 1.5;
    String unit = "liter";
    LocalDate bestBeforeDate = LocalDate.now().plusDays(5);
    double pricePerUnit = 20.0;

    Ingredient ingredient = new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);

    assertEquals(name, ingredient.getName());
    assertEquals(quantity, ingredient.getQuantity());
    assertEquals(unit, ingredient.getUnit());
    assertEquals(bestBeforeDate, ingredient.getBestBeforeDate());
    assertEquals(pricePerUnit, ingredient.getPricePerUnit());
  }

  /**
   * Tests that an exception is thrown when creating an {@code Ingredient} with an invalid name.
   */
  @Test
  void testConstructorInvalidName() {
    String name = "";
    double quantity = 1.5;
    String unit = "liter";
    LocalDate bestBeforeDate = LocalDate.now().plusDays(5);
    double pricePerUnit = 20.0;

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that an exception is thrown when creating an {@code Ingredient} with an invalid quantity.
   */
  @Test
  void testConstructorInvalidQuantity() {
    String name = "Milk";
    double quantity = -1.0;
    String unit = "liter";
    LocalDate bestBeforeDate = LocalDate.now().plusDays(5);
    double pricePerUnit = 20.0;

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);
    });
    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  /**
   * Tests that an exception is thrown when creating an {@code Ingredient} with an invalid unit.
   */
  @Test
  void testConstructorInvalidUnit() {
    String name = "Milk";
    double quantity = 1.5;
    String unit = "";
    LocalDate bestBeforeDate = LocalDate.now().plusDays(5);
    double pricePerUnit = 20.0;

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);
    });
    assertEquals("Unit cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that an exception is thrown when creating an {@code Ingredient} with a null best-before date.
   */
  @Test
  void testConstructorInvalidBestBeforeDate() {
    String name = "Milk";
    double quantity = 1.5;
    String unit = "liter";
    LocalDate bestBeforeDate = null;
    double pricePerUnit = 20.0;

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);
    });
    assertEquals("Best-before date cannot be null.", exception.getMessage());
  }

  /**
   * Tests that an exception is thrown when creating an {@code Ingredient} with an invalid price per unit.
   */
  @Test
  void testConstructorInvalidPricePerUnit() {
    String name = "Milk";
    double quantity = 1.5;
    String unit = "liter";
    LocalDate bestBeforeDate = LocalDate.now().plusDays(5);
    double pricePerUnit = -5.0;

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);
    });
    assertEquals("Price per unit must be positive.", exception.getMessage());
  }

  /**
   * Tests that the {@code getName} method returns the correct name.
   */
  @Test
  void testGetName() {
    Ingredient ingredient = new Ingredient("Salt", 500, "grams", LocalDate.now().plusDays(365), 1.0);

    String name = ingredient.getName();

    assertEquals("Salt", name);
  }

  /**
   * Tests that the {@code getQuantity} method returns the correct quantity.
   */
  @Test
  void testGetQuantity() {
    Ingredient ingredient = new Ingredient("Sugar", 1000, "grams", LocalDate.now().plusDays(180), 2.0);

    double quantity = ingredient.getQuantity();

    assertEquals(1000, quantity);
  }

  /**
   * Tests that the {@code setQuantity} method updates the quantity when given a valid value.
   */
  @Test
  void testSetQuantityValid() {
    Ingredient ingredient = new Ingredient("Sugar", 500, "grams", LocalDate.now().plusDays(180), 2.0);

    ingredient.setQuantity(750);

    assertEquals(750, ingredient.getQuantity());
  }

  /**
   * Tests that an exception is thrown when setting an invalid quantity.
   */
  @Test
  void testSetQuantityInvalid() {
    Ingredient ingredient = new Ingredient("Sugar", 500, "grams", LocalDate.now().plusDays(180), 2.0);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setQuantity(-200);
    });
    assertEquals("Quantity must be positive.", exception.getMessage());
  }

  /**
   * Tests that the {@code getUnit} method returns the correct unit.
   */
  @Test
  void testGetUnit() {
    Ingredient ingredient = new Ingredient("Butter", 250, "grams", LocalDate.now().plusDays(30), 15.0);

    String unit = ingredient.getUnit();

    assertEquals("grams", unit);
  }

  /**
   * Tests that the {@code getBestBeforeDate} method returns the correct date.
   */
  @Test
  void testGetBestBeforeDate() {
    LocalDate expectedDate = LocalDate.now().plusDays(60);
    Ingredient ingredient = new Ingredient("Cheese", 200, "grams", expectedDate, 25.0);

    LocalDate bestBeforeDate = ingredient.getBestBeforeDate();

    assertEquals(expectedDate, bestBeforeDate);
  }

  /**
   * Tests that the {@code setBestBeforeDate} method updates the date when given a valid value.
   */
  @Test
  void testSetBestBeforeDateValid() {
    Ingredient ingredient = new Ingredient("Yogurt", 500, "ml", LocalDate.now().plusDays(7), 10.0);
    LocalDate newDate = LocalDate.now().plusDays(10);

    ingredient.setBestBeforeDate(newDate);

    assertEquals(newDate, ingredient.getBestBeforeDate());
  }

  /**
   * Tests that an exception is thrown when setting a null best-before date.
   */
  @Test
  void testSetBestBeforeDateInvalid() {
    Ingredient ingredient = new Ingredient("Yogurt", 500, "ml", LocalDate.now().plusDays(7), 10.0);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      ingredient.setBestBeforeDate(null);
    });
    assertEquals("Best-before date cannot be null.", exception.getMessage());
  }

  /**
   * Tests that the {@code getPricePerUnit} method returns the correct price.
   */
  @Test
  void testGetPricePerUnit() {
    Ingredient ingredient = new Ingredient("Eggs", 12, "pieces", LocalDate.now().plusDays(14), 3.0);

    double pricePerUnit = ingredient.getPricePerUnit();

    assertEquals(3.0, pricePerUnit);
  }

  /**
   * Tests that two {@code Ingredient} objects with the same name and unit are considered equal.
   */
  @Test
  void testEqualsAndHashCode() {
    Ingredient ingredient1 = new Ingredient("Flour", 1000, "grams", LocalDate.now().plusDays(120), 4.0);
    Ingredient ingredient2 = new Ingredient("Flour", 500, "grams", LocalDate.now().plusDays(90), 4.0);

    assertEquals(ingredient1, ingredient2);
    assertEquals(ingredient1.hashCode(), ingredient2.hashCode());
  }

  /**
   * Tests that two {@code Ingredient} objects with different names are not considered equal.
   */
  @Test
  void testEqualsDifferentName() {
    Ingredient ingredient1 = new Ingredient("Flour", 1000, "grams", LocalDate.now().plusDays(120), 4.0);
    Ingredient ingredient2 = new Ingredient("Corn Flour", 1000, "grams", LocalDate.now().plusDays(120), 4.0);

    assertNotEquals(ingredient1, ingredient2);
  }

  /**
   * Tests that two {@code Ingredient} objects with different units are not considered equal.
   */
  @Test
  void testEqualsDifferentUnit() {
    Ingredient ingredient1 = new Ingredient("Milk", 1, "liter", LocalDate.now().plusDays(5), 20.0);
    Ingredient ingredient2 = new Ingredient("Milk", 1000, "ml", LocalDate.now().plusDays(5), 20.0);

    assertNotEquals(ingredient1, ingredient2);
  }

  /**
   * Tests that the {@code toString} method returns the expected string representation.
   */
  @Test
  void testToString() {
    LocalDate bestBeforeDate = LocalDate.now().plusDays(7);
    Ingredient ingredient = new Ingredient("Eggs", 12, "pieces", bestBeforeDate, 2.0);

    String expectedString = String.format(
        "Ingredient{name='%s', quantity=%.2f %s, bestBeforeDate=%s, pricePerUnit=%.2f NOK}",
        "Eggs", 12.00, "pieces", bestBeforeDate, 2.0);

    assertEquals(expectedString, ingredient.toString());
  }
}
