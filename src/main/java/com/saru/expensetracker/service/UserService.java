package com.saru.expensetracker.service;

import com.saru.expensetracker.dto.LoginDto;
import com.saru.expensetracker.dto.UserRequestDto;
import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import com.saru.expensetracker.model.User;
import com.saru.expensetracker.repository.UserRepository;
import com.saru.expensetracker.validators.Violations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final Violations validationConfig;

    @Transactional
    public void saveUser(UserRequestDto userRequestDto) {
        validationConfig.violationsMessage(userRequestDto);
        log.info(String.format("message->%s", encoder.encode(userRequestDto.getPassword()).toString()));
        if (userRepository.findByUsername(userRequestDto.getUsername()).isEmpty()) {
            userRepository.save(User.builder()
                    .username(userRequestDto.getUsername())
                    .firstName(userRequestDto.getFirstName())
                    .lastName(userRequestDto.getLastName())
                    .password(encoder.encode(userRequestDto.getPassword()))
                    .build());
        }else {
            throw new ExpenseTrackerExceptions("User name already exist.Try another one");
        }
    }

    @Transactional(readOnly = true)
    public User findUser(LoginDto loginDto) throws ExpenseTrackerExceptions {

        validationConfig.violationsMessage(loginDto);
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new ExpenseTrackerExceptions("No user found with given username"));
        log.info(String.format("User list->%s", user));

        if (encoder.matches(loginDto.getPassword(), user.getPassword())) {
            return user;
        } else {
            throw new ExpenseTrackerExceptions("Password did not match.Use correct password");
        }
    }


}
