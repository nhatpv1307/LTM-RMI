
CREATE DATABASE IF NOT EXISTS quanlythucdon DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE quanlythucdon;

CREATE TABLE tbl_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE tbl_dish (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image VARCHAR(255),
    price DOUBLE,
    prep_time INT, -- Thời gian phục vụ (phút)
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES tbl_category(id) ON DELETE CASCADE
);

USE quanlythucdon;

-- 1. Thêm dữ liệu mẫu cho bảng Loại món (tbl_category)
INSERT INTO tbl_category (name, description) VALUES 
('Món Nướng', 'Các món nướng than hoa, nướng BBQ'),
('Món Lẩu', 'Các loại lẩu nước, lẩu thái, lẩu hải sản'),
('Khai Vị', 'Các món súp, salad ăn trước bữa chính'),
('Đồ Uống', 'Nước giải khát, bia, rượu vang');

-- 2. Thêm dữ liệu mẫu cho bảng Món ăn (tbl_dish)
-- Giả sử các ID của Loại món ở trên lần lượt là 1, 2, 3, 4
INSERT INTO tbl_dish (name, image, price, prep_time, category_id) VALUES 
('Sườn nướng BBQ', 'suon_bbq.jpg', 150000, 25, 1),
('Bò tảng nướng tảng', 'bo_nuong.jpg', 250000, 20, 1),
('Lẩu Thái hải sản', 'lau_thai.jpg', 350000, 15, 2),
('Lẩu riêu cua bắp bò', 'lau_rieu.jpg', 280000, 15, 2),
('Salad cá ngừ', 'salad_ca.jpg', 65000, 10, 3),
('Khoai tây chiên', 'khoai_tay.jpg', 45000, 8, 3),
('Bia Heineken', 'heineken.jpg', 25000, 2, 4),
('Nước ép dưa hấu', 'nuoc_ep.jpg', 35000, 5, 4);