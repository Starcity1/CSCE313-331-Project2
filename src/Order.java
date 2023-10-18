import java.sql.ResultSet;
import java.sql.SQLException;
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
    ArrayList<Merch> merch;
    dbConnectionHandler db;

    /**
     * Initializes a new Order instance with a unique order ID and an empty list of drinks.
     */
    public Order(dbConnectionHandler dbase){
        orderID = orderIDCounter;
        drinks = new ArrayList<Drink>();
        merch = new ArrayList<Merch>();
        db = dbase;
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

    ArrayList<Merch> getMerch(){
        return merch;
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
        //System.out.print(d);
    }
    
    void addMerch(Merch m){
        merch.add(m);
    }

    /**
     * Calculates the total price of the order by summing the prices of all drinks included in the order.
     *
     * @return The total price.
     */
    double calcPrice() throws SQLException{
        double sum = 0.0;
        for(int i = 0; i < drinks.size(); i++){
            sum += drinks.get(i).calcPrice();
            //System.out.println("Currnet" + drinks.get(i).calcPrice());
        }
        for(int i = 0; i < merch.size(); i++){
            sum += merch.get(i).calcPrice();
            //System.out.println("Currnet" + drinks.get(i).calcPrice());
        }
        return sum;
    }
}
