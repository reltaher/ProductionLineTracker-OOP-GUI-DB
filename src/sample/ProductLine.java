package sample;

import javafx.beans.property.SimpleStringProperty;

public class ProductLine {
  private final SimpleStringProperty name;
  private final SimpleStringProperty type;
  private final SimpleStringProperty manufacturer;

  private ProductLine(String pName, String pType, String pManufacturer) {
    this.name = new SimpleStringProperty(pName);
    this.type = new SimpleStringProperty(pType);
    this.manufacturer = new SimpleStringProperty(pManufacturer);
  }

  public String getName() {
    return name.get();
  }

  public void setName(String pName) {
    name.set(pName);
  }

  public String getType() {
    return type.get();
  }

  public void setType(String pType) {
    type.set(pType);
  }

  public String getManufacturer() {
    return manufacturer.get();
  }

  public void setManufacturer(String pManufacturer) {
    manufacturer.set(pManufacturer);
  }


}
