# ĐẶC TẢ TỔNG QUAN YÊU CẦU DỰ ÁN (PROJECT REQUIREMENTS)
## Môn học: Object-Oriented Programming with Java (OOP - PRO192) - Đại học FPT
**Đề tài:** Topic 5 — Bank Account Management System (Hệ thống quản lý tài khoản ngân hàng)  
**Nền tảng:** Console Application (Java Core 100%)  
**Mục tiêu cốt lõi:** Áp dụng thuần thục 4 trụ cột OOP, Collections Framework, Xử lý ngoại lệ và Thuật toán; Hiểu 100% mã nguồn để bảo vệ thành công trước hội đồng chấm thi.

---

## 1. Mục Tiêu Học Thuật & Tiêu Chí Chấm Điểm (Rubric FPT)

| Tiêu chuẩn bắt buộc | Định hướng hiện thực trong dự án | Ghi chú phản biện |
| :--- | :--- | :--- |
| **Tối thiểu 3 Distinct Classes** | `Customer`, `Bank`, `Transaction` (không tính các class con kế thừa). | Đảm bảo tính độc lập và quan hệ rõ ràng giữa các thực thể. |
| **Cây kế thừa $\ge$ 3 Classes** | Lớp cha: `Account` (abstract)<br>Lớp con: `SavingsAccount`, `CheckingAccount`, `FixedDepositAccount`. | Thể hiện tính Kế thừa (**Inheritance**) và Đa hình (**Polymorphism**). |
| **Sử dụng Collections** | `HashMap<String, Account>` để tra cứu $O(1)$, `HashMap<String, Customer>`, `ArrayList<Transaction>` để lưu lịch sử. | Biết giải thích tại sao chọn `Map` thay vì duyệt mảng tuần tự. |
| **Xử lý ngoại lệ (Exception)** | Lớp cha: `BankException`<br>Lớp con: `InsufficientFundsException`, `InvalidAmountException`, `AccountNotFoundException`. | Bắt lỗi phân tầng (`try-catch`), không để chương trình crash do dữ liệu bẩn. |
| **Thuật toán chuyên sâu** | Sắp xếp tài khoản theo số dư (`Comparator`), Tìm kiếm nhị phân / tuyến tính theo CCCD hoặc STK. | Hiểu cơ chế hoạt động của thuật toán và độ phức tạp tính toán. |

---

## 2. Danh Sách 8 Chức Năng Cốt Lõi (Functionalities F1 - F8)

### F1: Mở tài khoản mới (Open Account)
- Cho phép khách hàng mở một trong 3 loại tài khoản:
  1. **SavingsAccount (Tiết kiệm không kỳ hạn):** Yêu cầu số dư ban đầu tối thiểu (ví dụ: 50.000 VNĐ), áp dụng lãi suất năm (ví dụ: 5%/năm).
  2. **CheckingAccount (Tài khoản vãng lai / thanh toán):** Cho phép thấu chi (Overdraft Limit, ví dụ: hạn mức âm 5.000.000 VNĐ), phí duy trì hoặc phí mỗi lần rút (ví dụ: 2.000 VNĐ/giao dịch).
  3. **FixedDepositAccount (Tiết kiệm có kỳ hạn):** Có kỳ hạn gửi (ví dụ: 6 tháng, 12 tháng), lãi suất cao hơn (ví dụ: 7%/năm). Không cho rút trước hạn hoặc nếu rút trước hạn thì phạt lãi về 0.5%.
- Tự động sinh mã tài khoản (Account Number) duy nhất, liên kết tài khoản với một `Customer`.

### F2: Nạp tiền (Deposit Money)
- Nhập số tài khoản và số tiền cần nạp.
- Kiểm tra tính hợp lệ của số tiền ($> 0$). Nếu $\le 0 \rightarrow$ ném ra `InvalidAmountException`.
- Cộng tiền vào số dư, tự động tạo một bản ghi `Transaction` kiểu `DEPOSIT` lưu vào lịch sử.

### F3: Rút tiền (Withdraw Money - Thể hiện tính Đa hình rõ nét nhất)
- Nhập số tài khoản và số tiền cần rút.
- **Quy tắc rút khác nhau tùy từng loại tài khoản:**
  - *SavingsAccount:* `số dư hiện tại - số tiền rút >= số dư tối thiểu`. Nếu không đủ $\rightarrow$ ném `InsufficientFundsException`.
  - *CheckingAccount:* `số dư hiện tại - số tiền rút - phí giao dịch >= -hạn mức thấu chi`.
  - *FixedDepositAccount:* Kiểm tra đã đến kỳ hạn đáo hạn chưa. Nếu chưa đến hạn $\rightarrow$ áp dụng quy tắc phạt hoặc từ chối giao dịch.
- Tự động tạo bản ghi `Transaction` kiểu `WITHDRAW`.

### F4: Chuyển khoản giữa 2 tài khoản (Transfer Money)
- Nhập tài khoản chuyển (Source), tài khoản nhận (Target) và số tiền.
- Kiểm tra cả 2 tài khoản có tồn tại không $\rightarrow$ ném `AccountNotFoundException` nếu không tìm thấy.
- Kiểm tra số tiền chuyển hợp lệ, kiểm tra tài khoản nguồn có đủ điều kiện rút không.
- Thực hiện giao dịch nguyên tử (Atomic transaction): Trừ tiền tài khoản nguồn $\rightarrow$ Cộng tiền tài khoản đích. Tạo 2 bản ghi giao dịch tương ứng (`TRANSFER_OUT` và `TRANSFER_IN`).

### F5: Liệt kê danh sách tài khoản của một khách hàng (List Customer Accounts)
- Tìm khách hàng theo Customer ID hoặc Số CCCD/CMND.
- Hiển thị danh sách tất cả các tài khoản khách hàng đó đang sở hữu cùng số dư hiện tại và loại tài khoản.

### F6: Xem lịch sử giao dịch của tài khoản (Transaction History)
- Nhập số tài khoản.
- Liệt kê toàn bộ các giao dịch đã thực hiện trên tài khoản đó (Mã giao dịch, Loại giao dịch, Số tiền, Thời gian, Số dư sau giao dịch).
- Cho phép sắp xếp giao dịch theo thời gian mới nhất đến cũ nhất.

### F7: Tính lãi hoặc điều chỉnh phí hàng tháng (Monthly Interest / Fee Adjustment)
- Duyệt qua toàn bộ danh sách tài khoản trong ngân hàng (Dùng vòng lặp đa hình `for (Account acc : accounts.values())`).
- Mỗi loại tài khoản sẽ tự động áp dụng logic riêng thông qua phương thức `applyMonthlyInterestOrFee()`:
  - *SavingsAccount:* Cộng tiền lãi tháng vào số dư (`balance += balance * interestRate / 12`).
  - *CheckingAccount:* Trừ phí duy trì hàng tháng (nếu số dư dưới mức yêu cầu) hoặc tính lãi vay thấu chi nếu số dư đang âm.
  - *FixedDepositAccount:* Tăng thời gian gửi thêm 1 tháng, nếu tròn kỳ hạn thì cộng lãi.

### F8: Xử lý ngoại lệ & Kiểm soát nhập liệu toàn diện (Exception Handling & Input Validation)
- Bắt tất cả các lỗi người dùng nhập bẩn trên Console (nhập chữ vào ô nhập tiền/lựa chọn menu $\rightarrow$ không để crash do `InputMismatchException`).
- Tự định nghĩa và xử lý hệ thống ngoại lệ rõ ràng, in thông báo lỗi bằng tiếng Việt/tiếng Anh thân thiện thay vì in stack trace bừa bãi.

---

## 3. Kiến Trúc 5 Giai Đoạn Nộp Bài (Phases)

* **Phase 1 — Design:** Thiết kế bản vẽ UML Class Diagram chi tiết (thuộc tính, phương thức, quan hệ). Viết báo cáo giải thích lý do lựa chọn thiết kế.
* **Phase 2 — Refine:** Tinh chỉnh thiết kế UML sau khi nhận phản biện/góp ý từ mentor hoặc giảng viên.
* **Phase 3 — Implement:** Hiện thực hóa toàn bộ hệ thống bằng Java Core console app. Đảm bảo cấu trúc code sạch, phân tách rõ các tầng (Model - Service - Exception - UI).
* **Phase 4 — Test:** Xây dựng bảng ma trận kiểm thử (Test Matrix) với các ca test chuẩn (Normal, Boundary, Exception Cases).
* **Phase 5 — Submit & Defense:** Chuẩn bị slide, bản báo cáo hoàn chỉnh và luyện tập trả lời câu hỏi vấn đáp code review.
