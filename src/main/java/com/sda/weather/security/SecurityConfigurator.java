package com.sda.weather.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurator extends WebSecurityConfigurerAdapter {
    private final SecurityUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/location").permitAll()
//                .antMatchers(HttpMethod.POST, "/location").hasAnyAuthority("ROLE_ADMIN") <-> .hasRole("ADMIN)
                .anyRequest().permitAll()
//                .authenticated()
                .and()
                .formLogin()        // POST: /login
                .and()
                .logout()           // POST: /logout
                .and()
                .csrf().disable()
                .httpBasic()
                .and()
                .userDetailsService(userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
