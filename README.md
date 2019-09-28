# ProductionLineTracker-OOP-GUI-DB

The Production Line Tracker is a program that will manage tracking inventory of what products are produced. This becomes essential for production workers because otherwise they will have to be physically producing items and write them down in a production log book. This program was made indiviually by Ramzy El-Taher, who has previously made different versions of the Production Line Tracker program in C++ and in Java. This project was started at August 19th as a semester long assignment for my Object Oriented Programming class at FGCU, and at this point of time, I have a decent amount of experience with Java, but no experience with JavaFX or databases. The project is currently on-going, with the plan to finish by December. The reason why I am making this is not only because it is an assignment, but it is also because working on projects like these are something that I am very passionate about and is enjoyable for me to work on daily.

## Demonstration


## Documentation


## Diagrams

![Sprint 1 Class Diagram](diagrams/Class_Diagram_Sprint1.pdf)


## Getting Started


## Built With


## Contributing


## Author

Ramzy El-Taher

## License


## Acknowledgments


## History

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

-The entire GUI structure of the program was assembled with different features, each tab having its own AnchorPane. The following was added to each tab:

#### Product Line:

-2x3 GridPane added to the AnchorPane

-Label (row 0, column 0), and TextField (row 0, column 1) for Product Name added to GridPane.

-Label (row 1, column 0), and TextField (row 1, column 1) for Manufacturer added to GridPane.

-Label (row 2, column 0), and ChoiceBox (row 2, column 1) for Item Type added to GridPane. Item Type was also populated with four choices: Audio, AudioMobile, Visual, and VisualMobile.

-Button with the text "Add Product" added to the AnchorPane. When clicked, the button prints to the console. At week 5, the button was changes so when clicked, it would access the database, executes a statement, and prints to console afterwards.

-Label with the text "Existing Products" and a TableView with three choices (Product Name, Manufacturer, Item Type) added to the AnchorPane.

#### Produce:

-Label with the text "Choose Product:" and a ListView below the label added to the AnchorPane.

-Below the ListView, a Label with the text "Choose Quantity:" and an editable ComboBox with a String datatype and values 1-10 populated in it were added to the AnchorPane.

-A button with the text "Record Production" was added to the AnchorPane. For now, this button just prints the selected value from the ComboBox to the console.

#### Production Log:

-A TextArea has been added to the AnchorPane.


#### Week 4 (Sept 15 - Sept 21):

-H2 DataBase was created within IntelliJ. A res folder was made to store database information.

-Within the Database, two tables were made: Product, and ProductionRecord. Both tables have different "fields" with different types of information that will be used later on. A unique index with the name "Product_id_uindex" was also made.

-Code to connect to the database was created in the Main Class within the method "initializeDB".

#### Week 5 (Sept 22 - Sept 28):



## Key Programming Concepts Utilized
