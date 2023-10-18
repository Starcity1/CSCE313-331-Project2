import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;


import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.YearMonth;

import java.sql.*;
import java.util.stream.Collectors;

public class ManagerGUI {
    Stage primaryStage;
    private YearMonth currentYearMonth;
    private ObservableList<ObservableList> data;
    private LineChart<Number, Number> ll;
    ListView<String> lv = new ListView<String>();
    dbConnectionHandler handler = new dbConnectionHandler();

    ManagerGUI(dbConnectionHandler handler) {
        currentYearMonth = YearMonth.now();
        data = FXCollections.observableArrayList();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Manager's GUI");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        GridPane primaryGP = new GridPane();
        primaryGP.setHgap(25);
        primaryGP.setVgap(25);

        ResultSet queryRes = handler.requestData("SELECT * FROM order_log WHERE date::DATE = \'2022-01-11\';");
        updateData(queryRes);

        // Tab seciton
        //VBox tabSection = new VBox();
        //tabSection.setSpacing(10);


        // Main section
        VBox mainSection = new VBox();
        HBox chartCalendarSection = new HBox();
        chartCalendarSection.setAlignment(Pos.CENTER);
        ll = createChart();
        chartCalendarSection.getChildren().add(ll);

        // Creating calendar.
        GridPane calendarGridPane = new GridPane();
        calendarGridPane.setVgap(10);
        calendarGridPane.setHgap(10);
        calendarGridPane.setAlignment(Pos.CENTER);
        Button prevButton = new Button("<<");
        prevButton.setOnAction(e -> updateManagerGUI(-1, calendarGridPane));
        Button nextButton = new Button(">>");
        nextButton.setOnAction(e -> updateManagerGUI(1, calendarGridPane));
        calendarGridPane.add(prevButton, 0, 0);
        calendarGridPane.add(new Label(currentYearMonth.toString()), 1, 0);
        calendarGridPane.add(nextButton, 2, 0);
        populateCalendar(calendarGridPane);
        chartCalendarSection.getChildren().add(calendarGridPane);
        mainSection.getChildren().add(chartCalendarSection);

        primaryGP.add(mainSection, 0, 1);
        InventoryRequestSection inventoryRequestSection = new InventoryRequestSection(handler);
        primaryGP.add(inventoryRequestSection, 0, 2);

        // Creating menu
        VBox menuSection = new VBox();
        HBox listBox = new HBox();
        HBox.setHgrow(menuSection, Priority.ALWAYS);
        ScrollPane sp = new ScrollPane();
        sp.setPannable(true);
        sp.setFitToWidth(true);
        sp.setMinHeight(600);
        sp.setMaxHeight(600);
        sp.setMinWidth(350);    
        VBox menuVBox = new VBox();
        menuVBox.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        HBox.setHgrow(menuVBox, Priority.ALWAYS);
        sp.setContent(listBox);
        //set other properties
        //menuVBox.setMinWidth(350);
        menuVBox.setMaxHeight(600);

        VBox statBox = new VBox();


        ComboBox combobox = new ComboBox();
        menuVBox.getChildren().add(combobox);
        populateComboBox(combobox);
        combobox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<String> names = createAllDrinks(statBox, combobox.getValue().toString());
                //System.out.println("Names" + names);
                changeList(names);
            }
        });


        listBox.getChildren().addAll(lv, statBox);


        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);

        primaryGP.getColumnConstraints().add(0, column1);
        primaryGP.getColumnConstraints().add(1, column2);

        menuSection.getChildren().addAll(menuVBox, sp, listBox);
        primaryGP.add(menuSection, 1, 1);

        HBox modBox = new HBox();

        TextField addDeleteField = new TextField();
        Button addDeleteButton = new Button("Add/Delete");
        addDeleteButton.setOnAction(e -> modifyList(addDeleteField.getText(), combobox));


        TextField priceField = new TextField();
        Button priceButton = new Button("Change Price To:");
        priceButton.setOnAction(e -> changePrice(lv.getSelectionModel().getSelectedItem(), priceField.getText()));

        modBox.getChildren().addAll(addDeleteField, addDeleteButton, priceField, priceButton);

        primaryGP.add(modBox, 1, 2);

        Scene primaryScene = new Scene(primaryGP);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }

    private void changeList(ObservableList<String> nms){
        lv.setItems(nms);
    }

    private void changePrice(String name, String price){
        double pc = Double.parseDouble(price);
        handler.executeUpdate("UPDATE menu SET price = " + Double.toString(pc) + " WHERE name = '" + name + "';");
    }

    private void modifyList(String item, ComboBox combobox){
        ObservableList<String> ns = lv.getItems();
        if(ns.contains(item)){
            handler.executeUpdate("DELETE FROM menu WHERE name = '" + item + "';");
        }
        else{
            int menuID = handler.requestInt("select MAX(menuid) from menu;") + 1;
            int invID = handler.requestInt("select MAX(inventoryid) from menu;") + 1;
            handler.executeUpdate(String.format("INSERT INTO menu (menuid, inventoryid, name, category, price) VALUES ('%d', '%d', '%s', '%s', '%.2f');"
            , menuID, invID, item, combobox.getValue(), 0.0));
        }
    }


    private void populateComboBox(ComboBox combobox) {
        // Request data.
        ResultSet categories_res = handler.requestData("SELECT DISTINCT category FROM menu;");
        if (categories_res == null)
            showAndThrowError("Could not retrieve data from menu db.");

        try {
            while(categories_res.next())
                combobox.getItems().add(categories_res.getString(1));
        } catch (Exception e) {
            showAndThrowError("Unexpected error occured when reading data.\n" + e.getMessage());
        }
        combobox.setPromptText("-- Select --");
    }

//    private void populateCategories() {
//        // Getting all categories
//        dbConnectionHandler handler = new dbConnectionHandler();
//        ResultSet categories_res = handler.requestData("SELECT DISTINCT category FROM menu;");
//        if (categories_res == null) {showAndThrowError("Could not retrieve category data from menu db.");}
//        try {
//            while(categories_res.next()) {
//                String drink = categories_res.getString(1).replace("'", "''");
//                String drinksQuerry = String.format("SELECT * FROM menu WHERE category = '%s';", drink);
//                ResultSet drinks_res = handler.requestData(drinksQuerry);
//                if (drinks_res == null) {showAndThrowError("Could not retrieve drink data from menu db.");}
//                // Populating drink.
//                menuItems.put(categories_res.getString(1), new ArrayList<String>());
//                while(drinks_res.next())
//                    menuItems.get(categories_res.getString(1)).add(drinks_res.getString(2));
//            }
//        } catch (Exception e) {
//            showAndThrowError("Unexpected error occured when reading data.\n" + e.getMessage());
//        }
//        System.out.println(menuItems.get("classic"));
//    }

    private ObservableList<String> createAllDrinks(VBox menuVBox, String category) {
        //System.out.println("Adding all drinks from " + category);
        menuVBox.getChildren().removeIf(node -> node instanceof HBox);
        dbConnectionHandler handler = new dbConnectionHandler();
        category = category.replace("'", "''");
        ResultSet drinksRes = handler.requestData(String.format("SELECT * FROM menu WHERE category = '%s';", category));
        //ListView<String> lv = new ListView<String>();
        ObservableList<String> names = FXCollections.observableArrayList();
        if (drinksRes == null) {showAndThrowError("Could not retrieve drink data from menu db.");}
        try {
            while (drinksRes.next()) {
                names.add(drinksRes.getString(3));

                HBox drinkSection = new HBox();
                drinkSection.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                drinkSection.setSpacing(20); // Add spacing between children
                drinkSection.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(drinkSection, Priority.ALWAYS); // Allow the parent to grow

                Label priceLabel = new Label("Price: " + drinksRes.getDouble(5));
                Label drinkQuantity = new Label("Qty: " + 0);
                Button requestButton = new Button("Request More");

                HBox.setHgrow(priceLabel, Priority.ALWAYS);
                HBox.setHgrow(drinkQuantity, Priority.ALWAYS);
                HBox.setHgrow(requestButton, Priority.ALWAYS);

                drinkSection.getChildren().addAll(priceLabel, drinkQuantity, requestButton);
                menuVBox.getChildren().add(drinkSection);

            }

        } catch (Exception e) {
            showAndThrowError("Unexpected error occured when reading data.\n" + e.getMessage());
        }
        return names;
    }

    private void updateData(ResultSet queryRes) {
        int queryResSize = 0;
        data.clear();
        try {
            if(queryRes == null) {throw new RuntimeException("Error");}
            queryResSize = queryRes.getMetaData().getColumnCount();
            while(queryRes.next())
            {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1; i <= queryResSize; ++i)
                {
                    row.add(queryRes.getString(i));
                }
                data.add(row);
            }
        } catch (Exception e) {
            showAndThrowError("Invalid input was entered to database.");
        }
    }

    private LineChart createChart() {
        NumberAxis x = new NumberAxis();
        x.setLabel("Day described");
        NumberAxis y = new NumberAxis();
        y.setLabel("Orders made");
        ll = new LineChart(x,y);
        ll.setTitle("Orders made.");
        List<String> xData = data.stream().map(row -> row.get(3).toString()).collect(Collectors.toList());
        List<Double> yData = data.stream().map(row -> Double.parseDouble(row.get(4).toString())).collect(Collectors.toList());
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < xData.size(); i++)
        {
            series.getData().add(new XYChart.Data<Number, Number>(i, yData.get(i)));
        }
        series.setName("Total Cost over 1-day period.");
        ll.getData().add(series);

        return ll;
    }

    private void updateChart(LineChart ll) {
        ll.getData().clear();
        List<String> xData = data.stream().map(row -> row.get(3).toString()).collect(Collectors.toList());
        List<Double> yData = data.stream().map(row -> Double.parseDouble(row.get(4).toString())).collect(Collectors.toList());
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        for (int i = 0; i < xData.size(); i++)
        {
            series.getData().add(new XYChart.Data<Number, Number>(i, yData.get(i)));
        }
        series.setName("Total Cost over 1-day period.");
        ll.getData().add(series);
    }

    private void showAndThrowError(String Message) {
        Alert failedConnection = new Alert(Alert.AlertType.ERROR);
        failedConnection.setTitle("Connection Error");
        failedConnection.setHeaderText("Error!");
        failedConnection.setContentText(Message);
        failedConnection.showAndWait();
        System.exit(1);
    }

    private void populateCalendar(GridPane gridPane) {
        LocalDate startDate = currentYearMonth.atDay(1);
        int daysInMonth = currentYearMonth.lengthOfMonth();
        int dayOfWeek = startDate.getDayOfWeek().getValue();  // 1-7 representing Monday-Sunday

        // Add day labels
        for (int i = 0; i < 7; i++) {
            gridPane.add(new Label("Sun Mon Tue Wed Thu Fri Sat ".substring(i * 4, (i + 1) * 4)), i, 1);
        }

        // Add day buttons
        for (int i = 0; i < daysInMonth; i++) {
            int row = (i + dayOfWeek) / 7 + 1;
            int col = (i + dayOfWeek) % 7;

            Button dayButton = new Button(String.valueOf(i + 1));
            dayButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String day = String.format("%02d", Integer.parseInt(((Button) mouseEvent.getSource()).getText().toString()));
                    String currentDate = currentYearMonth.toString() + "-" + day;
                    dbConnectionHandler handler = new dbConnectionHandler();
                    ResultSet queryRes = handler.requestData(String.format("SELECT * FROM order_log WHERE date::DATE = \'%s\';", currentDate));

                    updateData(queryRes);
                    updateChart(ll);
                }
            });
            dayButton.setMaxWidth(Double.MAX_VALUE);
            gridPane.add(dayButton, col, row + 1);
        }
    }

    private void updateManagerGUI(int monthOffset, GridPane gridPane) {
        currentYearMonth = currentYearMonth.plusMonths(monthOffset);

        // Update the label to display the updated YearMonth

        // Clear the existing day buttons
        gridPane.getChildren().removeIf(node -> node instanceof Button);
        gridPane.getChildren().removeIf(node -> node instanceof Label);

        // Add navigation buttons
        Button prevButton = new Button("<<");
        prevButton.setOnAction(e -> updateManagerGUI(-1, gridPane));
        Button nextButton = new Button(">>");
        nextButton.setOnAction(e -> updateManagerGUI(1, gridPane));

        gridPane.add(prevButton, 0, 0);
        gridPane.add(new Label(currentYearMonth.toString()), 1, 0);
        gridPane.add(nextButton, 2, 0);


        // Repopulate the calendar for the new month
        populateCalendar(gridPane);
    }
}

class InventoryRequestSection extends VBox {
    private TextField itemNameField;
    private TextField quantityField;
    private Button submitButton;
    private dbConnectionHandler handler;

    public InventoryRequestSection(dbConnectionHandler handler) {
        this.handler = handler;

        setSpacing(10);

        Label titleLabel = new Label("Add Inventory Request");
        itemNameField = new TextField();
        itemNameField.setPromptText("Item Name");
        quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        
        submitButton = new Button("Submit Request");
        submitButton.setOnAction(e -> submitRequest());

        getChildren().addAll(titleLabel, itemNameField, quantityField, submitButton);
    }

    private void submitRequest() {
        String itemName = itemNameField.getText().trim();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText().trim());
        } catch (NumberFormatException e) {
            showAndThrowError("Please input a valid number for quantity.");
            return;
        }

        // Updated insertQuery
        String insertQuery = String.format(
            "INSERT INTO inventory_requests (item_name, quantity) VALUES ('%s', %d);",
            itemName, quantity
        );

        handler.executeUpdate(insertQuery);
        showInfo("Inventory request added successfully!");
    }

    private void showAndThrowError(String message) {
        Alert failedInput = new Alert(Alert.AlertType.ERROR);
        failedInput.setTitle("Input Error");
        failedInput.setHeaderText("Error!");
        failedInput.setContentText(message);
        failedInput.showAndWait();
    }

    private void showInfo(String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Info");
        infoAlert.setHeaderText("Information");
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }
}