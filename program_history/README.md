# Program History

### Sprint 1 (GUI, Basic Database)

#### Week 1 (Aug 19 - Aug 24):

-Started the project on IntelliJ as a JavaFX Project

-Created a private repository on GitHub through IntelliJ and shared it

-Created a README on the GitHub Repository

#### Week 2 (Aug 25 - Aug 31):

-Created the GUI structure of the program on SceneBuilder. The structure includes a tab view with three tabs.

-Named each tab "Product Line", "Produce", and "Production Log".

-Created a CSS file with some code to change the look on the program.

#### Week 3 (Sept 1 - Sept 7):

-The entire GUI structure of the program was assembled with different features, each tab having its own AnchorPane. Each TextAreas, ComboBox, and ChoiceBox were given fx:ids which creates fields for them in the Controller Class which are used to obtain information from these containers. Each Button made within SceneBuilder is also given an "On Action" id which creates methods for them in the Controller class, which then allows the button to execute a block of code. The following was added to each tab:

#### Product Line:

-2x3 GridPane added to the AnchorPane

-Label (row 0, column 0), and TextField (row 0, column 1) for Product Name added to GridPane.

-Label (row 1, column 0), and TextField (row 1, column 1) for Manufacturer added to GridPane.

-Label (row 2, column 0), and ChoiceBox (row 2, column 1) for Item Type added to GridPane. Item Type was also populated with four choices: Audio, AudioMobile, Visual, and VisualMobile.

-Button with the text "Add Product" added to the AnchorPane. When clicked, the button prints to the console. At week 5, the button was changes so when clicked, it would access the database, executes a statement, and prints to console afterwards.

-Label with the text "Existing Products" and a TableView with three choices (Product Name, Manufacturer, Item Type) added to the AnchorPane.

#### Produce:

-Label with the text "Choose Product:" and a ListView below the label added to the AnchorPane.

-Below the ListView, a Label with the text "Choose Quantity:" and a ComboBox with a String datatype were added to the AnchorPane.

-A button with the text "Record Production" was added to the AnchorPane. For now, this button just prints the selected value from the ComboBox to the console.

#### Production Log:

-A TextArea has been added to the AnchorPane.


#### Week 4 (Sept 15 - Sept 21):

-H2 DataBase was created within IntelliJ. A res folder was made to store database information.

-Within the Database, two tables were made: Product, and ProductionRecord. Both tables have different "fields" with different types of information that will be used later on. A unique index with the name "Product_id_uindex" was also made.

-Code to connect to the database was created in the Main Class within the method "initializeDB".

#### Week 5 (Sept 22 - Sept 28):

-Within SceneBuilder, a skeleton Controller class is automatically made. I simply copy pasted that class into my own Controller class to use. 

-For the "Add Product" button in the Product Line tab, I added code to the button event handler so that it was able to access the database and insert a product into it. The Button is accessing the "intializeDB" method in the main class, which then executes the following SQL query: "INSERT INTO Product(type, manufacturer, name) VALUES ( 'AUDIO', 'Apple', 'iPod' );"

-For the ComboBox in the produce tab, I made a method in the Controller class classed "initialize". Within this method, I populated the ComboBox with values "1-10" which are read as a String. I called the method "setEditable(true)" so that users are able to enter their own values into the combo box. I also called the "getSelectionModel().selectFirst()" method to show a default value, which in this case would be 1.

-Within the same "initialize" method, I populated the Item Type Choice Box in the "Product Line" tab with the string values "Audio", "AudioMobile", "Visual", and "VisualMobile". I also called the method where it shows a default value for the choice box, in which the value here would be "Audio".

-After everything was complete for this sprint, I made sure that the Quality, Style, and Documentation expectations met the satisfactory requirements before submission. I also added a Sprint 1 Diagram to the Repository, and I added information about the project into the README.


### Sprint 2 

#### Week 6 (Sept. 29 - Oct. 5):

###### Product

-I created an enum called ItemType which stored four constants: AUDIO, VISUAL, AUDIOMOBILE, and VISUALMOBILE. Each of these enums has its code, which is stored as a String. AUDIO is AU, VISUAL is VI, AUDIOMOBILE is AM, and VISUALMOBILE is VM. The ChoiceBox in the Product Line tab has been populated with the enum constants, with the use of the implicit values method.

-I created an interface called Item that forces all classes to implement the following functions:

*A method called getId that which returns an int

*A method called setName which would have one String parameter

*A method called getName which returns a String

*A method called setManufacturer which would have one String parameter

*A method called getManufacturer which returns a String

-I created an abstract class called Product that implements the Item interface. The purpose of the Product class is that it will implement the basic functionality that all items on a production line should have. Within the Product class, it contains fields for the Item's id, name, manufacturer, and type. It also contains the methods from the Item interface, since it agreed on having those methods due to the class implementing the interface. The Product class also contains a constructor that takes in the name, manufacturer, and type of the product, and each variable is set to its field variables. Finally, the class contains a toString method that returns an item's name, manufacturer, and type in the form of a String.

-Since the Product class is abstract, I cannot make objects out of this class. To fix this problem, I created a Widget class that extends Product. I use this Widget class to store a created Widget object to the database and the productLine collection.


#### Week 7 (Oct. 6 - Oct. 12):

###### MultimediaControl

-I created an interface called MultimediaControl, and the purpose of this interface is because all of the items on this production line will have basic media controls. Since this is the case, the interface will contain four methods: play, stop, previous, and next. None of these methods return anything.

###### AudioPlayer

-I created an AudioPlayer class which will capture the details of an audio player. This class extends Product and implements the MultimediaControl interface. Within this class, it contains two fields: A String named supportedAudioFormats, and a String named supportedPlaylistFormats. Within this class, I created a constructor that takes in 4 parameters: name, manufacturer, supportedAudioFormats, and supportedPlaylistFormats. What this constructor does is that it calls its parent's constructor and sets the media type to AUDIO. 

-The methods from the MultimediaControl interface are also implemented, and within these methods, the actions are written to the console (Ex. play() methods prints "Playing" to the console). In an actual media player system, the code would instruct the media player to play, but for this program it will simply display a message.

-The final method that the AudioPlayer class contains is a toString method that displays the superclass's toString method, while also adding rows for supportedAudioFormats and supportedPlaylistFormats.

#### Week 8 (Oct. 13 - Oct. 19):

-I created an enum for MonitorType which will be used for creating portable movie players. This enum stores two types: LCD, and LED.

###### ScreenSpec

-I created an interface called ScreenSpec that defines three methods:

*A method called getResolution which returns a String

*A method called getRefreshRate which returns a String

*A method called getResponseTime which returns a String

###### Screen

-I created a class named Screen that implements ScreenSpec. This class contains fields for resolution, refreshrate, and responsetime, the methods from the ScreenSpec interface, and a toString method that returns the details of the 3 fields in the same format as the Product Class.

###### MoviePlayer

-I created a class named MoviePlayer that extends Product and implements MultimediaControl. This class contains fields for screen and monitorType, and the difference between these fields and other fields is that other fields were assigned either String or an appropriate primitive data type to them, while these fields are assigned of type Screen and of type MonitorType.

-This class also contains a constructor that accepts nae, manufacturer, a screen object, and a monitor type object. The constructor also sets the item type to VISUAL.

-Since this class implements MultimediaControl, it will complete the methods from that interface in a similar fashion to AudioPlayer.

-The final method that this class contains is a toString method which calls the Product's toString method and displays the monitor and the screen details.

###### Demonstration

-To demonstrate the functionality from these classes and interface, I created a method in my Controller named "testMultimedia", and this method creates objects for AudioPlayer, Screen, and MoviePlayer, stores the AudioPayer and MoviePlayer object in an ArrayList of type MultimediaControl, loops through that array with a for each loop, and prints the object's information and the methods from the MultimediaControl interface to the console.

#### Week 9 (Oct. 20 - Oct. 26):

###### ProductionRecord

-I created a ProductionRecord class with int fields for productionNumber and productID, a String field for serialNumber, and a field for the dateProduced of type java.util.Date.

-Within this class, 

--I created accessors and mutators for all fields. 

--I created a constructor that has a parameter for just the productID, which sets this productionNumber to 0 (since the database ends up auto-incrementing), the serialNumber to 0, anad the date to the current date (using new Date()).

--I created an overloaded constructor that is used when creating ProductionRecord objects from the database. This constructor contains parameters for all fields.

--I created a toString method which returns a string in the format "Prod. Num: 0 Product ID: 0 Serial Num: 0 Date: (current date)"

-I added functionality in my Controller class which allows the Production Record to display in the TextArea of the Production Log tab.

#### Week 10 (Oct. 27 - Nov. 2):

-The serial numbers that are displayed in the Production Log tab are currently set to 0, so the goal for this week is to produce a unique serial number for each product produced.

-In the ProductionRecord class, I added a constructor which accepts a Product object and an int which will be used to count the number of items of its type that have been created. Within this constructor, I set the serialNumber to start with the first three letters of the Manufacturer name, then the two letter ItemType code, then five digits (with leading 0s) that are unique and sequential for the item type. 

#### Week 11 (Nov. 3 - Nov. 9):

-I updated the production record text area information to show the product name instead of the product ID.

-The objective for this week was to show all Products in the Product Line TableView and ListView, to set the items of the TableView to the ObservableList, and to show the production log in the ProductionLog tab TextArea. However, I have already accomplished each of those tasks in the previous weeks.
