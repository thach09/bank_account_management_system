# DANH MỤC MINH CHỨNG CHỨC NĂNG & KỊCH BẢN CHỤP ẢNH BÁO CÁO (FUNCTIONAL EVIDENCE CHECKLIST)
## Môn học: Object-Oriented Programming with Java (PRO192) - Đại học FPT
**Dự án:** Topic 5 — Bank Account Management System  
**Mục đích:** Cung cấp danh mục chi tiết hướng dẫn 3 thành viên chuẩn bị dữ liệu, kịch bản kiểm thử (Test Cases), luồng gọi hàm thực thi (Runtime Call Flow) và các vị trí cần chụp ảnh màn hình (Mã nguồn + Kết quả Console) để đưa vào Báo cáo Đồ án cuối kỳ và phục vụ Vấn đáp bảo vệ (Defense).

> **Ghi chú quan trọng:** Toàn bộ các luồng gọi hàm và kết quả kiểm thử dưới đây là **kịch bản định hướng triển khai tương lai** (Future Implementation Flow) phục vụ các mốc M1–M3, không phải là kết quả đã cài đặt sẵn trong khung mã nguồn hiện tại.

---

## 1. Bảng Tổng Hợp Ma Trận Chức Năng & Phân Công (F1 - F8)

| Mã | Tên chức năng | Thành viên phụ trách | Lớp & Phương thức liên quan | Khái niệm OOP / Kỹ thuật trọng tâm |
| :---: | :--- | :---: | :--- | :--- |
| **F1** | Mở tài khoản mới | **Member 2** | `Bank.openAccount()`, `Customer.addAccount()`, Subclass constructors | `abstract class`, `inheritance`, constructor chaining (`super`). |
| **F2** | Nạp tiền | **Member 2** & **Member 1** | `Bank.deposit()`, `Account.deposit()`, `Account.validateAmount()`, `Account.addTransaction()` | Phân định trách nhiệm (Orchestration vs Domain), kiểm soát biến động số dư. |
| **F3** | Rút tiền | **Member 1** & **Member 2** | `Bank.withdraw()`, `Account.withdraw()`, Subclass `withdraw()` | **Đa hình động (Runtime Polymorphism)**, ghi đè phương thức (`@Override`). |
| **F4** | Chuyển khoản | **Member 2** & **Member 1** | `Bank.transfer()`, `Account.withdraw()`, `Account.deposit()` | Nguyên tắc **"Validate before modify"**, tính toàn vẹn dữ liệu. |
| **F5** | Liệt kê tài khoản khách hàng | **Member 2** | `Bank.getAccountsByCustomer()`, `Customer.getAccounts()` | Quan hệ đối tượng liên kết (`Association`), getter encapsulation. |
| **F6** | Xem lịch sử giao dịch | **Member 3** | `Account.getTransactions()`, `Transaction.toString()`, `ConsoleMenu` | `ArrayList` collection, `enum` phân loại, định dạng chuỗi. |
| **F7** | Tính lãi / Trừ phí định kỳ | **Member 1** & **Member 2** | `Bank.applyMonthlyAdjustments()`, Subclass `calculateMonthlyAdjustment()`, `Account.applyMonthlyAdjustment()` | Đa hình trên tập hợp đa hình (`Polymorphic Collection`), thuật toán tính toán. |
| **F8** | Điều hướng & Validate Console | **Member 3** | `ConsoleMenu.start()`, `ConsoleMenu.displayMenu()` | Phân tầng UI/Service, vòng lặp điều khiển, xử lý ngoại lệ `try/catch`. |

---

## 2. Kịch Bản Kiểm Thử & Hướng Dẫn Chụp Ảnh Từng Chức Năng

---

### F1: Mở Tài Khoản Mới (Open Account)
- **Thành viên phụ trách:** Member 2 (hỗ trợ bởi constructors của Member 1)
- **Phương thức liên quan:**
  - `public void openAccount(String customerID, Account account)` trong `Bank`
  - `public void addAccount(Account account)` trong `Customer`
  - Constructors: `SavingsAccount(...)`, `CheckingAccount(...)`, `FixedDepositAccount(...)`
- **Dữ liệu đầu vào:**
  - Mã khách hàng: `CUST001` (đã có trong hệ thống)
  - Loại tài khoản: Chọn `1` (`SavingsAccount`)
  - Số tài khoản: `SAV101`, Tiền nạp ban đầu: `1,000,000`, Lãi suất: `0.05` (5%/năm), Số dư tối thiểu: `50,000`
- **Kết quả mong đợi:** Tài khoản `SAV101` được lưu vào `Bank.accounts` và nằm trong danh sách `accounts` của khách hàng `CUST001`.
- **Luồng gọi hàm thực thi (Call Flow):**
  ```text
  ConsoleMenu
      ↓
  Bank.openAccount("CUST001", new SavingsAccount(...))
      ↓
  customers.get("CUST001") [Kiểm tra tồn tại]
      ↓
  accounts.put("SAV101", account)
      ↓
  customer.addAccount(account)
      ↓
  Console: In thông báo mở tài khoản thành công
  ```
- **Ca kiểm thử (Test Cases):**
  - *Ca bình thường (Normal):* Mở thành công với thông tin hợp lệ.
  - *Ca biên / lỗi (Invalid):* Nhập `customerId` không tồn tại trong hệ thống $\rightarrow$ Hệ thống báo lỗi "Customer not found", không tạo tài khoản; hoặc nhập số tài khoản đã tồn tại $\rightarrow$ Báo lỗi trùng số tài khoản.
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Thân hàm `Bank.openAccount()` (kiểm tra customer, lưu map, gọi `addAccount`).
  2. Constructor chaining của subclass (dòng gọi `super(...)`).
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Màn hình nhập thông tin và thông báo mở tài khoản thành công.
  2. Màn hình thông báo lỗi khi nhập mã khách hàng không tồn tại.
- **Giải thích bảo vệ vấn đáp:**
  - *"Tại sao không thể `new Account(...)` trực tiếp?"* $\rightarrow$ Vì `Account` là `abstract class`, đại diện cho khái niệm tài khoản tổng quát; chỉ có các lớp con cụ thể (`SavingsAccount`, `CheckingAccount`, `FixedDepositAccount`) mới được khởi tạo đối tượng thực tế.

---

### F2: Nạp Tiền Vào Tài Khoản (Deposit Money)
- **Thành viên phụ trách:** Member 2 (điều phối) & Member 1 (nghiệp vụ tài khoản)
- **Phương thức liên quan:**
  - `Bank.deposit(String accountNumber, long amount)`
  - `Account.deposit(long amount, TransactionType type, String note)`
  - `Account.validateAmount(long amount)`
  - `Account.addTransaction(TransactionType type, long amount, String note)`
- **Dữ liệu đầu vào:**
  - Số tài khoản: `SAV101`, Số tiền nạp: `500,000`
- **Kết quả mong đợi:** Số dư tài khoản tăng thêm `500,000`; danh sách giao dịch có thêm 1 bản ghi loại `DEPOSIT` với số tiền `500,000` và ghi chú rõ ràng.
- **Luồng gọi hàm thực thi (Call Flow):**
  ```text
  ConsoleMenu
      ↓
  Bank.deposit(accountNumber, amount)
      ↓
  Account.deposit(amount, TransactionType.DEPOSIT, "Cash deposit")
      ↓
  validateAmount(amount) [Kiểm tra amount > 0]
      ↓
  balance += amount [Cập nhật số dư nội bộ]
      ↓
  addTransaction(type, amount, note) [Tạo và lưu Transaction vào transactions]
      ↓
  Console: In thông báo nạp tiền thành công và số dư mới
  ```
- **Ca kiểm thử (Test Cases):**
  - *Ca bình thường (Normal):* Nạp `500,000` vào tài khoản có sẵn `1,000,000` $\rightarrow$ Số dư mới `1,500,000`.
  - *Ca biên / lỗi (Invalid):* Nạp số tiền âm (ví dụ: `-100,000`) hoặc bằng `0` $\rightarrow$ `validateAmount()` bắt lỗi, từ chối nạp tiền, số dư giữ nguyên. Hoặc nạp vào số tài khoản không tồn tại $\rightarrow$ Báo "Account not found".
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Phương thức `Bank.deposit()` gọi `acc.deposit(amount, TransactionType.DEPOSIT, "Cash deposit")`.
  2. Phương thức `Account.deposit()` thể hiện các bước: validate $\rightarrow$ cập nhật balance $\rightarrow$ thêm transaction.
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Nạp tiền thành công và hiển thị số dư sau nạp.
  2. Thử nạp số tiền $\le 0$ và nhận thông báo từ chối.
- **Giải thích bảo vệ vấn đáp:**
  - *"Tại sao `Bank.deposit` có 2 tham số mà `Account.deposit` lại có 3 tham số?"* $\rightarrow$ Tầng `Bank` là tầng điều phối tiếp nhận yêu cầu từ người dùng (chỉ gồm STK và số tiền); khi ủy quyền xuống thực thể `Account`, `Bank` bổ sung ngữ cảnh giao dịch (`TransactionType.DEPOSIT` và ghi chú `"Cash deposit"`) để `Account` tự đóng gói và tạo bản ghi lịch sử tương ứng.

---

### F3: Rút Tiền Từ Tài Khoản (Withdraw Money - Runtime Polymorphism)
- **Thành viên phụ trách:** Member 1 (quy tắc từng loại tài khoản) & Member 2 (điều phối)
- **Phương thức liên quan:**
  - `Bank.withdraw(String accountNumber, long amount)`
  - `Account.withdraw(long amount, TransactionType type, String note)`
  - `FixedDepositAccount.withdraw(long amount, TransactionType type, String note)`
  - `Account.getMinAllowedBalance()` (được override ở 3 lớp con)
- **Dữ liệu đầu vào:**
  - Thử nghiệm trên cả 3 loại tài khoản để chứng minh Đa hình:
    1. `SavingsAccount` (STK `SAV101`, số dư `1,000,000`, min balance `50,000`): Rút `200,000`.
    2. `CheckingAccount` (STK `CHK201`, số dư `200,000`, overdraft `500,000`): Rút `500,000` (vượt số dư nhưng trong hạn mức).
    3. `FixedDepositAccount` (STK `FD301`, chưa đến hạn `isMatured() == false`): Thử rút tiền.
- **Kết quả mong đợi:**
  - `SavingsAccount`: Rút thành công nếu `balance - amount >= minBalance`.
  - `CheckingAccount`: Rút thành công nếu `balance - amount >= -overdraftLimit` (số dư trở thành `-300,000`).
  - `FixedDepositAccount`: Báo phạt rút trước hạn theo quy tắc nhóm hoặc từ chối nếu chưa đáo hạn.
- **Luồng gọi hàm thực thi (Call Flow):**
  ```text
  ConsoleMenu
      ↓
  Bank.withdraw(accountNumber, amount)
      ↓
  accounts.get(accountNumber)
      ↓
  account.withdraw(amount, TransactionType.WITHDRAW, "Cash withdrawal")
      ↓ [JVM Dynamic Binding kích hoạt logic của subclass tương ứng]
  Kiểm tra điều kiện: (balance - amount >= getMinAllowedBalance())
      ↓
  balance -= amount
      ↓
  addTransaction(type, amount, note)
      ↓
  Console: In thông báo rút tiền thành công
  ```
- **Ca kiểm thử (Test Cases):**
  - *Ca bình thường (Normal):* Rút tiền hợp lệ trong giới hạn cho phép của từng loại tài khoản.
  - *Ca biên / lỗi (Boundary/Invalid):*
    - `SavingsAccount`: Rút vượt mức làm số dư $< 50,000$ (ví dụ: số dư $100,000$ rút $60,000$) $\rightarrow$ Từ chối giao dịch.
    - `CheckingAccount`: Rút vượt quá hạn mức thấu chi (số dư $0$, hạn mức thấu chi $500,000$, rút $600,000$) $\rightarrow$ Từ chối giao dịch.
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Dòng gọi đa hình trong `Bank.withdraw()` (không có `instanceof`).
  2. Ghi đè phương thức `withdraw()` trong `FixedDepositAccount` và các hiện thực `getMinAllowedBalance()`.
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Rút tiền thành công trên `SavingsAccount` và `CheckingAccount` (hiển thị số dư âm hợp lệ).
  2. Bị từ chối rút khi vi phạm hạn mức số dư tối thiểu.
- **Giải thích bảo vệ vấn đáp:**
  - *"Tính đa hình động (Dynamic Binding) thể hiện như thế nào khi rút tiền?"* $\rightarrow$ Biến tham chiếu trong `Bank` có kiểu khai báo là lớp cha `Account`, nhưng đối tượng thực tế tại runtime là `SavingsAccount`, `CheckingAccount` hoặc `FixedDepositAccount`. Khi gọi `account.withdraw(...)`, JVM tự động liên kết tới hành vi của đối tượng thực tế mà không cần dùng bất kỳ câu lệnh `if (acc instanceof ...)` nào.

---

### F4: Chuyển Khoản Giữa Hai Tài Khoản (Transfer Money)
- **Thành viên phụ trách:** Member 2 (chủ trì) & Member 1 (phối hợp)
- **Phương thức liên quan:**
  - `public void transfer(String fromAcc, String toAcc, long amount)` trong `Bank`
  - `Account.withdraw(amount, TransactionType.TRANSFER_OUT, note)`
  - `Account.deposit(amount, TransactionType.TRANSFER_IN, note)`
- **Dữ liệu đầu vào:**
  - Tài khoản chuyển: `SAV101`, Tài khoản nhận: `CHK201`, Số tiền: `300,000`
- **Kết quả mong đợi:**
  - `SAV101` bị trừ `300,000` và có 1 bản ghi `TRANSFER_OUT`.
  - `CHK201` được cộng `300,000` và có 1 bản ghi `TRANSFER_IN`.
  - Tổng số tiền giữa 2 tài khoản được bảo toàn.
- **Luồng gọi hàm thực thi (Call Flow):**
  ```text
  ConsoleMenu
      ↓
  Bank.transfer(fromAcc, toAcc, amount)
      ↓
  [BƯỚC KIỂM TRA TOÀN DIỆN - VALIDATE BEFORE MODIFY]
  1. fromAccount != null && toAccount != null
  2. !fromAcc.equals(toAcc)
  3. amount > 0
  4. fromAccount thỏa mãn điều kiện rút amount
      ↓ [Nếu bất kỳ điều kiện nào sai -> Báo lỗi cụ thể và dừng ngay]
  fromAccount.withdraw(amount, TransactionType.TRANSFER_OUT, "Transfer to " + toAcc)
      ↓
  toAccount.deposit(amount, TransactionType.TRANSFER_IN, "Transfer from " + fromAcc)
      ↓
  Console: In thông báo chuyển khoản thành công
  ```
- **Ca kiểm thử (Test Cases):**
  - *Ca bình thường (Normal):* Chuyển khoản thành công giữa 2 tài khoản hợp lệ với số tiền nằm trong khả năng chi trả của tài khoản nguồn.
  - *Ca biên / lỗi (Invalid):*
    - Chuyển cho chính mình (`fromAcc == toAcc`) $\rightarrow$ Báo lỗi.
    - Tài khoản nguồn không đủ số dư / vượt hạn mức thấu chi $\rightarrow$ Báo lỗi, không tài khoản nào bị thay đổi số dư.
    - Tài khoản đích không tồn tại $\rightarrow$ Báo lỗi, tài khoản nguồn không bị trừ tiền.
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Toàn bộ phương thức `Bank.transfer()` thể hiện rõ khối "Validate before modify" trước khi gọi bất kỳ hàm trừ/cộng tiền nào.
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Màn hình thực hiện chuyển tiền thành công.
  2. Màn hình lịch sử giao dịch của tài khoản gửi (thấy rõ `TRANSFER_OUT`).
  3. Màn hình lịch sử giao dịch của tài khoản nhận (thấy rõ `TRANSFER_IN`).
  4. Màn hình báo lỗi khi tài khoản nguồn không đủ số dư.
- **Giải thích bảo vệ vấn đáp:**
  - *"Tại sao phải tuân thủ nghiêm ngặt nguyên tắc 'Validate before modify'?"* $\rightarrow$ Trong môi trường không có cơ chế rollback giao dịch của database, nếu ta trừ tiền tài khoản nguồn trước rồi mới kiểm tra tài khoản đích, khi tài khoản đích không hợp lệ thì tài khoản nguồn đã bị mất tiền vô lý. Việc kiểm tra toàn bộ điều kiện trước đảm bảo tính toàn vẹn và nguyên tử của giao dịch.

---

### F5: Liệt Kê Danh Sách Tài Khoản Khách Hàng (List Accounts by Customer)
- **Thành viên phụ trách:** Member 2
- **Phương thức liên quan:**
  - `public List<Account> getAccountsByCustomer(String customerId)` trong `Bank`
  - `public List<Account> getAccounts()` trong `Customer`
- **Dữ liệu đầu vào:** Mã khách hàng `CUST001`
- **Kết quả mong đợi:** In toàn bộ các tài khoản thuộc quyền sở hữu của `CUST001` kèm theo thông tin chi tiết: Loại tài khoản, Số tài khoản, Số dư, Ngày mở.
- **Luồng gọi hàm thực thi (Call Flow):**
  ```text
  ConsoleMenu
      ↓
  Bank.getAccountsByCustomer("CUST001")
      ↓
  customers.get("CUST001")
      ↓
  customer.getAccounts()
      ↓
  Console: Duyệt danh sách và in bảng thông tin tài khoản
  ```
- **Ca kiểm thử (Test Cases):**
  - *Ca bình thường (Normal):* Khách hàng có 2 tài khoản (`SAV101`, `CHK201`) $\rightarrow$ In đủ 2 tài khoản.
  - *Ca biên / lỗi (Invalid):* Khách hàng chưa mở tài khoản nào $\rightarrow$ Thông báo danh sách trống; Khách hàng không tồn tại $\rightarrow$ Báo lỗi "Customer not found".
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Phương thức `Bank.getAccountsByCustomer()`.
  2. Vòng lặp hiển thị danh sách tài khoản trong `ConsoleMenu`.
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Bảng in danh sách tài khoản định dạng gọn gàng theo từng cột.

---

### F6: Xem Lịch Sử Giao Dịch Của Tài Khoản (Transaction History)
- **Thành viên phụ trách:** Member 3
- **Phương thức liên quan:**
  - `Account.getTransactions()`
  - `Transaction.toString()`
  - `ConsoleMenu`
- **Dữ liệu đầu vào:** Số tài khoản `SAV101` (đã thực hiện nạp tiền F2 và chuyển tiền F4)
- **Kết quả mong đợi:** Hiển thị tuần tự toàn bộ các giao dịch đã diễn ra trên tài khoản theo trật tự thời gian.
- **Luồng gọi hàm thực thi (Call Flow):**
  ```text
  ConsoleMenu
      ↓
  Bank.getAccount("SAV101")
      ↓
  account.getTransactions()
      ↓
  Duyệt List<Transaction> và gọi transaction.toString()
      ↓
  Console: In từng dòng giao dịch ra màn hình
  ```
- **Ca kiểm thử (Test Cases):**
  - *Ca bình thường (Normal):* In danh sách giao dịch gồm nhiều loại: `DEPOSIT`, `TRANSFER_OUT`, `INTEREST`...
  - *Ca biên / lỗi (Boundary):* Tài khoản vừa mở, chưa có giao dịch nào $\rightarrow$ Thông báo "No transactions found".
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Định nghĩa constructor và phương thức `toString()` trong `Transaction.java`.
  2. Phương thức in lịch sử giao dịch trong `ConsoleMenu.java`.
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Bảng lịch sử giao dịch với đầy đủ các cột: Mã GD, Loại GD, Số tiền, Số dư sau GD, Thời gian, Ghi chú.

---

### F7: Tính Lãi Hoặc Trừ Phí Hàng Tháng (Monthly Adjustments)
- **Thành viên phụ trách:** Member 1 & Member 2
- **Phương thức liên quan:**
  - `Bank.applyMonthlyAdjustments()`
  - `Account.applyMonthlyAdjustment()`
  - `SavingsAccount.calculateMonthlyAdjustment()`
  - `CheckingAccount.calculateMonthlyAdjustment()`
  - `FixedDepositAccount.calculateMonthlyAdjustment()`
- **Dữ liệu đầu vào:** Kích hoạt chức năng định kỳ hàng tháng từ menu.
- **Kết quả mong đợi:**
  - `SavingsAccount`: Số dư được cộng thêm lãi tháng: $\text{Lãi} = \text{Math.round}(\text{getBalance}() \times \text{interestRate} / 12)$; ghi nhận giao dịch `INTEREST`.
  - `CheckingAccount`: Số dư bị trừ đi khoản phí hàng tháng `monthlyFee`; ghi nhận giao dịch `FEE`.
- **Luồng gọi hàm thực thi (Call Flow):**
  ```text
  ConsoleMenu
      ↓
  Bank.applyMonthlyAdjustments()
      ↓
  for (Account acc : accounts.values())
      ↓
  acc.applyMonthlyAdjustment() [Gọi phương thức cụ thể của subclass]
      ↓
  long adj = calculateMonthlyAdjustment()
      ↓
  Cập nhật số dư: balance += adj (nếu lãi) hoặc balance -= fee (nếu phí)
      ↓
  addTransaction(INTEREST / FEE, adj, note)
      ↓
  Console: In báo cáo tổng hợp kết quả điều chỉnh của toàn bộ tài khoản
  ```
- **Ca kiểm thử (Test Cases):**
  - *Ca bình thường (Normal):* Chạy chức năng, sau đó tra cứu lại số dư và lịch sử giao dịch của cả `SavingsAccount` và `CheckingAccount` để xác nhận số dư đã biến động chính xác theo công thức.
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Vòng lặp duyệt collection trong `Bank.applyMonthlyAdjustments()`.
  2. Công thức tính lãi trong `SavingsAccount.calculateMonthlyAdjustment()`.
  3. Phương thức `applyMonthlyAdjustment()` trong `Account.java`.
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Màn hình thực thi tính điều chỉnh định kỳ.
  2. Màn hình lịch sử giao dịch của `SavingsAccount` xuất hiện bản ghi `INTEREST`.
  3. Màn hình lịch sử giao dịch của `CheckingAccount` xuất hiện bản ghi `FEE`.
- **Giải thích bảo vệ vấn đáp:**
  - *"Tại sao trong `SavingsAccount` ta lại viết `getBalance() * interestRate / 12` mà không viết `balance * interestRate / 12`?"* $\rightarrow$ Vì trong FINAL UML, thuộc tính `balance` ở lớp cha `Account` được khai báo phạm vi `private` để đảm bảo tính đóng gói (`Encapsulation`). Các lớp con không được quyền truy cập trực tiếp mà phải thông qua getter công khai `getBalance()`.

---

### F8: Điều Hướng Menu & Bắt Lỗi Nhập Liệu (Console Navigation & Input Validation)
- **Thành viên phụ trách:** Member 3
- **Phương thức liên quan:**
  - `ConsoleMenu.start()`
  - `ConsoleMenu.displayMenu()`
- **Dữ liệu đầu vào:**
  - Người dùng nhập ký tự chữ (ví dụ: `abc`) tại ô chọn chức năng hoặc ô nhập số tiền.
- **Kết quả mong đợi:** Chương trình bắt được `InputMismatchException` hoặc `NumberFormatException`, in thông báo yêu cầu nhập lại số hợp lệ, không bị crash chương trình.
- **Vị trí chụp ảnh mã nguồn (Source Code Screenshots):**
  1. Khối `try/catch` bọc quanh lệnh đọc input từ `Scanner` trong `ConsoleMenu.java`.
- **Ảnh chụp kết quả Console (Console Screenshots):**
  1. Giao diện Menu chính gồm đầy đủ các mục từ 1 đến 8 và 0 để thoát.
  2. Thử nhập chuỗi ký tự bất thường tại ô nhập số và màn hình thông báo lỗi an toàn.

