package Control;
import Model.Category;
import Model.Dish;
import Server.RMIMenuInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;

public class RMIMenuServerControl extends UnicastRemoteObject implements RMIMenuInterface {
    private Connection con;
    private Registry registry;
    
    public RMIMenuServerControl() throws RemoteException {
        getDBConnection("quanlythucdon", "dba01", "K23DTCN176"); // Mật khẩu DB của bạn
        try {
            registry = LocateRegistry.createRegistry(3232);
            registry.rebind("rmiMenuServer", this);
            System.out.println("Server is running...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    private void getDBConnection(String dbName, String username, String password) {
        try {
            String dbUrl = "jdbc:mysql://172.16.5.21:3306/" + dbName;
            con = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public boolean addCategory(Category c) throws RemoteException {
        String sql = "INSERT INTO tbl_category (name, description) VALUES (?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    @Override
    public ArrayList<Category> getAllCategories() throws RemoteException {
        ArrayList<Category> list = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_category");
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public boolean addDish(Dish d) throws RemoteException {
        String sql = "INSERT INTO tbl_dish (name, image, price, prep_time, category_id) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, d.getName()); ps.setString(2, d.getImage());
            ps.setDouble(3, d.getPrice()); ps.setInt(4, d.getPrepTime());
            ps.setInt(5, d.getCategoryId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    @Override
    public ArrayList<Dish> getAllDishes() throws RemoteException {
        ArrayList<Dish> list = new ArrayList<>();
        String sql = "SELECT d.*, c.name as category_name FROM tbl_dish d JOIN tbl_category c ON d.category_id = c.id";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Dish dish = new Dish(rs.getString("name"), rs.getString("image"), 
                                     rs.getDouble("price"), rs.getInt("prep_time"), rs.getInt("category_id"));
                dish.setCategoryName(rs.getString("category_name"));
                list.add(dish);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}