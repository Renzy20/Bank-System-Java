import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.*;
import java.io.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Bank{
        public static void main(String[] args) {
            start();
        }

        public static void start() {
            System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("|| 1. Register Account ||");
            System.out.println("|| 2. Login Account    ||");
            System.out.println("|| 3. Exit             ||");
            System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~");
            choice();
        }

         public static void choice() {
        Scanner bank = new Scanner(System.in);
        System.out.print("|Enter choice: ");
        String choice = bank.next();

        if (choice.equals("1")) {
            registerAccount();
            start();
        } else if (choice.equals("2")) {
            logInAccount();
            space();
            welcomeUser();
            bankSystem();
        } else if (choice.equals("3")) {
            exit();
        } else if (choice.matches(".*\\d+.*")) {
            invalidInput();
            space();
            choice();
        } else {
            invalidInput();
            space();
            choice();
        }
    }

    public static void invalidInput() {
        System.out.println("Invalid Input!\nPlease try again!");
    }

    public static void space() {
        System.out.println();
    }

    public static void registerAccount() {
        Scanner reg = new Scanner(System.in);
        String accNumber = "";

        do {
            System.out.print("|Input your account number (max 11 digits): ");
            accNumber = reg.nextLine();

            if (!accNumber.matches("[0-9]+")) {
                System.out.println("Invalid account number format. Please enter numbers only.");
                continue;
            }
            if (accNumber.length() > 11) {
                System.out.println("The account number exceeds the limit!\nPlease try again!");
            } else if (accNumber.length() < 11) {
                System.out.println("The account number does not reach the maximum limit!\nPlease try again!");
            }
        } while (accNumber.length() != 11 || accNumber.matches("[^0-9]"));

        System.out.print("|Create account name (No numbers or Special Characters): ");
        String accName = reg.nextLine();
        while (!accName.matches("[a-zA-Z ]+$")) {
            System.out.println("Invalid account name format. Please enter letters and spaces only.");
            System.out.print("|Create account name (No numbers or Special Characters): ");
            accName = reg.nextLine();
        }

        String accPass = "";
        System.out.print("|Create account password (Contain numbers only): ");
        do {
            accPass = reg.next();
            if (!accPass.matches("[0-9]+")) {
                System.out.println("Invalid password format. Please enter numbers only.");
                System.out.print("|Create account password (Contain numbers only): ");
            }
        } while (!accPass.matches("[0-9]+"));

        try {
            File file = new File("C:\\Users\\Administrator\\Documents\\Java Project\\account.txt1
            ");
            FileWriter writer = new FileWriter(file, true); // fixed file reference
            writer.write(accNumber + "," + accName + "," + accPass + "\n");
            writer.close();
            System.out.println("Account created successfully!");
            System.out.println("You now have free P5,000 for registering!");
            space();
        } catch (IOException e) {
            System.out.println("Error occurred while saving the account details to file.");
            e.printStackTrace();
        }
    }

    public static void logInAccount() {
        Scanner log = new Scanner(System.in);
        String accName, accNumber, accPass, line;
        boolean loggedIn = false;

        System.out.print("|Enter your account number: ");
        accNumber = log.nextLine();
        System.out.print("|Enter your account name: ");
        accName = log.nextLine();
        System.out.print("|Enter your account password: ");
        accPass = log.nextLine();

        try {
            File file = new File("C:\\Users\\Administrator\\Documents\\Java Project\\account.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            if (reader.readLine() == null) {
                System.out.println("No accounts found!");
            } else {
                reader = new BufferedReader(new FileReader(file));
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (accNumber.equals(parts[0]) && accName.equals(parts[1]) && accPass.equals(parts[2])) {
                        System.out.println("Log in Successful!");
                        loggedIn = true;
                        break;
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading from file!");
        }

        if (!loggedIn) {
            System.out.println("Invalid account details!\nPlease try again!");
            space();
            logInAccount();
        }
    }

    public static void exit() {
        Scanner exit = new Scanner(System.in);
        System.out.println("Do you want to exit? (Yes or No)");
        System.out.print("|Enter here: ");
        String choice = exit.next();

        if (choice.equalsIgnoreCase("Yes")) {
            System.out.println("|Exiting program.....");
            System.exit(0);
        } else if (choice.equalsIgnoreCase("No")) {
            choice();
        } else {
            System.out.println("Invalid Input!\nChoose between Yes or No.");
        }
    }

    //----------------Menu-----------------------
    public static void welcomeUser() {
        System.out.println("Welcome to our Bank!");
    }

    public static void bankMenu() {
        space();
        System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("|| 1. View account balance    ||");
        System.out.println("|| 2. Cash in                 ||");
        System.out.println("|| 3. Cash out                ||");
        System.out.println("|| 4. View transaction history||");
        System.out.println("|| 5. Exit                    ||");
        System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.print("|Choose(1-5): ");
    }

    public static void bankSystem() {
        Scanner bs = new Scanner(System.in);
        String accountNumber;
        double balance = 0;
        List<String> transactionHistory = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        // Ask for account number for 2nd verification
        System.out.print("|Enter account number (for 2nd verification): ");
        while (true) {
            if (bs.hasNext("\\d{11}")) {
                accountNumber = bs.next();
                break;
            } else {
                System.out.println("Invalid account number!");
                System.out.print("|Enter account number (for 2nd verification): ");
                bs.nextLine();
            }
        }

        String filename = accountNumber + ".txt";

        // Read account information from file
        try {
            File file = new File(filename);
           
                       if (file.exists()) {
                Scanner scanner = new Scanner(file);
                balance = scanner.nextDouble();
                scanner.nextLine(); // read past the newline character

                while (scanner.hasNextLine()) {
                    transactionHistory.add(scanner.nextLine());
                }
                scanner.close();
            } else {
                balance = 5000.0; // set initial balance to 5000 if file doesn't exist
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        bankMenu();

        while (true) {
            String option;

            if (bs.hasNext()) {
                option = bs.next();

                if (option.matches("[1-5]")) { // check if input is between 1-5
                    switch (option) {
                        case "1": // display account balance
                            System.out.println("|Your balance is P" + balance);
                            space();
                            break;
                        case "2":
                            double cashInAmount;
                            do {
                                System.out.print("|Enter amount to cash in: ");
                                if (bs.hasNextDouble()) {
                                    cashInAmount = bs.nextDouble();
                                    if (cashInAmount > 0) {
                                        balance += cashInAmount;
                                        LocalDateTime now = LocalDateTime.now();
                                        String transaction = String.format("%s: Cash In of %.2f", formatter.format(now),
                                                cashInAmount);
                                        transactionHistory.add(transaction);
                                        System.out.printf("You have successfully cashed in %.2f\n", cashInAmount);
                                        space();
                                        break;
                                    } else {
                                        System.out.println("Invalid Input! Amount must be greater than 0.");
                                    }
                                } else {
                                    bs.next(); // discard invalid input
                                    System.out.println("Invalid Input!");
                                }
                            } while (true);

                            // write account information to file
                            try {
                                PrintWriter writer = new PrintWriter(filename);
                                writer.println(balance);
                                for (String transaction : transactionHistory) {
                                    writer.println(transaction);
                                }
                                writer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "3":
                            double cashOutAmount;
                            do {
                                System.out.print("|Enter amount to cash out: ");
                                if (bs.hasNextDouble()) {
                                    cashOutAmount = bs.nextDouble();
                                    if (cashOutAmount > 0) {
                                        if (cashOutAmount <= balance) {
                                            balance -= cashOutAmount;
                                            LocalDateTime now = LocalDateTime.now();
                                            String transaction = String.format("%s: Cash Out of %.2f",
                                                    formatter.format(now), cashOutAmount);
                                            transactionHistory.add(transaction);
                                            System.out.printf("You have successfully cashed out %.2f\n",
                                                    cashOutAmount);
                                            space();
                                            break;
                                        } else {
                                            System.out.println("Insufficient funds!");
                                        }
                                    } else {
                                        System.out.println("Invalid Input! Amount must be greater than 0.");
                                    }
                                } else {
                                    bs.next(); // discard invalid input
                                    System.out.println("Invalid Input!");
                                }
                            } while (true);

                            // write account information to file
                            try (PrintWriter writer = new PrintWriter(filename)) {
                                writer.println(balance);
                                for (String transaction : transactionHistory) {
                                    writer.println(transaction);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "4": // display transaction history
                            if (transactionHistory.isEmpty()) {
                                System.out.println("|Transaction History is empty.");
                            } else {
                                System.out.println("|Transaction History: ");
                                for (String transaction : transactionHistory) {
                                    System.out.println("• " + transaction);
                                }
                            }
                            space();
                            break;
                        case "5": // exit program
                            System.out.println("|Exiting program...");
                            space();
                            start();
                            break;
                        default:
                            break;
                    }
                    bankMenu();
                } else {
                    System.out.println("Invalid option!\nPlease choose a valid option from (1-5).");
                    space();
                    System.out.print("|Choose(1-5): ");
                    bs.nextLine(); // discard invalid input
                }
            } else {
                break;
            }
        }
    }

}