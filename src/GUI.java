import javafx.application.Application;

import javafx.event.EventHandler;

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

    public EventHandler<MouseEvent> onClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            String drinkType = ((Button) mouseEvent.getSource()).getText();
            
            // Array later becomes all other products in popups.
            ArrayList<String> items;
            switch(drinkType)
            {
                case "Classic":
                    items = new ArrayList<>(
                            Arrays.asList("Longan Jujube Tea", "Kung Fu Black Tea", "Kung Fu Green Tea",
                                        "Kung Fu Oolong Tea", "Kung Fu Honey Tea")
                    );
                    break;
                case "Milk Tea":
                    items = new ArrayList<>(
                            Arrays.asList("Kung Fu Milk/Milk Green Tea", "Rosehip Milk Tea", "Coffee Milk Tea",
                                          "Taro Milk/Milk Green Tea", "Honey Milk/Milk Green Tea", "Thai Milk Tea",
                                          "Winter Melon Milk Green Tea", "Oolong Milk Tea/ Honey Oolong Milk Tea", "Coconut Milk Tea",
                                          "Almond Milk Tea")
                    );
                    break;
                case "Punch":
                    items = new ArrayList<>(
                            Arrays.asList("Grapefruit Green Tea", "Rosehip Lemonade", "Lychee Punch",
                                          "Lychee Black Tea", "Sunshine Pineapple Tea", "Strawberry Lemonade",
                                          "Honey Lemonade", "Peach Oolong Tea", "Mango Green Tea",
                                          "Passionfruit Green Tea", "Strawberry Lemon Green Tea", "Orange Green Tea")
                    );
                    break;
                case "Milk Cap":
                    items = new ArrayList<>(
                            Arrays.asList("Honey Tea Cap (Black/Green/Oolong)", "Matcha Milk Cap", "Cocoa Cream Wow Milk Cap",
                                          "Sunshine Pineapple Tea Cap", "Winter Melon Tea Cap")
                    );
                    break;
                case "Yogurt":
                    items = new ArrayList<>(
                            Arrays.asList("Yogurt Orange", "Yogurt Grapefruit", "Yogurt Green Tea")
                    );
                    break;
                case "Slush":
                    items = new ArrayList<>(
                            Arrays.asList("Mango/Mango Snow Slush", "Taro Slush", "Matcha Red Bean Slush")
                    );
                    break;
                case "Milk Strike":
                    items = new ArrayList<>(
                            Arrays.asList("Herbal Jelly Wow", "Matcha Milk", "Red Bean Wow",
                                          "Sesame Matcha", "Chair Milk", "Cocoa Cream Wow",
                                          "Ginger Milk", "Oreo Wow")
                    );
                    break;
                case "Espresso":
                    items = new ArrayList<>(
                            Arrays.asList("Signature Coffee", "Caramel Macchiato", "Mocha",
                                          "Latte", "Cappuccino")
                    );
                    break;
                case "Seasonal":
                    items = new ArrayList<>(
                            Arrays.asList("Brown Sugar Ginger", "Pumpkin Oolong Milk Tea", "Purple Yam Latte",
                                          "Rosehip Pineapple Punch", "Yogurt Peach Lemonade")
                    );
                    break;
                case "What's New":
                    items = new ArrayList<>(
                          Arrays.asList("Mango Creamsicle", "Caramel milk Tea", "Lemon-Almond Pie",
                                        "Sesame oolong Milk Tea", "Sesame Slush", "White Grape Punch",
                                        "White Grape Slush", "Hershey's Smores Slush/Coffee Slush", "Hershey's Cocoa",
                                        "Wow Milk Cap (Green/Black/Oolong)", "Coffee Wow Milk Cap")
                    );
                    break;
                default:
                    items = new ArrayList<>();
                    break;
            }

            drinkPopup new_popup = new drinkPopup(items);
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("315 Project");

        classic = new Button();
        classic.setText("Classic");
        classic.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        milk_tea = new Button();
        milk_tea.setText("Milk Tea");
        milk_tea.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        punch = new Button();
        punch.setText("Punch");
        punch.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        milk_cap = new Button();
        milk_cap.setText("Milk Cap");
        milk_cap.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        yogurt = new Button();
        yogurt.setText("Yogurt");
        yogurt.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        slush = new Button();
        slush.setText("Slush");
        slush.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        milk_strike = new Button();
        milk_strike.setText("Milk Strike");
        milk_strike.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        espresso = new Button();
        espresso.setText("Espresso");
        espresso.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        seasonal = new Button();
        seasonal.setText("Seasonal");
        seasonal.addEventFilter(MouseEvent.MOUSE_CLICKED, onClickHandler);

        limited = new Button();
        limited.setText("What's New");
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
        Label totalItemLabel = new Label("Total");

        GridPane.setConstraints(itemLabel, 0, 0);
        GridPane.setConstraints(quantityLabel, 1, 0);
        GridPane.setConstraints(totalItemLabel, 2, 0);

        orderGridPane.getChildren().addAll(itemLabel, quantityLabel, totalItemLabel);

        for (int i = 0; i < order_cols; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/order_cols);
            orderGridPane.getColumnConstraints().add(column);
        }

        Label totalLabel = new Label("Total: ");

        orderArea.getChildren().addAll(orderLabel, orderGridPane, totalLabel);

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
    }
}
