import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order{
    public static int orderIDCounter = 1;

    int orderID;
    ArrayList<Drink> drinks;
    ArrayList<Merch> merch;
    dbConnectionHandler db;

    public Order(dbConnectionHandler dbase){
        orderID = orderIDCounter;
        drinks = new ArrayList<Drink>();
        merch = new ArrayList<Merch>();
        db = dbase;

        orderIDCounter++;
    }

    ArrayList<Drink> getDrinks(){
        return drinks;
    }

    ArrayList<Merch> getMerch(){
        return merch;
    }

    int getOrderID(){
        return orderID;
    }

    void addDrink(Drink d){
        //System.out.println("HELP");
        drinks.add(d);
        //System.out.print(d);
    }

    void addMerch(Merch m){
        merch.add(m);
    }

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
