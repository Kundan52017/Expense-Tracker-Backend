package com.example.eems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eems.model.Expense;
import com.example.eems.model.ExpenseStatus;
import com.example.eems.model.User;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	 List<Expense> findByUser(User user); // ✅ Use User object instead of username
    List<Expense> findByStatus(ExpenseStatus status);

}