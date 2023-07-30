Performance Assessment:Software II - Advanced Java Concepts (QAM2)
The purpose of the application is to provide a GUI-based appointment scheduling solution as a desktop application.

Author: Jin Bok Lee
Contact: jlee785@wgu.edu
Application Version: 1.0
Date: July 30, 2023


IDE : IntelliJ IDEA 2023.1.3 Community Edition 
Build #IC-231.9161.38
Runtime version: 17.0.7+10-b829.16 x86_64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
JavaFX: javafx-sdk-17.0.1
mySQL connector: 8.0.25


How to Run the Program:
Find the Main class located in project/src/main/java/c195/project/Main.java
The program starts with a login screen. It will ask for username and password credentials.

Once successfully logged in, user is presented with the main appointments screen with a table view and different menu items.
The top menu items focus on manipulating current data to provide different type of reports:
Contacts report, month/type report, and all/monthly/weekly report.

The Add / Modify / Delete appointments give the user the ability to perform CRUD actions to the appointments data.

The customer menu gives user the ability to add new customers in a new screen.
In the new screen, the user can modify or delete selected customer from the table view.

Lastly, to return to the login screen, press the exit button on the bottom right of the page



Additional Report:
An additional report is generated in the bottom of the screen that returns the total number of appointments scheduled.
The report is auto-generated and updates on any addition of new appointments and deletion of old appointments.