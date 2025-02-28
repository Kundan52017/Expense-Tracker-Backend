package com.example.eems.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private String description;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)  // FOOD, TRAVEL, OFFICE_SUPPLIES
    private ExpenseCategory category;

    private LocalDate expenseDate;

    //@Enumerated(EnumType.STRING)
   // private ExpenseType type;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;  // PENDING, APPROVED, REJECTED

    private String invoiceUrl; // ✅ Store invoice as a URL instead of binary

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // ✅ Maps to `users` table
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}*/

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	/*public ExpenseType getType() {
		return type;
	}

	public void setType(ExpenseType type) {
		this.type = type;
	}*/

	public ExpenseStatus getStatus() {
		return status;
	}

	public void setStatus(ExpenseStatus status) {
		this.status = status;
	}


	public String getInvoiceUrl() {
		return invoiceUrl;
	}

	public void setInvoiceUrl(String invoiceUrl) {
		this.invoiceUrl = invoiceUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

	@Override
	public String toString() {
		return "Expense [id=" + id + /*", description=" + description + */", amount=" + amount + ", category=" + category
				+ ", expenseDate=" + expenseDate + /*", type=" + type + */", status=" + status + ", invoiceUrl="
				+ invoiceUrl + ", user=" + user + "]";
	}


}