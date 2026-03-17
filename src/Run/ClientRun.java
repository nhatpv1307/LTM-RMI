package Run;

import View.RMIMenuClientView;

public class ClientRun {
    public static void main(String[] args) {
        // Sử dụng EventQueue để đảm bảo Thread an toàn cho giao diện Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Khởi tạo Form và cho phép hiển thị
                new RMIMenuClientView().setVisible(true);
            }
        });
    }
}