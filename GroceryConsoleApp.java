import java.util.*;
///////
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
        addPredefinedItem("567879", "Chicken", 50, 5.49);
        addPredefinedItem("678901", "Orange", 25, 0.79);
        addPredefinedItem("789012", "Cheese", 18, 4.29);
        addPredefinedItem("890123", "Yogurt", 22, 1.49);
        addPredefinedItem("901234", "Butter", 14, 2.99);
        addPredefinedItem("012345", "Cereal", 16, 3.99);
        addPredefinedItem("132435", "Tomato", 40, 0.89);
        addPredefinedItem("243546", "Potato", 35, 0.49);
        addPredefinedItem("354657", "Onion", 28, 0.59);
        addPredefinedItem("465768", "Lettuce", 20, 1.29);
        addPredefinedItem("576879", "Carrot", 30, 0.69);
        addPredefinedItem("687980", "Grapes", 22, 2.19);
        addPredefinedItem("798091", "Watermelon", 10, 4.99);
        addPredefinedItem("809102", "Strawberry", 18, 3.59);
        addPredefinedItem("910213", "Blueberry", 15, 3.99);
        addPredefinedItem("021324", "Peach", 25, 1.79);
        addPredefinedItem("132435", "Pear", 20, 1.49);
        addPredefinedItem("243546", "Cucumber", 30, 0.99);
        addPredefinedItem("354657", "Zucchini", 18, 1.29);
        addPredefinedItem("465768", "Bell Pepper", 22, 1.59);
        addPredefinedItem("576879", "Mushroom", 15, 2.29);
        addPredefinedItem("687980", "Spinach", 20, 1.99);
        addPredefinedItem("798091", "Avocado", 12, 2.49);
        addPredefinedItem("809102", "Lemon", 25, 0.79);
            }

    private static void addPredefinedItem(String code, String name, int quantity, double price) {
        GroceryItem item = new GroceryItem(name, quantity, price);
        inventory.add(item);
        codeToItem.put(code, item);
    }
    

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
        boolean scanning = true;
        double sessionTotal = 0.0;
        int sessionCount = 0;
        Map<String, Integer> sessionScans = new HashMap<>(); // track scanned items
    
        while (scanning) {
            System.out.print("Scan or enter item code (or type 'done' to finish): ");
            String code = scanner.nextLine();
            if (code.equalsIgnoreCase("done")) {
                scanning = false;
                break;
            }
    
            GroceryItem item = codeToItem.get(code);
            if (item != null) {
                // Ask for quantity
                System.out.print("How many quantity: ");
                int qty = Integer.parseInt(scanner.nextLine());
    
                // Update inventory (reduce stock if selling)
                if (item.getQuantity() >= qty) {
                    item.setQuantity(item.getQuantity() - qty);
                } else {
                    System.out.println("Not enough stock! Only " + item.getQuantity() + " available.");
                    continue;
                }
    
                double subtotal = item.getPrice() * qty;
                sessionTotal += subtotal;
                sessionCount += qty;
    
                sessionScans.put(item.getName(), sessionScans.getOrDefault(item.getName(), 0) + qty);
    
                System.out.printf("Scanned: %s | Qty: %d | Subtotal: %.2f\n",
                                  item.getName(), qty, subtotal);
            } else {
                System.out.println("Code not found. Adding new item.");
                System.out.print("Enter item name: ");
                String name = scanner.nextLine();
                System.out.print("Enter price: ");
                double price = Double.parseDouble(scanner.nextLine());
                System.out.print("How many quantity: ");
                int qty = Integer.parseInt(scanner.nextLine());
    
                GroceryItem newItem = new GroceryItem(name, qty, price);
                inventory.add(newItem);
                codeToItem.put(code, newItem);
    
                double subtotal = price * qty;
                sessionTotal += subtotal;
                sessionCount += qty;
                sessionScans.put(name, sessionScans.getOrDefault(name, 0) + qty);
    
                System.out.printf("Added and scanned: %s | Qty: %d | Subtotal: %.2f\n",
                                  name, qty, subtotal);
            }
    
            System.out.printf("Total items scanned this session: %d | Total sum: %.2f\n",
                              sessionCount, sessionTotal);
        }
    
        if (sessionCount > 0) {
            System.out.println("\n--- Session Summary ---");
            for (Map.Entry<String, Integer> entry : sessionScans.entrySet()) {
                System.out.printf("%s → %d pcs\n", entry.getKey(), entry.getValue());
            }
            System.out.printf("TOTAL to pay: %.2f\n", sessionTotal);
    
            System.out.print("Enter buyer's money: ");
            double money = Double.parseDouble(scanner.nextLine());
            if (money >= sessionTotal) {
                double change = money - sessionTotal;
                System.out.printf("Change: %.2f\n", change);
            } else {
                System.out.printf("Insufficient funds! Buyer is short by: %.2f\n", sessionTotal - money);
            }
    
            scannedTotal += sessionTotal;
            scannedCount += sessionCount;
        }
    }
    
    
    private static void listItems() {
        System.out.println("\nCurrent Inventory:");
        System.out.printf("%-12s %-20s %5s %10s\n", "Barcode", "Name", "Qty", "Price");
        for (Map.Entry<String, GroceryItem> entry : codeToItem.entrySet()) {
            String code = entry.getKey();
            GroceryItem item = entry.getValue();
            System.out.printf("%-12s %-20s %5d %10.2f\n", code, item.getName(), item.getQuantity(), item.getPrice());
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
