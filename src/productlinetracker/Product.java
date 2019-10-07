package productlinetracker;

abstract class Product implements Item {
  int id;
  String type;
  String manufacturer;
  String name;

  public Product(String name) {
    this.name = name;
  }

  public String toString() {
    return "Name: " + name + "\nManufacturer: " + manufacturer + "\nType: " + type;
  }

}
