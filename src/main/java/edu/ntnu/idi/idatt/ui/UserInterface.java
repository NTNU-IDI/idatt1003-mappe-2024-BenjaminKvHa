package edu.ntnu.idi.idatt.ui;

import edu.ntnu.idi.idatt.model.Ingredient;
import java.time.LocalDate;

public class UserInterface {

  public void init() {
  }

  public void start() {
    Ingredient milk = new Ingredient("Milk", 1.5, "liter",
        LocalDate.of(2024, 10, 21), 20.0);

    Ingredient eggs = new Ingredient("Eggs", 12, "pieces",
        LocalDate.of(2024, 11, 22), 2.5);

    System.out.println(milk);
    System.out.println(eggs);
  }
}
