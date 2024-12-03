package edu.ntnu.idi.idatt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.model.Ingredient;
import edu.ntnu.idi.idatt.model.Unit;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link InventoryService} class.
 */
class InventoryServiceTest {

  private InventoryService inventoryService;

  @BeforeEach
  void setUp() {
    inventoryService = new InventoryService();
    inventoryService.populateSampleIngredients();
  }

  @DisplayName("Test adding a new ingredient to the inventory")
  @Test
  void testAddIngredient() {
    Ingredient newIngredient = new Ingredient("Butter", 500, Unit.GRAM,
        LocalDate.now().plusDays(20), 40.0);
    inventoryService.addIngredient(newIngredient);

    Ingredient retrievedIngredient = inventoryService.findIngredientByName("Butter");

    assertNotNull(retrievedIngredient);
    assertEquals(newIngredient, retrievedIngredient);
  }

  @DisplayName("Test adding an existing ingredient updates the quantity")
  @Test
  void testAddIngredientExisting() {
    Ingredient additionalMilk = new Ingredient("Milk", 1.0, Unit.LITER, LocalDate.now().plusDays(7),
        22.0);
    inventoryService.addIngredient(additionalMilk);

    Ingredient retrievedIngredient = inventoryService.findIngredientByName("Milk");

    assertNotNull(retrievedIngredient);
    assertEquals(3.0, retrievedIngredient.getQuantity());
  }

  @DisplayName("Test removing a valid quantity from an ingredient")
  @Test
  void testRemoveQuantitySuccess() {
    boolean result = inventoryService.removeQuantity("Eggs", 6, Unit.PIECE);
    assertTrue(result);

    Ingredient eggs = inventoryService.findIngredientByName("Eggs");

    assertNotNull(eggs);
    assertEquals(6, eggs.getQuantity());
  }

  @DisplayName("Test finding an ingredient that exists in the inventory")
  @Test
  void testFindIngredientByNameExists() {
    Ingredient flour = inventoryService.findIngredientByName("Flour");

    assertNotNull(flour);
    assertEquals("Flour", flour.getName());
  }

  @DisplayName("Test finding an ingredient that does not exist returns null")
  @Test
  void testFindIngredientByNameNotExists() {
    Ingredient spice = inventoryService.findIngredientByName("Spice");

    assertNull(spice);
  }

  @DisplayName("Test retrieving all ingredients sorted by name")
  @Test
  void testGetAllIngredientsSortedByName() {
    List<Ingredient> ingredients = inventoryService.getAllIngredientsSortedByName();

    assertEquals(6, ingredients.size());
    assertEquals("Bread", ingredients.get(0).getName());
    assertEquals("Sugar", ingredients.get(5).getName());
  }

  @DisplayName("Test getting ingredients expiring before a certain date")
  @Test
  void testGetIngredientsExpiringBefore() {
    LocalDate date = LocalDate.now().plusDays(10);
    List<Ingredient> expiringIngredients = inventoryService.getIngredientsExpiringBefore(date);

    assertFalse(expiringIngredients.isEmpty());
    for (Ingredient ingredient : expiringIngredients) {
      assertTrue(ingredient.getBestBeforeDate().isBefore(date));
    }
  }

  @DisplayName("Test getting ingredients when none expire before the given date")
  @Test
  void testGetIngredientsExpiringBeforeNone() {
    LocalDate date = LocalDate.now();
    List<Ingredient> expiringIngredients = inventoryService.getIngredientsExpiringBefore(date);

    assertTrue(expiringIngredients.isEmpty());
  }
}
