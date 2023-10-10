import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


final class dbSetup  {
    public static final String user = "csce331_903_davidrodriguez24";
    public static final String pswd = "cEl240403";
}

class dbConnectionHandler {
     public final void requestData(String query) {
         Connection conn = null;
         String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_03r_db";

         //Connecting to the database
         try {
             conn = DriverManager.getConnection(dbConnectionString, dbSetup.user, dbSetup.pswd);
         } catch (Exception e) {
             e.printStackTrace();
             System.err.println(e.getClass().getName() + ": " + e.getMessage());
             System.exit(1);
         }

         Alert succesfulAlert = new Alert(Alert.AlertType.CONFIRMATION);
         succesfulAlert.setTitle("Successful connection.");
         succesfulAlert.setHeaderText("Success!");
         succesfulAlert.setContentText("Established connection to Database successfully.");
         succesfulAlert.show();

         try {
             conn.close();
             Alert closeAlert = new Alert(Alert.AlertType.CONFIRMATION);
             closeAlert.setTitle("Database Closed");
             closeAlert.setHeaderText("Success!");
             closeAlert.setContentText("Closed connection with database successfully.");
             closeAlert.show();
         } catch (Exception e) {
             Alert closeAlert = new Alert(Alert.AlertType.ERROR);
             closeAlert.setTitle("Database Not Closed");
             closeAlert.setHeaderText("Error!");
             closeAlert.setContentText("Could not close connection with database.");
             closeAlert.show();
             System.exit(1);
         }
     }
}

public class ManagerGUI {
    Stage primaryStage;
    ManagerGUI()
    {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Manager's GUI");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        GridPane primaryGP = new GridPane();

        // Tab seciton
        //VBox tabSection = new VBox();
        //tabSection.setSpacing(10);


        // Main section
        VBox mainSection = new VBox();
        mainSection.setSpacing(50);


        Scene primaryScene = new Scene(primaryGP);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        dbConnectionHandler handler = new dbConnectionHandler();
        handler.requestData("SELECT * FROM drink LIMIT 5;");
    }
}
