package productlinetracker;

public interface Item {

  //Each method is implicitly public, so the access modifier is not needed.
  int getId();
  void setName(String name);
  String getName();
  void setManufacturer(String manufacturer);
  String getManufacturer();





}
