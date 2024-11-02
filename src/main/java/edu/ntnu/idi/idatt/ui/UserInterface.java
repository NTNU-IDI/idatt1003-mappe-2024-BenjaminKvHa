package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.model.Ingredient;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user interactions and provides a text-based user interface for the application.
 * <p>
 * This class manages the input and output operations with the user, allowing them to
 * create ingredients, list all ingredients, and perform other actions as defined.
 * </p>
 */
public class UserInterface {

  private List<Ingredient> ingredientList;

  /**
   * Initializes the user interface.
   * <p>
   * This method sets up any necessary data structures or variables before the
   * user interface starts interacting with the user.
   * </p>
   */
  public void init() {
    ingredientList = new ArrayList<>();
  }

  /**
   * Starts the user interface and handles the main menu loop.
   * <p>
   * This method displays the menu options to the user, processes their input,
   * and calls the appropriate methods based on the user's choices.
   * </p>
   */
  public void start() {
    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    while (running) {
      System.out.println("\n--- Ingredient Manager ---");
      System.out.println("1. Create new ingredient");
      System.out.println("2. List all ingredients");
      System.out.println("3. Exit");
      System.out.print("Choose an option: ");

      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          Ingredient ingredient = createIngredient();
          if (ingredient != null) {
            ingredientList.add(ingredient);
            System.out.println("Ingredient added to the list.");
          }
          break;
        case "2":
          listAllIngredients();
          break;
        case "3":
          running = false;
          System.out.println("Exiting the application. Goodbye!");
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  /**
   * Creates an {@code Ingredient} based on user input.
   * <p>
   * This method prompts the user for the ingredient's name, quantity, unit,
   * best-before date, and price per unit. It validates the input and constructs
   * an {@code Ingredient} object if all inputs are valid.
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

  /**
   * Lists all ingredients stored in the ingredient list.
   * <p>
   * This method displays all the ingredients that have been added to the list.
   * If the list is empty, it informs the user accordingly.
   * </p>
   */
  public void listAllIngredients() {
    if (ingredientList.isEmpty()) {
      System.out.println("No ingredients have been added yet.");
    } else {
      System.out.println("\n--- List of Ingredients ---");
      for (int i = 0; i < ingredientList.size(); i++) {
        Ingredient ingredient = ingredientList.get(i);
        System.out.printf("%d. %s\n", i + 1, ingredient);
      }
    }
  }
}
