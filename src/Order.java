public class Order{
    private static int orderIDCounter = 1;

    int orderID;
    Drink[] drinks;

    Order(Drink[] drinks){
        orderID = orderIDCounter;
        this.drinks = drinks;

        orderIDCounter++;
    }

    Drink[] getDrinks(){
        return drinks;
    }

    double calcPrice(){
        double sum = 0.0;
        for(int i = 0; i < drinks.length; i++){
            sum += drinks[i].calcPrice();
        }
        return sum;
    }
}
