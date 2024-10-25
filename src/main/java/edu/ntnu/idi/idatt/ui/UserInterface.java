package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.model.Ingredient;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * UserInterface class.
 */
public class UserInterface {

  private List<Ingredient> ingredientList;

  /**
   * Initializes the user interface.
   */
  public void init() {
    ingredientList = new ArrayList<>();
  }

  /**
   * Starts the user interface.
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
   * Creates an Ingredient based on user input.
   */
  public Ingredient createIngredient() {
    Scanner scanner = new Scanner(System.in);
    String name;
    double quantity;
    String unit;
    LocalDate bestBeforeDate;
    double pricePerUnit;

    while (true) {
      System.out.print("Enter ingredient name: ");
      name = scanner.nextLine().trim();
      if (!name.isEmpty()) {
        break;
      }
      System.out.println("Name cannot be empty. Please try again.");
    }

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

    while (true) {
      System.out.print("Enter unit of measurement: ");
      unit = scanner.nextLine().trim();
      if (!unit.isEmpty()) {
        break;
      }
      System.out.println("Unit cannot be empty. Please try again.");
    }

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
