package com.saru.expensetracker.service;

import com.saru.expensetracker.config.UserDetailsImpl;
import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import com.saru.expensetracker.model.User;
import com.saru.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user= userRepository.findByUsername(username);
        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new ExpenseTrackerExceptions("user not found " + username));
    }
}

