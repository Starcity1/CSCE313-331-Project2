/**
 * Represents a drink item, typically found in a menu, with various attributes defining its properties.
 * Each drink is automatically assigned a unique ID upon creation.
 */
public class Drink {
    // Attributes representing the properties of a drink.
    int drinkID;
    String name;
    String category;
    String size;
    String temp;
    String ice_level;
    int sugar_level;
    double price;

    // Static counter to maintain unique IDs for each drink.
    public static int drinkIDCounter = 1;

    /**
     * Creates a new Drink with specified properties.
     *
     * @param name        the name of the drink
     * @param category    the category to which the drink belongs
     * @param size        the size of the drink
     * @param temp        the preferred temperature of the drink (e.g., hot, cold)
     * @param ice_level   the level of ice in the drink
     * @param sugar_level the level of sugar in the drink, represented as a string (e.g., "50%")
     * @param price       the price of the drink
     */
    public Drink(String name, String category, String size, String temp, String ice_level, String sugar_level, double price){
        this.drinkID = drinkIDCounter;
        this.name = name;
        this.category = category;
        this.size = size;
        this.temp = temp;
        this.ice_level = ice_level;
        this.sugar_level = Integer.parseInt(sugar_level.substring(0, sugar_level.length() - 1));
        this.price = price;
        drinkIDCounter++;
    }

    /**
     * Retrieves the unique ID of this drink.
     *
     * @return an integer representing the unique drink ID
     */
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

     /**
     * Calculates the price based on current settings. This method might need access to a database for accurate pricing.
     *
     * @return a double representing the total price
     */
    double calcPrice(){
        // The calculation can be expanded to consider current prices and additional factors, such as toppings.
        return price;
    }

    /**
     * Returns a string representation of the drink, primarily displaying its name.
     *
     * @return a string showing the drink's name
     */
    public String toString(){
        return "Name: " + name;
    }
}