package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.model.FoodInventory;
import edu.ntnu.idi.idatt.model.Ingredient;
import edu.ntnu.idi.idatt.model.Unit;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class that provides operations related to the food inventory.
 *
 * <p>
 * The {@code InventoryService} class acts as an intermediary between the user interface and the
 * underlying {@link FoodInventory} model. It encapsulates all business logic related to managing
 * ingredients in the inventory, such as adding ingredients, removing quantities, finding ingredients,
 * and retrieving ingredients based on expiration dates.
 * </p>
 *
 * <p>
 * This class ensures that the {@link FoodInventory} is manipulated in a controlled manner, enforcing
 * validation rules and providing a clean API for higher layers of the application.
 * </p>
 */
public class InventoryService {

  private final FoodInventory foodInventory;

  public InventoryService() {
    this.foodInventory = new FoodInventory();
  }

  /**
   * Adds an ingredient to the inventory.
   *
   * @param ingredient the ingredient to add
   * @throws IllegalArgumentException if the ingredient is invalid or cannot be added
   */
  public void addIngredient(Ingredient ingredient) {
    foodInventory.addIngredient(ingredient);
  }

  /**
   * Removes a quantity of an ingredient from the inventory.
   *
   * @param name     the name of the ingredient
   * @param quantity the quantity to remove
   * @param unit     the unit of the quantity
   * @return true if the quantity was removed, false if the ingredient was not found
   * @throws IllegalArgumentException if parameters are invalid
   */
  public boolean removeQuantity(String name, double quantity, Unit unit) {
    return foodInventory.removeQuantity(name, quantity, unit);
  }

  /**
   * Finds an ingredient by name.
   *
   * @param name the name of the ingredient
   * @return the ingredient if found, or null if not found
   */
  public Ingredient findIngredientByName(String name) {
    return foodInventory.findIngredientByName(name);
  }

  /**
   * Gets all ingredients sorted by name.
   *
   * @return a list of ingredients
   */
  public List<Ingredient> getAllIngredientsSortedByName() {
    return foodInventory.getAllIngredientsSortedByName();
  }

  /**
   * Gets ingredients that expire before the specified date.
   *
   * @param date the date to compare
   * @return a list of ingredients expiring before the specified date
   */
  public List<Ingredient> getIngredientsExpiringBefore(LocalDate date) {
    return foodInventory.getIngredientsExpiringBefore(date);
  }

  /**
   * Gets the underlying FoodInventory.
   *
   * @return the FoodInventory instance
   */
  public FoodInventory getFoodInventory() {
    return foodInventory;
  }

  /**
   * Populates the inventory with sample ingredients.
   */
  public void populateSampleIngredients() {
    Ingredient milk = new Ingredient("Milk", 2.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
    Ingredient bread = new Ingredient("Bread", 1.0, Unit.PIECE, LocalDate.now().plusDays(2), 25.0);
    Ingredient eggs = new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0);
    Ingredient cheese = new Ingredient("Cheese", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(15),
        50.0);
    Ingredient flour = new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30),
        15.0);
    Ingredient sugar = new Ingredient("Sugar", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(365),
        10.0);

    addIngredient(milk);
    addIngredient(bread);
    addIngredient(eggs);
    addIngredient(cheese);
    addIngredient(flour);
    addIngredient(sugar);
  }
}
