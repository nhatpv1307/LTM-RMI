package Control;
import Model.Category;
import Model.Dish;
import Server.RMIMenuInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class RMIMenuClientControl {
    private RMIMenuInterface rmiServer;
    
    public RMIMenuClientControl() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 3232);
            rmiServer = (RMIMenuInterface) registry.lookup("rmiMenuServer");
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public boolean addCategory(Category c) {
        try { return rmiServer.addCategory(c); } catch (Exception e) { return false; }
    }
    
    public ArrayList<Category> getAllCategories() {
        try { return rmiServer.getAllCategories(); } catch (Exception e) { return new ArrayList<>(); }
    }
    
    public boolean addDish(Dish d) {
        try { return rmiServer.addDish(d); } catch (Exception e) { return false; }
    }
    
    public ArrayList<Dish> getAllDishes() {
        try { return rmiServer.getAllDishes(); } catch (Exception e) { return new ArrayList<>(); }
    }
}