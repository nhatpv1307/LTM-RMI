package Model;
import java.io.Serializable;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String description;

    public Category(int id, String name, String description) {
        this.id = id; this.name = name; this.description = description;
    }
    public Category(String name, String description) {
        this.name = name; this.description = description;
    }
    
    // Getters và Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    
    @Override
    public String toString() { return name; } // Để hiển thị đẹp trên ComboBox
}