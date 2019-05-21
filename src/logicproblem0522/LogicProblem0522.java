/*

*/
package logicproblem0522;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent e) -> {
            System.out.println("Hello World!");
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
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
