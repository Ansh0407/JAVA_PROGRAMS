import java.util.ArrayList;
import java.util.List;

interface DeliveryService<T extends Food> {
    void placeOrder(Order<T> order, Customer customer);
    void trackOrder(Order<T> order);
}

class Food {
    private String name;

    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Order<T extends Food> {
    private List<T> items;
    private Customer customer;
    private DeliveryStatus deliveryStatus;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
        this.deliveryStatus = DeliveryStatus.PLACED;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public void displayOrderDetails() {
        System.out.println("Order Details for " + customer.getName() + ":");
        for (T item : items) {
            System.out.println("- " + item.getName());
        }
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum DeliveryStatus {
    PLACED,
    IN_TRANSIT,
    DELIVERED
}

class FoodDeliveryService implements DeliveryService<Food> {
    private List<Order<Food>> orders;

    public FoodDeliveryService() {
        orders = new ArrayList<>();
    }

    @Override
    public void placeOrder(Order<Food> order, Customer customer) {
        System.out.println("Placing a food order for " + customer.getName() + "...");
        order.displayOrderDetails();
        order.setDeliveryStatus(DeliveryStatus.IN_TRANSIT);
        orders.add(order);
        System.out.println("Order placed successfully!");
    }

    @Override
    public void trackOrder(Order<Food> order) {
        System.out.println("Tracking a food order...");
        order.displayOrderDetails();
        System.out.println("Delivery Status: " + order.getDeliveryStatus());
    }
}

public class FoodDeliveryApp {
    public static void main(String[] args) {
        // Creating a customer
        Customer customer = new Customer("John Doe");

        // Creating a generic order for pizzas
        Order<Food> pizzaOrder = new Order<>(customer);
        pizzaOrder.addItem(new Food("Margherita Pizza"));
        pizzaOrder.addItem(new Food("Pepperoni Pizza"));

        // Creating a food delivery service
        DeliveryService<Food> foodDeliveryService = new FoodDeliveryService();

        // Placing and tracking pizza order
        foodDeliveryService.placeOrder(pizzaOrder, customer);
        foodDeliveryService.trackOrder(pizzaOrder);
    }
}
