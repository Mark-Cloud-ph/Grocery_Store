import java.util.*;

class GroceryItem {
    private String name;
    private int quantity;
    private double price;

    public GroceryItem(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%-20s %5d %10.2f", name, quantity, price);
    }
}

public class GroceryConsoleApp {
    private static List<GroceryItem> inventory = new ArrayList<>();
    private static Map<String, GroceryItem> codeToItem = new HashMap<>(); // code to item mapping
    private static Scanner scanner = new Scanner(System.in);
    private static double scannedTotal = 0.0;
    private static int scannedCount = 0;

    public static void main(String[] args) {
        // Predefined barcode/code list
        addPredefinedItems();
        while (true) {
            System.out.println("\nGrocery Inventory Console");
            System.out.println("1. Add Item");
            System.out.println("2. List Items");
            System.out.println("3. Update Item");
            System.out.println("4. Remove Item");
            System.out.println("5. Scan/Enter Code");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addItem();
                    break;
                case "2":
                    listItems();
                    break;
                case "3":
                    updateItem();
                    break;
                case "4":
                    removeItem();
                    break;
                case "5":
                    scanCode();
                    break;
                case "6":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // Add some predefined barcodes and items
    private static void addPredefinedItems() {
        addPredefinedItem("123456", "Apple", 10, 0.99);
        addPredefinedItem("234567", "Banana", 20, 0.59);
        addPredefinedItem("345678", "Milk", 15, 2.49);
        addPredefinedItem("456789", "Bread", 12, 1.99);
        addPredefinedItem("567890", "Eggs", 30, 3.49);
    }

    private static void addPredefinedItem(String code, String name, int quantity, double price) {
        GroceryItem item = new GroceryItem(name, quantity, price);
        inventory.add(item);
        codeToItem.put(code, item);
    }
    // ...existing code...

    private static void addItem() {
        System.out.print("Enter item code: ");
        String code = scanner.nextLine();
        if (codeToItem.containsKey(code)) {
            System.out.println("Code already exists. Use update to change item.");
            return;
        }
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        GroceryItem item = new GroceryItem(name, quantity, price);
        inventory.add(item);
        codeToItem.put(code, item);
        System.out.println("Item added with code: " + code);
    }

    private static void scanCode() {
        System.out.print("Scan or enter item code: ");
        String code = scanner.nextLine();
        GroceryItem item = codeToItem.get(code);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
            scannedTotal += item.getPrice();
            scannedCount++;
            System.out.println("Scanned: " + item.getName() + " | Price: " + item.getPrice());
        } else {
            System.out.println("Code not found. Adding new item.");
            System.out.print("Enter item name: ");
            String name = scanner.nextLine();
            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());
            GroceryItem newItem = new GroceryItem(name, 1, price);
            inventory.add(newItem);
            codeToItem.put(code, newItem);
            scannedTotal += price;
            scannedCount++;
            System.out.println("Added and scanned: " + name + " | Price: " + price);
        }
        System.out.printf("Total items scanned: %d | Total sum: %.2f\n", scannedCount, scannedTotal);
    }

    private static void listItems() {
        System.out.println("\nCurrent Inventory:");
        System.out.printf("%-20s %5s %10s\n", "Name", "Qty", "Price");
        for (GroceryItem item : inventory) {
            System.out.println(item);
        }
    }

    private static void updateItem() {
        System.out.print("Enter item name to update: ");
        String name = scanner.nextLine();
        for (GroceryItem item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new quantity: ");
                item.setQuantity(Integer.parseInt(scanner.nextLine()));
                System.out.print("Enter new price: ");
                item.setPrice(Double.parseDouble(scanner.nextLine()));
                System.out.println("Item updated.");
                return;
            }
        }
        System.out.println("Item not found.");
    }

    private static void removeItem() {
        System.out.print("Enter item name to remove: ");
        String name = scanner.nextLine();
        Iterator<GroceryItem> it = inventory.iterator();
        while (it.hasNext()) {
            GroceryItem item = it.next();
            if (item.getName().equalsIgnoreCase(name)) {
                it.remove();
                System.out.println("Item removed.");
                return;
            }
        }
        System.out.println("Item not found.");
    }
}
