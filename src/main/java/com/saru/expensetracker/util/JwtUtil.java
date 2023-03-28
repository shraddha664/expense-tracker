package com.saru.expensetracker.util;

import com.saru.expensetracker.exceptions.ExpenseTrackerExceptions;
import com.saru.expensetracker.service.JwtService;
import com.saru.expensetracker.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        log.info(String.format("authheader->%s",authHeader));
        String token=null;
        String username=null;
        try {
            if (authHeader!=null && authHeader.startsWith("Bearer")){
                token=authHeader.substring(7);
                log.info(String.format("token->%s",token));

                username=jwtService.extractUsername(token);

                log.info(String.format("username->%s",username));
                if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                    log.info(String.format("user details->%s",userDetails.getUsername()));
                    Boolean value=jwtService.validateToken(userDetails,token);
                    if (jwtService.validateToken(userDetails,token)){
                        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                }
            }
        }catch (Exception e){
            throw new ExpenseTrackerExceptions("Invalid token");
        }
        filterChain.doFilter(request,response);
    }
}
