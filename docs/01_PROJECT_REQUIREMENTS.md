# ĐẶC TẢ TỔNG QUAN YÊU CẦU DỰ ÁN (PROJECT REQUIREMENTS)
## Môn học: Object-Oriented Programming with Java (PRO192) - Đại học FPT
**Đề tài:** Topic 5 — Bank Account Management System (Hệ thống quản lý tài khoản ngân hàng)  
**Nền tảng:** Console Application (Java Core 100%)  
**Kế hoạch thực hiện:** ~3 tuần trước buổi Code Review / Vấn đáp  
**Mục tiêu cốt lõi:** Thể hiện đúng, rõ ràng và dễ giải thích các khái niệm OOP: abstraction, inheritance, polymorphism, encapsulation, collections và algorithm; code mức sinh viên năm 2 dễ trace, không over-engineering.

---

## 1. Mục Tiêu Học Thuật & Định Hướng Thực Hiện (Lecturer Feedback)

* **Giữ nguyên UML hiện tại làm baseline thiết kế:** Không tự ý thêm class, pattern phức tạp, hoặc thư viện ngoài.
* **Bỏ toàn bộ custom BankException hierarchy:**
  - Không dùng: `BankException`, `AccountNotFoundException`, `InsufficientFundsException`, `InvalidAmountException`.
  - Nghiệp vụ được kiểm soát bằng **validation đơn giản (`if/else`)** và thông báo lỗi rõ ràng.
  - Tầng Console sử dụng `try/catch` với các exception có sẵn của Java (`NumberFormatException`, `InputMismatchException`...) để xử lý người dùng nhập sai kiểu dữ liệu.
* **File I/O (Save/Load):** Nằm trong scope theo UML nhưng được đánh dấu là **lower priority / stretch goal** sau khi toàn bộ core OOP và business flow đã chạy ổn định.
* **Không đưa các quy tắc ngân hàng thực tế phức tạp vào dự án:** Không có xác thực (authentication), tính lãi kép phức tạp, bảo mật đa lớp, hay concurrency/database transactions.

---

## 2. Danh Sách 8 Chức Năng Cốt Lõi (Functionalities F1 - F8)

### F1: Mở tài khoản mới (Open Account)
- Quy trình: Nhập Customer ID $\rightarrow$ Bank kiểm tra sự tồn tại của Customer $\rightarrow$ Nhập thông tin tài khoản theo loại được chọn (`SavingsAccount`, `CheckingAccount`, `FixedDepositAccount`) $\rightarrow$ Tạo instance subclass tương ứng $\rightarrow$ Lưu vào collection của Bank $\rightarrow$ Liên kết số tài khoản với Customer.
- Không thể khởi tạo trực tiếp `Account` vì `Account` là `abstract class`.

### F2: Nạp tiền (Deposit Money)
- Quy trình: Nhập `accountNumber` và `amount` $\rightarrow$ Bank tìm `Account` qua `accountNumber` $\rightarrow$ Kiểm tra `amount > 0`.
- Nếu hợp lệ: Ủy quyền cho `Account.deposit(amount)` tăng số dư và ghi nhận lịch sử `Transaction`.
- Nếu không hợp lệ hoặc không tìm thấy tài khoản: Thông báo lỗi qua `if/else`, không cập nhật dữ liệu.

### F3: Rút tiền (Withdraw Money - Nơi thể hiện Polymorphism)
- Quy trình: Bank tìm `Account` tương ứng rồi gọi `account.withdraw(amount)`.
- Bank không cần biết công thức riêng của từng loại tài khoản; **tính đa hình động (Dynamic Binding)** tại runtime sẽ kích hoạt logic của subclass tương ứng:
  - **SavingsAccount:** Rút tiền nhưng không được để số dư thấp hơn mức quy định (`balance - amount >= 0` hoặc số dư tối thiểu).
  - **CheckingAccount:** Cho phép rút vượt số dư trong phạm vi hạn mức thấu chi (`balance - amount >= -overdraftLimit`).
  - **FixedDepositAccount:** Nếu đã đến ngày đáo hạn (`isMatured == true`) thì cho phép rút; nếu chưa đáo hạn thì từ chối hoặc xử lý theo quy định đơn giản của nhóm.

### F4: Chuyển khoản giữa 2 tài khoản (Transfer Money)
- **Nguyên tắc sống còn: "Validate before modify" (Kiểm tra toàn bộ trước khi sửa dữ liệu):**
  1. Tìm tài khoản nguồn (`fromAcc`).
  2. Tìm tài khoản đích (`toAcc`).
  3. Kiểm tra `fromAcc` khác `toAcc` (không tự chuyển cho chính mình).
  4. Kiểm tra số tiền chuyển `amount > 0`.
  5. Kiểm tra tài khoản nguồn có thỏa mãn điều kiện rút tiền không.
  6. Sau khi tất cả điều kiện đều hợp lệ mới thực hiện: `fromAcc.withdraw(amount)` $\rightarrow$ `toAcc.deposit(amount)` $\rightarrow$ Ghi nhận lịch sử giao dịch.

### F5: Liệt kê danh sách tài khoản của một khách hàng (List Customer Accounts)
- Nhập `customerId` $\rightarrow$ Tìm `Customer` $\rightarrow$ Lấy danh sách `accountNumbers` $\rightarrow$ Truy xuất và in thông tin từng tài khoản tương ứng từ Bank.

### F6: Xem lịch sử giao dịch của tài khoản (Transaction History)
- Nhập `accountNumber` $\rightarrow$ Lấy danh sách giao dịch `List<Transaction>` $\rightarrow$ Duyệt vòng lặp `for` tuần tự để in ra màn hình.

### F7: Tính lãi hoặc điều chỉnh phí hàng tháng (Monthly Interest / Fee Adjustment)
- Duyệt qua toàn bộ danh sách `Account` trong Bank:
  ```java
  for (Account acc : accounts.values()) {
      acc.applyMonthlyAdjustment(); // Runtime polymorphism tự gọi phương thức của subclass
  }
  ```
  - `SavingsAccount`: Tính và cộng lãi định kỳ vào số dư.
  - `CheckingAccount`: Khấu trừ phí quản lý hàng tháng (`monthlyFee`).
  - `FixedDepositAccount`: Tăng số tháng đã gửi (`monthsElapsed++`), cập nhật trạng thái đáo hạn và cộng lãi nếu đến hạn.

### F8: Kiểm soát nhập liệu trên Console (Console Input Validation)
- Dùng `try/catch` bọc việc parse dữ liệu (`Integer.parseInt`, `Double.parseDouble`, `Scanner`) để chống crash khi người dùng nhập chữ vào ô số.
- Vòng lặp menu `do-while` chạy liên tục cho đến khi người dùng chọn thoát (Exit).

---

## 3. Cấu Trúc Dữ Liệu & Độ Phức Tạp Thuật Toán (Collections & Complexity)

| Nghiệp vụ / Thao tác | Cấu trúc dữ liệu & Thuật toán | Độ phức tạp | Lý do kỹ thuật bảo vệ trước Giảng viên |
| :--- | :--- | :--- | :--- |
| Tìm tài khoản theo STK | `HashMap<String, Account>` (`get`) | $O(1)$ trung bình | Lookup theo key duy nhất tức thời, vượt trội so với tìm kiếm tuyến tính $O(n)$ của `ArrayList`. |
| Tìm khách hàng theo ID | `HashMap<String, Customer>` (`get`) | $O(1)$ trung bình | Tra cứu trực tiếp theo mã khách hàng duy nhất. |
| Ghi lịch sử giao dịch | `ArrayList<Transaction>` (`add`) | $O(1)$ amortized | Thêm tuần tự vào cuối danh sách theo dòng thời gian. |
| In lịch sử giao dịch | Vòng lặp duyệt `ArrayList` | $O(n)$ | Dễ đọc, dễ trace, in đúng thứ tự thời gian phát sinh. |
| Sắp xếp tài khoản theo số dư | Copy sang `ArrayList` + `Comparator` | $O(n \log n)$ | Đủ để chứng minh năng lực thuật toán mà không over-engineer. |

> **Lưu ý quan trọng:** Không sử dụng Binary Search cho tìm kiếm số tài khoản vì đã sử dụng `HashMap` với độ phức tạp $O(1)$. Không thêm thuật toán chỉ để trông phức tạp.

---

## 4. Kiến Trúc 3 Tuần Triển Khai (3-Week Implementation Timeline)

* **Tuần 1: Chốt Contracts & Core OOP (Model & Base Logic)**
  - Chốt Method Signatures đúng 100% theo UML.
  - Hoàn thiện cây kế thừa `Account`, `Customer`, `Transaction`, `TransactionType`.
  - Khung sườn `Bank` và `ConsoleMenu` biên dịch thành công trên branch chung.
* **Tuần 2: Hoàn Thiện Nghiệp Vụ & Ghép Nối (Service & UI Integration)**
  - Hoàn thiện F1 $\rightarrow$ F7 trong `Bank.java`.
  - Hoàn thiện điều hướng ConsoleMenu, validate input chống crash.
  - Test end-to-end toàn bộ flow nạp, rút, chuyển khoản, tính lãi, sắp xếp.
* **Tuần 3: Cross-Review, Test Cases & Mock Defense**
  - Đóng băng tính năng (Feature Freeze - không thêm tính năng mới).
  - Từng thành viên đọc hiểu và trace code của nhau (chuẩn bị vấn đáp chéo).
  - Lập bảng ma trận Test Cases (Normal, Boundary, Invalid Input).
  - Luyện tập trả lời các câu hỏi phản biện của giảng viên.
