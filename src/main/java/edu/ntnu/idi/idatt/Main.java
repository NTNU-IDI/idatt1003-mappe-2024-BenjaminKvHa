package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.ui.UserInterface;

/**
 * Main class.
 */
public class Main {
  /**
   * The main method to run the application.
   */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}

