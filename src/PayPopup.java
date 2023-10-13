import java.util.ArrayList;

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

    PayPopup(dbConnectionHandler handler, Order o){
        db = handler;
        System.out.print("FIRST OR");
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
                    for(int i = 0; i < drinks.size(); i++) {
                        System.out.print("SECOND OR");
                        db.executeUpdate(String.format("INSERT INTO drink (drinkid, orderid, name, category, size, temp, ice_level, sugar_level, price) VALUES ('%d', '%d', '%s', '%s', '%s', '%s', '%s', '%d', '%.2f');",
                        drinks.get(i).getDrinkID(), ord.getOrderID(), drinks.get(i).getName(), drinks.get(i).getCategory(), drinks.get(i).getSize(), drinks.get(i).getTemp(), drinks.get(i).getIce_level(), drinks.get(i).getSugar_level(), drinks.get(i).getPrice()));
                    }
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
