package Run;

import Control.RMIMenuServerControl;

public class ServerRun {
    public static void main(String[] args) {
        try {
            System.out.println("Đang khởi động RMI Server...");
            
            // Khởi tạo Control của Server, tự động kết nối DB và mở RMI Registry
            new RMIMenuServerControl();
            
            System.out.println("RMI Server đã khởi động thành công và đang lắng nghe tại cổng 3232!");
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi động Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}