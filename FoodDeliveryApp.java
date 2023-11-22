
import java.util.*;
abstract class FoodItem {
    protected String name;
    protected double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public abstract void displayItem();

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Pizza extends FoodItem {
    private String size;

    public Pizza(String name, double price, String size) {
        super(name, price);
        this.size = size;
    }

    @Override
    public void displayItem() {
        System.out.println("Pizza: " + name + " (Size: " + size + "), Price: Rs" + price);
    }

    public void customizePizza(String topping) {
        System.out.println("--------------------------------");
        System.out.println("Customizing pizza with " + topping);
        System.out.println("--------------------------------");
    }
}

class Drink extends FoodItem {
    private String type;

    public Drink(String name, double price, String type) {
        super(name, price);
        this.type = type;
    }

    @Override
    public void displayItem() {
        System.out.println("Drink: " + name + " (Type: " + type + "), Price: Rs" + price);
    }

    public void addIce() {
        System.out.println("--------------------------------");
        System.out.println("Adding ice to the drink");
        System.out.println("--------------------------------");
    }
}

final class Dessert extends FoodItem {
    private String flavor;

    public Dessert(String name, double price, String flavor) {
        super(name, price);
        this.flavor = flavor;
    }

    @Override
    public void displayItem() {
        System.out.println("Dessert: " + name + " (Flavor: " + flavor + "), Price: Rs" + price);
    }
}

public class FoodDeliveryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean continueOrdering = true;

        while (continueOrdering) {
            System.out.println("\n\n\t --------------------Welcome to the FoodXpress !!--------------------\n\n");
            System.out.println("Select an option:");
            System.out.println("1. Pizza");
            System.out.println("2. Drink");
            System.out.println("3. Dessert");
            System.out.println("4. Exit");  
            System.out.println("-----------------------------------------------------------------------------");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    customizePizza(scanner);
                    break;
                case 2:
                    customizeDrink(scanner);
                    break;
                case 3:
                    customizeDessert(scanner);
                    break;
                case 4:
                    continueOrdering = false;
                    System.out.println("\n\n\t --------------Thank you for visiting FoodXpress.-------------- \n\t\t --------------Exiting!!!--------------");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        scanner.close();
    }

    private static void customizePizza(Scanner scanner) {
        System.out.print("Enter pizza name: ");
        String pizzaName = scanner.nextLine();

        System.out.print("Enter pizza price: ");
        double pizzaPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter pizza size: ");
        String pizzaSize = scanner.nextLine();

        Pizza pizza = new Pizza(pizzaName, pizzaPrice, pizzaSize);

        System.out.print("Do you want to customize the pizza? (yes/no): ");
        String customizeOption = scanner.nextLine();

        if (customizeOption.equalsIgnoreCase("yes")) {
            System.out.print("Enter topping for customization: ");
            String topping = scanner.nextLine();
            pizza.customizePizza(topping);
        }

        System.out.println("\nCustomized Food Item:");
        pizza.displayItem();
        System.out.println("--------------------------------");
    }

    private static void customizeDrink(Scanner scanner) {
        System.out.print("Enter drink name: ");
        String drinkName = scanner.nextLine();

        System.out.print("Enter drink price: ");
        double drinkPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter drink type: ");
        String drinkType = scanner.nextLine();

        Drink drink = new Drink(drinkName, drinkPrice, drinkType);

        System.out.print("Do you want to add ice to the drink? (yes/no): ");
        String addIceOption = scanner.nextLine();

        if (addIceOption.equalsIgnoreCase("yes")) {
            drink.addIce();
        }

        System.out.println("\nCustomized Food Item:");
        drink.displayItem();
        System.out.println("--------------------------------");
    }

    private static void customizeDessert(Scanner scanner) {
        System.out.print("Enter dessert name: ");
        String dessertName = scanner.nextLine();

        System.out.print("Enter dessert price: ");
        double dessertPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter dessert flavor: ");
        String dessertFlavor = scanner.nextLine();

        Dessert dessert = new Dessert(dessertName, dessertPrice, dessertFlavor);

        System.out.println("\nCustomized Food Item:");
        dessert.displayItem();
        System.out.println("--------------------------------");
    }
}
