package com.saru.expensetracker.repository;

import com.saru.expensetracker.model.Category;
import com.saru.expensetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("Select c.expenses From Category c Where c.categoryId= :id")
    Optional<List<Expense>> findExpenseAmountByCategoryId(@Param("id") Long categoryId);

}
