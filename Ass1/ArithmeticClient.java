import java.rmi.Naming;
import java.util.Scanner;

public class ArithmeticClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote ArithmeticService from the RMI registry
            ArithmeticService arithmeticService = (ArithmeticService) Naming.lookup("rmi://10.255.255.254/ArithmeticService");

            // Create a scanner to take user input
            Scanner scanner = new Scanner(System.in);

            // Ask for two numbers to perform arithmetic operations
            System.out.print("Enter the first number: ");
            double num1 = scanner.nextDouble();

            System.out.print("Enter the second number: ");
            double num2 = scanner.nextDouble();

            // Ask for the operation to perform
            System.out.println("\nChoose an operation:");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();

            // Perform the chosen operation
            double result = 0.0;
            switch (choice) {
                case 1:
                    result = arithmeticService.add(num1, num2);
                    System.out.println("Result: " + result);
                    break;
                case 2:
                    result = arithmeticService.subtract(num1, num2);
                    System.out.println("Result: " + result);
                    break;
                case 3:
                    result = arithmeticService.multiply(num1, num2);
                    System.out.println("Result: " + result);
                    break;
                case 4:
                    try {
                        result = arithmeticService.divide(num1, num2);
                        System.out.println("Result: " + result);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number between 1 and 4.");
            }

            // Close the scanner to prevent resource leak
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

