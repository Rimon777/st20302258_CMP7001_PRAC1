package com.baymotors;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class BayMotorsUI extends Application {
    private SystemManager systemManager;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        systemManager = SystemManager.getInstance();
        this.primaryStage = primaryStage;

        showLoginScreen();
    }

    private void showLoginScreen() {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));

        Label label = new Label("Enter your name to login:");
        TextField nameField = new TextField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (systemManager.login(name)) {
                showMainMenu();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid name. Please try again.");
            }
        });

        loginLayout.getChildren().addAll(label, nameField, loginButton);
        Scene loginScene = new Scene(loginLayout, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Bay Motors - Login");
        primaryStage.show();
    }

    private void showMainMenu() {
        VBox menuLayout = new VBox(10);
        menuLayout.setPadding(new Insets(20));

        Label welcomeLabel = new Label("Welcome, " + systemManager.getLoggedInUser());
        Button logoutButton = new Button("Logout");

        logoutButton.setOnAction(e -> {
            systemManager.logout_();
            showLoginScreen();
        });

        if ("Manager".equals(systemManager.getLoggedInRole())) {
            // Manager-specific options
            Button addCustomerButton = new Button("Log Customer & Vehicle Details");
            Button upgradeCustomerButton = new Button("Upgrade Customer to Registered");
            Button allocateTaskButton = new Button("Allocate Task to Mechanic");
            Button viewTasksButton = new Button("View All Tasks");
            Button postNotificationsButton = new Button("Post Notifications");
            Button manageManufacturersButton = new Button("Manage Manufacturers & Suppliers");

            addCustomerButton.setOnAction(e -> showAddCustomerScreen());
            upgradeCustomerButton.setOnAction(e -> showUpgradeCustomerScreen());
            allocateTaskButton.setOnAction(e -> showAllocateTaskScreen());
            viewTasksButton.setOnAction(e -> showViewTasksScreen());
            postNotificationsButton.setOnAction(e -> showPostNotificationsScreen());
            manageManufacturersButton.setOnAction(e -> showManageManufacturersScreen());

            menuLayout.getChildren().addAll(welcomeLabel, addCustomerButton, upgradeCustomerButton,
                    allocateTaskButton, viewTasksButton, postNotificationsButton, manageManufacturersButton, logoutButton);
        } else if ("Mechanic".equals(systemManager.getLoggedInRole())) {
            // Mechanic-specific options
            Button addCustomerButton = new Button("Add New Customer & Vehicle");
            Button completeTaskButton = new Button("Mark Task as Complete");
            Button viewMyTasksButton = new Button("View My Tasks");
            Button upgradeCustomerButton = new Button("Upgrade Customer to Registered");

            addCustomerButton.setOnAction(e -> showAddCustomerScreen());
            completeTaskButton.setOnAction(e -> showCompleteTaskScreen());
            viewMyTasksButton.setOnAction(e -> showViewMyTasksScreen());
            upgradeCustomerButton.setOnAction(e -> showUpgradeCustomerScreen());

            menuLayout.getChildren().addAll(welcomeLabel, addCustomerButton, completeTaskButton,
                    viewMyTasksButton, upgradeCustomerButton, logoutButton);
        }

        Scene menuScene = new Scene(menuLayout, 400, 400);
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Bay Motors - Main Menu");
    }

    private Object showViewMyTasksScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object showManageManufacturersScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object showPostNotificationsScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	private void showAddCustomerScreen() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextField nameField = new TextField();
        nameField.setPromptText("Customer Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Customer Email");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Customer Phone");

        TextField regField = new TextField();
        regField.setPromptText("Vehicle Registration Number");

        TextField makeField = new TextField();
        makeField.setPromptText("Vehicle Make");

        TextField modelField = new TextField();
        modelField.setPromptText("Vehicle Model");

        Button submitButton = new Button("Add Customer");
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String reg = regField.getText();
            String make = makeField.getText();
            String model = modelField.getText();

            systemManager.addWalkInCustomer(name, email, phone, reg, make, model);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer and Vehicle added successfully.");
            showMainMenu();
        });

        layout.getChildren().addAll(new Label("Add Customer & Vehicle"), nameField, emailField, phoneField,
                regField, makeField, modelField, submitButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showUpgradeCustomerScreen() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextField customerIdField = new TextField();
        customerIdField.setPromptText("Customer ID");

        Button upgradeButton = new Button("Upgrade");
        upgradeButton.setOnAction(e -> {
            int customerId = Integer.parseInt(customerIdField.getText());
            systemManager.upgradeCustomer(customerId);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer upgraded successfully.");
            showMainMenu();
        });

        layout.getChildren().addAll(new Label("Upgrade Customer to Registered"), customerIdField, upgradeButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
    }

    private void showAllocateTaskScreen() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        TextField mechanicIdField = new TextField();
        mechanicIdField.setPromptText("Mechanic ID");

        TextField regField = new TextField();
        regField.setPromptText("Vehicle Registration Number");

        TextField taskDescriptionField = new TextField();
        taskDescriptionField.setPromptText("Task Description");

        TextField priorityField = new TextField();
        priorityField.setPromptText("Priority");

        Button allocateButton = new Button("Allocate Task");
        allocateButton.setOnAction(e -> {
            int mechanicId = Integer.parseInt(mechanicIdField.getText());
            String reg = regField.getText();
            String description = taskDescriptionField.getText();
            int priority = Integer.parseInt(priorityField.getText());

            systemManager.allocateTask(mechanicId, reg, description, priority);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Task allocated successfully.");
            showMainMenu();
        });

        layout.getChildren().addAll(new Label("Allocate Task to Mechanic"), mechanicIdField, regField,
                taskDescriptionField, priorityField, allocateButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showViewTasksScreen() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        // Get all tasks and convert them into a string representation
        List<Task> allTasks = systemManager.getAllTasks();
        StringBuilder tasksDisplay = new StringBuilder();
        for (Task task : allTasks) {
            tasksDisplay.append(task.toString()).append("\n");  // Assuming Task has a suitable toString() method
        }

        // Create a Label to display the tasks as a string
        Label taskList = new Label(tasksDisplay.toString());
        
        // Add a back button to return to the main menu
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainMenu());

        layout.getChildren().addAll(new Label("All Tasks"), taskList, backButton);

        // Create the scene and set it
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showCompleteTaskScreen() {
    	VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label instructionLabel = new Label("Enter Task ID to Mark as Complete:");
        TextField taskIdField = new TextField();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            try {
                int taskId = Integer.parseInt(taskIdField.getText()); // Parse the task ID from the text field
                systemManager.markTaskAsComplete(taskId); // Call the method with the task ID
                showMainMenu(); // Go back to the main menu
            } catch (NumberFormatException ex) {
                showErrorMessage("Please enter a valid task ID.");
            } catch (NotFoundException ex) {
                showErrorMessage(ex.getMessage());
            }
        });

        backButton.setOnAction(e -> showMainMenu());

        layout.getChildren().addAll(instructionLabel, taskIdField, submitButton, backButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
