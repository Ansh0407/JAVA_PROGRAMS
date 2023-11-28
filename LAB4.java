import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface DeliveryService {
    void deliverFood(String address);
}
class Restaurant implements DeliveryService {
    private String name;
    private List<String> menu;

    public Restaurant(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
    }

    public void addToMenu(String item) {
        menu.add(item);
    }

    public void displayMenu() {
        System.out.println("-------------------------------------------");
        System.out.println("Menu at " + name + ":");
        for (String item : menu) {
            System.out.println("\t - " + item);
        }
        System.out.println("-------------------------------------------");
    }

    public void prepareFood(String item) {
        System.out.println(name + " is preparing " + item);
    }

    @Override
    public void deliverFood(String address) {
        System.out.println(name + " is delivering food to " + address);
    }
}

class FoodDeliveryDriver implements DeliveryService {
    private String name;

    public FoodDeliveryDriver(String name) {
        this.name = name;
    }

    @Override
    public void deliverFood(String address) {
        System.out.println(name + " is delivering food to " + address);
    }
}
class Customer {
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
public class LAB4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Restaurant restaurant = new Restaurant("Delicious Bites");
        restaurant.addToMenu("Pizza");
        restaurant.addToMenu("Burger");
        restaurant.addToMenu("Pasta");
        restaurant.addToMenu("Fries");
        restaurant.addToMenu("Spaghetti");
        restaurant.addToMenu("Sushi");
  
        restaurant.displayMenu();
        System.out.println("Welcome !!!!");
        System.out.println("Enter your name to proceed: ");
        String customerName = scanner.nextLine();
        Customer customer = new Customer(customerName);

        int choice;
        do {
            System.out.println("\n\t --------------Delicious Bites  --------------\n");
            System.out.println("1. Display Menu");
            System.out.println("2. Place an Order");
            System.out.println("3. Exit");
            System.out.println("------------------------------------------------------");
            System.out.print(" Enter your choice : ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    restaurant.displayMenu();
                    break;
                case 2:
                    System.out.print("Enter the item to order: ");
                    String orderedItem = scanner.nextLine();
                    System.out.print("Enter delivery address: ");
                    String deliveryAddress = scanner.nextLine();
                    customer.placeOrder(restaurant, orderedItem, deliveryAddress);
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
