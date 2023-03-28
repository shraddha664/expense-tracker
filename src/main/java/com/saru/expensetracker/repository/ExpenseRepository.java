package com.saru.expensetracker.repository;

import com.saru.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
   @Query("SELECT e.expenseAmount FROM Expense e WHERE e.userId = :id")
   Optional<List<Double>> findExpenseAmountByUserId(@Param("id") Long userId);
}
