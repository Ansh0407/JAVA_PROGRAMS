import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MenuItem<T> {
    private T item;

    public MenuItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

abstract class User {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    public abstract void displayDetails();
}

class Customer extends User {
    public Customer(String name) {
        super(name);
    }

    @Override
    public void displayDetails() {
        System.out.println("Customer: " + name);
    }

    public void placeOrder(String orderDetails) {
        System.out.println(name + " placed an order: " + orderDetails);
    }

    public void placeOrder(String foodItem, int quantity) {
        System.out.println(name + " placed an order: " + quantity + " " + foodItem + "(s)");
    }
}

interface RestaurantFilter {
    boolean test(Restaurant restaurant);
}

class Restaurant {
    private String name;
    private int rating;

    public Restaurant(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}

interface Sortable<T> {
    void sort(List<T> items);
}

class RestaurantSorter implements Sortable<Restaurant> {
    @Override
    public void sort(List<Restaurant> items) {
        Collections.sort(items, (r1, r2) -> Integer.compare(r2.getRating(), r1.getRating()));
    }
}

class OrderProcessor implements Runnable {
    private String customerName;
    private String orderDetails;

    public OrderProcessor(String customerName, String orderDetails) {
        this.customerName = customerName;
        this.orderDetails = orderDetails;
    }

    @Override
    public void run() {
        System.out.println("Processing order from " + customerName + ": " + orderDetails);
    }
}

public class LAB7 {

    public static void main(String[] args) {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Restaurant A", 4));
        restaurants.add(new Restaurant("Restaurant B", 3));
        restaurants.add(new Restaurant("Restaurant C", 5));

        Customer customer = new Customer("John Doe");
        customer.displayDetails();

        customer.placeOrder("Pizza");
        customer.placeOrder("Burger", 2);

        Sortable<Restaurant> restaurantSorter = new RestaurantSorter();
        restaurantSorter.sort(restaurants);

        System.out.println("\nAfter sorting by rating (descending order):");
        printRestaurants(restaurants);

        int ratingThreshold = 4;
        List<Restaurant> highRatedRestaurants = filterRestaurants(restaurants, r -> r.getRating() >= ratingThreshold);

        System.out.println("\nHigh-rated restaurants (rating >= " + ratingThreshold + "):");
        printRestaurants(highRatedRestaurants);

        MenuItem<String> menuItemString = new MenuItem<>("Soda");
        System.out.println("\nMenuItem (String): " + menuItemString.getItem());

        MenuItem<Integer> menuItemInteger = new MenuItem<>(10);
        System.out.println("MenuItem (Integer): " + menuItemInteger.getItem());

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new OrderProcessor("Alice", "Pasta"));
        executorService.submit(new OrderProcessor("Bob", "Salad"));

        executorService.shutdown();
    }

    private static void printRestaurants(List<Restaurant> restaurants) {
        restaurants.forEach(System.out::println);
    }

    private static List<Restaurant> filterRestaurants(List<Restaurant> restaurants, RestaurantFilter filter) {
        List<Restaurant> filteredRestaurants = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            if (filter.test(restaurant)) {
                filteredRestaurants.add(restaurant);
            }
        }
        return filteredRestaurants;
    }
}
