import java.util.ArrayList;

public class Merch {
    int merchID;
    String name;
    double price;

    public static int merchIDCounter = 1;


    public Merch(String name, double price){
        this.merchID = merchIDCounter;
        this.name = name;
        this.price = price;
        merchIDCounter++;

    }

    public int getMerchID() {
        return merchID;
    }

    public String getName() {
        return name;
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