import java.util.*;


class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    
    public double getTotalPrice() {
        return price * quantity;
    }
}


abstract class Discount {
    public abstract double applyDiscount(double total, List<Product> products);
}


class FestiveDiscount extends Discount {
    @Override
    public double applyDiscount(double total, List<Product> products) {
        return total * 0.90; 
    }
}


class BulkDiscount extends Discount {
    @Override
    public double applyDiscount(double total, List<Product> products) {
        for (Product p : products) {
            if (p.getQuantity() > 5) {
                return total * 0.80; 
            }
        }
        return total; 
    }
}


interface Payment {
    void pay(double amount);
}

class OnlinePayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Total Amount Payable: " + amount);
    }
}


public class ShoppingCartSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine()); 

        List<Product> cart = new ArrayList<>();

        
        for (int i = 0; i < n; i++) {
            String[] input = sc.nextLine().split(" ");
            String name = input[0];
            double price = Double.parseDouble(input[1]);
            int quantity = Integer.parseInt(input[2]);

            cart.add(new Product(name, price, quantity));
        }

        String discountType = sc.nextLine().trim().toLowerCase();

        
        double total = 0;
        for (Product p : cart) {
            total += p.getTotalPrice();
        }

        
        Discount discount;
        if (discountType.equals("festive")) {
            discount = new FestiveDiscount();
        } else if (discountType.equals("bulk")) {
            discount = new BulkDiscount();
        } else {
            discount = new Discount() {
                @Override
                public double applyDiscount(double total, List<Product> products) {
                    return total; // no discount if invalid type
                }
            };
        }

        
        double finalAmount = discount.applyDiscount(total, cart);

    
        for (Product p : cart) {
            System.out.println("Product: " + p.getName() +", Price: " + p.getPrice() + ", Quantity: " + p.getQuantity());
        }

        
        Payment payment = new OnlinePayment();
        payment.pay(finalAmount);

        sc.close();
    }
}