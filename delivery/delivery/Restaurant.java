
package delivery;

import java.util.ArrayList;
import java.util.List;

public class Restaurant implements DeliveryService {
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
