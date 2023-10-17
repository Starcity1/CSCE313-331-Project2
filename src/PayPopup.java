import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class PayPopup extends GUI {

    dbConnectionHandler db;
    Order ord;
    private double tip = 0.0;

    PayPopup(dbConnectionHandler handler, Order o){
        db = handler;
        ord = o;
    }

    public EventHandler<MouseEvent> payHandle = new EventHandler<>() {
        @Override
        public void handle(MouseEvent mouseEvent)
        {
            // Getting payment information.
            mouseEvent.getSource();

            Stage payStage = new Stage();
            payStage.setMinHeight(600);
            payStage.setMinWidth(800);
            GridPane payGP = new GridPane();
            payGP.setAlignment(Pos.CENTER);
            payGP.setVgap(50);
            payGP.setPadding(new Insets(25, 25, 25, 25));

            VBox headerSection = new VBox();
            headerSection.setAlignment(Pos.CENTER);
            headerSection.setSpacing(20);
            Label thanksLabel = new Label("Thank you for your purchase!");
            Label totalLabel = new Label(String.format("Total: $%.2f", totalCost));
            thanksLabel.setStyle("-fx-font-family: Arial;-fx-font-size: 24;");
            totalLabel.setStyle("-fx-font-family: Arial;-fx-font-size: 18;");
            headerSection.getChildren().addAll(thanksLabel, totalLabel);
            payGP.add(headerSection, 1, 0);


            VBox tipSection = new VBox();
            tipSection.setAlignment(Pos.TOP_CENTER);
            tipSection.setSpacing(30);
            Label tipLabel = new Label("Would you like to leave a tip?");
            RadioButton noTip = new RadioButton("No tip :(");
            RadioButton oneDollarTip = new RadioButton("$1.00");
            RadioButton twoDollarTip = new RadioButton("$2.00");
            tipSection.getChildren().addAll(tipLabel, noTip, oneDollarTip, twoDollarTip);
            payGP.add(tipSection, 0, 1);

            // Create a ToggleGroup to group the radio buttons
            ToggleGroup tipToggleGroup = new ToggleGroup();
            noTip.setToggleGroup(tipToggleGroup);
            oneDollarTip.setToggleGroup(tipToggleGroup);
            twoDollarTip.setToggleGroup(tipToggleGroup);
    
            // Add a change listener to the ToggleGroup
            tipToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == noTip) {
                    tip = 0.0;
                } else if (newValue == oneDollarTip) {
                    tip = 1.0;
                } else if (newValue == twoDollarTip) {
                    tip = 2.0;
                }
            });

            VBox receiptSection = new VBox();
            receiptSection.setAlignment(Pos.TOP_CENTER);
            receiptSection.setSpacing(30);
            Label receiptLabel = new Label("Would you like your receipt?");
            RadioButton noReceipt = new RadioButton("No thank you");
            RadioButton printReceipt = new RadioButton("Print");
            RadioButton emailReceipt = new RadioButton("Email");
            receiptSection.getChildren().addAll(receiptLabel, noReceipt, printReceipt, emailReceipt);
            payGP.add(receiptSection, 2, 1);

            VBox paySection = new VBox(new Button("Pay"));
            paySection.setAlignment(Pos.CENTER);
            ((Button) paySection.getChildren().get(0)).setStyle("-fx-font-size: 24;");
            ((Button) paySection.getChildren().get(0)).addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent1) {
                    ArrayList<Drink> drinks = ord.getDrinks();
                    int empID = 0001;
                    
                    LocalDate currentDate = LocalDate.now();
                    LocalTime currentTime = LocalTime.now();

                    int tpID = db.requestInt("select MAX(toppingid) from topping;") + 1;

                    for(int i = 0; i < drinks.size(); i++) {
                        db.executeUpdate(String.format("INSERT INTO drink (drinkid, orderid, name, category, size, temp, ice_level, sugar_level, price) VALUES ('%d', '%d', '%s', '%s', '%s', '%s', '%s', '%d', '%.2f');",
                        drinks.get(i).getDrinkID(), ord.getOrderID(), drinks.get(i).getName(), drinks.get(i).getCategory(), drinks.get(i).getSize(), drinks.get(i).getTemp(), drinks.get(i).getIce_level(), drinks.get(i).getSugar_level(), drinks.get(i).getPrice()));
                        ArrayList<String> tpp = drinks.get(i).getToppings();
                        for(int j = 0; j < tpp.size(); j++){
                            try {
                                ResultSet r2 = db.requestData("SELECT price FROM menu where name = '"+ tpp.get(j) + "';");
                                r2.next();
                                db.executeUpdate(String.format("INSERT INTO topping (toppingid, drinkid, name, quantity, price) VALUES ('%s', '%s', '%s', '%d', '%.2f');",
                                tpID, drinks.get(i).getDrinkID(), tpp.get(j), 1, r2.getDouble(1)));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            tpID++;
                        }
                    }
                    String insertQuery = String.format(
                        "INSERT INTO order_log (orderID, empID, date, time, total, tip) VALUES (%d, %d, '%s', '%s', %f, %f);",
                        ord.orderID, empID, currentDate, currentTime, ord.calcPrice(), tip
                    );
                    db.executeUpdate(insertQuery);

                    payStage.close();
                }
            });
            payGP.add(paySection, 1, 2);

            Scene payScene = new Scene(payGP);
            payStage.setScene(payScene);
            payStage.show();
        }
    };
}
