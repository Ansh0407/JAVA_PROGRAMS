import java.util.*;

class FoodOrder {
    private int orderID;
    private String customerName;
    private ArrayList<MenuItem> items;
    private double totalPrice;

    public FoodOrder() {
        this.orderID = 0;
        this.customerName = "";
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public FoodOrder(int orderID, String customerName) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.totalPrice = 0.0;
    }

    public void addItem(String itemName, double price) {
        items.add(new MenuItem(itemName, price));
        totalPrice += price;
    }

    public void addItem(String itemName, double price, int quantity) {
        items.add(new MenuItem(itemName, price, quantity));
        totalPrice += price * quantity;
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Items in the order:");
        for (MenuItem item : items) {
            item.displayItem();
        }
        System.out.println("Total Price: Rs: " + totalPrice);
    }
}

class MenuItem {
    private String itemName;
    private double price;
    private int quantity;

    public MenuItem(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = 1;
    }

    public MenuItem(String itemName, double price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public void displayItem() {
        if (quantity == 1) {
            System.out.println(itemName + ": Rs: " + price);
        } else {
            System.out.println(itemName + " (Quantity: " + quantity + "): Rs: " + price);
        }
    }
}
public class LAB1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<FoodOrder> orders = new ArrayList<>(); 

        while (true) {
            System.out.println("\n\n--------------Food Delivery App --------------\n\n");
            System.out.println("1. Create New Order");
            System.out.println("2. Add Item to Order");
            System.out.println("3. Display Orders");
            System.out.println("4. Exit");
            System.out.println("----------------------------------------------------- ");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("----------------------------------------------- ");
                    System.out.print("Enter Order ID: ");
                    int orderID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    FoodOrder newOrder = new FoodOrder(orderID, customerName);
                    orders.add(newOrder); // Add the new order to the list
                    System.out.println("\n\t---------New order created.----------\n");
                    break;

                case 2:
                    if (orders.isEmpty()) {
                        System.out.println("Create an order first.");
                    } else {
                        System.out.println("----------------------------------------------- ");
                        System.out.print("Enter the order number to add an item (1, 2, ...): ");
                        int orderNumber = scanner.nextInt();
                        scanner.nextLine();
                        if (orderNumber <= 0 || orderNumber > orders.size()) {
                            System.out.println("Invalid order number.");
                            break;
                        }
                        FoodOrder currentOrder = orders.get(orderNumber - 1);

                        System.out.print("Enter Item Name: ");
                        String itemName = scanner.next();
                        System.out.print("Enter Item Price Rs: ");
                        double itemPrice = scanner.nextDouble();
                        System.out.print("Enter Item Quantity (1 if not specified): ");
                        int itemQuantity = scanner.nextInt();
                        if (itemQuantity == 1) {
                            currentOrder.addItem(itemName, itemPrice);
                        } else {
                            currentOrder.addItem(itemName, itemPrice, itemQuantity);
                        }
                        System.out.println("\n\t------Item added to the order.------\n");
                    }
                    break;

                    case 3:
                    if (orders.isEmpty()) {
                        System.out.println("\n----------Create an order first.----------\n");
                    } else {
                        System.out.println("\n-------------- Orders --------------");
                        int orderNumber = 1;
                        for (FoodOrder order : orders) {
                            System.out.println("Order " + orderNumber + ":");
                            order.displayOrder();
                            System.out.println("----------------------------");
                            orderNumber++;
                        }
                    }
                    break;
                

                case 4:
                    System.out.println("\n\t-----Exiting!!!!-----\n\n");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n----Invalid choice. Please select a valid option.----\n");
            }
        }
    }
}
