import java.util.ArrayList;
import java.util.Scanner;
public class ATM_Interface
{
    public static void main(String[] args)
    {
        ATM atm = new ATM();
        atm.start();
    }
}
    class Transaction
    {
        private String type;
        private double amount;

        public Transaction(String type, double amount) {
            this.type = type;
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }
    }

    class User {
        private String userId;
        private String pin;
        private double balance;
        private ArrayList<Transaction> transactionHistory;

        public User(String userId, String pin) {
            this.userId = userId;
            this.pin = pin;
            this.balance = 0.0;
            this.transactionHistory = new ArrayList<>();
        }

        public String getUserId() {
            return userId;
        }

        public boolean authenticate(String enteredPin) {
            return pin.equals(enteredPin);
        }

        public double getBalance()
        {
            return balance;
        }

        public void deposit(double amount)
        {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
        }

        public void withdraw(double amount)
        {
            if (balance >= amount)
            {
                balance -= amount;
                transactionHistory.add(new Transaction("Withdrawal", amount));
            } else
            {
                System.out.println("Insufficient funds!");
            }
        }

        public void transfer(User recipient, double amount)
        {
            if (balance >= amount)
            {
                balance -= amount;
                recipient.deposit(amount);
                transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
            } else
            {
                System.out.println("Insufficient funds!");
            }
        }

        public ArrayList<Transaction> getTransactionHistory()
        {
            return transactionHistory;
        }
    }

    class ATM
    {
        private User currentUser;

        public void start()
        {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            // Authenticate user
            User user = authenticateUser(userId, pin);

            if (user != null)
            {
                currentUser = user;
                showMainMenu();
            } else
            {
                System.out.println("Invalid User ID or PIN. Exiting...");
            }

            scanner.close();
        }

        private User authenticateUser(String userId, String pin)
        {
            // This is a dummy implementation. In a real system, you would check against a database of users.
            if ("12345".equals(userId) && "1234".equals(pin))
            {
                return new User("12345", "1234");
            } else
            {
                return null;
            }
        }

        private void showMainMenu()
        {
            Scanner scanner = new Scanner(System.in);

            while (true)
            {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        displayTransactionHistory();
                        break;
                    case 2:
                        performWithdrawal();
                        break;
                    case 3:
                        performDeposit();
                        break;
                    case 4:
                        performTransfer();
                        break;
                    case 5:
                        System.out.println("Exiting... Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        private void displayTransactionHistory()
        {
            System.out.println("\nTransaction History:");
            for (Transaction transaction : currentUser.getTransactionHistory())
            {
                System.out.println(transaction.getType() + ": " + transaction.getAmount());
            }
        }

        private void performWithdrawal()
        {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter withdrawal amount: ");
            double amount = scanner.nextDouble();

            currentUser.withdraw(amount);
        }

        private void performDeposit()
        {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter deposit amount: ");
            double amount = scanner.nextDouble();

            currentUser.deposit(amount);
        }

        private void performTransfer()
        {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter recipient's User ID: ");
            String recipientId = scanner.nextLine();

            // Dummy implementation for finding the recipient user
            User recipient = new User("67890", "5678");

            if (recipient != null)
            {
                System.out.print("Enter transfer amount: ");
                double amount = scanner.nextDouble();

                currentUser.transfer(recipient, amount);
            } else
            {
                System.out.println("Recipient not found.");
            }
        }
    }
