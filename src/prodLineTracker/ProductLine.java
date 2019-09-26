package prodLineTracker;

import javafx.beans.property.SimpleStringProperty;

public class ProductLine {
  private final SimpleStringProperty name = new SimpleStringProperty("");
  private final SimpleStringProperty type = new SimpleStringProperty("");
  private final SimpleStringProperty manufacturer = new SimpleStringProperty("");

  public ProductLine() {
    this("", "", "");
  }

  public ProductLine(String pName, String pType, String pManufacturer) {
    setName(pName);
    setType(pType);
    setManufacturer(pManufacturer);
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
