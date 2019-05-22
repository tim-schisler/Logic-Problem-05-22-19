/*
    Tim Schisler
    22 May 2019
    Solution to logic problem for Charter application

    "A retailer offers a rewards program to its customers,
    awarding points based on each recorded purchase. 

    A customer receives 2 points for every dollar spent over $100
    in each transaction, plus 1 point for every dollar spent
    over $50 in each transaction 
    (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

    Given a record of every transaction during a three month period,
    calculate the reward points earned for each customer
    per month and total.

    · Make up a data set to best demonstrate your solution
    · Check solution into GitHub"

*/
package logicproblem0522;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class LogicProblem0522 extends Application {
    
    /**
     lpConnection method: makes connection to the database
     used in this program.
     */
    private static Connection lpConnection() {
        try {
            // load the driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // get a connection
            String dbURL = "jdbc:mysql://localhost:3306/rewarddb";
            String username = "root";
            String password = "sesame";
            return DriverManager.getConnection(
                    dbURL, username, password);
            
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the databse driver: ");
            System.out.println(e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error executing the SQL statement: ");
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    /**
     start method: sets up the GUI for this JavaFX application.
     */
    private TextField currentMonth = new TextField();
    private TextField lastMonth = new TextField();
    private TextField beforeMonth = new TextField();
    private TextField threeMonthTotal = new TextField();
    
    @Override
    public void start(Stage primaryStage) {
        
        //Create the basic layout of the GUI.
        Text instructions = new Text();
        ComboBox cb = new ComboBox();
        GridPane summaryPane = new GridPane();
        
        //Add text to the instructions field to direct the user.
        String instr = "To view a summary of rewards for a customer,\n"
                + "select a customer ID from the dropdown list.";
        instructions.setText(instr);
        
        //Query the db for a list of customer IDs; add them to cb.
        
        
        //Add the labels and fields to the summary pane.
        summaryPane.add(new Label("Rewards This Month:"), 0, 0);
        summaryPane.add(currentMonth, 1, 0);
        summaryPane.add(new Label("Rewards Last Month:"), 0, 1);
        summaryPane.add(lastMonth, 1, 1);
        summaryPane.add(new Label("Rewards the Month Before:"), 0, 2);
        summaryPane.add(beforeMonth, 1, 2);
        summaryPane.add(new Label("Three Month Total:"), 0, 3);
        summaryPane.add(threeMonthTotal, 1, 3);
        
        //Prepare the scene.
        VBox root = new VBox();
        root.getChildren().addAll(instructions, cb, summaryPane);
        Scene scene = new Scene(root, 300, 250);
        
        //Set up and show the application stage.
        primaryStage.setTitle("Rewards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     main method: launches the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}