import java.util.ArrayList;

public class Order{
    //private static int orderIDCounter = 1;

    int orderID;
    ArrayList<Drink> drinks;

    public Order(){
        orderID = 4;
        drinks = new ArrayList<Drink>();

        //orderIDCounter++;
    }

    ArrayList<Drink> getDrinks(){
        return drinks;
    }

    void addDrink(Drink d){
        //System.out.println("HELP");
        drinks.add(d);
        System.out.print(d);
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
