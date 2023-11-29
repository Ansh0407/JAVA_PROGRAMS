package delivery;

public class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public void placeOrder(Restaurant restaurant, String item, String deliveryAddress) {
        System.out.println(name + " is placing an order for " + item);
        restaurant.prepareFood(item);
        restaurant.deliverFood(deliveryAddress);
        System.out.println("Order delivered to " + name + " at " + deliveryAddress);
    }
}
