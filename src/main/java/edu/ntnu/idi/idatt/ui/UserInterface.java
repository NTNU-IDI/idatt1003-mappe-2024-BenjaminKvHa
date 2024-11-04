package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.model.FoodInventory;
import edu.ntnu.idi.idatt.model.Ingredient;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions and provides a text-based user interface for the application.
 * <p>
 * This class manages the input and output operations with the user, allowing them to interact with
 * the food inventory system.
 * </p>
 */
public class UserInterface {

  private FoodInventory foodInventory;

  /**
   * Initializes the user interface.
   * <p>
   * This method sets up any necessary data structures or variables before the user interface starts
   * interacting with the user.
   * </p>
   */
  public void init() {
    foodInventory = new FoodInventory();
    populateSampleIngredients();
  }

  /**
   * Starts the user interface and handles the main menu loop.
   * <p>
   * This method displays the menu options to the user, processes their input, and calls the
   * appropriate methods based on the user's choices.
   * </p>
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    while (running) {
      System.out.println("\n--- Food Inventory Manager ---");
      System.out.println("1. Add new ingredient");
      System.out.println("2. List all ingredients");
      System.out.println("3. Find ingredient by name");
      System.out.println("4. Remove quantity from ingredient");
      System.out.println("5. List ingredients expiring before a date");
      System.out.println("6. Exit");
      System.out.print("Choose an option: ");

      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          addNewIngredient();
          break;
        case "2":
          listAllIngredients();
          break;
        case "3":
          findIngredientByName();
          break;
        case "4":
          removeQuantityFromIngredient();
          break;
        case "5":
          listIngredientsExpiringBeforeDate();
          break;
        case "6":
          running = false;
          System.out.println("Exiting the application. Goodbye!");
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  /**
   * Populates the food inventory with sample ingredients.
   */
  private void populateSampleIngredients() {
    Ingredient milk = new Ingredient("Milk", 2.0, "liter", LocalDate.now().plusDays(5), 20.0);
    Ingredient bread = new Ingredient("Bread", 1.0, "loaf", LocalDate.now().plusDays(2), 25.0);
    Ingredient eggs = new Ingredient("Eggs", 12, "pieces", LocalDate.now().plusDays(10), 3.0);
    Ingredient cheese = new Ingredient("Cheese", 0.5, "kg", LocalDate.now().plusDays(15), 50.0);

    foodInventory.addIngredient(milk);
    foodInventory.addIngredient(bread);
    foodInventory.addIngredient(eggs);
    foodInventory.addIngredient(cheese);
  }

  /**
   * Adds a new ingredient to the food inventory based on user input.
   */
  private void addNewIngredient() {
    Ingredient ingredient = createIngredient();
    if (ingredient != null) {
      foodInventory.addIngredient(ingredient);
      System.out.println("Ingredient added to the inventory.");
    }
  }

  /**
   * Lists all ingredients in the food inventory.
   */
  private void listAllIngredients() {
    List<Ingredient> ingredients = foodInventory.getAllIngredientsSortedByName();
    if (ingredients.isEmpty()) {
      System.out.println("No ingredients in the inventory.");
    } else {
      System.out.println("\n--- List of Ingredients ---");
      for (Ingredient ingredient : ingredients) {
        System.out.println(ingredient);
      }
    }
  }

  /**
   * Finds an ingredient by name based on user input.
   */
  private void findIngredientByName() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name of the ingredient to find: ");
    String name = scanner.nextLine().trim();

    if (name.isEmpty()) {
      System.out.println("Name cannot be empty.");
      return;
    }

    Ingredient ingredient = foodInventory.findIngredientByName(name);
    if (ingredient != null) {
      System.out.println("Ingredient found:");
      System.out.println(ingredient);
    } else {
      System.out.println("Ingredient not found in the inventory.");
    }
  }

  /**
   * Removes a quantity from an ingredient based on user input.
   */
  private void removeQuantityFromIngredient() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name of the ingredient: ");
    String name = scanner.nextLine().trim();

    if (name.isEmpty()) {
      System.out.println("Name cannot be empty.");
      return;
    }

    System.out.print("Enter the quantity to remove: ");
    String quantityInput = scanner.nextLine().trim();
    double quantity;

    try {
      quantity = Double.parseDouble(quantityInput);
      if (quantity <= 0) {
        System.out.println("Quantity must be positive.");
        return;
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid quantity format.");
      return;
    }

    boolean result = foodInventory.removeQuantity(name, quantity);
    if (result) {
      System.out.println("Quantity removed successfully.");
    } else {
      System.out.println("Ingredient not found in the inventory.");
    }
  }

  /**
   * Lists ingredients that expire before a certain date based on user input.
   */
  private void listIngredientsExpiringBeforeDate() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter a date (YYYY-MM-DD): ");
    String dateInput = scanner.nextLine().trim();

    LocalDate date;
    try {
      date = LocalDate.parse(dateInput);
    } catch (DateTimeParseException e) {
      System.out.println("Invalid date format.");
      return;
    }

    List<Ingredient> expiringIngredients = foodInventory.getIngredientsExpiringBefore(date);
    if (expiringIngredients.isEmpty()) {
      System.out.println("No ingredients expiring before " + date + ".");
    } else {
      System.out.println("\n--- Ingredients expiring before " + date + " ---");
      for (Ingredient ingredient : expiringIngredients) {
        System.out.println(ingredient);
      }
    }
  }

  /**
   * Creates an {@code Ingredient} based on user input.
   * <p>
   * This method prompts the user for the ingredient's name, quantity, unit, best-before date, and
   * price per unit. It validates the input and constructs an {@code Ingredient} object if all
   * inputs are valid.
   * </p>
   *
   * @return the created {@code Ingredient}, or {@code null} if creation failed
   */
  public Ingredient createIngredient() {
    Scanner scanner = new Scanner(System.in);
    String name;
    double quantity;
    String unit;
    LocalDate bestBeforeDate;
    double pricePerUnit;

    // Read and validate name
    while (true) {
      System.out.print("Enter ingredient name: ");
      name = scanner.nextLine().trim();
      if (!name.isEmpty()) {
        break;
      }
      System.out.println("Name cannot be empty. Please try again.");
    }

    // Read and validate quantity
    while (true) {
      System.out.print("Enter quantity: ");
      String quantityInput = scanner.nextLine().trim();
      try {
        quantity = Double.parseDouble(quantityInput);
        if (quantity > 0) {
          break;
        } else {
          System.out.println("Quantity must be a positive number.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid number format. Please enter a valid number.");
      }
    }

    // Read and validate unit
    while (true) {
      System.out.print("Enter unit of measurement: ");
      unit = scanner.nextLine().trim();
      if (!unit.isEmpty()) {
        break;
      }
      System.out.println("Unit cannot be empty. Please try again.");
    }

    // Read and validate best-before date
    while (true) {
      System.out.print("Enter best-before date (YYYY-MM-DD): ");
      String dateInput = scanner.nextLine().trim();
      try {
        bestBeforeDate = LocalDate.parse(dateInput);
        if (!bestBeforeDate.isBefore(LocalDate.now())) {
          break;
        } else {
          System.out.println("Best-before date cannot be in the past.");
        }
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
      }
    }

    // Read and validate price per unit
    while (true) {
      System.out.print("Enter price per unit (NOK): ");
      String priceInput = scanner.nextLine().trim();
      try {
        pricePerUnit = Double.parseDouble(priceInput);
        if (pricePerUnit > 0) {
          break;
        } else {
          System.out.println("Price per unit must be a positive number.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid number format. Please enter a valid number.");
      }
    }

    try {
      Ingredient ingredient = new Ingredient(name, quantity, unit, bestBeforeDate, pricePerUnit);
      System.out.println("Ingredient created successfully!");
      return ingredient;
    } catch (IllegalArgumentException e) {
      System.out.println("Error creating ingredient: " + e.getMessage());
    }
    return null;
  }
}
