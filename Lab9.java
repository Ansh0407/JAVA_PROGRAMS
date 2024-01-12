import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class FoodItem {
    String name;
    float price;
    int quantity;

    FoodItem(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

class Customer {
    String name;
    int age;

    Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class Lab9 extends JFrame {
    private List<FoodItem> menu;
    private List<Customer> customers;

    private JTextArea outputArea;
    private DefaultListModel<String> menuListModel;

    
    public Lab9() {
        menu = new ArrayList<>();
        customers = new ArrayList<>();

        outputArea = new JTextArea(20, 50);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        menuListModel = new DefaultListModel<>();
        JList<String> menuList = new JList<>(menuListModel);
        JButton addButton = new JButton("Add Food Item to Menu");

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(new JLabel("Food Item Menu"), BorderLayout.NORTH);
        menuPanel.add(new JScrollPane(menuList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);

        add(menuPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddFoodItemDialog();
            }
        });

        setTitle("Food Delivery App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void showAddFoodItemDialog() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Food Item Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Food Item Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Food Item Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Food Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String foodName = nameField.getText();
                float foodPrice = Float.parseFloat(priceField.getText());
                int foodQuantity = Integer.parseInt(quantityField.getText());

                createFoodItem(foodName, foodPrice, foodQuantity);
                updateMenuList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers for price and quantity.");
            }
        }
    }

    private void createFoodItem(String foodName, float foodPrice, int foodQuantity) {
        FoodItem newFoodItem = new FoodItem(foodName, foodPrice, foodQuantity);
        menu.add(newFoodItem);

        updateOutput("Food item added to the menu: " + newFoodItem.name);
    }

    private void updateMenuList() {
        menuListModel.clear();
        for (FoodItem foodItem : menu) {
            menuListModel.addElement(String.format("%s | Rs. %.2f | Quantity: %d", foodItem.name, foodItem.price, foodItem.quantity));
        }
    }

    private void updateOutput(String message) {
        outputArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lab9();
            }
        });
    }
}
