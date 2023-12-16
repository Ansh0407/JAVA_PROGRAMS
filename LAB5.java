import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FoodRestaurant {
    private String name;
    private List<String> menu;

    public FoodRestaurant(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
        initializeMenu();
    }

    private void initializeMenu() {
        menu.add("Pizza");
        menu.add("Burger");
        menu.add("Pasta");
        menu.add("Fries");
        menu.add("Spaghetti");
        menu.add("Sushi");
    }

    public void displayMenu() {
        System.out.println("-------------------------------------------");
        System.out.println("Menu at " + name + ":");
        for (String item : menu) {
            System.out.println("\t - " + item);
        }
        System.out.println("-------------------------------------------");
    }

    public void processOrder(String item, String deliveryAddress) {
        System.out.println(name + " is processing an order for " + item);
        prepareFood(item);
        deliverFood(deliveryAddress);
        System.out.println("Order delivered to " + name + " at " + deliveryAddress);
    }

    private void prepareFood(String item) {
        System.out.println(name + " is preparing " + item);
    }

    private void deliverFood(String address) {
        System.out.println(name + " is delivering food to " + address);
    }
}

class FoodDeliveryService implements Runnable {
    private FoodRestaurant restaurant;
    private String item;
    private String deliveryAddress;

    public FoodDeliveryService(FoodRestaurant restaurant, String item, String deliveryAddress) {
        this.restaurant = restaurant;
        this.item = item;
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public void run() {
        restaurant.processOrder(item, deliveryAddress);
    }
}

class FoodCustomer {
    private String name;

    public FoodCustomer(String name) {
        this.name = name;
    }

    public void displayMenu(FoodRestaurant restaurant) {
        restaurant.displayMenu();
    }

    public void placeOrder(FoodRestaurant restaurant, String item, String deliveryAddress) {
        System.out.println(name + " is placing an order for " + item);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new FoodDeliveryService(restaurant, item, deliveryAddress));
        executor.shutdown();
    }
}

public class LAB5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        FoodRestaurant restaurant = new FoodRestaurant("Delicious Bites");
        FoodCustomer customer = new FoodCustomer("ANsh");

        int choice;
        do {
            System.out.println("\n\t --------------Delicious Bites Menu --------------\n");
            System.out.println("1. Display Menu");
            System.out.println("2. Place an Order");
            System.out.println("3. Exit");
            System.out.println("------------------------------------------------------");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    customer.displayMenu(restaurant);
                    break;
                case 2:
                    System.out.print("Enter the item to order: ");
                    String orderedItem = scanner.nextLine();
                    System.out.print("Enter delivery address: ");
                    String deliveryAddress = scanner.nextLine();
                    customer.placeOrder(restaurant, orderedItem, deliveryAddress);
                      System.out.println("------------------------------------------------------------");
                    break;
                case 3:
                    System.out.println("\t\n---------------Exiting !!---------------\n");
                    System.out.println("\n-----------Visit us Again :))----------- ");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 3);

        scanner.close();
    }
}
