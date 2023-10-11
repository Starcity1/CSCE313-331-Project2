
public class Drink {
    int drinkID;
    String name;
    String category;
    String size;
    String temp;
    int ice_level;
    int sugar_level;
    double price;
    String[] toppings;

    private static int drinkIDCounter = 1;


    Drink(String name, String category, String size, String temp, int ice_level, int sugar_level, double price, String[] toppings){
        this.name = name;
        this.size = size;
        this.temp = temp;
        this.ice_level = ice_level;
        this.sugar_level = sugar_level;
        this.price = price;
        this.toppings = toppings;

        this.drinkID = drinkIDCounter;
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

    public int getIce_level() {
        return ice_level;
    }

    public int getSugar_level() {
        return sugar_level;
    }

    public double getPrice() {
        return price;
    }

    public String[] getToppings() {
        return toppings;
    }

    double calcPrice(){
        // We might need a database for current prices
        // price of drink and size + price of toppings
        return 0.0;
    }
}