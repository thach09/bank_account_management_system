# ĐẶC TẢ TỔNG QUAN YÊU CẦU DỰ ÁN (PROJECT REQUIREMENTS)
## Môn học: Object-Oriented Programming with Java (PRO192) - Đại học FPT
**Đề tài:** Topic 5 — Bank Account Management System (Hệ thống quản lý tài khoản ngân hàng)  
**Nền tảng:** Console Application (Java Core 100% - Java SE 8+) 
**Kế hoạch thực hiện:** Kế hoạch theo mốc (Milestone-based plan) hướng tới bảo vệ đồ án / Code Review
**Mục tiêu cốt lõi:** Thể hiện chuẩn xác, rõ ràng và dễ bảo vệ các nguyên lý OOP cốt lõi: Abstraction, Inheritance, Polymorphism, Encapsulation, kết hợp Collections (`HashMap`, `ArrayList`) và thuật toán cơ bản (`Comparator`); cấu trúc mã nguồn mức sinh viên năm 2 dễ trace, không over-engineering.

---

## 1. Mục Tiêu Học Thuật & Định Hướng Kiến Trúc (Lecturer-Approved Baseline)

* **FINAL UML là chuẩn thiết kế duy nhất có hiệu lực:**
  - Toàn bộ cấu trúc lớp, thuộc tính, phạm vi truy cập (visibility), và chữ ký phương thức (signature) tuân thủ tuyệt đối sơ đồ FINAL UML đã chốt.
  - Không tự ý thêm bớt class, interface, enum, method, field, hoặc quan hệ ngoài UML.
* **Không dùng Interface thừa:**
  - Không sử dụng `MonthlyAdjustable`, `Displayable` hay bất kỳ interface nào không có trong FINAL UML.
  - Toàn bộ cơ chế điều chỉnh định kỳ được hiện thực qua phương thức trừu tượng `calculateMonthlyAdjustment()` và phương thức cụ thể `applyMonthlyAdjustment()` ngay trong `Account`.
* **Không dùng File I/O (Save/Load):**
  - Chức năng lưu/đọc file không có trong FINAL UML và đã được loại bỏ hoàn toàn khỏi phạm vi dự án để nhóm tập trung 100% vào mô hình hóa OOP và nghiệp vụ trong bộ nhớ (in-memory).
* **Bỏ toàn bộ custom BankException hierarchy:**
  - Không định nghĩa hay sử dụng: `BankException`, `AccountNotFoundException`, `InsufficientFundsException`, `InvalidAmountException`.
  - Nghiệp vụ kiểm soát chặt chẽ bằng **validation đơn giản (`if/else`)** và thông báo lỗi rõ ràng.
  - Tầng Console sử dụng `try/catch` với các exception có sẵn của Java (`NumberFormatException`, `InputMismatchException`) để chống crash khi người dùng nhập sai kiểu dữ liệu.
* **Đóng gói thuộc tính Account (`private`):**
  - Cả 5 thuộc tính của `Account` (`accountNumber`, `balance`, `openDate`, `owner`, `transactions`) đều có phạm vi truy cập `private` (`-` trong UML).
  - Các lớp con (`SavingsAccount`, `CheckingAccount`, `FixedDepositAccount`) không được truy cập trực tiếp `balance`, mà bắt buộc phải thông qua getter công khai `getBalance()`.
* **Phương thức kiểm tra ngày mở tài khoản:**
  - Cung cấp getter công khai `getOpenDate(): Date` trong `Account`.
* **Không đưa nghiệp vụ ngân hàng thực tế phức tạp:**
  - Không dùng concurrency / multi-threading, database transactions, bảo mật mật khẩu, hay xác thực 2 lớp (2FA).

---

## 2. Đặc Tả Chi Tiết 8 Chức Năng Cốt Lõi (Functionalities F1 - F8)

### F1: Mở tài khoản mới (Open Account)
- **Mục tiêu OOP:** Thể hiện `abstract class`, `inheritance`, constructor chaining (`super`).
- **Quy trình:**
  1. Người dùng nhập `customerId` tại Console.
  2. `Bank.openAccount(customerId, account)` kiểm tra sự tồn tại của khách hàng trong `customers`.
  3. Nếu khách hàng hợp lệ: Chọn loại tài khoản (`SavingsAccount`, `CheckingAccount`, `FixedDepositAccount`), nhập các tham số khởi tạo đặc thù.
  4. Tạo instance subclass tương ứng (không thể `new Account()` vì `Account` là abstract).
  5. `Bank` lưu `Account` vào `accounts` (tra cứu theo `accountNumber`) và gọi `customer.addAccount(account)` để thiết lập quan hệ hai chiều.
- **Xử lý lỗi:** Báo lỗi nếu `customerId` không tồn tại hoặc `accountNumber` đã bị trùng.

### F2: Nạp tiền (Deposit Money)
- **Mục tiêu OOP:** Thể hiện phân định trách nhiệm (`Separation of Concerns`) giữa tầng điều phối (`Bank`) và tầng thực thể (`Account`), ghi nhận `Transaction`.
- **Chữ ký phương thức:**
  - Tại `Bank`: `public void deposit(String accountNumber, long amount)` (giữ nguyên 2 tham số tiếp nhận từ Console).
  - Tại `Account`: `public void deposit(long amount, TransactionType type, String note)` (3 tham số theo FINAL UML).
- **Quy trình dự kiến:**
  1. Console nhận `accountNumber` và `amount` $\rightarrow$ gọi `Bank.deposit(accountNumber, amount)`.
  2. `Bank` tìm kiếm tài khoản: `Account acc = accounts.get(accountNumber)`.
  3. Nếu không tìm thấy hoặc `amount <= 0`: Báo lỗi và dừng thao tác.
  4. Nếu hợp lệ: `Bank` điều phối gọi `acc.deposit(amount, TransactionType.DEPOSIT, "Cash deposit")`.
  5. `Account.deposit` gọi nội bộ `validateAmount(amount)`, cập nhật `balance += amount`, và gọi `addTransaction(type, amount, note)` tạo đối tượng `Transaction` lưu vào `transactions`.

### F3: Rút tiền (Withdraw Money - Nơi thể hiện Runtime Polymorphism)
- **Mục tiêu OOP:** Thể hiện **Đa hình động (Dynamic Binding / Method Overriding)**.
- **Chữ ký phương thức:**
  - Tại `Bank`: `public void withdraw(String accountNumber, long amount)` (2 tham số).
  - Tại `Account`: `public void withdraw(long amount, TransactionType type, String note)` (3 tham số).
  - Tại `FixedDepositAccount`: `@Override public void withdraw(long amount, TransactionType type, String note)`.
- **Quy trình dự kiến:**
  1. Console nhận `accountNumber` và `amount` $\rightarrow$ gọi `Bank.withdraw(accountNumber, amount)`.
  2. `Bank` tìm tài khoản và gọi `acc.withdraw(amount, TransactionType.WITHDRAW, "Cash withdrawal")`.
  3. `Bank` tuyệt đối không dùng `instanceof` để kiểm tra loại tài khoản. Thay vào đó, máy ảo Java (JVM) tự động điều hướng:
     - **SavingsAccount:** Rút tiền đảm bảo số dư sau rút không nhỏ hơn `getMinAllowedBalance()` (số dư tối thiểu `minBalance`).
     - **CheckingAccount:** Cho phép rút vượt số dư hiện tại trong giới hạn hạn mức thấu chi (`balance - amount >= getMinAllowedBalance()`, với `getMinAllowedBalance() = -overdraftLimit`).
     - **FixedDepositAccount:** Ghi đè `withdraw(...)` để kiểm tra điều kiện đáo hạn thông qua `isMatured()`. Nếu rút trước hạn, áp dụng tỷ lệ phạt rút trước hạn `earlyWithdrawalPenaltyRate` (chi tiết công thức phạt do nhóm thống nhất khi hiện thực).
  4. Khi rút hợp lệ: Trừ số dư, ghi nhận `Transaction` với loại `WITHDRAW`.

### F4: Chuyển khoản giữa hai tài khoản (Transfer Money)
- **Mục tiêu OOP:** Quản lý tính toàn vẹn dữ liệu, phối hợp nhiều đối tượng trong `Bank`.
- **Chữ ký phương thức:** `public void transfer(String fromAcc, String toAcc, long amount)` trong `Bank`.
- **Nguyên tắc cốt lõi: "Validate before modify" (Kiểm tra toàn bộ trước khi sửa đổi bất kỳ số dư nào):**
  1. Tìm tài khoản nguồn `fromAccount = accounts.get(fromAcc)`.
  2. Tìm tài khoản đích `toAccount = accounts.get(toAcc)`.
  3. Kiểm tra tính hợp lệ của hai tài khoản: Cả 2 phải tồn tại trong hệ thống.
  4. Kiểm tra `!fromAcc.equals(toAcc)` (không được tự chuyển khoản cho chính mình).
  5. Kiểm tra số tiền chuyển `amount > 0`.
  6. Kiểm tra tài khoản nguồn có đủ điều kiện rút số tiền `amount` hay không (dựa trên quy tắc số dư tối thiểu / thấu chi của tài khoản nguồn).
  7. Chỉ khi **tất cả** các bước kiểm tra trên đều vượt qua, mới thực hiện thay đổi dữ liệu:
     - `fromAccount.withdraw(amount, TransactionType.TRANSFER_OUT, "Transfer to " + toAcc)`
     - `toAccount.deposit(amount, TransactionType.TRANSFER_IN, "Transfer from " + fromAcc)`
  8. Kết quả: Bên gửi có một bản ghi `TRANSFER_OUT`, bên nhận có một bản ghi `TRANSFER_IN`. Không bao giờ xảy ra tình trạng tài khoản nguồn bị trừ tiền mà tài khoản đích chưa được cộng tiền.

### F5: Liệt kê danh sách tài khoản của một khách hàng (List Accounts by Customer)
- **Mục tiêu OOP:** Khai thác mối quan hệ liên kết (`Association`) giữa `Customer` và `Account`.
- **Chữ ký phương thức:** `public List<Account> getAccountsByCustomer(String customerId)` trong `Bank`.
- **Quy trình:**
  1. Tra cứu `Customer` trong `customers` bằng `customerId`.
  2. Nếu tìm thấy, trả về danh sách tài khoản liên kết `customer.getAccounts()`.
  3. Console duyệt danh sách và in chi tiết: Số tài khoản, loại tài khoản (`getAccountType()`), số dư hiện tại (`getBalance()`), ngày mở (`getOpenDate()`).

### F6: Xem lịch sử giao dịch của tài khoản (Transaction History)
- **Mục tiêu OOP:** Sử dụng `ArrayList` quản lý danh sách giao dịch, truy xuất đóng gói qua getter.
- **Chữ ký phương thức:** `public List<Transaction> getTransactions()` trong `Account`.
- **Quy trình:**
  1. Nhập `accountNumber` $\rightarrow$ lấy `Account` từ `Bank`.
  2. Lấy danh sách giao dịch: `List<Transaction> txList = acc.getTransactions()`.
  3. Duyệt danh sách và in thông tin từng giao dịch: Mã giao dịch (`transactionId`), thời gian (`timestamp`), phân loại (`type`: `DEPOSIT`, `WITHDRAW`, `TRANSFER_IN`, `TRANSFER_OUT`, `INTEREST`, `FEE`), số tiền (`amount`), số dư sau giao dịch (`balanceAfter`), và ghi chú (`note`).
  4. Nếu tài khoản chưa có giao dịch: Thông báo tài khoản chưa phát sinh giao dịch.

### F7: Tính lãi hoặc trừ phí định kỳ hàng tháng (Apply Monthly Adjustments)
- **Mục tiêu OOP:** Thể hiện tính Đa hình động trên tập hợp đối tượng đa hình (`Polymorphic Collection`).
- **Chữ ký phương thức:**
  - Tại `Bank`: `public void applyMonthlyAdjustments()`
  - Tại `Account`:
    - `public abstract long calculateMonthlyAdjustment()`
    - `public void applyMonthlyAdjustment()`
- **Quy trình dự kiến:**
  1. `Bank` duyệt qua toàn bộ tài khoản trong hệ thống:
     ```java
     for (Account acc : accounts.values()) {
         acc.applyMonthlyAdjustment();
     }
     ```
  2. Mỗi subclass thực hiện tính toán khoản điều chỉnh theo công thức riêng:
     - `SavingsAccount`: Tính lãi tháng theo công thức đã kiểm chứng:
       $$\text{Lãi} = \text{Math.round}(\text{getBalance}() \times \text{interestRate} / 12)$$
       Nếu lãi $> 0$, số dư được cộng thêm và ghi nhận `Transaction` loại `INTEREST`.
     - `CheckingAccount`: Xác định khoản phí duy trì hàng tháng (`monthlyFee`). Số dư bị trừ đi khoản phí này (có thể làm số dư âm trong phạm vi `overdraftLimit`) và ghi nhận `Transaction` loại `FEE`.
     - `FixedDepositAccount`: Kiểm tra trạng thái đáo hạn dựa trên `maturityDate`. Nếu đến hạn (`isMatured() == true`), tính lãi toàn kỳ hoặc lãi tháng theo quy định thống nhất của nhóm và ghi nhận `Transaction` loại `INTEREST`.
  3. *Lưu ý kiến trúc:* `FixedDepositAccount` không có trường `monthsElapsed` trong UML. Việc xác định đáo hạn được thực hiện thông qua so sánh thời gian hiện tại với `maturityDate` trong `isMatured()`.

### F8: Kiểm soát nhập liệu và điều hướng Menu trên Console (Console Navigation & Input Validation)
- **Mục tiêu OOP:** Tách bạch tầng giao diện người dùng (UI) khỏi tầng nghiệp vụ lõi (Domain / Service).
- **Quy trình:**
  1. Menu dạng văn bản hiển thị danh sách từ chức năng 1 đến 8 và lựa chọn 0 để thoát (Exit).
  2. Sử dụng vòng lặp `do-while` duy trì chương trình.
  3. Xử lý triệt để ngoại lệ nhập liệu bằng `try/catch` bọc quanh `Scanner` / `Integer.parseInt` để bắt `InputMismatchException` hoặc `NumberFormatException`, in thông báo hướng dẫn và yêu cầu nhập lại, tuyệt đối không để ứng dụng crash đột ngột.
  4. `ConsoleMenu` không tự ý sửa số dư, không tự tính lãi, không nhét logic nghiệp vụ vào UI.

---

## 3. Hệ Thống Enum Phân Loại Giao Dịch (TransactionType)

FINAL UML định nghĩa chính xác 6 hằng số enum trong `TransactionType`:

| Hằng số Enum | Nghiệp vụ phát sinh | Ý nghĩa hiển thị & Vai trò |
| :--- | :--- | :--- |
| `DEPOSIT` | Nạp tiền mặt tại quầy / ATM | Tăng số dư tài khoản |
| `WITHDRAW` | Rút tiền mặt | Giảm số dư tài khoản |
| `TRANSFER_IN` | Nhận tiền chuyển khoản từ tài khoản khác | Tăng số dư bên nhận, kèm ghi chú số tài khoản gửi |
| `TRANSFER_OUT` | Chuyển tiền sang tài khoản khác | Giảm số dư bên gửi, kèm ghi chú số tài khoản nhận |
| `INTEREST` | Cộng lãi định kỳ hàng tháng / đáo hạn | Tăng số dư, ghi nhận nguồn lãi (`SavingsAccount` / `FixedDepositAccount`) |
| `FEE` | Khấu trừ phí quản lý hàng tháng | Giảm số dư, ghi nhận phí duy trì (`CheckingAccount`) |

---

## 4. Cấu Trúc Dữ Liệu & Độ Phức Tạp Thuật Toán (Collections & Complexity)

| Thao tác nghiệp vụ | Cấu trúc dữ liệu & Thuật toán | Độ phức tạp | Luận điểm giải thích trước Giảng viên |
| :--- | :--- | :--- | :--- |
| Tìm tài khoản theo STK | `HashMap<String, Account>` (`get`) | $O(1)$ trung bình | Khóa chính duy nhất, tra cứu tức thời qua bảng băm; tối ưu hơn hẳn tìm tuyến tính $O(n)$ trong List. |
| Tìm khách hàng theo ID | `HashMap<String, Customer>` (`get`) | $O(1)$ trung bình | Tra cứu trực tiếp theo mã khách hàng duy nhất. |
| Ghi lịch sử giao dịch | `ArrayList<Transaction>` (`add`) | $O(1)$ amortized | Ghi nhận tuần tự vào cuối danh sách theo trật tự thời gian. |
| In lịch sử giao dịch | Duyệt tuần tự `ArrayList` | $O(n)$ | In toàn bộ lịch sử theo đúng thứ tự thời gian phát sinh. |
| Sắp xếp tài khoản theo số dư | Copy sang `ArrayList` + `Comparator` | $O(n \log n)$ | Sử dụng TimSort tích hợp sẵn trong Java Core, minh họa kỹ năng sử dụng Interface chức năng cơ bản. |

> **Quy tắc bảo vệ đồ án:** Không dùng Binary Search cho STK vì tra cứu qua `HashMap` đã đạt $O(1)$. Không đưa các thuật toán phức tạp không cần thiết vào dự án.

---

## 5. Các Vấn Đề Nghiệp Vụ Cần Thống Nhất Nội Bộ Nhóm (Team Decisions)

Các điểm sau đây là các quyết định nghiệp vụ nội bộ khi bước vào giai đoạn cài đặt logic chi tiết (Milestone M1), không phải là thay đổi cấu trúc UML:
1. **Công thức phạt rút trước hạn đối với `FixedDepositAccount`:**
   Nhóm cần thống nhất: Khi `isMatured() == false`, khách hàng rút tiền sẽ nhận mức lãi suất không kỳ hạn (ví dụ $0.5\%/\text{năm}$) hay bị trừ một tỷ lệ phần trăm trực tiếp trên số tiền rút theo `earlyWithdrawalPenaltyRate`.
2. **Quy tắc tạo mã giao dịch (`transactionId`):**
   Sử dụng biến tĩnh `nextId` tự tăng trong `Transaction` để tạo mã định danh duy nhất (ví dụ: `TXN0001`, `TXN0002`...).
3. **Định dạng hiển thị thời gian (`timestamp`):**
   Sử dụng `SimpleDateFormat` chuẩn trong `ConsoleMenu` hoặc `Transaction.toString()` để in ngày tháng thân thiện (ví dụ: `dd/MM/yyyy HH:mm:ss`).
