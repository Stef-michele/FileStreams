import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String description;
    private String productID;
    private double cost;

    // Constructor
    public Product(String name, String description, String productID, double cost) {
        this.name = name;
        this.description = description;
        this.productID = productID;
        this.cost = cost;
    }

    // Getters
    public String getName() { return name.trim(); }
    public String getDescription() { return description.trim(); }
    public String getProductID() { return productID.trim(); }
    public double getCost() { return cost; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setProductID(String productID) { this.productID = productID; }
    public void setCost(double cost) { this.cost = cost; }

    // Fixed-length padding helpers for RandomAccessFile
    public String getFixedLengthName() {
        return String.format("%-35s", name);
    }

    public String getFixedLengthDescription() {
        return String.format("%-75s", description);
    }

    public String getFixedLengthID() {
        return String.format("%-6s", productID);
    }

    public String getFixedLengthRecord() {
        return getFixedLengthID() + getFixedLengthName() + getFixedLengthDescription() + String.format("%10.2f", cost);
    }
}
