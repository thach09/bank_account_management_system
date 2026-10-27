# KẾ HOẠCH PHÂN CHIA NHIỆM VỤ & LỘ TRÌNH TRIỂN KHAI THEO MỐC (PROJECT PLAN)
## Môn học: Object-Oriented Programming with Java (PRO192) - Đại học FPT
**Dự án:** Topic 5 — Bank Account Management System
**Mục tiêu:** Kế hoạch hành động theo mốc (Milestone-based plan) phân công rõ ràng cho 3 thành viên, kiểm soát tiến độ tới khi hoàn thiện mã nguồn, kiểm thử, chụp ảnh minh chứng và bảo vệ đồ án.

---

## 1. Nguyên Tắc Phối Hợp & Phân Quyền (Separation of Responsibilities)

Để đảm bảo 3 thành viên làm việc độc lập, không xung đột mã nguồn và kiểm soát tốt chất lượng:
1. **Contract-First:** Toàn bộ chữ ký phương thức (Method Signatures), kiểu dữ liệu và phạm vi truy cập đã được đóng băng theo FINAL UML tại mốc M0. Tuyệt đối không thay đổi contract này.
2. **Account Hierarchy (Member 1):** Chịu trách nhiệm toàn bộ logic nội tại của tài khoản: nạp, rút, trừ phí, tính lãi, ghi nhận `Transaction` cục bộ. Tuyệt đối không phụ thuộc vào `Scanner`, UI hay `Bank`.
3. **Bank Operations (Member 2):** Điều phối nghiệp vụ hệ thống: quản lý khách hàng, mở tài khoản, chuyển tiền (Transfer), điều phối nạp/rút từ xa, sắp xếp danh sách. Tuyệt đối không dùng `instanceof` để can thiệp vào công thức riêng của từng loại tài khoản.
4. **Transaction & Console UI (Member 3):** Giao diện tương tác người dùng, định dạng hiển thị giao dịch, sinh mã giao dịch, kiểm soát chống crash khi nhập sai kiểu dữ liệu, chuẩn bị dữ liệu mẫu và điều phối tích hợp luồng chạy. Tuyệt đối không tự tính toán số dư hay can thiệp nghiệp vụ trong UI.

---

## 2. Bảng Phân Chia Nhiệm Vụ 3 Thành Viên (Core Ownership Matrix)

| Thành viên | Tập tin sở hữu chính | Nhiệm vụ kỹ thuật chi tiết | Trọng tâm kiến thức OOP & Điểm bảo vệ |
| :--- | :--- | :--- | :--- |
| **Member 1**<br>*(Account Core)* | `Account.java`<br>`SavingsAccount.java`<br>`CheckingAccount.java`<br>`FixedDepositAccount.java` | • Hiện thực `validateAmount(amount)` và `addTransaction(type, amount, note)` trong `Account`.<br>• Triển khai `deposit(amount, type, note)` và `withdraw(amount, type, note)`.<br>• Cài đặt quy tắc rút tiền riêng cho từng loại tài khoản (`SavingsAccount`, `CheckingAccount`, `FixedDepositAccount`).<br>• Hoàn thiện công thức tính lãi tháng / phí duy trì (`calculateMonthlyAdjustment()` & `applyMonthlyAdjustment()`).<br>• Xử lý kiểm tra ngày đáo hạn `isMatured()` và phạt rút trước hạn `earlyWithdrawalPenaltyRate`.<br>• **Bảo toàn:** Giữ nguyên logic đã hoàn thành của `SavingsAccount` (`minBalance`, lãi tháng).<br>• Viết các test case kiểm tra độc lập từng loại tài khoản. | `abstract class`, `inheritance`, `super()`, method overriding (`@Override`), runtime polymorphism, đóng gói thuộc tính `private`. |
| **Member 2**<br>*(Bank Operations)* | `Customer.java`<br>`Bank.java` | • Quản lý danh sách khách hàng (`addCustomer`).<br>• Mở tài khoản và liên kết 2 chiều với khách hàng (`openAccount`, `addAccount`).<br>• Điều phối nạp/rút tiền: Gọi đúng phương thức 3 tham số của `Account` (`acc.deposit(amount, DEPOSIT, note)`, `acc.withdraw(amount, WITHDRAW, note)`).<br>• Chuyển tiền (`transfer`) tuân thủ nghiêm ngặt nguyên tắc **"Validate before modify"**.<br>• Tra cứu tài khoản theo khách hàng (`getAccountsByCustomer`).<br>• Sắp xếp danh sách tài khoản theo số dư (`getAccountsSortedByBalance`) sử dụng `Comparator`.<br>• Viết test case kiểm tra các nghiệp vụ điều phối của `Bank`. | Quan hệ đối tượng (`Association`), tra cứu bảng băm `HashMap` $O(1)$, thuật toán sắp xếp `Comparator` $O(n \log n)$, an toàn dữ liệu giao dịch. |
| **Member 3**<br>*(Transaction & Console)* | `Transaction.java`<br>`TransactionType.java`<br>`ConsoleMenu.java`<br>`Main.java` | • Cơ chế tự sinh mã giao dịch duy nhất sử dụng biến tĩnh `nextId`.<br>• Định dạng hiển thị chuỗi giao dịch trong `Transaction.toString()`.<br>• Hiển thị lịch sử giao dịch rõ ràng, định dạng ngày tháng thân thiện.<br>• Xây dựng vòng lặp menu `ConsoleMenu` từ chức năng 1 đến 8 và lựa chọn 0 để thoát.<br>• Bọc `try/catch` chống crash khi người dùng nhập sai kiểu dữ liệu.<br>• Khởi tạo dữ liệu mẫu phong phú trong `Main.java` để phục vụ demo.<br>• Phối hợp kiểm thử tích hợp toàn diện luồng chạy (End-to-End).<br>• Chụp ảnh màn hình mã nguồn và kết quả console làm minh chứng báo cáo. | `enum`, biến tĩnh `static`, danh sách động `ArrayList`, xử lý ngoại lệ `InputMismatchException` / `NumberFormatException`, tích hợp hệ thống. |

---

## 3. Lộ Trình Triển Khai Theo Mốc (Milestone-based Plan)

Kế hoạch được cấu trúc theo 5 mốc liên tiếp (M0 đến M4). Mỗi mốc có điều kiện tiên quyết, mục tiêu và Tiêu chí Hoàn thành (Definition of Done - DoD) rõ ràng:

```
[M0: UML Contract Freeze]
       │
       ▼
[M1: Independent Member Implementation]
       │
       ▼
[M2: Feature Integration & E2E Testing]
       │
       ▼
[M3: Final Report Evidence & Screenshots]
       │
       ▼
[M4: Review, Mock Defense & Final Freeze]
```

---

### Mốc M0 — Final UML Contract Freeze (Chốt Khung & Biên Dịch Sạch)
- **Mục tiêu:** Đồng bộ toàn bộ mã nguồn Java với FINAL UML, đảm bảo tất cả file biên dịch sạch 100% không cảnh báo, không thêm bớt bất kỳ class hay phương thức nào ngoài UML.
- **Phụ thuộc:** Không.
- **Công việc thực hiện:**
  - Đồng bộ `Account.java`: Chuyển 5 thuộc tính sang `private`, cập nhật chữ ký `deposit(long, TransactionType, String)` và `withdraw(long, TransactionType, String)`, bổ sung getter `getOpenDate()`.
  - Cập nhật `FixedDepositAccount.java`: Ghi đè phương thức `withdraw(long, TransactionType, String)`.
  - Thích ứng `SavingsAccount.java`: Sử dụng `getBalance()` thay vì truy cập trực tiếp `balance`, bảo toàn logic đã viết.
  - Kiểm tra `CheckingAccount`, `Customer`, `Transaction`, `TransactionType`, `Bank`, `Main`, `ConsoleMenu`.
- **Definition of Done (DoD):**
  - [x] Lệnh `javac -Xlint:all` trên toàn bộ thư mục `src` trả về exit code `0`.
  - [x] Không còn phương thức nào sử dụng chữ ký cũ 1 tham số trong `Account`.
  - [x] Tài liệu dự án được cập nhật đồng bộ với FINAL UML.

---

### Mốc M1 — Independent Member Implementation (Cài Đặt Độc Lập)
- **Mục tiêu:** Từng thành viên hoàn thiện các phương thức được phân công trong phạm vi các file sở hữu; viết mã ngắn gọn, rõ ràng theo đúng chuẩn sinh viên PRO192.
- **Phụ thuộc:** Hoàn thành M0.
- **Công việc thực hiện:**
  - **Member 1:**
    - Cài đặt `validateAmount(amount)`: Kiểm tra `amount > 0`.
    - Cài đặt `addTransaction(type, amount, note)`: Tạo `new Transaction(...)` và thêm vào `transactions`.
    - Cài đặt `deposit(...)` và `withdraw(...)` trong `Account`.
    - Cài đặt quy tắc rút tiền cho `SavingsAccount` (rút không vượt quá số dư tối thiểu).
    - Cài đặt quy tắc rút tiền cho `CheckingAccount` (rút không vượt quá hạn mức thấu chi).
    - Cài đặt `FixedDepositAccount`: Kiểm tra đáo hạn `isMatured()`, xử lý rút trước hạn / đúng hạn trong `withdraw(...)`.
    - Hoàn thiện `calculateMonthlyAdjustment()` và `applyMonthlyAdjustment()` cho cả 3 subclasses.
  - **Member 2:**
    - Cài đặt `addCustomer(Customer customer)`.
    - Cài đặt `openAccount(String customerID, Account account)`.
    - Cài đặt `deposit(String accountNumber, long amount)` và `withdraw(String accountNumber, long amount)` trong `Bank` (gọi phương thức 3 tham số của `Account`).
    - Cài đặt `transfer(...)` theo đúng nguyên tắc "Validate before modify".
    - Cài đặt `applyMonthlyAdjustments()`.
    - Cài đặt `getAccountsByCustomer(String customerId)`.
    - Cài đặt `getAccountsSortedByBalance()` dùng `Comparator`.
  - **Member 3:**
    - Cài đặt cơ chế sinh mã `transactionId` từ `nextId` trong constructor `Transaction`.
    - Cài đặt `Transaction.toString()`.
    - Xây dựng khung menu 8 chức năng trong `ConsoleMenu.java`.
    - Cài đặt `try/catch` bắt lỗi nhập liệu cho từng chức năng.
- **Definition of Done (DoD):**
  - [ ] Toàn bộ TODO trong từng file được thay thế bằng mã nguồn hoàn chỉnh.
  - [ ] Không phát sinh lỗi biên dịch khi compile từng thành phần.
  - [ ] Mỗi thành viên tự kiểm thử đơn vị độc lập với các ca kiểm thử: Normal, Boundary, Invalid Input.
  - [ ] Từng thành viên có khả năng giải thích từng dòng code mình viết.

---

### Mốc M2 — Feature Integration (Tích Hợp Hệ Thống & Kiểm Thử End-to-End)
- **Mục tiêu:** Ghép nối toàn bộ các tầng: `Main` $\rightarrow$ `ConsoleMenu` $\rightarrow$ `Bank` $\rightarrow$ `Account` $\rightarrow$ `Transaction`.
- **Phụ thuộc:** Hoàn thành M1.
- **Công việc thực hiện:**
  - Khởi tạo dữ liệu mẫu phong phú trong `Main.java` (ít nhất 2 khách hàng, mỗi khách hàng có từ 1-2 loại tài khoản khác nhau).
  - Kết nối các lựa chọn trong `ConsoleMenu` gọi trực tiếp các phương thức tương ứng của `Bank`.
  - Chạy thử nghiệm và xác minh hoạt động của cả 8 chức năng F1 đến F8.
  - Kiểm tra đặc biệt:
    - Luồng chuyển tiền F4: Bên gửi bị trừ, bên nhận được cộng, lịch sử giao dịch của cả 2 bên hiển thị chính xác (`TRANSFER_OUT` và `TRANSFER_IN`).
    - Luồng nạp/rút F2, F3: Số dư cập nhật đúng, lịch sử `DEPOSIT`, `WITHDRAW` hiển thị chính xác.
    - Luồng điều chỉnh tháng F7: Kiểm tra lãi cộng vào `SavingsAccount`, phí trừ khỏi `CheckingAccount`.
    - Luồng sắp xếp: Thứ tự số dư tăng dần hoặc giảm dần đúng chuẩn.
- **Definition of Done (DoD):**
  - [ ] Ứng dụng chạy liên tục từ `Main.java` mà không gặp ngoại lệ runtime chưa xử lý.
  - [ ] Cả 8 chức năng thực thi đúng nghiệp vụ theo đặc tả.
  - [ ] Thao tác chuyển tiền đảm bảo tính nguyên tử (không bao giờ mất tiền hay phát sinh tiền sai).

---

### Mốc M3 — Final Report Evidence (Thu Thập Minh Chứng & Chụp Ảnh Báo Cáo)
- **Mục tiêu:** Chụp ảnh màn hình minh chứng mã nguồn và kết quả thực thi Console cho từng chức năng F1–F8 để đưa vào báo cáo đồ án.
- **Phụ thuộc:** Hoàn thành M2.
- **Công việc thực hiện:**
  - Bám sát tài liệu `docs/04_FUNCTIONAL_EVIDENCE_CHECKLIST.md`.
  - Chụp ảnh mã nguồn đại diện cho từng chức năng (làm nổi bật các đoạn code thể hiện OOP: Abstraction, Inheritance, Polymorphism, Encapsulation, Collections, Comparator).
  - Chụp ảnh màn hình Console chạy thực tế cho cả 2 trường hợp: Ca kiểm thử bình thường (Normal case) và Ca kiểm thử biên/lỗi (Boundary/Invalid case).
  - Soạn phần giải thích ngắn gọn, súc tích cho từng hình ảnh: Luồng gọi hàm (Call Flow), tham số đầu vào, biến động dữ liệu, và kết quả hiển thị.
- **Definition of Done (DoD):**
  - [ ] Đầy đủ minh chứng ảnh chụp cho toàn bộ 8 chức năng F1–F8.
  - [ ] Mỗi ảnh chụp có chú thích rõ ràng, gắn liền với ca kiểm thử cụ thể trong danh mục kiểm tra.
  - [ ] Ảnh chụp khớp 100% với mã nguồn thực tế của dự án.

---

### Mốc M4 — Review and Submission (Tổng Duyệt, Vấn Đáp Thử & Đóng Băng Nộp Bài)
- **Mục tiêu:** Rà soát chéo giữa 3 thành viên, tổng duyệt vấn đáp thử nghiệm (Mock Defense), và đóng băng mã nguồn trước khi nộp.
- **Phụ thuộc:** Hoàn thành M3.
- **Công việc thực hiện:**
  - Kiểm tra đối chiếu lần cuối: Mã nguồn khớp 100% với FINAL UML.
  - Biên dịch toàn diện lần cuối bằng `javac -Xlint:all`.
  - Tổ chức buổi vấn đáp thử (Mock Defense):
    - Member 1 trả lời về tính Đa hình trong `withdraw()` và `calculateMonthlyAdjustment()`, lý do trường `balance` là `private`.
    - Member 2 trả lời về nguyên tắc "Validate before modify" trong `transfer()` và lý do dùng `HashMap` thay vì `ArrayList`.
    - Member 3 trả lời về cơ chế sinh mã `transactionId`, định dạng `toString()`, và cách bắt ngoại lệ `try/catch` trong UI.
  - Đóng gói mã nguồn, xuất báo cáo và chuẩn bị nộp bài.
- **Definition of Done (DoD):**
  - [ ] Dự án hoàn toàn sạch lỗi, biên dịch thành công.
  - [ ] Cả 3 thành viên tự tin bảo vệ được toàn bộ dòng code trong phần việc của mình và hiểu rõ phần việc của đồng đội.
  - [ ] Mã nguồn được commit, merge vào `main` và sẵn sàng nộp bài.
