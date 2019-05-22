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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;


public class LogicProblem0522 extends Application {
    /**
     The following textfields exist to display information about rewards earned.
     They will be used in the start method and updated by an event handler.
     */
    private TextField currentMonth = new TextField();
    private TextField lastMonth = new TextField();
    private TextField beforeMonth = new TextField();
    private TextField threeMonthTotal = new TextField();
    
    /**
     lpConnection method: makes connection to the database
     used in this program.
     */
    private static Connection lpConnection() {
        try {
            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // get a connection
            String dbURL = "jdbc:mysql://localhost:3306/rewarddb"
                    + "?serverTimezone=UTC";
            String username = "audience";
            String password = "sesame";
            return DriverManager.getConnection(dbURL, username, password);
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
     getCustomerIDs method: queries the database and returns each
     distinct customer ID number.
     */
    private static ArrayList<Integer> getCustomerIDs() {
        ArrayList<Integer> ids = new ArrayList<>();
        String query = "SELECT DISTINCT customer_id FROM transactions";
        Connection conn = lpConnection();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while( rs.next() ) {    //loop through each record in the DB
                ids.add(rs.getInt("customer_id"));
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return ids;
    }
    
    /**
     calculateReward method: returns the number of reward points earned
     for a given transaction total.
     */
    private static int calculateReward(int total) {
        if (total <= 50) {
            return 0;
        } else if (total <= 100) {
            return total - 50;
        } else {
            int dblPts = total - 100;
            return 50 + 2 * dblPts;
        }
    }
    
    /**
     setRewardFields method: updates the rewards text fields to reflect
     a given customer ID number.
     */
    private void setRewardFields(int ID) {
        String query = "SELECT * FROM transactions "
                + "WHERE customer_id = " + ID;
        Connection conn = lpConnection();
        int rw1 = 0,    //these variables accumulate rewards for each month
            rw2 = 0,
            rw3 = 0;
        int rwTotal = 0;
        int mo = -1;
        Calendar now = Calendar.getInstance();  //the current date+time
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while( rs.next() ) {    //loop through each record in the DB.
                mo = (  //test how many months ago the transaction was.
                        now.get(Calendar.MONTH)
                        - rs.getDate("trans_date").getMonth()
                        );
                //accumulate reward points for the appropriate month.
                switch(mo) {
                    case 0:     //current month
                        rw1 += calculateReward(rs.getInt("total"));
                        break;
                    case 1:     //last month
                    case -11:
                        rw2 += calculateReward(rs.getInt("total"));
                        break;
                    case 2:     //month before last
                    case -10:
                        rw3 += calculateReward(rs.getInt("total"));
                        break;
                    default:
                        break;
                }   
            }
            //compute the 3-month total.
            rwTotal = rw1 + rw2 + rw3;
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        //set the text fields with the computed rewards totals.
        currentMonth.setText(Integer.toString(rw1));
        lastMonth.setText(Integer.toString(rw2));
        beforeMonth.setText(Integer.toString(rw3));
        threeMonthTotal.setText(Integer.toString(rwTotal));
    }
    
    /**
     start method: sets up the GUI for this JavaFX application.
     */
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
        cb.getItems().addAll(getCustomerIDs());
        
        //Connect event handler to update summary pane when an ID is chosen.
        cb.setOnAction(e -> {
            setRewardFields((Integer)cb.getValue());
        });
        
        //Add the labels and fields to the summary pane, and format.
        summaryPane.setHgap(5.0);
        summaryPane.setVgap(5.0);
        summaryPane.add(new Label("Rewards This Month:"), 0, 0);
        summaryPane.add(currentMonth, 1, 0);
        summaryPane.add(new Label("Rewards Last Month:"), 0, 1);
        summaryPane.add(lastMonth, 1, 1);
        summaryPane.add(new Label("Rewards the Month Before:"), 0, 2);
        summaryPane.add(beforeMonth, 1, 2);
        summaryPane.add(new Label("Three Month Total:"), 0, 3);
        summaryPane.add(threeMonthTotal, 1, 3);
        currentMonth.setEditable(false);
        lastMonth.setEditable(false);
        beforeMonth.setEditable(false);
        threeMonthTotal.setEditable(false);
        
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