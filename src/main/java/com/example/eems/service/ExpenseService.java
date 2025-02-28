package com.example.eems.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eems.model.Expense;
import com.example.eems.model.ExpenseCategory;
import com.example.eems.model.ExpenseStatus;
import com.example.eems.model.User;
import com.example.eems.repository.ExpenseRepository;
import com.example.eems.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

	   @Autowired
	    private ExpenseRepository expenseRepository;
	   @Autowired
	    private UserRepository userRepository;  // ✅ Inject UserRepository

	    public Expense addExpense(Expense expense) {
	        return expenseRepository.save(expense);
	    }

	    public List<Expense> getExpensesByUser(String username) {
	        Optional<User> user = userRepository.findByUsername(username);
	        return user.map(expenseRepository::findByUser).orElseThrow(() -> new RuntimeException("User not found"));
	    }


	    public Optional<Expense> getExpenseById(Long id) {
	        return expenseRepository.findById(id);
	    }

	    public List<Expense> getPendingExpenses() {
	        return expenseRepository.findByStatus(ExpenseStatus.PENDING);
	    }

	    public List<Expense> getApprovedExpenses() {
	        return expenseRepository.findByStatus(ExpenseStatus.APPROVED);
	    }

	    public Expense updateExpense(Expense updatedExpense) {
	        return expenseRepository.save(updatedExpense);
	    }

	    public boolean updateExpenseStatus(Long id, ExpenseStatus status) {
	        Optional<Expense> expenseOpt = expenseRepository.findById(id);
	        if (expenseOpt.isPresent()) {
	            Expense expense = expenseOpt.get();
	            expense.setStatus(status);
	            expenseRepository.save(expense);
	            return true;
	        }
	        return false;
	    }

	   /* public boolean deleteExpense(Long id) {
	        if (expenseRepository.existsById(id)) {
	            expenseRepository.deleteById(id);
	            return true;
	        }
	        return false;
	    }
    */  @Transactional
	    public boolean deleteExpense(Long id) {
	        return expenseRepository.findById(id).map(expense -> {
	            expenseRepository.delete(expense);
	            return true;
	        }).orElse(false);
	    }

	    // new category method adding this code
	    public BigDecimal getTotalExpensesByUser(String username) {
	        Optional<User> user = userRepository.findByUsername(username);
	        if (user.isEmpty()) {
	            throw new RuntimeException("User not found");
	        }
	        return expenseRepository.findByUser(user.get())
	                .stream()
	                .map(Expense::getAmount)
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	    }

	    public Map<ExpenseCategory, BigDecimal> getCategoryWiseExpenses(String username) {
	        Optional<User> user = userRepository.findByUsername(username);
	        if (user.isEmpty()) {
	            throw new RuntimeException("User not found");
	        }
	        return expenseRepository.findByUser(user.get())
	                .stream()
	                .collect(Collectors.groupingBy(Expense::getCategory,
	                        Collectors.mapping(Expense::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
	    }

	}