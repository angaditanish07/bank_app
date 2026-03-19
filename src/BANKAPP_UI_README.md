# Bank App UI - Professional Java Swing Interface

A clean, modern, and professional user interface for your UPI Payments System built with Java Swing.

## 📋 Features

### ✅ Login Screen
- Professional gradient background
- Username/Password authentication
- Modern input fields with focus states
- Register and Login options

### 📊 Dashboard
- **Account Balance Display** - Real-time balance overview
- **Quick Actions** - Send Money, Receive Money, View History buttons
- **Recent Transactions** - Quick summary card
- **Account Information** - Account type and status

### 💳 Transaction Management
- Send Money form with recipient details
- Amount validation
- Description/notes field
- Success notifications
- Clean form layout with proper spacing

### 📜 Transaction History
- Complete transaction list with timestamps
- Color-coded transaction types (Sent/Received)
- Transaction details (name, amount, type, time)
- Scrollable history view

## 🎨 Design Features

### Color Scheme
- **Primary Blue**: `#1976D2` (Main brand color)
- **Success Green**: `#4CAF50` (Received transactions)
- **Danger Red**: `#F44336` (Sent transactions)
- **Light Gray**: `#F5F7FA` (Background)
- **White**: Cards and primary containers

### Typography
- **Font Family**: Segoe UI (Professional & Clean)
- **Headings**: Bold, 22-28px
- **Body Text**: Regular, 12-14px
- **Labels**: Regular, 11-13px

### UI Components
- **Rounded Buttons** with hover effects
- **Gradient Cards** for visual hierarchy
- **Smooth Transitions** on button interactions
- **Professional Spacing** (15px, 20px grid)
- **Custom Rounded Borders** for modern look

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Any IDE (IntelliJ IDEA, Eclipse, VS Code with Java Extension)

### Installation

1. **Copy the file to your project**
   ```
   cp BankAppUI.java /path/to/your/project/src/
   ```

2. **Compile the Java file**
   ```
   javac BankAppUI.java
   ```

3. **Run the application**
   ```
   java BankAppUI
   ```

### Default Login Credentials
- **Username**: `tanish`
- **Password**: (any value for demo)

## 🏗️ Architecture Integration

### Layered Architecture
```
┌─────────────────────┐
│   UI Layer          │  ← BankAppUI.java
│ (Swing Components)  │
├─────────────────────┤
│   Service Layer     │  ← Your existing services
│ (Business Logic)    │
├─────────────────────┤
│   DAO Layer         │  ← Your existing DAOs
│ (Database Access)   │
├─────────────────────┤
│   Database          │  ← MySQL
│ (Persistence)       │
└─────────────────────┘
```

### Integration Steps

1. **Connect Dashboard to Service Layer**
   ```java
   // Fetch balance from service
   accountBalance = userService.getBalance(currentUserId);
   ```

2. **Connect Transaction Form to DAO**
   ```java
   // On Send Money button
   transactionDAO.createTransaction(recipientId, amount, description);
   ```

3. **Load Transaction History**
   ```java
   // Populate history panel
   List<Transaction> history = transactionDAO.getTransactionHistory(userId);
   ```

4. **Implement User Authentication**
   ```java
   // In login panel
   User user = userService.authenticate(username, password);
   if (user != null) {
       currentUser = user.getName();
       cardLayout.show(mainPanel, "DASHBOARD");
   }
   ```

## 📱 Screen Navigation Flow

```
LOGIN → DASHBOARD ↔ TRANSACTION
          ↕
        HISTORY
```

- **LOGIN**: Initial authentication screen
- **DASHBOARD**: Main hub with quick actions
- **TRANSACTION**: Send money form
- **HISTORY**: View past transactions

## 🔧 Customization Guide

### Change Primary Color
```java
// Find this color in the code and replace
new Color(25, 118, 210)  // Change to your brand color
```

### Add New Screens
```java
// In constructor, add new panel:
mainPanel.add(createMyNewPanel(), "MYSCREEN");

// Create the panel method:
private JPanel createMyNewPanel() {
    JPanel panel = new JPanel();
    // Add components here
    return panel;
}

// Navigate to it:
cardLayout.show(mainPanel, "MYSCREEN");
```

### Adjust Font Sizes
```java
// Look for lines like:
new Font("Segoe UI", Font.BOLD, 22)  // Change 22 to your preferred size
```

### Change Button Styles
- Modify `createPrimaryButton()` method for primary button style
- Modify `createSecondaryButton()` method for secondary button style
- Modify `createActionButton()` method for action buttons

## 📊 Sample Data

The application comes with sample data for demonstration:
- **User**: Tanish Angadi
- **Balance**: ₹50,000.00
- **Transactions**: 5 sample transactions with timestamps

## 🔐 Security Considerations

For production use:
1. **Don't hardcode credentials** - Use secure authentication
2. **Encrypt passwords** - Use bcrypt or similar
3. **Validate inputs** - Check all form inputs before sending to backend
4. **Use HTTPS** - If connecting to remote services
5. **Handle sensitive data** - Clear sensitive fields after operations

## 🚦 State Management

Current UI uses:
- `CardLayout` for screen switching
- `currentUser` - Stores logged-in user name
- `accountBalance` - Stores user's balance

For enhanced state management, consider:
```java
public class AppState {
    private User currentUser;
    private Account currentAccount;
    private List<Transaction> recentTransactions;
    // Getters and setters
}
```

## 📝 Code Structure

```java
BankAppUI.java
├── Constructor & Initialization
├── createLoginPanel()
├── createDashboardPanel()
├── createTransactionPanel()
├── createHistoryPanel()
├── Helper Methods:
│   ├── createTopNavigation()
│   ├── createCardPanel()
│   ├── createPrimaryButton()
│   ├── createSecondaryButton()
│   ├── createActionButton()
│   ├── addFormField()
│   ├── createTransactionItem()
│   └── RoundedBorder (Custom Class)
└── main()
```

## 🐛 Troubleshooting

**Issue**: Buttons not appearing correctly
- **Solution**: Ensure Segoe UI font is installed, or change to a system font

**Issue**: Window too small
- **Solution**: Increase initial size in constructor: `setSize(1200, 800)`

**Issue**: Scrollbars not working
- **Solution**: Wrap panels with `JScrollPane`

**Issue**: Colors look different
- **Solution**: Adjust RGB values based on your system color profile

## 📦 Dependencies

- **Java Swing** (Built-in) - UI Framework
- **AWT** (Built-in) - Graphics and layouts
- **Java Util** (Built-in) - Collections and utilities

No external dependencies required!

## 🎯 Future Enhancements

- [ ] Dark mode toggle
- [ ] Transaction search and filters
- [ ] Receipt generation/printing
- [ ] Biometric authentication
- [ ] Real-time notifications
- [ ] Multi-language support
- [ ] Accessibility improvements (WCAG compliance)
- [ ] Settings/preferences panel
- [ ] Profile management

## 📄 License

This UI component is ready to integrate with your existing Bank App project.

---

**Questions?** Review the integration steps above or customize the code as needed for your specific architecture!
