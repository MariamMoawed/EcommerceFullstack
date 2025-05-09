package com.example.EcommerceFullstack.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String username = authentication.getName();
            String password = String.valueOf(authentication.getCredentials());
            
            logger.debug("Attempting to authenticate user: {}", username);
            
            // Load the user details
            UserDetails user = userDetailsService.loadUserByUsername(username);
            
            // Check if the password matches
            if (passwordEncoder.matches(password, user.getPassword())) {
                logger.debug("Authentication successful for user: {}", username);
                return new UsernamePasswordAuthenticationToken(
                    user, 
                    null, // Don't include credentials in authenticated token for security
                    user.getAuthorities()
                );
            } else {
                logger.debug("Invalid password for user: {}", username);
                throw new BadCredentialsException("Invalid password");
            }
        } catch (UsernameNotFoundException e) {
            logger.debug("User not found: {}", e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            logger.error("Authentication error: {}", e.getMessage());
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
