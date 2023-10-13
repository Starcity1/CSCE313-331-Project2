import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;

import javafx.event.EventHandler;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;


public class GUI extends Application{
    protected Stage primaryStage;
    protected Button classic;
    protected Button milk_tea;
    protected Button punch;
    protected Button milk_cap;
    protected Button yogurt;
    protected Button slush;
    protected Button milk_strike;
    protected Button espresso;
    protected Button seasonal;
    protected Button limited;
    protected Boolean showBorders = true;
    protected Integer menu_rows = 4;
    protected Integer menu_cols = 3;

    protected Integer order_cols = 3;

    protected static float totalCost;

    Order o = new Order();

    ObjectMapper mapper;
    Map<String, Map<String, List<Double>>> drinkMap;
    Map<String,Double> toppingsMap;

    public EventHandler<MouseEvent> onClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            String drinkType = ((Button) mouseEvent.getSource()).getText();
            
            // Array later becomes all other products in popups.

            drinkPopup new_popup = new drinkPopup(drinkType, drinkMap, toppingsMap ,o);

        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {

        mapper = new ObjectMapper();
        drinkMap = mapper.readValue(new File("data\\drinks.json"), Map.class);
        toppingsMap = mapper.readValue(new File("data\\toppings.json"), Map.class);


this.primaryStage = primaryStage;
        primaryStage.setTitle("315 Project");
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(650);

        classic = new Button();
        classic.setText("Classic");
        classic.setStyle("-fx-font:18px Tahoma;");
        classic.setPadding(new Insets(30, 30, 30, 30));
        classic.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        milk_tea = new Button();
        milk_tea.setText("Milk Tea");
        milk_tea.setStyle("-fx-font:18px Tahoma;");
        milk_tea.setPadding(new Insets(30, 30, 30, 30));
        milk_tea.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        punch = new Button();
        punch.setText("Punch");
        punch.setStyle("-fx-font:18px Tahoma;");
        punch.setPadding(new Insets(30, 30, 30, 30));
        punch.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        milk_cap = new Button();
        milk_cap.setText("Milk Cap");
        milk_cap.setStyle("-fx-font:18px Tahoma;");
        milk_cap.setPadding(new Insets(30, 30, 30, 30));
        milk_cap.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        yogurt = new Button();
        yogurt.setText("Yogurt");
        yogurt.setStyle("-fx-font:18px Tahoma;");
        yogurt.setPadding(new Insets(30, 30, 30, 30));
        yogurt.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        slush = new Button();
        slush.setText("Slush");
        slush.setStyle("-fx-font:18px Tahoma;");
        slush.setPadding(new Insets(30, 30, 30, 30));
        slush.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        milk_strike = new Button();
        milk_strike.setText("Milk Strike");
        milk_strike.setStyle("-fx-font:18px Tahoma;");
        milk_strike.setPadding(new Insets(30, 30, 30, 30));
        milk_strike.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        espresso = new Button();
        espresso.setText("Espresso");
        espresso.setStyle("-fx-font:18px Tahoma;");
        espresso.setPadding(new Insets(30, 30, 30, 30));
        espresso.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        seasonal = new Button();
        seasonal.setText("Seasonal");
        seasonal.setStyle("-fx-font:18px Tahoma;");
        seasonal.setPadding(new Insets(30, 30, 30, 30));
        seasonal.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        limited = new Button();
        limited.setText("What's New");
        limited.setStyle("-fx-font:18px Tahoma;");
        limited.setPadding(new Insets(30, 30, 30, 30));
        limited.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        // To Manager's GUI!!!
        Button managerGUI = new Button("ManagerGUI:");
        managerGUI.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ManagerGUI managerGUI = new ManagerGUI();
            }
        });

        GridPane layout = new GridPane();

        GridPane menu = new GridPane();
        HBox.setHgrow(menu, Priority.SOMETIMES);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(25, 25, 25, 25));

        GridPane.setConstraints(managerGUI, 1, 0);
        GridPane.setConstraints(classic, 0, 1);
        GridPane.setConstraints(milk_tea, 1, 1);
        GridPane.setConstraints(punch, 2, 1);
        GridPane.setConstraints(milk_cap, 0, 2);
        GridPane.setConstraints(yogurt, 1, 2);
        GridPane.setConstraints(slush, 2, 2);
        GridPane.setConstraints(milk_strike, 0, 3);
        GridPane.setConstraints(espresso, 1, 3);
        GridPane.setConstraints(seasonal, 2, 3);
        GridPane.setConstraints(limited, 1, 4);

        for (int i = 0; i < menu_cols; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/menu_cols);
            menu.getColumnConstraints().add(column);
        }

        for (int i = 0; i < menu_rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/menu_rows);
            menu.getRowConstraints().add(row);
        }


        menu.getChildren().addAll(managerGUI, classic, milk_tea, punch, milk_cap, yogurt, slush, milk_strike, espresso, seasonal, limited);

        VBox orderArea = new VBox();
        HBox.setHgrow(orderArea, Priority.SOMETIMES);

        Label orderLabel = new Label("Order");

        GridPane orderGridPane = new GridPane();
        VBox.setVgrow(orderGridPane, Priority.ALWAYS);

        Label itemLabel = new Label("Item");
        Label quantityLabel = new Label("Quantity");


        Label totalItemLabel = new Label("Total: ");

        GridPane.setConstraints(itemLabel, 0, 0);
        GridPane.setConstraints(quantityLabel, 1, 0);
        GridPane.setConstraints(totalItemLabel, 2, 0);

        orderGridPane.getChildren().addAll(itemLabel, quantityLabel, totalItemLabel);

        Label totalLabel = new Label(String.format("Total:\t%.2f$", 0.0));
        totalCost = 694.20F;
        
        Timeline updateTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
            ArrayList<Drink> d = o.getDrinks();
            if(d != null){
                for(int i = 0; i < d.size(); i++){
                    Label drinkLabel = new Label(d.get(i).getName());
                    Label drinkQuantityLabel = new Label("1"); // Replace this with the actual quantity
                    Label priceLabel = new Label(Double.toString(d.get(i).getPrice()));
            
                    // Adjust the row index to start from 1, not -1
                    GridPane.setConstraints(drinkLabel, 0, i + 1);
                    GridPane.setConstraints(drinkQuantityLabel, 1, i + 1);
                    GridPane.setConstraints(priceLabel, 2, i + 1);
                    orderGridPane.getChildren().addAll(drinkLabel, drinkQuantityLabel, priceLabel);
                }
                totalCost = (float)o.calcPrice();
                totalLabel.setText(String.format("Total:\t%.2f$", totalCost));
            }
            })
        );

        for (int i = 0; i < order_cols; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/order_cols);
            orderGridPane.getColumnConstraints().add(column);
        }

        HBox payArea = new HBox();
        payArea.setAlignment(Pos.CENTER_RIGHT);
        payArea.setSpacing(25);
        Button payButton = new Button("Pay");
        payArea.getChildren().addAll(totalLabel, payButton);
        orderArea.getChildren().addAll(orderLabel, orderGridPane, payArea);

        // Go to pay page.
        payButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new PayPopup().payHandle);

        //orderArea.getChildren().addAll(orderLabel, orderGridPane, totalLabel);

        GridPane.setConstraints(menu, 0, 0);
        GridPane.setConstraints(orderArea, 1, 0);
        layout.getChildren().addAll(menu, orderArea);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(70);
        layout.getColumnConstraints().add(column);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(30);
        layout.getColumnConstraints().add(column2);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(100);
        layout.getRowConstraints().addAll(row);

        Scene scene = new Scene(layout);

        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        if(showBorders){
            orderGridPane.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            orderArea.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            menu.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            layout.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }

        updateTimeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
        updateTimeline.play();
    }
}
