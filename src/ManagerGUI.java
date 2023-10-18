import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;
import javafx.scene.chart.XYChart;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * ManagerGUI is responsible for creating and managing the main GUI for a Manager's dashboard.
 * It includes functionalities such as displaying orders data, navigating through a calendar, 
 * and handling inventory requests.
 */
public class ManagerGUI {
    Stage primaryStage;
    private YearMonth currentYearMonth;
    private ObservableList<ObservableList> data;

    /**
     * Constructs a ManagerGUI object with a connection to the database.
     *
     * @param handler The database connection handler to be used for querying data.
     */
    ManagerGUI(dbConnectionHandler handler)
    {
        currentYearMonth = YearMonth.now();
        data = FXCollections.observableArrayList();

        Stage primaryStage = new Stage();
        primaryStage.setTitle("Manager's GUI");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        GridPane primaryGP = new GridPane();

        ResultSet queryRes = handler.requestData("SELECT * FROM order_log WHERE date::DATE = \'2022-01-11\';");

        // Adding results to this array. Array will be then used to generate table and line graph.
        int queryResSize = 0;
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

        // Tab seciton
        //VBox tabSection = new VBox();
        //tabSection.setSpacing(10);


        // Main section
        VBox mainSection = new VBox();
        HBox chartCalendarSection = new HBox();
        chartCalendarSection.setAlignment(Pos.CENTER);

        // Creating chart.
        NumberAxis x = new NumberAxis();
        x.setLabel("Day described");
        NumberAxis y = new NumberAxis();
        y.setLabel("Orders made");
        LineChart<Number, Number> ll = new LineChart(x,y);
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
        chartCalendarSection.getChildren().add(ll);

        // Creating calendar.
        GridPane calendarGridPane = new GridPane();
        calendarGridPane.setVgap(10);
        calendarGridPane.setHgap(10);
        calendarGridPane.setAlignment(Pos.CENTER);
        Button prevButton = new Button("<<");
        prevButton.setOnAction(e -> updateCalendar(-1, calendarGridPane));
        Button nextButton = new Button(">>");
        nextButton.setOnAction(e -> updateCalendar(1, calendarGridPane));
        calendarGridPane.add(prevButton, 0, 0);
        calendarGridPane.add(new Label(currentYearMonth.toString()), 1, 0);
        calendarGridPane.add(nextButton, 2, 0);
        populateCalendar(calendarGridPane);
        chartCalendarSection.getChildren().add(calendarGridPane);
        mainSection.getChildren().add(chartCalendarSection);

        // Creating Table
        VBox tableSection = new VBox();
        Label tableLabel = new Label("Table from orders");
        TableView table = new TableView();
        table.setEditable(true);
        try{
            for(int i = 0; i < queryResSize; ++i)
            {
                final int j = i;
                TableColumn tmpCol = new TableColumn(queryRes.getMetaData().getColumnName(i + 1));
                tmpCol.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(tmpCol);
            }
            table.setItems(data);
        } catch(Exception e)
        {
            showAndThrowError("Could not load data from database. Aborting.\n" +e.getMessage());
        }

        tableSection.getChildren().addAll(tableLabel, table);
        mainSection.getChildren().add(tableSection);

        primaryGP.add(mainSection, 0, 1);
        InventoryRequestSection inventoryRequestSection = new InventoryRequestSection(handler);
        primaryGP.add(inventoryRequestSection, 1, 1);

        Scene primaryScene = new Scene(primaryGP);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }

     /**
     * Displays an error message in an alert dialog and terminates the application.
     *
     * @param message The error message to display.
     */
    private void showAndThrowError(String Message)
    {
        Alert failedConnection = new Alert(Alert.AlertType.ERROR);
        failedConnection.setTitle("Connection Error");
        failedConnection.setHeaderText("Error!");
        failedConnection.setContentText(Message);
        failedConnection.showAndWait();
        System.exit(1);
    }

     /**
     * Fills the calendar grid with appropriate day labels and buttons corresponding to the days.
     *
     * @param gridPane The GridPane representing the calendar.
     */
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
            dayButton.setMaxWidth(Double.MAX_VALUE);
            gridPane.add(dayButton, col, row + 1);
        }
    }

    /**
     * Updates the calendar displayed to the user, shifting the months shown.
     *
     * @param monthOffset A positive or negative number indicating how many months to move forward or backward.
     * @param gridPane    The GridPane representing the calendar.
     */
    private void updateCalendar(int monthOffset, GridPane gridPane) {
        currentYearMonth = currentYearMonth.plusMonths(monthOffset);

        // Update the label to display the updated YearMonth
        ((Label) gridPane.getChildren().filtered(node -> node instanceof Label).get(1)).setText(currentYearMonth.toString());

        // Clear the existing day buttons
        gridPane.getChildren().removeIf(node -> node instanceof Button);

        // Add navigation buttons
        Button prevButton = new Button("<<");
        prevButton.setOnAction(e -> updateCalendar(-1, gridPane));
        Button nextButton = new Button(">>");
        nextButton.setOnAction(e -> updateCalendar(1, gridPane));

        gridPane.add(prevButton, 0, 0);
        gridPane.add(nextButton, 2, 0);

        // Repopulate the calendar for the new month
        populateCalendar(gridPane);
    }
}

/**
 * InventoryRequestSection is a UI component allowing managers to request inventory items.
 * It contains fields for item details and a submission button to process the request.
 */
class InventoryRequestSection extends VBox {
    private TextField itemNameField;
    private TextField quantityField;
    private Button submitButton;
    private dbConnectionHandler handler;

    /**
     * Constructs an InventoryRequestSection with the necessary UI components and a link to the database.
     *
     * @param handler The database connection handler for processing inventory requests.
     */
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

    /**
     * Handles the submission of an inventory request, input validation, and database insertion.
     */
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

    /**
     * Displays an error message in an alert dialog to the user.
     *
     * @param message The error message to display.
     */
    private void showAndThrowError(String message) {
        Alert failedInput = new Alert(Alert.AlertType.ERROR);
        failedInput.setTitle("Input Error");
        failedInput.setHeaderText("Error!");
        failedInput.setContentText(message);
        failedInput.showAndWait();
    }

    /**
     * Displays an information alert dialog to the user.
     *
     * @param message The information message to display.
     */
    private void showInfo(String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Info");
        infoAlert.setHeaderText("Information");
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }
}