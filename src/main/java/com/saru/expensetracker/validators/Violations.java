package com.saru.expensetracker.validators;

import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Violations {
    private final ObjectValidator validator;
    public void violationsMessage(Object object){
        Set<String> violationCollection= validator.validate(object);

        if (!violationCollection.isEmpty()){
            StringBuilder messages=new StringBuilder();
            for (String violation:violationCollection) {
                messages.append(violation).append("|");
            }
            throw new ExpenseTrackerExceptions(messages.toString());
        }
    }
}
