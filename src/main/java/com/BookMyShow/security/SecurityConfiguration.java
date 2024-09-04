package com.BookMyShow.security;

import com.BookMyShow.services.UserService;
import com.BookMyShow.webtoken.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){

        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public UserDetailsService userDetailsService(){

        return userService;

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                   registry.requestMatchers("/movie/addMovie","/movie/updateMovie","/movie/deleteMovie").hasRole("ADMIN");
                   registry.requestMatchers("/movie/**").hasAnyRole("ADMIN","USER");
                   registry.requestMatchers("/show/**").hasRole("ADMIN");
                   registry.requestMatchers("/theater/addTheater","/theater/associateSeats").hasRole("ADMIN");
                   registry.requestMatchers("theater/**").hasAnyRole("ADMIN","USER");
                   registry.requestMatchers("/ticket/generateTicket").hasRole("ADMIN");
                   registry.requestMatchers("/ticket/**").hasAnyRole("ADMIN","USER");
                   registry.requestMatchers("/user/**").hasAnyRole("ADMIN","USER");
                   registry.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();

                })
                .formLogin(formLogin -> formLogin.permitAll()) // Permit all for form login
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
                .build();
    }




}
