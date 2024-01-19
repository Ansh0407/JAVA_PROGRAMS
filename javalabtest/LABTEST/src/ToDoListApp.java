import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Task {
    int taskId;
    String taskDescription;
    boolean completed;

    Task(int taskId, String taskDescription, boolean completed) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.completed = completed;
    }
}

class ToDoListSystem {
    private List<Task> taskList;
    private Connection connection;
    private Statement statement;

    ToDoListSystem() {
        taskList = new ArrayList<>();
        initializeDatabase();
    }

    private void initializeDatabase() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/ToDoList";
        String user = "root";
        String password = "Ansh@2002";

        try {
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            statement = connection.createStatement();

            String createTaskTable = "CREATE TABLE IF NOT EXISTS Tasks (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "description TEXT NOT NULL," +
                    "completed BOOLEAN NOT NULL)";
            statement.executeUpdate(createTaskTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTask(String taskDescription) {
        Task newTask = new Task(0, taskDescription, false);
        taskList.add(newTask);

        try {
            String insertQuery = String.format("INSERT INTO Tasks (description, completed) VALUES ('%s', %b)",
                    newTask.taskDescription, newTask.completed);
            statement.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                newTask.taskId = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public void populateTaskList() {
        taskList.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Tasks");
            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String taskDescription = resultSet.getString("description");
                boolean completed = resultSet.getBoolean("completed");
                taskList.add(new Task(taskId, taskDescription, completed));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markTaskAsCompleted(int taskId) {
        for (Task task : taskList) {
            if (task.taskId == taskId) {
                task.completed = true;
                break;
            }
        }

        try {
            String updateQuery = String.format("UPDATE Tasks SET completed = true WHERE id = %d", taskId);
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeTask(int taskId) {
        taskList.removeIf(task -> task.taskId == taskId);

        try {
            String deleteQuery = String.format("DELETE FROM Tasks WHERE id = %d", taskId);
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTask(int taskId, String newTaskDescription) {
        for (Task task : taskList) {
            if (task.taskId == taskId) {
                task.taskDescription = newTaskDescription;
                break;
            }
        }

        try {
            String updateQuery = String.format("UPDATE Tasks SET description = '%s' WHERE id = %d",
                    newTaskDescription, taskId);
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearTasksFromDatabase() {
        try {
            String clearQuery = "DELETE FROM Tasks";
            statement.executeUpdate(clearQuery);

            // Clear the task list since there are no tasks in the database
            taskList.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDatabase() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class ToDoListApp extends JFrame {
    private ToDoListSystem toDoListSystem;
    private JTextArea outputArea;

    public ToDoListApp() {
        toDoListSystem = new ToDoListSystem();
        outputArea = new JTextArea(20, 50);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        JButton addTaskButton = new JButton("Add Task");
        JButton displayTasksButton = new JButton("Display Tasks");
        JButton markAsCompletedButton = new JButton("Mark Task as Completed");
        JButton removeTaskButton = new JButton("Remove Task");
        JButton updateTaskButton = new JButton("Update Task");
        JButton clearTasksButton = new JButton("Clear Tasks");
        JButton exitButton = new JButton("Exit");

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(addTaskButton);
        buttonPanel.add(displayTasksButton);
        buttonPanel.add(markAsCompletedButton);
        buttonPanel.add(removeTaskButton);
        buttonPanel.add(updateTaskButton);
        buttonPanel.add(clearTasksButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        displayTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTasks();
            }
        });

        markAsCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markTaskAsCompleted();
            }
        });

        removeTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        updateTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTask();
            }
        });

        clearTasksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTasks();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDoListSystem.closeDatabase();
                System.exit(0);
            }
        });

        setTitle("ToDo List App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addTask() {
        String taskDescription = JOptionPane.showInputDialog("Enter Task Description:");
        toDoListSystem.addTask(taskDescription);
        updateOutput("Task added: " + taskDescription);
    }

    private void displayTasks() {
        toDoListSystem.populateTaskList();
        List<Task> tasks = toDoListSystem.getTasks();
        if (tasks.isEmpty()) {
            updateOutput("Task list is empty.");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Task task : tasks) {
                stringBuilder.append("Task ID: ").append(task.taskId)
                        .append(", Description: ").append(task.taskDescription)
                        .append(", Completed: ").append(task.completed)
                        .append("\n");
            }
            updateOutput(stringBuilder.toString());
        }
    }

    private void markTaskAsCompleted() {
        int taskId = Integer.parseInt(JOptionPane.showInputDialog("Enter Task ID to mark as completed:"));
        toDoListSystem.markTaskAsCompleted(taskId);
        updateOutput("Task marked as completed: Task ID " + taskId);
    }

    private void removeTask() {
        int taskId = Integer.parseInt(JOptionPane.showInputDialog("Enter Task ID to remove:"));
        toDoListSystem.removeTask(taskId);
        updateOutput("Task removed: Task ID " + taskId);
    }

    private void updateTask() {
        int taskId = Integer.parseInt(JOptionPane.showInputDialog("Enter Task ID to update:"));
        String newTaskDescription = JOptionPane.showInputDialog("Enter New Task Description:");

        toDoListSystem.updateTask(taskId, newTaskDescription);
        updateOutput("Task updated: Task ID " + taskId);
    }

    private void clearTasks() {
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to clear all tasks from the database?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            toDoListSystem.clearTasksFromDatabase();
            updateOutput("All tasks cleared from the database.");
        }
    }

    private void updateOutput(String message) {
        outputArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoListApp();
            }
        });
    }
}
