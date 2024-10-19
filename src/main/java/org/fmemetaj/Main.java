package org.fmemetaj;

import org.fmemetaj.entity.User;
import org.fmemetaj.service.UserService;
import org.fmemetaj.util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Simple CRUD Console Application ===");

        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    readAllUsers();
                    break;
                case 3:
                    readUserById();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option (1-6).");
            }
        }

        HibernateUtil.shutdown();
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nPlease choose an operation:");
        System.out.println("1. Create User");
        System.out.println("2. Read All Users");
        System.out.println("3. Read User by ID");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. Exit");
        System.out.print("Enter your choice (1-6): ");
    }

    private static int getChoice() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
        }
        return choice;
    }

    private static void createUser() {
        System.out.println("\n--- Create User ---");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        userService.createUser(name, email);
    }

    private static void readAllUsers() {
        System.out.println("\n--- Read All Users ---");
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void readUserById() {
        System.out.println("\n--- Read User by ID ---");
        System.out.print("Enter user ID: ");
        Long id = getLongInput();
        if (id == null) return;

        User user = userService.getUserById(id);
        if (user != null) {
            System.out.println("User Details:");
            System.out.println(user);
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }

    private static void updateUser() {
        System.out.println("\n--- Update User ---");
        System.out.print("Enter user ID to update: ");
        Long id = getLongInput();
        if (id == null) return;

        System.out.print("Enter new name (leave blank to keep current): ");
        String newName = scanner.nextLine();

        System.out.print("Enter new email (leave blank to keep current): ");
        String newEmail = scanner.nextLine();

        userService.updateUser(id, newName, newEmail);
    }

    private static void deleteUser() {
        System.out.println("\n--- Delete User ---");
        System.out.print("Enter user ID to delete: ");
        Long id = getLongInput();
        if (id == null) return;

        userService.deleteUser(id);
    }

    private static Long getLongInput() {
        String input = scanner.nextLine().trim();
        Long id = null;
        try {
            id = Long.parseLong(input);
            if (id <= 0) {
                System.out.println("ID must be a positive number.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid numeric ID.");
        }
        return id;
    }
}