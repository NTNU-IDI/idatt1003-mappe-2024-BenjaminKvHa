package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FoodInventory} class.
 * <p>
 * This class tests the functionality of the FoodInventory class, including adding ingredients,
 * finding ingredients by name, removing quantities, retrieving ingredients expiring before a
 * certain date, and getting all ingredients sorted by name.
 * </p>
 */
class FoodInventoryTest {

  private FoodInventory foodInventory;

  private Ingredient milk;
  private Ingredient bread;
  private Ingredient eggs;

  /**
   * Sets up a new FoodInventory and sample ingredients before each test.
   */
  @BeforeEach
  void setUp() {
    foodInventory = new FoodInventory();

    milk = new Ingredient(
        "Milk",
        2.0,
        "liter",
        LocalDate.now().plusDays(5),
        20.0
    );

    bread = new Ingredient(
        "Bread",
        1.0,
        "loaf",
        LocalDate.now().plusDays(2),
        25.0
    );

    eggs = new Ingredient(
        "Eggs",
        12,
        "pieces",
        LocalDate.now().plusDays(10),
        3.0
    );
  }

  /**
   * Tests that an ingredient can be added to the inventory.
   */
  @Test
  void testAddIngredient() {
    // Arrange
    // Ingredients are set up in the setUp() method

    // Act
    foodInventory.addIngredient(milk);

    // Assert
    Ingredient retrievedMilk = foodInventory.findIngredientByName("Milk");
    assertNotNull(retrievedMilk);
    assertEquals(milk.getName(), retrievedMilk.getName());
    assertEquals(milk.getQuantity(), retrievedMilk.getQuantity());
  }

  /**
   * Tests that adding an ingredient that already exists increases its quantity.
   */
  @Test
  void testAddExistingIngredientIncreasesQuantity() {
    // Arrange
    foodInventory.addIngredient(milk);

    Ingredient additionalMilk = new Ingredient(
        "Milk",
        1.0,
        "liter",
        LocalDate.now().plusDays(7),
        20.0
    );

    // Act
    foodInventory.addIngredient(additionalMilk);

    // Assert
    Ingredient retrievedMilk = foodInventory.findIngredientByName("Milk");
    assertNotNull(retrievedMilk);
    assertEquals(3.0, retrievedMilk.getQuantity());
  }

  /**
   * Tests that adding a null ingredient throws an exception.
   */
  @Test
  void testAddNullIngredientThrowsException() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      foodInventory.addIngredient(null);
    });
    assertEquals("Ingredient cannot be null.", exception.getMessage());
  }

  /**
   * Tests that an ingredient can be found by name.
   */
  @Test
  void testFindIngredientByName() {
    // Arrange
    foodInventory.addIngredient(bread);

    // Act
    Ingredient foundIngredient = foodInventory.findIngredientByName("Bread");

    // Assert
    assertNotNull(foundIngredient);
    assertEquals(bread, foundIngredient);
  }

  /**
   * Tests that searching for a non-existent ingredient returns null.
   */
  @Test
  void testFindIngredientByNameNotFound() {
    // Arrange
    // No ingredients added

    // Act
    Ingredient foundIngredient = foodInventory.findIngredientByName("Cheese");

    // Assert
    assertNull(foundIngredient);
  }

  /**
   * Tests that searching with a null name throws an exception.
   */
  @Test
  void testFindIngredientByNameNullThrowsException() {
    // Arrange, Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      foodInventory.findIngredientByName(null);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }

  /**
   * Tests that removing a quantity of an ingredient updates its quantity.
   */
  @Test
  void testRemoveQuantity() {
    // Arrange
    foodInventory.addIngredient(eggs);

    // Act
    boolean result = foodInventory.removeQuantity("Eggs", 6);

    // Assert
    assertTrue(result);
    Ingredient retrievedEggs = foodInventory.findIngredientByName("Eggs");
    assertNotNull(retrievedEggs);
    assertEquals(6, retrievedEggs.getQuantity());
  }

  /**
   * Tests that removing a quantity greater than the available amount removes the ingredient.
   */
  @Test
  void testRemoveQuantityExceedingAmountRemovesIngredient() {
    // Arrange
    foodInventory.addIngredient(bread);

    // Act
    boolean result = foodInventory.removeQuantity("Bread", 1.5);

    // Assert
    assertTrue(result);
    Ingredient retrievedBread = foodInventory.findIngredientByName("Bread");
    assertNull(retrievedBread);
  }

  /**
   * Tests that removing a quantity of a non-existent ingredient returns false.
   */
  @Test
  void testRemoveQuantityNonExistentIngredient() {
    // Arrange
    // No ingredients added

    // Act
    boolean result = foodInventory.removeQuantity("Cheese", 1.0);

    // Assert
    assertFalse(result);
  }

  /**
   * Tests that removing a negative quantity throws an exception.
   */
  @Test
  void testRemoveQuantityNegativeThrowsException() {
    // Arrange
    foodInventory.addIngredient(milk);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      foodInventory.removeQuantity("Milk", -1.0);
    });
    assertEquals("Quantity to remove must be positive.", exception.getMessage());
  }

  /**
   * Tests that getting ingredients expiring before a certain date returns the correct ingredients.
   */
  @Test
  void testGetIngredientsExpiringBefore() {
    // Arrange
    foodInventory.addIngredient(milk);
    foodInventory.addIngredient(bread);
    foodInventory.addIngredient(eggs);

    LocalDate date = LocalDate.now().plusDays(6);

    // Act
    List<Ingredient> expiringIngredients = foodInventory.getIngredientsExpiringBefore(date);

    // Assert
    assertEquals(2, expiringIngredients.size());
    assertTrue(expiringIngredients.contains(milk));
    assertTrue(expiringIngredients.contains(bread));
    assertFalse(expiringIngredients.contains(eggs));
  }

  /**
   * Tests that getting ingredients expiring before with a null date throws an exception.
   */
  @Test
  void testGetIngredientsExpiringBeforeNullDateThrowsException() {
    // Arrange
    foodInventory.addIngredient(milk);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      foodInventory.getIngredientsExpiringBefore(null);
    });
    assertEquals("Date cannot be null.", exception.getMessage());
  }

  /**
   * Tests that getting all ingredients sorted by name returns a sorted list.
   */
  @Test
  void testGetAllIngredientsSortedByName() {
    // Arrange
    foodInventory.addIngredient(eggs);
    foodInventory.addIngredient(milk);
    foodInventory.addIngredient(bread);

    // Act
    List<Ingredient> sortedIngredients = foodInventory.getAllIngredientsSortedByName();

    // Assert
    assertEquals(3, sortedIngredients.size());
    assertEquals("Bread", sortedIngredients.get(0).getName());
    assertEquals("Eggs", sortedIngredients.get(1).getName());
    assertEquals("Milk", sortedIngredients.get(2).getName());
  }

  /**
   * Tests that adding ingredients with names differing only in case are treated the same.
   */
  @Test
  void testAddIngredientCaseInsensitive() {
    // Arrange
    Ingredient cheese = new Ingredient(
        "Cheese",
        1.0,
        "block",
        LocalDate.now().plusDays(15),
        50.0
    );

    Ingredient cheeseLowerCase = new Ingredient(
        "cheese",
        0.5,
        "block",
        LocalDate.now().plusDays(10),
        50.0
    );

    // Act
    foodInventory.addIngredient(cheese);
    foodInventory.addIngredient(cheeseLowerCase);

    // Assert
    Ingredient retrievedCheese = foodInventory.findIngredientByName("CHEESE");
    assertNotNull(retrievedCheese);
    assertEquals(1.5, retrievedCheese.getQuantity());
  }

  /**
   * Tests that removing quantity with a null name throws an exception.
   */
  @Test
  void testRemoveQuantityNullNameThrowsException() {
    // Arrange
    foodInventory.addIngredient(milk);

    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      foodInventory.removeQuantity(null, 1.0);
    });
    assertEquals("Name cannot be null or empty.", exception.getMessage());
  }
}
