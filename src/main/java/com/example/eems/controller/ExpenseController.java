package com.example.eems.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eems.model.Expense;
import com.example.eems.model.ExpenseCategory;
import com.example.eems.model.ExpenseStatus;
import com.example.eems.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expense Controller", description = "Handles expense-related operations")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // 1️⃣ Add Expense (Employee)
    @PostMapping("/add")
    @Operation(summary = "Add a new expense", description = "Allows employees to add a new expense.")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.addExpense(expense));
    }

    // 2️⃣ Get Expenses by User (Employee)
    @GetMapping("/user/{username}")
    @Operation(summary = "Get user's expenses", description = "Fetches all expenses submitted by a specific user.")
    public ResponseEntity<List<Expense>> getUserExpenses(@PathVariable("username") String username) {
        return ResponseEntity.ok(expenseService.getExpensesByUser(username));
    }


    // 3️⃣ Get Single Expense by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get expense by ID", description = "Fetches details of a specific expense using its ID.")
    public ResponseEntity<?> getExpenseById(@PathVariable("id") Long id) {
        Optional<Expense> expense = expenseService.getExpenseById(id);
        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4️⃣ Get All Pending Expenses (Manager)
    @GetMapping("/pending")
    @Operation(summary = "Get all pending expenses", description = "Fetches all expenses that are pending approval.")
    public ResponseEntity<List<Expense>> getPendingExpenses() {
        return ResponseEntity.ok(expenseService.getPendingExpenses());
    }

    // 5️⃣ Get All Accepted Expenses (Admin)
    @GetMapping("/approved")
    @Operation(summary = "Get approved expenses", description = "Fetches all expenses that have been approved.")
    public ResponseEntity<List<Expense>> getApprovedExpenses() {
        return ResponseEntity.ok(expenseService.getApprovedExpenses());
    }

    // 6️⃣ Update Expense (Employee)
    @PutMapping("/update")
    @Operation(summary = "Update an expense", description = "Allows employees to update an existing expense.")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense) {
        return ResponseEntity.ok(expenseService.updateExpense(expense));
    }

    // 7️⃣ Approve/Reject Expense (Manager/Admin)
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateExpenseStatus(@PathVariable("id") Long id,
                                                 @RequestBody Map<String, ExpenseStatus> request) {
        ExpenseStatus status = request.get("status");
        boolean updated = expenseService.updateExpenseStatus(id, status);
        return updated ? ResponseEntity.ok("Expense status updated") : ResponseEntity.notFound().build();
    }

    // 8️⃣ Delete Expense (Employee)
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an expense", description = "Allows employees to delete an expense they created.")
    public ResponseEntity<?> deleteExpense(@PathVariable("id") Long id) {
        boolean deleted = expenseService.deleteExpense(id);
        return deleted ? ResponseEntity.ok("Expense deleted") : ResponseEntity.notFound().build();
    }

    // 9️⃣ Get Total Expenses by User
    @GetMapping("/total/{username}")
    public ResponseEntity<BigDecimal> getTotalExpenses(@PathVariable("username") String username) {
        return ResponseEntity.ok(expenseService.getTotalExpensesByUser(username));
    }
    // 🔟 Get Category-wise Expenses by User
    @GetMapping("/category/{username}")
    @Operation(summary = "Get category-wise expenses", description = "Fetches expenses categorized by type for a user.")
    public ResponseEntity<Map<ExpenseCategory, BigDecimal>> getCategoryWiseExpenses(
            @PathVariable("username") String username) {
        return ResponseEntity.ok(expenseService.getCategoryWiseExpenses(username));
    }
}
