import java.util.ArrayList;

/**
 * Represents a customer's order containing a list of drinks.
 * Each order is uniquely identified by an order ID.
 */
public class Order{
    /**
     * Static counter to ensure each order receives a unique ID.
     */
    public static int orderIDCounter = 1;

    /**
     * Unique identifier for this order.
     */
    int orderID;

    /**
     * List containing all drinks included in this order.
     */
    ArrayList<Drink> drinks;

    /**
     * Initializes a new Order instance with a unique order ID and an empty list of drinks.
     */
    public Order(){
        orderID = orderIDCounter;
        drinks = new ArrayList<Drink>();

        orderIDCounter++;
    }

    /**
     * Retrieves the list of drinks in the order.
     *
     * @return An ArrayList of drinks.
     */
    ArrayList<Drink> getDrinks(){
        return drinks;
    }

    /**
     * Gets the unique order ID for this order.
     *
     * @return The order ID.
     */
    int getOrderID(){
        return orderID;
    }

    /**
     * Adds a new drink to the order.
     *
     * @param d The drink to be added.
     */
    void addDrink(Drink d){
        //System.out.println("HELP");
        drinks.add(d);
        System.out.print(d);
    }

    /**
     * Calculates the total price of the order by summing the prices of all drinks included in the order.
     *
     * @return The total price.
     */
    double calcPrice(){
        double sum = 0.0;
        for(int i = 0; i < drinks.size(); i++){
            sum += drinks.get(i).calcPrice();
            //System.out.println("Currnet" + drinks.get(i).calcPrice());
        }
        return sum;
    }
}
