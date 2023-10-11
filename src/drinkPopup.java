import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;

class drinkPopup {
    Drink d;
    drinkPopup(ArrayList<String> items, Order ord) {
        Stage popupStage = new Stage();

        // Creating right side of popup.
        GridPane popupGP = new GridPane();
        popupGP.setMinSize(600, 400);
        popupGP.setPadding(new Insets(4, 4, 4, 4));

        VBox drinkList = new VBox();
        drinkList.setSpacing(25);
        ToggleGroup itemToggleGroup = new ToggleGroup();
        for(String item : items)
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

        // Creating left side of popup
        VBox leftSection = new VBox();

        VBox sizeTemperatureSection = new VBox();
        GridPane sizeTemperatureGP = new GridPane();

        Label sizeLabel = new Label("Size:");
        sizeTemperatureGP.add(sizeLabel, 0, 0);
        HBox sizeToggles = new HBox();
        ToggleGroup sizeToggleGroup = new ToggleGroup();
        RadioButton mediumButton = new RadioButton("M: ");
        mediumButton.setToggleGroup(sizeToggleGroup);
        RadioButton largeButton = new RadioButton("L: ");
        largeButton.setToggleGroup(sizeToggleGroup);
        sizeToggles.getChildren().addAll(largeButton, mediumButton);
        sizeTemperatureGP.add(sizeToggles, 0, 1);
        Label temperatureLabel = new Label("Temperature:");
        sizeTemperatureGP.add(temperatureLabel, 2, 0);
        HBox temperatureToggles = new HBox();
        ToggleGroup temperatureToggleGroup = new ToggleGroup();
        RadioButton hotButton = new RadioButton("Hot: ");
        hotButton.setToggleGroup(temperatureToggleGroup);
        RadioButton coldButton = new RadioButton("Cold: ");
        coldButton.setToggleGroup(temperatureToggleGroup);
        temperatureToggles.getChildren().addAll(hotButton, coldButton);
        sizeTemperatureGP.add(temperatureToggles, 2, 1);
        sizeTemperatureSection.getChildren().add(sizeTemperatureGP);

        // 14 Toppings, adding 7 in one column and 7 in the other.
        VBox toppingsSection = new VBox();
        GridPane toppingsGP = new GridPane();
        ArrayList<String> toppingsList = new ArrayList<>(
                Arrays.asList("Brown Sugar Wow", "Bubble", "Mango Jelly, Aloe Jelly", "Nata Jelly",
                            "Herbal Jelly", "Milk Cap", "Berry Crystal Bubble", "Crystal Bubble",
                            "Grape Popping Bubble", "Mango Popping Bubble", "Coffee Popping Bubble", "Red Bean",
                            "Oreo", "Pudding")
        );
        for(int i = 0; i < 7; ++i)
        {
            CheckBox toppingCheckbox = new CheckBox(toppingsList.get(i));
            toppingsGP.add(toppingCheckbox, 0, i);
        }
        for(int i = 0; i < 7; ++i)
        {
            CheckBox toppingCheckbox = new CheckBox(toppingsList.get(i + 7));
            toppingsGP.add(toppingCheckbox, 1, i);
        }
        toppingsSection.getChildren().add(toppingsGP);

        VBox sugarLevelSection = new VBox();
        Label sugarLevelLabel = new Label("Sugar Level: ");
        sugarLevelSection.getChildren().add(sugarLevelLabel);
        HBox sugarToggles = new HBox();
        ToggleGroup sugarToggleGroup = new ToggleGroup();
        RadioButton sugar100 = new RadioButton("100%");
        sugar100.setToggleGroup(sugarToggleGroup);
        RadioButton sugar120 = new RadioButton("120%");
        sugar120.setToggleGroup(sugarToggleGroup);
        RadioButton sugar70 = new RadioButton("70%");
        sugar70.setToggleGroup(sugarToggleGroup);
        RadioButton sugar50 = new RadioButton("50%");
        sugar50.setToggleGroup(sugarToggleGroup);
        RadioButton sugar30 = new RadioButton("30%");
        sugar30.setToggleGroup(sugarToggleGroup);
        RadioButton sugar0 = new RadioButton("0%");
        sugar0.setToggleGroup(sugarToggleGroup);
        sugarToggles.getChildren().addAll(sugar100, sugar120, sugar70, sugar50, sugar30, sugar0);
        sugarLevelSection.getChildren().add(sugarToggles);



        VBox iceLevelSection = new VBox();
        Label iceLevelLabel = new Label("Ice Level:");
        iceLevelSection.getChildren().add(iceLevelLabel);
        HBox iceLevelToggles = new HBox();
        ToggleGroup iceLevelToggleGroup = new ToggleGroup();
        RadioButton regularIce = new RadioButton("Regular Ice");
        regularIce.setToggleGroup(iceLevelToggleGroup);
        RadioButton lessIce = new RadioButton("Less Ice");
        lessIce.setToggleGroup(iceLevelToggleGroup);
        RadioButton noIce = new RadioButton("No Ice");
        noIce.setToggleGroup(iceLevelToggleGroup);
        RadioButton moreIce = new RadioButton("More Ice");
        moreIce.setToggleGroup(iceLevelToggleGroup);
        iceLevelToggles.getChildren().addAll(regularIce, lessIce, noIce, moreIce);
        iceLevelSection.getChildren().add(iceLevelToggles);


        VBox notesSection = new VBox();
        Label notesLabel = new Label("Notes");
        notesSection.getChildren().add(notesLabel);
        TextArea notesArea = new TextArea("Insert your notes here...");
        notesSection.getChildren().add(notesArea);


        leftSection.getChildren().addAll(sizeTemperatureSection, toppingsSection, sugarLevelSection, iceLevelSection, notesSection);
        popupGP.add(leftSection, 1, 0);
        Scene popupScene = new Scene(popupGP);
        popupStage.setTitle("Select Drink");
        popupStage.setScene(popupScene);

        popupStage.show();

        closeButton.setOnAction(event -> {
            popupStage.close();
        });

        doneButton.setOnAction(event -> {
            // d = new Drink(((RadioButton)itemToggleGroup.getSelectedToggle()).getText(),
            // "WIP",
            // ((RadioButton)sizeToggleGroup.getSelectedToggle()).getText(),
            // ((RadioButton)temperatureToggleGroup.getSelectedToggle()).getText(),
            // ((RadioButton)iceLevelToggleGroup.getSelectedToggle()).getText(),
            // ((RadioButton)sugarToggleGroup.getSelectedToggle()).getText(),
            // "0.0",
            // new String[3]);

            //d = new Drink("test", "test", "test","test","test","100%","0.0");

            String name = ((RadioButton)itemToggleGroup.getSelectedToggle()).getText();
            String size = ((RadioButton)sizeToggleGroup.getSelectedToggle()).getText();
            String temp = ((RadioButton)temperatureToggleGroup.getSelectedToggle()).getText();
            String ice_level = ((RadioButton)iceLevelToggleGroup.getSelectedToggle()).getText();
            String sugar_level = ((RadioButton)sugarToggleGroup.getSelectedToggle()).getText();

            d = new Drink(name, "Some Category", size, temp, ice_level, sugar_level, "0.0");
            System.out.print(((RadioButton)sugarToggleGroup.getSelectedToggle()).getText());
            ord.addDrink(d);
            popupStage.close();
        });
    }



};