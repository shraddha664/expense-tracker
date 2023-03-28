package com.saru.expensetracker.service;

import com.saru.expensetracker.dto.AccountDto;
import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import com.saru.expensetracker.model.Account;
import com.saru.expensetracker.model.Expense;
import com.saru.expensetracker.repository.AccountRepository;
import com.saru.expensetracker.repository.CategoryRepository;
import com.saru.expensetracker.repository.ExpenseRepository;
import com.saru.expensetracker.validators.Violations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final ExpenseRepository expenseRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    private final Violations validationConfig;
    @Transactional
    public void saveAccount(AccountDto accountDto, Long user_Id) {

        validationConfig.violationsMessage(accountDto);
        log.info("Inside saveAccount of balance service");
        accountRepository.save( Account.builder()
                .accountType(accountDto.getAccountType())
                .accountNumber(accountDto.getAccountNumber())
                .balance(accountDto.getBalance())
                .userId(user_Id)
                .build());
    }


    @Transactional
    public AccountDto updateAccount(Long userId, Long accountId, AccountDto accountDto) {
        Account account=  accountRepository.findByUserIdAndId(userId,accountId).orElseThrow(()->new ExpenseTrackerExceptions("No account with the provided accountId:"+accountId+"for the user"+userId));

        account.setAccountType(accountDto.getAccountType());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setBalance(accountDto.getBalance());
        account.setUserId(userId);
         accountRepository.save(account);
         return accountDto;

    }

    @Transactional
    public void deleteAccount(Long userId, Long accountId) {
        Account account=  accountRepository.findByUserIdAndId(userId,accountId).orElseThrow(()->new ExpenseTrackerExceptions("No account with the provided accountId:"+accountId));
        accountRepository.deleteById(accountId);

    }

    @Transactional(readOnly = true)
    public List<AccountDto> getAllAccount(Long userId) {
        List<Account> accountList= accountRepository.findByUserId(userId).orElseThrow(()->new ExpenseTrackerExceptions("No any accounts added yet"));
        return  accountList.stream().map(account ->AccountDto.builder().accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance()).build()).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public String getRemainingBalance(Long userId) {
        Optional<List<Account>> accounts=accountRepository.findByUserId(userId);
        log.info(String.format("accounts->%s",accounts.get().toString()));
        if (accounts.get().isEmpty()){
            return "No any accounts details added yet";
        }else {
            List<Double> expensesAmount=expenseRepository.findExpenseAmountByUserId(userId).orElseThrow(()->new ExpenseTrackerExceptions("No any expenses added yet"));
            Double totalExpenseAmount= expensesAmount.stream().reduce(0.0,Double::sum);
            log.info(String.format("total expense amount=%s",totalExpenseAmount));
            Double totalBalanceInAccounts=accounts.get().stream().mapToDouble(Account::getBalance).reduce(0,Double::sum);
            Double remainingBalance=totalBalanceInAccounts-totalExpenseAmount;
            if(remainingBalance>0){
                return "Your total expenses till "+new Date(System.currentTimeMillis())+" is:"+totalExpenseAmount+"\n total remaining balance is:"+remainingBalance;
            }else {
                return "You are in debt of total amount "+remainingBalance;
            }

        }
    }

    @Transactional(readOnly = true)
    public String getTotalExpenseByExpense(Long categoryId) {
        List<Expense> totalExpense=categoryRepository.findExpenseAmountByCategoryId(categoryId).orElseThrow(()->new ExpenseTrackerExceptions("No such category exist"));
        if (totalExpense.isEmpty()){
            return "No any expenses made in this category";
        }else {
            return ("Total expense in this category is:"+totalExpense.stream().mapToDouble(Expense::getExpenseAmount).reduce(0,Double::sum));
        }
    }

}
