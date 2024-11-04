package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.ui.UserInterface;

/**
 * The entry point of the application.
 * <p>
 * This class contains the {@code main} method which starts the application by initializing and
 * starting the user interface.
 * </p>
 */
public class Main {

  /**
   * The main method to run the application.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
