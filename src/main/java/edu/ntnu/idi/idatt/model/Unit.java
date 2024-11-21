package edu.ntnu.idi.idatt.model;

/**
 * Represents units of measurement with associated properties.
 */
public enum Unit {

  // Volume Units
  LITER("L", UnitType.VOLUME, 1.0),
  DECILITER("dl", UnitType.VOLUME, 0.1),
  MILLILITER("ml", UnitType.VOLUME, 0.001),

  // Mass Units
  GRAM("g", UnitType.MASS, 1.0),
  KILOGRAM("kg", UnitType.MASS, 1000.0),
  MILLIGRAM("mg", UnitType.MASS, 0.001),

  // Count Units
  PIECE("pcs", UnitType.COUNT, 1.0);

  private final String abbreviation;
  private final UnitType unitType;
  private final double conversionFactorToBaseUnit;

  Unit(String abbreviation, UnitType unitType, double conversionFactorToBaseUnit) {
    this.abbreviation = abbreviation;
    this.unitType = unitType;
    this.conversionFactorToBaseUnit = conversionFactorToBaseUnit;
  }

  /**
   * Gets the abbreviation of the unit.
   *
   * @return the abbreviation
   */
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * Gets the type of the unit.
   *
   * @return the unit type
   */
  public UnitType getUnitType() {
    return unitType;
  }

  /**
   * Checks if this unit is compatible with another unit.
   *
   * @param otherUnit the unit to compare with; cannot be null
   * @return true if both units are of the same UnitType, false otherwise
   * @throws IllegalArgumentException if otherUnit is null
   */
  public boolean isCompatibleWith(Unit otherUnit) {
    if (otherUnit == null) {
      throw new IllegalArgumentException("Other unit cannot be null.");
    }
    return this.unitType == otherUnit.unitType;
  }

  /**
   * Converts a quantity from this unit to the base unit.
   *
   * @param quantity the quantity in this unit
   * @return the equivalent quantity in the base unit
   */
  public double toBaseUnit(double quantity) {
    return quantity * conversionFactorToBaseUnit;
  }

  /**
   * Converts a quantity from the base unit to this unit.
   *
   * @param baseQuantity the quantity in the base unit
   * @return the equivalent quantity in this unit
   */
  public double fromBaseUnit(double baseQuantity) {
    return baseQuantity / conversionFactorToBaseUnit;
  }

  /**
   * Converts a quantity from this unit to another compatible unit.
   *
   * @param targetUnit the unit to convert to; cannot be null
   * @param quantity   the quantity in this unit
   * @return the equivalent quantity in the target unit
   * @throws IllegalArgumentException if targetUnit is null or units are incompatible
   */
  public double convertTo(Unit targetUnit, double quantity) {
    if (targetUnit == null) {
      throw new IllegalArgumentException("Target unit cannot be null.");
    }
    if (!this.isCompatibleWith(targetUnit)) {
      throw new IllegalArgumentException("Units are incompatible for conversion.");
    }
    double baseQuantity = this.toBaseUnit(quantity);
    return targetUnit.fromBaseUnit(baseQuantity);
  }

  /**
   * Enum representing types of units.
   */
  public enum UnitType {
    MASS,
    VOLUME,
    COUNT
  }
}
