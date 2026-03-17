package Server;
import Model.Category;
import Model.Dish;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIMenuInterface extends Remote {
    public boolean addCategory(Category category) throws RemoteException;
    public ArrayList<Category> getAllCategories() throws RemoteException;
    
    public boolean addDish(Dish dish) throws RemoteException;
    public ArrayList<Dish> getAllDishes() throws RemoteException;
}