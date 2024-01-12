import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class FoodItem {
    private int id;
    private String name;
    private float price;

    public FoodItem() {
    }

    public FoodItem(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

class Customer {
    private int id;
    private String name;
    private int age;

    public Customer() {
    }

    public Customer(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class Lab10 extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/university";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Ansh@2002";

    private JTextArea outputArea;

    public Lab10() {
        outputArea = new JTextArea(20, 50);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JButton addButton = new JButton("Add Customer & FoodItem");

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performJdbcOperations();
            }
        });

        setTitle("Lab10 - JDBC Operations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performJdbcOperations() {
        initializeDatabase();
        addFoodItemWithUserInput();
        addCustomerWithUserInput();
        displayFoodItems();
        displayCustomers();
    }

    private void initializeDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {

                String createFoodItemsTable = "CREATE TABLE IF NOT EXISTS food_items (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(255) NOT NULL," +
                        "price FLOAT NOT NULL)";
                statement.executeUpdate(createFoodItemsTable);

                String createCustomersTable = "CREATE TABLE IF NOT EXISTS customers (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(255) NOT NULL," +
                        "age INT NOT NULL)";
                statement.executeUpdate(createCustomersTable);

            } catch (SQLException e) {
                e.printStackTrace();
                appendToOutput("Error initializing database: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            appendToOutput("Error loading MySQL JDBC driver: " + e.getMessage());
        }
    }

    private void addFoodItemWithUserInput() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Food Item Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Food Item Price:"));
        panel.add(priceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Food Item",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String foodName = nameField.getText();
                float foodPrice = Float.parseFloat(priceField.getText());

                createFoodItem(foodName, foodPrice);
                updateMenuList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers for price.");
            }
        }
    }

    private void addCustomerWithUserInput() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Customer Age:"));
        panel.add(ageField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String customerName = nameField.getText();
                int customerAge = Integer.parseInt(ageField.getText());

                createCustomer(customerName, customerAge);
                displayCustomers();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number for age.");
            }
        }
    }

    private void createFoodItem(String foodName, float foodPrice) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO food_items (name, price) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, foodName);
            preparedStatement.setFloat(2, foodPrice);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            appendToOutput("Error inserting food item: " + e.getMessage());
        }
    }

    private void createCustomer(String customerName, int customerAge) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers (name, age) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, customerName);
            preparedStatement.setInt(2, customerAge);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            appendToOutput("Error inserting customer: " + e.getMessage());
        }
    }

    private void displayFoodItems() {
        List<FoodItem> foodItems = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM food_items")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");

                FoodItem foodItem = new FoodItem(id, name, price);
                foodItems.add(foodItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            appendToOutput("Error retrieving food items: " + e.getMessage());
        }

        appendToOutput("Food Items: " + foodItems);
    }

    private void displayCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM customers")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                Customer customer = new Customer(id, name, age);
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            appendToOutput("Error retrieving customers: " + e.getMessage());
        }

        appendToOutput("Customers: " + customers);
    }

    private void updateMenuList() {
       
    }

    private void appendToOutput(String message) {
        outputArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Lab10();
            }
        });
    }
}
