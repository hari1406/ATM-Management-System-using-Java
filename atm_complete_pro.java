package atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Date;

public class atm_complete_pro {
    static final String DB_URL = "jdbc:mysql://localhost:3306/atms";
    static final String USER = "root";
    static final String PASS = "HARI";

    static Connection conn = null;
    static PreparedStatement stmt = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database!");

            while (true) {
                System.out.println("1. Check Balance");
                System.out.println("2. Add Amount");
                System.out.println("3. Take Amount");
                System.out.println("4. Take Receipt");
                System.out.println("5. Create Account");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter your account ID: ");
                        int accountId1 = scanner.nextInt();
                        System.out.print("Enter your password: ");
                        String password1 = scanner.next();
                        if (authenticate(accountId1, password1)) {
                            checkBalance(accountId1);
                        } else {
                            System.out.println("Authentication failed. Invalid account ID or password.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter your account ID: ");
                        int accountId2 = scanner.nextInt();
                        System.out.print("Enter your password: ");
                        String password2 = scanner.next();
                        if (authenticate(accountId2, password2)) {
                            addAmount(accountId2);
                        } else {
                            System.out.println("Authentication failed. Invalid account ID or password.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter your account ID: ");
                        int accountId3 = scanner.nextInt();
                        System.out.print("Enter your password: ");
                        String password3 = scanner.next();
                        if (authenticate(accountId3, password3)) {
                            takeAmount(accountId3);
                        } else {
                            System.out.println("Authentication failed. Invalid account ID or password.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter your account ID: ");
                        int accountId4 = scanner.nextInt();
                        System.out.print("Enter your password: ");
                        String password4 = scanner.next();
                        if (authenticate(accountId4, password4)) {
                            takeReceipt(accountId4);
                        } else {
                            System.out.println("Authentication failed. Invalid account ID or password.");
                        }
                        break;
                    case 5:
                        createAccount();
                        break;
                    case 6:
                        System.out.println("Thank you for using our ATM. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void checkBalance(int accountId) throws SQLException {
        // Implementation to check balance
        System.out.println("Checking Balance...");
        String sql = "SELECT balance FROM accounts WHERE account_id = ?";
        
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("Your current balance: " + balance);
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addAmount(int accountId) throws SQLException {
        // Implementation to add amount
        System.out.println("Adding Amount...");
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        System.out.print("Enter the amount to add: ");
        double amount = scanner.nextDouble();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Amount added successfully!");
            } else {
                System.out.println("Failed to add amount. Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void takeAmount(int accountId) throws SQLException {
        // Implementation to take amount
        System.out.println("Taking Amount...");
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        System.out.print("Enter the amount to take: ");
        double amount = scanner.nextDouble();

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Amount taken successfully!");
            } else {
                System.out.println("Failed to take amount. Account not found or insufficient balance!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void takeReceipt(int accountId) {
        System.out.println("Taking Receipt...");

        try {
            // Query the database to retrieve the amount for the given account ID
            String sql = "SELECT balance FROM accounts WHERE account_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double amount = rs.getDouble("balance");
                // Print receipt details
                System.out.println("===================================");
                System.out.println("           RECEIPT                ");
                System.out.println("===================================");
                System.out.println("Account ID: " + accountId);
                System.out.println("Amount: $" + amount);
                System.out.println("Date: " + new Date());
                System.out.println("Thank you for using our ATM!");
                System.out.println("===================================");
            } else {
                System.out.println("Account not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void createAccount() throws SQLException {
        System.out.println("Creating Account...");
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter your name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();

        String sql = "INSERT INTO accounts (account_holder_name, password, balance) VALUES (?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountHolderName);
            stmt.setString(2, password);
            stmt.setDouble(3, balance);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account created successfully!");
            } else {
                System.out.println("Failed to create account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean authenticate(int accountId, String password) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ? AND password = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
