package budget.model;

public class Purchase {
    private String name;
    private double price;

    public Purchase(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", this.name, this.price);
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }
}
