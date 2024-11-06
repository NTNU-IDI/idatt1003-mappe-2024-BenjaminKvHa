package edu.ntnu.idi.idatt.model;

/**
 * Represents the unit of measurement for ingredients.
 */
public enum Unit {
  LITER("L"),
  DECILITER("dl"),
  KILOGRAM("kg"),
  GRAM("g"),
  PIECE("pcs");

  private final String abbreviation;

  Unit(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  /**
   * Returns the abbreviation of the unit.
   *
   * @return the unit abbreviation
   */
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * Parses a string to a Unit.
   *
   * @param input the input string
   * @return the corresponding Unit
   * @throws IllegalArgumentException if the input does not match any unit
   */
  public static Unit fromString(String input) {
    if (input == null || input.trim().isEmpty()) {
      throw new IllegalArgumentException("Unit cannot be null or empty.");
    }
    String normalizedInput = input.trim().toLowerCase();
    switch (normalizedInput) {
      case "l":
      case "liter":
        return LITER;
      case "dl":
      case "deciliter":
        return DECILITER;
      case "kg":
      case "kilogram":
        return KILOGRAM;
      case "g":
      case "gram":
        return GRAM;
      case "pcs":
      case "piece":
      case "pieces":
        return PIECE;
      default:
        throw new IllegalArgumentException("Invalid unit: " + input);
    }
  }
}
