package View;

import Control.RMIMenuClientControl;
import Model.Category;
import Model.Dish;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Author: Phạm Văn Nhật (K23DTCN176)
 */
public class RMIMenuClientView extends JFrame {
    private RMIMenuClientControl control;
    
    // Components cho Loại món
    private JTextField txtCatName, txtCatDesc;
    private JButton btnAddCat, btnRefreshCat;
    private JTable tblCat;
    private DefaultTableModel modelCat;
    
    // Components cho Món ăn
    private JTextField txtDishName, txtDishPrice, txtDishTime;
    private JComboBox<Category> cbxCategory;
    private JButton btnAddDish, btnRefreshDish;
    private JTable tblDish;
    private DefaultTableModel modelDish;

    public RMIMenuClientView() {
        super("Hệ thống Quản lý Thực đơn - K23DTCN176 - Phạm Văn Nhật");
        control = new RMIMenuClientControl();
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị form ở giữa màn hình
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Quản lý Loại món", createCategoryPanel());
        tabbedPane.addTab("Quản lý Món ăn", createDishPanel());
        
        add(tabbedPane);
        loadCategories(); // Tải dữ liệu ban đầu
        loadDishes();
    }
    
    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        form.add(new JLabel("Tên loại món (*):")); 
        txtCatName = new JTextField(); 
        form.add(txtCatName);
        
        form.add(new JLabel("Mô tả (*):")); 
        txtCatDesc = new JTextField(); 
        form.add(txtCatDesc);
        
        btnRefreshCat = new JButton("Làm mới");
        btnAddCat = new JButton("Thêm Loại Món"); 
        
        // Thêm 2 nút vào dòng cuối của Form
        form.add(btnRefreshCat); 
        form.add(btnAddCat);
        
        modelCat = new DefaultTableModel(new String[]{"ID", "Tên loại", "Mô tả"}, 0);
        tblCat = new JTable(modelCat);
        
        // SỰ KIỆN NÚT LÀM MỚI LOẠI MÓN
        btnRefreshCat.addActionListener((ActionEvent e) -> {
            loadCategories();
            txtCatName.setText("");
            txtCatDesc.setText("");
            txtCatName.requestFocus();
        });
        
        // SỰ KIỆN NÚT THÊM LOẠI MÓN
        btnAddCat.addActionListener((ActionEvent e) -> {
            String name = txtCatName.getText().trim();
            String desc = txtCatDesc.getText().trim();
            
            if (name.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Tên loại món và Mô tả!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Category c = new Category(name, desc);
            if (control.addCategory(c)) {
                JOptionPane.showMessageDialog(this, "Thêm loại món thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadCategories();
                txtCatName.setText("");
                txtCatDesc.setText("");
                txtCatName.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm loại món thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblCat), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createDishPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        form.add(new JLabel("Tên món ăn (*):")); txtDishName = new JTextField(); form.add(txtDishName);
        form.add(new JLabel("Giá (VNĐ) (*):")); txtDishPrice = new JTextField(); form.add(txtDishPrice);
        form.add(new JLabel("Thời gian phục vụ (phút) (*):")); txtDishTime = new JTextField(); form.add(txtDishTime);
        form.add(new JLabel("Loại món (*):")); cbxCategory = new JComboBox<>(); form.add(cbxCategory);
        
        btnRefreshDish = new JButton("Làm mới");
        btnAddDish = new JButton("Thêm Món Ăn"); 
        
        // Thêm 2 nút vào dòng cuối của Form
        form.add(btnRefreshDish);
        form.add(btnAddDish);
        
        modelDish = new DefaultTableModel(new String[]{"Tên món", "Loại", "Giá", "Thời gian phục vụ"}, 0);
        tblDish = new JTable(modelDish);
        
        // SỰ KIỆN NÚT LÀM MỚI MÓN ĂN
        btnRefreshDish.addActionListener((ActionEvent e) -> {
            loadCategories(); // Cập nhật lại ComboBox phòng khi có loại món mới
            loadDishes();     // Cập nhật lại bảng món ăn
            txtDishName.setText("");
            txtDishPrice.setText("");
            txtDishTime.setText("");
            if (cbxCategory.getItemCount() > 0) cbxCategory.setSelectedIndex(0);
            txtDishName.requestFocus();
        });
        
        // SỰ KIỆN NÚT THÊM MÓN ĂN
        btnAddDish.addActionListener((ActionEvent e) -> {
            String name = txtDishName.getText().trim();
            String priceStr = txtDishPrice.getText().trim();
            String timeStr = txtDishTime.getText().trim();
            Category selectedCat = (Category) cbxCategory.getSelectedItem();
            
            if (name.isEmpty() || priceStr.isEmpty() || timeStr.isEmpty() || selectedCat == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin món ăn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double price = 0;
            int time = 0;
            try {
                price = Double.parseDouble(priceStr);
                time = Integer.parseInt(timeStr);
                
                if (price <= 0 || time <= 0) {
                    JOptionPane.showMessageDialog(this, "Giá và Thời gian phục vụ phải lớn hơn 0!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: 'Giá' và 'Thời gian' phải nhập bằng số!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            
            Dish d = new Dish(name, "none", price, time, selectedCat.getId());
            if (control.addDish(d)) {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadDishes();
                txtDishName.setText("");
                txtDishPrice.setText("");
                txtDishTime.setText("");
                if (cbxCategory.getItemCount() > 0) cbxCategory.setSelectedIndex(0);
                txtDishName.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblDish), BorderLayout.CENTER);
        return panel;
    }
    
    private void loadCategories() {
        modelCat.setRowCount(0);
        cbxCategory.removeAllItems();
        ArrayList<Category> list = control.getAllCategories();
        for (Category c : list) {
            modelCat.addRow(new Object[]{c.getId(), c.getName(), c.getDescription()});
            cbxCategory.addItem(c); 
        }
    }
    
    private void loadDishes() {
        modelDish.setRowCount(0);
        ArrayList<Dish> list = control.getAllDishes();
        for (Dish d : list) {
            String displayPrice = String.format("%,.0f", d.getPrice());
            modelDish.addRow(new Object[]{d.getName(), d.getCategoryName(), displayPrice, d.getPrepTime()});
        }
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new RMIMenuClientView().setVisible(true);
        });
    }
}