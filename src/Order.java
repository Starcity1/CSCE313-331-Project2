import java.util.ArrayList;

public class Order{
    public static int orderIDCounter = 1;

    int orderID;
    ArrayList<Drink> drinks;

    public Order(){
        orderID = orderIDCounter;
        drinks = new ArrayList<Drink>();

        orderIDCounter++;
    }

    ArrayList<Drink> getDrinks(){
        return drinks;
    }

    int getOrderID(){
        return orderID;
    }

    void addDrink(Drink d){
        //System.out.println("HELP");
        drinks.add(d);
        //System.out.print(d);
    }

    double calcPrice(){
        double sum = 0.0;
        for(int i = 0; i < drinks.size(); i++){
            sum += drinks.get(i).calcPrice();
            //System.out.println("Currnet" + drinks.get(i).calcPrice());
        }
        return sum;
    }
}
