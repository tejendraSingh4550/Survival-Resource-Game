import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * A small console game for practicing core Java concepts.
 * Compile: javac Main.java
 * Run:     java Main
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            SurvivalGame game = new SurvivalGame(scanner);
            game.start();
        }
    }
}

/** Holds the game loop and coordinates the player's actions. */
class SurvivalGame {
    private final Scanner scanner;
    private final Random random;
    private final ArrayList<Resource> inventory;
    private boolean running;

    SurvivalGame(Scanner scanner) {
        this.scanner = scanner;
        this.random = new Random();
        this.inventory = new ArrayList<>();
        this.running = true;
        addResource("Water", 3);
        addResource("Food", 2);
    }

    void start() {
        System.out.println("====================================");
        System.out.println("      SURVIVAL RESOURCE MANAGER");
        System.out.println("====================================");

        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    showInventory();
                    break;
                case 2:
                    collectResource();
                    break;
                case 3:
                    useResource();
                    break;
                case 4:
                    addResourceFromInput();
                    break;
                case 5:
                    showHelp();
                    break;
                case 0:
                    running = false;
                    System.out.println("Good luck out there. Stay alive!");
                    break;
                default:
                    System.out.println("Invalid option. Choose a number from 0 to 5.");
            }
            System.out.println();
        }
    }

    private void showMenu() {
        System.out.println("\n--- Camp Menu ---");
        System.out.println("1. View inventory");
        System.out.println("2. Scavenge for a random resource");
        System.out.println("3. Use a resource");
        System.out.println("4. Add a resource manually");
        System.out.println("5. How to play");
        System.out.println("0. Exit");
    }

    private void showInventory() {
        System.out.println("\nYour inventory:");
        if (inventory.isEmpty()) {
            System.out.println("  It's empty. Try scavenging!");
            return;
        }

        for (int i = 0; i < inventory.size(); i++) {
            Resource resource = inventory.get(i);
            System.out.println("  " + (i + 1) + ". " + resource.getName()
                    + " — " + resource.getQuantity());
        }
    }

    private void collectResource() {
        String[] possibleResources = {"Water", "Food", "Wood", "Medicine", "Cloth"};
        String foundName = possibleResources[random.nextInt(possibleResources.length)];
        int amount = random.nextInt(3) + 1;
        addResource(foundName, amount);
        System.out.println("You scavenged " + amount + " " + foundName + "!");
    }

    private void useResource() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty. Find some resources first.");
            return;
        }

        showInventory();
        int index = readInt("Enter the item number to use (0 to cancel): ");
        if (index == 0) {
            System.out.println("Cancelled.");
            return;
        }
        if (index < 1 || index > inventory.size()) {
            System.out.println("That item number isn't in your inventory.");
            return;
        }

        Resource resource = inventory.get(index - 1);
        int amount = readInt("How many " + resource.getName() + " do you want to use? ");
        try {
            resource.use(amount);
            System.out.println("Used " + amount + " " + resource.getName() + ".");
            if (resource.getQuantity() == 0) {
                inventory.remove(resource);
                System.out.println(resource.getName() + " has run out and was removed.");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Couldn't use resource: " + exception.getMessage());
        }
    }

    private void addResourceFromInput() {
        System.out.print("Resource name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        int amount = readInt("Quantity to add: ");
        try {
            addResource(name, amount);
            System.out.println("Added " + amount + " " + name + ".");
        } catch (IllegalArgumentException exception) {
            System.out.println("Couldn't add resource: " + exception.getMessage());
        }
    }

    private void addResource(String name, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero.");
        }

        for (Resource resource : inventory) {
            if (resource.getName().equalsIgnoreCase(name)) {
                resource.add(amount);
                return;
            }
        }
        inventory.add(new Resource(name, amount));
    }

    /** Reads a whole number and recovers cleanly from invalid input. */
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private void showHelp() {
        System.out.println("Scavenge to collect supplies, check your inventory, and use supplies when needed.");
        System.out.println("You can also add sample supplies manually to try the inventory features.");
    }
}

/** A resource stack. Private fields demonstrate encapsulation. */
class Resource {
    private final String name;
    private int quantity;

    Resource(String name, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero.");
        }
        this.name = name.trim();
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void add(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("quantity must be greater than zero.");
        }
        quantity += amount;
    }

    public void use(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("enter an amount greater than zero.");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("you only have " + quantity + ".");
        }
        quantity -= amount;
    }
}
