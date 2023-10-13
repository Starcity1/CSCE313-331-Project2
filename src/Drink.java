import java.util.ArrayList;

public class Drink {
    int drinkID;
    String name;
    String category;
    String size;
    String temp;
    String ice_level;
    int sugar_level;
    double price;
    ArrayList<String> toppings;

    public static int drinkIDCounter = 1;


    public Drink(String name, String category, String size, String temp, String ice_level, String sugar_level, double price, ArrayList<String> toppings){
        this.drinkID = drinkIDCounter;
        this.name = name;
        this.category = category;
        this.size = size;
        this.temp = temp;
        this.ice_level = ice_level;
        this.sugar_level = Integer.parseInt(sugar_level.substring(0,sugar_level.length() - 1));
        this.price = price;
        this.toppings = toppings;
        drinkIDCounter++;

    }

    public int getDrinkID() {
        return drinkID;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getSize() {
        return size;
    }

    public String getTemp() {
        return temp;
    }

    public String getIce_level() {
        return ice_level;
    }

    public int getSugar_level() {
        return sugar_level;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getToppings() {
        return toppings;
    }

    double calcPrice(){
        // We might need a database for current prices
        // price of drink and size + price of toppings
        return price;
    }

    public String toString(){
        return "Name: " + name;
    }
}