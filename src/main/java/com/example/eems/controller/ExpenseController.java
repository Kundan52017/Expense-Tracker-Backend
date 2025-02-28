package com.example.eems.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eems.model.Expense;
import com.example.eems.model.ExpenseCategory;
import com.example.eems.model.ExpenseStatus;
import com.example.eems.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

	   @Autowired
	    private ExpenseService expenseService;

	    // 1️⃣ Add Expense (Employee)
	    @PostMapping("/add")
	    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
	        return ResponseEntity.ok(expenseService.addExpense(expense));
	    }

	    // 2️⃣ Get Expenses by User (Employee)
	    @GetMapping("/user/{username}")
	    public ResponseEntity<List<Expense>> getUserExpenses(@PathVariable String username) {
	        return ResponseEntity.ok(expenseService.getExpensesByUser(username));
	    }

	    // 3️⃣ Get Single Expense by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
	        Optional<Expense> expense = expenseService.getExpenseById(id);
	        return expense.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // 4️⃣ Get All Pending Expenses (Manager)
	    @GetMapping("/pending")
	    public ResponseEntity<List<Expense>> getPendingExpenses() {
	        return ResponseEntity.ok(expenseService.getPendingExpenses());
	    }

	    // 5️⃣ Get All Accepted Expenses (Admin)
	    @GetMapping("/approved")
	    public ResponseEntity<List<Expense>> getApprovedExpenses() {
	        return ResponseEntity.ok(expenseService.getApprovedExpenses());
	    }

	    // 6️⃣ Update Expense (Employee)
	    @PutMapping("/update")
	    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense) {
	        return ResponseEntity.ok(expenseService.updateExpense(expense));
	    }

	    // 7️⃣ Approve/Reject Expense (Manager/Admin)
	    @PutMapping("/update-status/{id}")
	    public ResponseEntity<?> updateExpenseStatus(@PathVariable Long id, @RequestParam ExpenseStatus status) {
	        boolean updated = expenseService.updateExpenseStatus(id, status);
	        return updated ? ResponseEntity.ok("Expense status updated") : ResponseEntity.notFound().build();
	    }

	 /*   // 8️⃣ Delete Expense (Employee)
	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
	        boolean deleted = expenseService.deleteExpense(id);
	        return deleted ? ResponseEntity.ok("Expense deleted") : ResponseEntity.notFound().build();
	    }*/
	    // 8️⃣ Delete Expense (Employee)
	    //<--- Login the employee id then copy the token-->
	    //<---http://localhost:8093/api/expenses/delete/5-->
	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<?> deleteExpense(@PathVariable("id") Long id) {
	        boolean deleted = expenseService.deleteExpense(id);
	        return deleted ? ResponseEntity.ok("Expense deleted") : ResponseEntity.notFound().build();
	    }

	    // category method

	    @GetMapping("/total/{username}")
	    public BigDecimal getTotalExpenses(@PathVariable String username) {
	        return expenseService.getTotalExpensesByUser(username);
	    }

	    @GetMapping("/category/{username}")
	    public Map<ExpenseCategory, BigDecimal> getCategoryWiseExpenses(@PathVariable String username) {
	        return expenseService.getCategoryWiseExpenses(username);
	    }
	}