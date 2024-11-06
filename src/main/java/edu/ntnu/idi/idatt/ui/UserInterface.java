package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles user interactions and provides a text-based user interface for the application.
 * <p>
 * This class manages the input and output operations with the user, allowing them to
 * interact with the food inventory and cookbook system.
 * </p>
 */
public class UserInterface {

  private FoodInventory foodInventory;
  private Cookbook cookbook;

  /**
   * Initializes the user interface.
   * <p>
   * This method sets up any necessary data structures or variables before the
   * user interface starts interacting with the user.
   * </p>
   */
  public void init() {
    foodInventory = new FoodInventory();
    cookbook = new Cookbook();
    populateSampleIngredients();
    populateSampleRecipes();
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
      System.out.println("\n--- Food Inventory and Cookbook Manager ---");
      System.out.println("1. Add new ingredient");
      System.out.println("2. List all ingredients");
      System.out.println("3. Find ingredient by name");
      System.out.println("4. Remove quantity from ingredient");
      System.out.println("5. List ingredients expiring before a date");
      System.out.println("6. Add new recipe");
      System.out.println("7. List all recipes");
      System.out.println("8. Find recipe by name");
      System.out.println("9. Check if a recipe can be made");
      System.out.println("10. Get recipes that can be made with current inventory");
      System.out.println("11. Exit");
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
          addNewRecipe();
          break;
        case "7":
          listAllRecipes();
          break;
        case "8":
          findRecipeByName();
          break;
        case "9":
          checkIfRecipeCanBeMade();
          break;
        case "10":
          getRecipesCanBeMade();
          break;
        case "11":
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
    Ingredient milk = new Ingredient("Milk", 2.0, Unit.LITER, LocalDate.now().plusDays(5), 20.0);
    Ingredient bread = new Ingredient("Bread", 1.0, Unit.PIECE, LocalDate.now().plusDays(2), 25.0);
    Ingredient eggs = new Ingredient("Eggs", 12, Unit.PIECE, LocalDate.now().plusDays(10), 3.0);
    Ingredient cheese = new Ingredient("Cheese", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(15), 50.0);
    Ingredient flour = new Ingredient("Flour", 1.0, Unit.KILOGRAM, LocalDate.now().plusDays(30), 15.0);
    Ingredient sugar = new Ingredient("Sugar", 0.5, Unit.KILOGRAM, LocalDate.now().plusDays(365), 10.0);

    foodInventory.addIngredient(milk);
    foodInventory.addIngredient(bread);
    foodInventory.addIngredient(eggs);
    foodInventory.addIngredient(cheese);
    foodInventory.addIngredient(flour);
    foodInventory.addIngredient(sugar);
  }

  /**
   * Populates the cookbook with sample recipes.
   */
  private void populateSampleRecipes() {
    // Pancake recipe
    Recipe pancakeRecipe = new Recipe(
        "Pancakes",
        "Fluffy pancakes",
        "Mix ingredients and cook on a skillet.",
        4
    );
    pancakeRecipe.addIngredient("Flour", 200, Unit.GRAM);
    pancakeRecipe.addIngredient("Milk", 3, Unit.DECILITER);
    pancakeRecipe.addIngredient("Eggs", 2, Unit.PIECE);
    pancakeRecipe.addIngredient("Sugar", 50, Unit.GRAM);

    // Omelette recipe
    Recipe omeletteRecipe = new Recipe(
        "Omelette",
        "Simple omelette",
        "Beat eggs and cook on a pan.",
        2
    );
    omeletteRecipe.addIngredient("Eggs", 3, Unit.PIECE);
    omeletteRecipe.addIngredient("Cheese", 50, Unit.GRAM);
    omeletteRecipe.addIngredient("Milk", 0.5, Unit.DECILITER);

    try {
      cookbook.addRecipe(pancakeRecipe);
      cookbook.addRecipe(omeletteRecipe);
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding sample recipes: " + e.getMessage());
    }
  }

  /**
   * Adds a new ingredient to the food inventory based on user input.
   */
  private void addNewIngredient() {
    Ingredient ingredient = createIngredient();
    if (ingredient != null) {
      try {
        foodInventory.addIngredient(ingredient);
        System.out.println("Ingredient added to the inventory.");
      } catch (IllegalArgumentException e) {
        System.out.println("Error adding ingredient: " + e.getMessage());
      }
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

    // Read and validate unit
    Unit unit;
    while (true) {
      System.out.println("Select the unit of the quantity to remove:");
      System.out.println("1. Liter (L)");
      System.out.println("2. Deciliter (dl)");
      System.out.println("3. Kilogram (kg)");
      System.out.println("4. Gram (g)");
      System.out.println("5. Piece (pcs)");
      System.out.print("Enter your choice (1-5): ");
      String unitChoice = scanner.nextLine().trim();
      switch (unitChoice) {
        case "1":
          unit = Unit.LITER;
          break;
        case "2":
          unit = Unit.DECILITER;
          break;
        case "3":
          unit = Unit.KILOGRAM;
          break;
        case "4":
          unit = Unit.GRAM;
          break;
        case "5":
          unit = Unit.PIECE;
          break;
        default:
          System.out.println("Invalid choice. Please select a valid unit.");
          continue;
      }
      break;
    }

    // Read and validate quantity
    double quantity;
    while (true) {
      System.out.print("Enter the quantity to remove (" + unit.getAbbreviation() + "): ");
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

    try {
      boolean result = foodInventory.removeQuantity(name, quantity, unit);
      if (result) {
        System.out.println("Quantity removed successfully.");
      } else {
        System.out.println("Ingredient not found in the inventory.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error removing quantity: " + e.getMessage());
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
   * Adds a new recipe to the cookbook based on user input.
   */
  private void addNewRecipe() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter recipe name: ");
    String name = scanner.nextLine().trim();
    if (name.isEmpty()) {
      System.out.println("Name cannot be empty.");
      return;
    }

    System.out.print("Enter a short description: ");
    String description = scanner.nextLine().trim();
    if (description.isEmpty()) {
      System.out.println("Description cannot be empty.");
      return;
    }

    System.out.print("Enter the preparation method: ");
    String preparationMethod = scanner.nextLine().trim();
    if (preparationMethod.isEmpty()) {
      System.out.println("Preparation method cannot be empty.");
      return;
    }

    System.out.print("Enter the number of servings: ");
    String servingsInput = scanner.nextLine().trim();
    int servings;
    try {
      servings = Integer.parseInt(servingsInput);
      if (servings <= 0) {
        System.out.println("Servings must be positive.");
        return;
      }
    } catch (NumberFormatException e) {
      System.out.println("Invalid number format.");
      return;
    }

    Recipe recipe;
    try {
      recipe = new Recipe(name, description, preparationMethod, servings);
    } catch (IllegalArgumentException e) {
      System.out.println("Error creating recipe: " + e.getMessage());
      return;
    }

    // Add ingredients
    System.out.println("Add ingredients to the recipe.");
    boolean addingIngredients = true;
    while (addingIngredients) {
      System.out.print("Enter ingredient name (or 'done' to finish): ");
      String ingredientName = scanner.nextLine().trim();
      if (ingredientName.equalsIgnoreCase("done")) {
        addingIngredients = false;
        continue;
      }
      if (ingredientName.isEmpty()) {
        System.out.println("Ingredient name cannot be empty.");
        continue;
      }

      System.out.print("Enter quantity required: ");
      String quantityInput = scanner.nextLine().trim();
      double quantity;
      try {
        quantity = Double.parseDouble(quantityInput);
        if (quantity <= 0) {
          System.out.println("Quantity must be positive.");
          continue;
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid number format.");
        continue;
      }

      Unit unit;
      while (true) {
        System.out.println("Select unit of measurement:");
        System.out.println("1. Liter (L)");
        System.out.println("2. Deciliter (dl)");
        System.out.println("3. Kilogram (kg)");
        System.out.println("4. Gram (g)");
        System.out.println("5. Piece (pcs)");
        System.out.print("Enter your choice (1-5): ");
        String unitChoice = scanner.nextLine().trim();
        switch (unitChoice) {
          case "1":
            unit = Unit.LITER;
            break;
          case "2":
            unit = Unit.DECILITER;
            break;
          case "3":
            unit = Unit.KILOGRAM;
            break;
          case "4":
            unit = Unit.GRAM;
            break;
          case "5":
            unit = Unit.PIECE;
            break;
          default:
            System.out.println("Invalid choice. Please select a valid unit.");
            continue;
        }
        break;
      }

      try {
        recipe.addIngredient(ingredientName, quantity, unit);
      } catch (IllegalArgumentException e) {
        System.out.println("Error adding ingredient: " + e.getMessage());
      }
    }

    try {
      cookbook.addRecipe(recipe);
      System.out.println("Recipe added to the cookbook.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding recipe: " + e.getMessage());
    }
  }

  /**
   * Lists all recipes in the cookbook.
   */
  private void listAllRecipes() {
    List<Recipe> recipes = cookbook.getAllRecipes();
    if (recipes.isEmpty()) {
      System.out.println("No recipes in the cookbook.");
    } else {
      System.out.println("\n--- List of Recipes ---");
      for (Recipe recipe : recipes) {
        System.out.println("- " + recipe.getName() + ": " + recipe.getDescription());
      }
    }
  }

  /**
   * Finds a recipe by name based on user input.
   */
  private void findRecipeByName() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name of the recipe to find: ");
    String name = scanner.nextLine().trim();

    if (name.isEmpty()) {
      System.out.println("Name cannot be empty.");
      return;
    }

    Recipe recipe = cookbook.findRecipeByName(name);
    if (recipe != null) {
      System.out.println("\nRecipe found:");
      System.out.println("Name: " + recipe.getName());
      System.out.println("Description: " + recipe.getDescription());
      System.out.println("Preparation Method: " + recipe.getPreparationMethod());
      System.out.println("Servings: " + recipe.getServings());
      System.out.println("Ingredients:");
      for (Map.Entry<String, IngredientRequirement> entry : recipe.getIngredients().entrySet()) {
        IngredientRequirement req = entry.getValue();
        System.out.println("- " + entry.getKey() + ": " + req.getQuantity() + " " + req.getUnit().getAbbreviation());
      }
    } else {
      System.out.println("Recipe not found in the cookbook.");
    }
  }

  /**
   * Checks if a recipe can be made with the current inventory.
   */
  private void checkIfRecipeCanBeMade() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name of the recipe to check: ");
    String name = scanner.nextLine().trim();

    if (name.isEmpty()) {
      System.out.println("Name cannot be empty.");
      return;
    }

    Recipe recipe = cookbook.findRecipeByName(name);
    if (recipe != null) {
      boolean canBeMade = recipe.canBeMadeFromInventory(foodInventory);
      if (canBeMade) {
        System.out.println("You have all the necessary ingredients to make \"" + recipe.getName() + "\".");
      } else {
        System.out.println("You do not have all the necessary ingredients to make \"" + recipe.getName() + "\".");
      }
    } else {
      System.out.println("Recipe not found in the cookbook.");
    }
  }

  /**
   * Gets and displays recipes that can be made with the current inventory.
   */
  private void getRecipesCanBeMade() {
    List<Recipe> availableRecipes = cookbook.getRecipesCanBeMade(foodInventory);
    if (availableRecipes.isEmpty()) {
      System.out.println("No recipes can be made with the current inventory.");
    } else {
      System.out.println("\n--- Recipes that can be made ---");
      for (Recipe recipe : availableRecipes) {
        System.out.println("- " + recipe.getName());
      }
    }
  }

  /**
   * Creates an {@code Ingredient} based on user input.
   *
   * @return the created {@code Ingredient}, or {@code null} if creation failed
   */
  public Ingredient createIngredient() {
    Scanner scanner = new Scanner(System.in);
    String name;
    double quantity;
    Unit unit;
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
      System.out.println("Select unit of measurement:");
      System.out.println("1. Liter (L)");
      System.out.println("2. Deciliter (dl)");
      System.out.println("3. Kilogram (kg)");
      System.out.println("4. Gram (g)");
      System.out.println("5. Piece (pcs)");
      System.out.print("Enter your choice (1-5): ");
      String unitChoice = scanner.nextLine().trim();
      switch (unitChoice) {
        case "1":
          unit = Unit.LITER;
          break;
        case "2":
          unit = Unit.DECILITER;
          break;
        case "3":
          unit = Unit.KILOGRAM;
          break;
        case "4":
          unit = Unit.GRAM;
          break;
        case "5":
          unit = Unit.PIECE;
          break;
        default:
          System.out.println("Invalid choice. Please select a valid unit.");
          continue;
      }
      break;
    }

    while (true) {
      System.out.print("Enter quantity (" + unit.getAbbreviation() + "): ");
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
}