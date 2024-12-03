package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.ui.UserInterface;

/**
 * The entry point of the application.
 *
 * <p>
 * This class contains the {@code main} method which initializes and starts the user interface. It
 * serves as the starting point when the application is launched.
 * </p>
 */
public class Main {

  /**
   * The main method to run the application.
   *
   * @param args command-line arguments (currently unused)
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    try {
      ui.init();
      ui.start();
    } catch (Exception e) {
      System.err.println("An unexpected error occurred: " + e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }
}
