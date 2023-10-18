import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

class merchPopup {
    merchPopup(Order ord, dbConnectionHandler dab) throws SQLException {
        dbConnectionHandler db = dab;

        ArrayList<String> merch = new ArrayList<String>();
        ResultSet rs = db.requestData("SELECT * FROM menu where category = 'merchandise';");

        while (rs.next()) {
            merch.add(rs.getString("name"));
        }

        Stage popupStage = new Stage();

        // Creating right side of popup.
        GridPane popupGP = new GridPane();
        popupGP.setMinSize(600, 400);
        popupGP.setPadding(new Insets(4, 4, 4, 4));

        VBox drinkList = new VBox();
        drinkList.setSpacing(25);
        ToggleGroup itemToggleGroup = new ToggleGroup();
        for(String item : merch)
        {
            RadioButton itemButton = new RadioButton(item);
            itemButton.setToggleGroup(itemToggleGroup);
            drinkList.getChildren().add(itemButton);
        }
        popupGP.add(drinkList, 0, 0);

        Button doneButton = new Button("Done");
        popupGP.add(doneButton, 0, 1);

        Button closeButton = new Button("Close");
        popupGP.add(closeButton, 1, 1);

        Scene popupScene = new Scene(popupGP);
        popupStage.setTitle("Select Drink");
        popupStage.setScene(popupScene);

        popupStage.show();

        closeButton.setOnAction(event -> {
            popupStage.close();
        });

        doneButton.setOnAction(event -> {
            String name = ((RadioButton)itemToggleGroup.getSelectedToggle()).getText();

            double price = 0.0;

                    try {
                        //System.out.println("SELECT price FROM menu where name = '"+ name + "';");
                        ResultSet r2 = db.requestData("SELECT price FROM menu where name = '"+ name + "';");
                        r2.next();
                        price = r2.getDouble(1);
                        //System.out.println(price);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

            ord.addMerch(new Merch(name, price));
            popupStage.close();
        });
    }

};