CREATE DATABASE ABCNews
GO
USE ABCNews
GO

CREATE TABLE Categories (
    Id VARCHAR(10) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL
);

CREATE TABLE Users (
    Id VARCHAR(20) PRIMARY KEY,
    Password VARCHAR(100) NOT NULL,
    Fullname NVARCHAR(50) NOT NULL,
    Birthday DATE,
    Gender BIT,
    Mobile VARCHAR(15),
    Email VARCHAR(100) UNIQUE,
    Role BIT NOT NULL -- 1 = admin, 0 = reporter
);

CREATE TABLE News (
    Id VARCHAR(10) PRIMARY KEY,
    Title NVARCHAR(200) NOT NULL,
    Content NTEXT,
    Image VARCHAR(200),
    PostedDate DATE DEFAULT GETDATE(),
    Author VARCHAR(20) REFERENCES Users(Id),
    ViewCount INT DEFAULT 0,
    CategoryId VARCHAR(10) REFERENCES Categories(Id),
    Home BIT DEFAULT 0
);

CREATE TABLE Newsletters (
    Email VARCHAR(100) PRIMARY KEY,
    Enabled BIT DEFAULT 1
);

-- XÓA DATA CŨ ĐỂ TRÁNH LỖI TRÙNG (an toàn nhất)
DELETE FROM News
DELETE FROM Newsletters
DELETE FROM Users
DELETE FROM Categories
GO

-- INSERT Categories ĐÚNG 100% (không trùng Id nữa)
INSERT INTO Categories VALUES 
('TT', N'Thời sự'),
('PL', N'Pháp luật'),
('KT', N'Kinh tế'),
('TG', N'Thế giới'),
('VH', N'Văn hóa'),
('SK', N'Sức khỏe'),
('GD', N'Giáo dục'),
('TS', N'Thể thao');   -- <-- sửa thành TS, không trùng TT nữa
GO

-- Users giữ nguyên
INSERT INTO Users VALUES 
('admin', '123456', N'Quản Trị Viên', '1990-01-01', 1, '0901234567', 'admin@gmail.com', 1),
('pv001', '123456', N'Nguyễn Thị Hương', '1995-08-15', 0, '0908111222', 'huong@gmail.com', 0),
('pv002', '123456', N'Trần Văn Nam', '1993-03-20', 1, '0912333444', 'nam@gmail.com', 0),
('pv003', '123456', N'Lê Minh Tuấn', '1997-12-10', 1, '0933555777', 'tuan@gmail.com', 0);
GO

-- 20 tin mẫu (đã sửa CategoryId cho tin thể thao thành 'TS')
INSERT INTO News (Id, Title, Content, Image, Author, CategoryId, ViewCount, Home) VALUES
('N001', N'Tuyển sinh ĐH 2025: Nhiều trường công bố điểm chuẩn sớm', N'Nội dung chi tiết tin tức tuyển sinh 2025...', 'uploads/news1.jpg', 'pv001', 'GD', 156, 1),
('N002', N'Việt Nam vô địch AFF Cup 2024', N'Đội tuyển Việt Nam đã xuất sắc đánh bại Thái Lan...', 'uploads/news2.jpg', 'pv002', 'TS', 289, 1),  -- đổi thành TS
('N003', N'Giá vàng hôm nay tăng vọt lên 85 triệu/lượng', N'Thị trường vàng trong nước tiếp tục sôi động...', 'uploads/news3.jpg', 'pv001', 'KT', 423, 1),
('N004', N'Bão số 8 sắp vào Biển Đông', N'Cơn bão mạnh cấp 12, giật cấp 15...', 'uploads/news4.jpg', 'pv003', 'TT', 512, 1),
('N005', N'iPhone 17 sẽ có thiết kế hoàn toàn mới', N'Apple chuẩn bị ra mắt thế hệ iPhone đột phá...', 'uploads/news5.jpg', 'pv002', 'VH', 198, 1),
('N006', N'Bệnh nhân đầu tiên ghép thận heo sống khỏe mạnh', N'Ca ghép thận từ heo biến đổi gen thành công...', 'uploads/news6.jpg', 'pv001', 'SK', 334, 0),
('N007', N'Chứng khoán Việt Nam lập đỉnh lịch sử', N'VN-Index vượt 1.500 điểm...', 'uploads/news7.jpg', 'pv003', 'KT', 267, 0),
('N008', N'Tuyển Việt Nam chuẩn bị SEA Games 33', N'Huấn luyện viên Kim Sang-sik công bố danh sách...', 'uploads/news8.jpg', 'pv002', 'TS', 445, 0),  -- đổi TS
('N009', N'Elon Musk mua lại TikTok?', N'Tin đồn gây sốc trên mạng xã hội...', 'uploads/news9.jpg', 'pv001', 'VH', 890, 0),
('N010', N'Bitcoin vượt 100.000 USD', N'Tiền ảo tiếp tục tăng trưởng mạnh mẽ...', 'uploads/news10.jpg', 'pv003', 'KT', 1200, 0),
('N011', N'Vụ án Vạn Thịnh Phát: Phiên tòa tiếp tục', N'Bà Trương Mỹ Lan và đồng phạm...', 'uploads/news11.jpg', 'pv001', 'PL', 678, 0),
('N012', N'Thời tiết miền Bắc sắp rét đậm', N'Không khí lạnh mạnh tràn về...', 'uploads/news12.jpg', 'pv002', 'TT', 345, 0),
('N013', N'Phim Việt đạt doanh thu kỷ lục 2025', N'Bộ phim "Mai" vượt 500 tỷ...', 'uploads/news13.jpg', 'pv003', 'VH', 567, 0),
('N014', N'Vaccine COVID biến thể mới sắp về Việt Nam', N'Bộ Y tế thông báo...', 'uploads/news14.jpg', 'pv001', 'SK', 234, 0),
('N015', N'Tuyển thủ Quang Hải trở lại châu Âu?', N'Câu lạc bộ Pháp quan tâm...', 'uploads/news15.jpg', 'pv002', 'TS', 789, 0),
('N016', N'Ngân hàng giảm lãi suất huy động', N'Người dân đổ xô gửi tiết kiệm...', 'uploads/news16.jpg', 'pv003', 'KT', 156, 0),
('N017', N'Thủ tướng gặp gỡ cộng đồng người Việt tại Mỹ', N'Chuyến công du quan trọng...', 'uploads/news17.jpg', 'pv001', 'TT', 412, 0),
('N018', N'VinFast giao 10.000 xe điện tại Mỹ', N'Tốc độ tăng trưởng ấn tượng...', 'uploads/news18.jpg', 'pv002', 'KT', 523, 0),
('N019', N'World Cup 2026: Việt Nam đặt mục tiêu', N'Liên đoàn bóng đá công bố kế hoạch...', 'uploads/news19.jpg', 'pv003', 'TS', 667, 0),
('N020', N'TP.HCM mưa lớn lịch sử tháng 11', N'Nội thành ngập sâu, giao thông tê liệt...', 'uploads/news20.jpg', 'pv001', 'TT', 890, 0);