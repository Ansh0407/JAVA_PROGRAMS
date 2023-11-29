//javac delivery/*.java main/*.java
//java main.LAB4

package main;

import delivery.Customer;
import delivery.Restaurant; 

import java.util.Scanner;

public class LAB4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        delivery.Restaurant restaurant = new delivery.Restaurant("Delicious Bites");
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
        delivery.Customer customer = new delivery.Customer(customerName);

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
