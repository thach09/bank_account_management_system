# KẾ HOẠCH PHÂN CHIA NHIỆM VỤ & LỘ TRÌNH 3 TUẦN (PROJECT PLAN)
## Môn học: PRO192 — Bank Account Management System

---

## 1. Nguyên Tắc Phối Hợp & Phân Quyền (Separation of Responsibility)

Để 3 thành viên không giẫm chân nhau và code đủ đơn giản để sinh viên năm 2 có thể trace bằng tay:
* **Contract-First:** Thống nhất và đóng băng chữ ký phương thức (Method Signatures) theo đúng UML baseline trước khi code logic.
* **ConsoleMenu (Member 3):** Chỉ hiển thị menu, nhận input, gọi `Bank`, và in kết quả. Tuyệt đối **không tự tính lãi, không tự sửa balance, không quyết định rule rút tiền**.
* **Bank (Member 2):** Điều phối nghiệp vụ (`openAccount`, `deposit`, `withdraw`, `transfer`, `sort`). Tuyệt đối **không dùng `instanceof`** để nhét rule của từng loại tài khoản vào `Bank`.
* **Account Hierarchy (Member 1):** Quản lý trạng thái tài khoản và rule rút tiền/tính lãi riêng của từng loại. Tuyệt đối **không gọi Scanner hay phụ thuộc vào UI**.
* **Không dùng Custom Exception Hierarchy:** Toàn bộ nghiệp vụ kiểm soát bằng `if/else` trả về kết quả hoặc in thông báo; lỗi nhập liệu Console dùng `try/catch` với Exception có sẵn của Java.

---

## 2. Bảng Phân Chia Nhiệm Vụ 3 Thành Viên

| Thành viên | File sở hữu chính | Chức năng phụ trách | Trọng tâm kiến thức OOP & Điểm chấm |
| :--- | :--- | :--- | :--- |
| **Member 1**<br>*(Account Core)* | `Account.java`<br>`SavingsAccount.java`<br>`CheckingAccount.java`<br>`FixedDepositAccount.java` | • **F3:** Rút tiền với quy tắc riêng từng loại.<br>• **F7:** Phương thức tính lãi / trừ phí định kỳ (`applyMonthlyAdjustment`).<br>• Cấu trúc kế thừa và constructor chaining (`super`). | `abstract class`, `inheritance`, `method overriding`, runtime `polymorphism`, `encapsulation`. |
| **Member 2**<br>*(Bank Operations)* | `Customer.java`<br>`Bank.java` | • **F1:** Mở tài khoản và liên kết với Customer.<br>• **F2:** Điều phối nạp tiền (`deposit`).<br>• **F4:** Chuyển tiền (`transfer`) với nguyên tắc *"Validate before modify"*.<br>• **F5:** Liệt kê tài khoản của khách hàng.<br>• **Thuật toán:** Sắp xếp tài khoản theo số dư (`Comparator`).<br>• *(Stretch Goal)* Save/Load file nếu còn thời gian. | `HashMap` lookup $O(1)$, quan hệ đối tượng (`Association`), điều phối nghiệp vụ, `Comparator` $O(n \log n)$. |
| **Member 3**<br>*(Transaction & UI)* | `Transaction.java`<br>`TransactionType.java`<br>`ConsoleMenu.java`<br>`Main.java` | • **F6:** Xem lịch sử giao dịch.<br>• **F8:** Điều hướng Console Menu, validate chống crash (`try/catch`).<br>• Khởi tạo dữ liệu demo trong `Main.java`.<br>• Tích hợp luồng chạy end-to-end cho cả nhóm. | `enum`, `ArrayList` append $O(1)$, điều khiển luồng Scanner, tích hợp hệ thống, demo flow. |

---

## 3. Lộ Trình Triển Khai 3 Tuần Chi Tiết (3-Week Roadmap)

```
Tuần 1 (Ngày 1 - 7): Nền tảng UML & Cốt lõi OOP
├── Ngày 1-2: Cả nhóm rà soát UML, freeze method signatures, thống nhất contract.
├── Ngày 3-6: Member 1 code Account hierarchy; Member 2 code Customer + Bank skeleton; Member 3 code Transaction + Menu skeleton.
└── Ngày 7: Tích hợp lần 1, đảm bảo toàn bộ dự án compile thành công trên branch chung; demo flow nạp/rút cơ bản.

Tuần 2 (Ngày 8 - 14): Hoàn Thiện Core Business Flow
├── Member 1: Hoàn thiện rule withdraw & monthly adjustment cho cả 3 subclasses.
├── Member 2: Hoàn thiện F1 (openAccount), F2 (deposit), F4 (transfer), F5 (list accounts), sort by balance.
├── Member 3: Hoàn thiện Menu điều hướng F1-F8, in lịch sử giao dịch, validate input số chống crash.
└── Cuối tuần 2: Toàn bộ 8 chức năng cốt lõi chạy ổn định.

Tuần 3 (Ngày 15 - 21): Freeze Feature, Cross-Review & Mock Defense
├── Đầu tuần 3: Feature Freeze (không thêm tính năng mới, không over-engineering).
├── Giữa tuần 3: Cross-Review chéo:
│   ├── Member 1 giải thích Account hierarchy cho Member 2 & 3.
│   ├── Member 2 giải thích luồng Bank & transfer cho Member 1 & 3.
│   └── Member 3 giải thích điều hướng Menu & Input handling cho Member 1 & 2.
└── Cuối tuần 3: Lập bảng ma trận Test Case, tổng duyệt vấn đáp (Mock Defense) và nộp bài.
```

---

## 4. Tiêu Chuẩn Hoàn Thành Cho Từng Chức Năng (Definition of Done)

Một chức năng chỉ được xem là hoàn thành khi:
1. Compile và chạy được trực tiếp từ `Main.java` thông qua `ConsoleMenu`.
2. Có đầy đủ kịch bản kiểm thử: Ca thông thường (Normal case), Ca giá trị biên (Boundary case), và Ca dữ liệu không hợp lệ (Invalid input).
3. Không trùng lặp (duplicate) logic nghiệp vụ ở nhiều nơi.
4. Thành viên viết code giải thích được rành mạch luồng: **Input $\rightarrow$ Xử lý $\rightarrow$ Dữ liệu biến đổi $\rightarrow$ Output**.
5. Ít nhất một thành viên khác trong nhóm đã đọc và trace thử luồng code trước khi merge.

---

## 5. Bộ Câu Hỏi Vấn Đáp Thử (Mock Defense Checklist)

* **Member 1 (Account Core):**
  - *"Tại sao `Account` lại là `abstract class` mà không phải class thông thường hay interface?"*
  - *"Tính đa hình (Polymorphism) xuất hiện ở dòng code nào khi gọi `withdraw()` và JVM chọn method tại runtime ra sao?"*
  - *"Từ khóa `super(...)` trong constructor của `SavingsAccount` có tác dụng gì?"*
* **Member 2 (Bank Operations):**
  - *"Tại sao lại dùng `HashMap` để lưu tài khoản trong `Bank` thay vì `ArrayList`? Độ phức tạp tìm kiếm là bao nhiêu?"*
  - *"Trình bày từng bước thực hiện của phương thức `transfer()`? Tại sao phải 'Validate before modify'?"*
  - *"Thuật toán sắp xếp tài khoản theo số dư hoạt động như thế nào (`Comparator`)?"*
* **Member 3 (Transaction & UI):**
  - *"Luồng chạy từ `Main` $\rightarrow$ `ConsoleMenu` $\rightarrow$ `Bank` $\rightarrow$ `Account` được liên kết như thế nào?"*
  - *"Nếu người dùng nhập chữ vào ô nhập số tiền rút thì chương trình xử lý thế nào để không bị văng (crash)?"*
  - *"Tại sao `ConsoleMenu` không được phép trực tiếp sửa số dư của `Account`?"*
* **Cả nhóm:**
  - *"Nếu ngân hàng muốn mở rộng thêm loại tài khoản thứ 4 (ví dụ: `BusinessAccount`), những class nào phải sửa đổi và những class nào giữ nguyên?"* (Câu trả lời chuẩn: Chỉ tạo thêm subclass mới kế thừa `Account`; `Bank` và `ConsoleMenu` hầu như không cần thay đổi nhờ tính Đa hình và nguyên lý Open/Closed).
