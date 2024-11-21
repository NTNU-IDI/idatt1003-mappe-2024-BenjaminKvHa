package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.model.Ingredient;
import edu.ntnu.idi.idatt.model.IngredientRequirement;
import edu.ntnu.idi.idatt.model.Recipe;
import edu.ntnu.idi.idatt.model.Unit;
import edu.ntnu.idi.idatt.service.InventoryService;
import edu.ntnu.idi.idatt.service.RecipeService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles user interactions and provides a text-based user interface for the application.
 * <p>
 * This class manages the input and output operations with the user, allowing them to interact with
 * the food inventory and cookbook system.
 * </p>
 */
public class UserInterface {

  private InventoryService inventoryService;
  private RecipeService recipeService;
  private Scanner scanner;

  // Menu option constants
  private static final String OPTION_ADD_INGREDIENT = "1";
  private static final String OPTION_LIST_INGREDIENTS = "2";
  private static final String OPTION_FIND_INGREDIENT = "3";
  private static final String OPTION_REMOVE_INGREDIENT_QUANTITY = "4";
  private static final String OPTION_LIST_INGREDIENTS_EXPIRING = "5";
  private static final String OPTION_ADD_RECIPE = "6";
  private static final String OPTION_LIST_RECIPES = "7";
  private static final String OPTION_FIND_RECIPE = "8";
  private static final String OPTION_CHECK_RECIPE_CAN_BE_MADE = "9";
  private static final String OPTION_GET_RECIPES_CAN_BE_MADE = "10";
  private static final String OPTION_EXIT = "11";

  /**
   * Initializes the user interface.
   * <p>
   * This method sets up any necessary data structures or variables before the user interface starts
   * interacting with the user.
   * </p>
   */
  public void init() {
    inventoryService = new InventoryService();
    recipeService = new RecipeService();
    scanner = new Scanner(System.in);
    inventoryService.populateSampleIngredients();
    recipeService.populateSampleRecipes();
  }

  /**
   * Starts the user interface and handles the main menu loop.
   * <p>
   * This method displays the menu options to the user, processes their input, and calls the
   * appropriate methods based on the user's choices.
   * </p>
   */
  public void start() {
    boolean running = true;

    while (running) {
      displayMainMenu();
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case OPTION_ADD_INGREDIENT:
          addNewIngredient();
          break;
        case OPTION_LIST_INGREDIENTS:
          listAllIngredients();
          break;
        case OPTION_FIND_INGREDIENT:
          findIngredientByName();
          break;
        case OPTION_REMOVE_INGREDIENT_QUANTITY:
          removeQuantityFromIngredient();
          break;
        case OPTION_LIST_INGREDIENTS_EXPIRING:
          listIngredientsExpiringBeforeDate();
          break;
        case OPTION_ADD_RECIPE:
          addNewRecipe();
          break;
        case OPTION_LIST_RECIPES:
          listAllRecipes();
          break;
        case OPTION_FIND_RECIPE:
          findRecipeByName();
          break;
        case OPTION_CHECK_RECIPE_CAN_BE_MADE:
          checkIfRecipeCanBeMade();
          break;
        case OPTION_GET_RECIPES_CAN_BE_MADE:
          getRecipesCanBeMade();
          break;
        case OPTION_EXIT:
          running = confirmExit();
          break;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  /**
   * Displays the main menu options.
   */
  private void displayMainMenu() {
    System.out.println("\n--- Food Inventory and Cookbook Manager ---");
    System.out.println(OPTION_ADD_INGREDIENT + ". Add new ingredient");
    System.out.println(OPTION_LIST_INGREDIENTS + ". List all ingredients");
    System.out.println(OPTION_FIND_INGREDIENT + ". Find ingredient by name");
    System.out.println(OPTION_REMOVE_INGREDIENT_QUANTITY + ". Remove quantity from ingredient");
    System.out.println(
        OPTION_LIST_INGREDIENTS_EXPIRING + ". List ingredients expiring before a date");
    System.out.println(OPTION_ADD_RECIPE + ". Add new recipe");
    System.out.println(OPTION_LIST_RECIPES + ". List all recipes");
    System.out.println(OPTION_FIND_RECIPE + ". Find recipe by name");
    System.out.println(OPTION_CHECK_RECIPE_CAN_BE_MADE + ". Check if a recipe can be made");
    System.out.println(
        OPTION_GET_RECIPES_CAN_BE_MADE + ". Get recipes that can be made with current inventory");
    System.out.println(OPTION_EXIT + ". Exit");
    System.out.print("Choose an option: ");
  }

  /**
   * Confirms exit with the user.
   *
   * @return false if the user confirms exit, true otherwise
   */
  private boolean confirmExit() {
    System.out.print("Are you sure you want to exit? (Y/N): ");
    String response = scanner.nextLine().trim();
    if (response.equalsIgnoreCase("Y")) {
      System.out.println("Exiting the application. Goodbye!");
      return false;
    } else {
      System.out.println("Returning to the main menu.");
      return true;
    }
  }

  /**
   * Adds a new ingredient to the food inventory based on user input.
   */
  private void addNewIngredient() {
    Ingredient ingredient = createIngredient();
    if (ingredient != null) {
      try {
        inventoryService.addIngredient(ingredient);
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
    List<Ingredient> ingredients = inventoryService.getAllIngredientsSortedByName();
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
    String name = readNonEmptyString("Enter the name of the ingredient to find: ");
    Ingredient ingredient = inventoryService.findIngredientByName(name);
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
    String name = readNonEmptyString("Enter the name of the ingredient: ");
    Unit unit = selectUnit();
    double quantity = readPositiveDouble(
        "Enter the quantity to remove (" + unit.getAbbreviation() + "): ");

    try {
      boolean result = inventoryService.removeQuantity(name, quantity, unit);
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
    LocalDate date = readDate("Enter a date (YYYY-MM-DD): ");

    List<Ingredient> expiringIngredients = inventoryService.getIngredientsExpiringBefore(date);
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
    String name = readNonEmptyString("Enter recipe name: ");
    String description = readNonEmptyString("Enter a short description: ");
    String preparationMethod = readNonEmptyString("Enter the preparation method: ");
    int servings = readPositiveInt("Enter the number of servings: ");

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
      String ingredientName = readString("Enter ingredient name (or 'done' to finish): ");
      if (ingredientName.equalsIgnoreCase("done")) {
        addingIngredients = false;
        continue;
      }
      if (ingredientName.isEmpty()) {
        System.out.println("Ingredient name cannot be empty.");
        continue;
      }

      double quantity = readPositiveDouble("Enter quantity required: ");
      Unit unit = selectUnit();

      try {
        recipe.addIngredient(ingredientName, quantity, unit);
        System.out.println("Ingredient added to the recipe.");
      } catch (IllegalArgumentException e) {
        System.out.println("Error adding ingredient: " + e.getMessage());
      }
    }

    try {
      recipeService.addRecipe(recipe);
      System.out.println("Recipe added to the cookbook.");
    } catch (IllegalArgumentException e) {
      System.out.println("Error adding recipe: " + e.getMessage());
    }
  }

  /**
   * Lists all recipes in the cookbook.
   */
  private void listAllRecipes() {
    List<Recipe> recipes = recipeService.getAllRecipes();
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
    String name = readNonEmptyString("Enter the name of the recipe to find: ");
    Recipe recipe = recipeService.findRecipeByName(name);
    if (recipe != null) {
      displayRecipeDetails(recipe);
    } else {
      System.out.println("Recipe not found in the cookbook.");
    }
  }

  /**
   * Checks if a recipe can be made with the current inventory.
   */
  private void checkIfRecipeCanBeMade() {
    String name = readNonEmptyString("Enter the name of the recipe to check: ");
    Recipe recipe = recipeService.findRecipeByName(name);
    if (recipe != null) {
      boolean canBeMade = recipeService.canRecipeBeMade(recipe,
          inventoryService.getFoodInventory());
      if (canBeMade) {
        System.out.println(
            "You have all the necessary ingredients to make \"" + recipe.getName() + "\".");
      } else {
        System.out.println(
            "You do not have all the necessary ingredients to make \"" + recipe.getName() + "\".");
      }
    } else {
      System.out.println("Recipe not found in the cookbook.");
    }
  }

  /**
   * Gets and displays recipes that can be made with the current inventory.
   */
  private void getRecipesCanBeMade() {
    List<Recipe> availableRecipes = recipeService.getRecipesCanBeMade(
        inventoryService.getFoodInventory());
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
  private Ingredient createIngredient() {
    String name = readNonEmptyString("Enter ingredient name: ");
    Unit unit = selectUnit();
    double quantity = readPositiveDouble("Enter quantity (" + unit.getAbbreviation() + "): ");
    LocalDate bestBeforeDate = readFutureDate("Enter best-before date (YYYY-MM-DD): ");
    double pricePerUnit = readPositiveDouble("Enter price per unit (NOK): ");

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
   * Reads and validates a non-empty string input from the user.
   *
   * @param prompt the prompt to display
   * @return the non-empty string input
   */
  private String readNonEmptyString(String prompt) {
    String input;
    while (true) {
      System.out.print(prompt);
      input = scanner.nextLine().trim();
      if (!input.isEmpty()) {
        break;
      }
      System.out.println("Input cannot be empty. Please try again.");
    }
    return input;
  }

  /**
   * Reads a string input from the user.
   *
   * @param prompt the prompt to display
   * @return the string input
   */
  private String readString(String prompt) {
    System.out.print(prompt);
    return scanner.nextLine().trim();
  }

  /**
   * Reads and validates a positive double from the user.
   *
   * @param prompt the prompt to display
   * @return the positive double value
   */
  private double readPositiveDouble(String prompt) {
    double value;
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        value = Double.parseDouble(input);
        if (value > 0) {
          break;
        } else {
          System.out.println("Value must be positive.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid number format. Please enter a valid number.");
      }
    }
    return value;
  }

  /**
   * Reads and validates a positive integer from the user.
   *
   * @param prompt the prompt to display
   * @return the positive integer value
   */
  private int readPositiveInt(String prompt) {
    int value;
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      try {
        value = Integer.parseInt(input);
        if (value > 0) {
          break;
        } else {
          System.out.println("Value must be a positive integer.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid number format. Please enter a valid integer.");
      }
    }
    return value;
  }

  /**
   * Reads and validates a date from the user.
   *
   * @param prompt the prompt to display
   * @return the LocalDate value
   */
  private LocalDate readDate(String prompt) {
    LocalDate date;
    while (true) {
      System.out.print(prompt);
      String dateInput = scanner.nextLine().trim();
      try {
        date = LocalDate.parse(dateInput);
        break;
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
      }
    }
    return date;
  }

  /**
   * Reads and validates a future date from the user.
   *
   * @param prompt the prompt to display
   * @return the LocalDate value in the future
   */
  private LocalDate readFutureDate(String prompt) {
    LocalDate date;
    while (true) {
      date = readDate(prompt);
      if (!date.isBefore(LocalDate.now())) {
        break;
      } else {
        System.out.println("Date cannot be in the past. Please enter a future date.");
      }
    }
    return date;
  }

  /**
   * Allows the user to select a unit from available options.
   *
   * @return the selected Unit
   */
  private Unit selectUnit() {
    Unit[] units = Unit.values();
    while (true) {
      System.out.println("Select unit of measurement:");
      for (int i = 0; i < units.length; i++) {
        System.out.println(
            (i + 1) + ". " + units[i].name() + " (" + units[i].getAbbreviation() + ")");
      }
      System.out.print("Enter your choice (1-" + units.length + "): ");
      String unitChoice = scanner.nextLine().trim();
      try {
        int choice = Integer.parseInt(unitChoice);
        if (choice >= 1 && choice <= units.length) {
          return units[choice - 1];
        } else {
          System.out.println("Invalid choice. Please select a valid unit.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
  }

  /**
   * Displays the details of a recipe.
   *
   * @param recipe the recipe to display
   */
  private void displayRecipeDetails(Recipe recipe) {
    System.out.println("\nRecipe found:");
    System.out.println("Name: " + recipe.getName());
    System.out.println("Description: " + recipe.getDescription());
    System.out.println("Preparation Method: " + recipe.getPreparationMethod());
    System.out.println("Servings: " + recipe.getServings());
    System.out.println("Ingredients:");
    for (Map.Entry<String, IngredientRequirement> entry : recipe.getIngredients().entrySet()) {
      IngredientRequirement req = entry.getValue();
      System.out.println(
          "- " + entry.getKey() + ": " + req.getQuantity() + " " + req.getUnit().getAbbreviation());
    }
  }
}
