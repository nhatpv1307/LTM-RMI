package Model;
import java.io.Serializable;

public class Dish implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String image;
    private double price;
    private int prepTime;
    private int categoryId;
    private String categoryName; // Dùng để hiển thị trên bảng

    public Dish(String name, String image, double price, int prepTime, int categoryId) {
        this.name = name; this.image = image; this.price = price; 
        this.prepTime = prepTime; this.categoryId = categoryId;
    }
    
    // Thêm các Getters/Setters tương ứng...
    public String getName() { return name; }
    public String getImage() { return image; }
    public double getPrice() { return price; }
    public int getPrepTime() { return prepTime; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCategoryName() { return categoryName; }
}