import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.control.Label;

public class GUI extends Application{
    Button classic;
    Button milk_tea;
    Button punch;
    Button milk_cap;
    Button yogurt;
    Button slush;
    Button milk_strike;
    Button espresso;
    Button seasonal;
    Button limited;
    Boolean showBorders = true;
    Integer menu_rows = 4;
    Integer menu_cols = 3;

    Integer order_cols = 3;

    public EventHandler<MouseEvent> onClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            String drink_type = ((Button) mouseEvent.getSource()).getText();
            System.out.println("Mouse is detecting click from button :: " + drink_type + "!");
            switch(drink_type)
            {
                case "Classic":
                case "Milk Tea":
                case "Punch":
                case "Milk Cap":
                case "Yogurt":
                case "Slush":
                case "Milk Strike":
                case "Seasonal":
                case "Espresso":
            }
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
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

        GridPane layout = new GridPane();

        GridPane menu = new GridPane();
        HBox.setHgrow(menu, Priority.SOMETIMES);

        GridPane.setConstraints(classic, 0, 0);
        GridPane.setConstraints(milk_tea, 1, 0);
        GridPane.setConstraints(punch, 2, 0);
        GridPane.setConstraints(milk_cap, 0, 1);
        GridPane.setConstraints(yogurt, 1, 1);
        GridPane.setConstraints(slush, 2, 1);
        GridPane.setConstraints(milk_strike, 0, 2);
        GridPane.setConstraints(espresso, 1, 2);
        GridPane.setConstraints(seasonal, 2, 2);
        GridPane.setConstraints(limited, 0, 3);

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


        menu.getChildren().addAll(classic, milk_tea, punch, milk_cap, yogurt, slush, milk_strike, espresso, seasonal, limited);

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

        primaryStage.setFullScreen(true);
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
