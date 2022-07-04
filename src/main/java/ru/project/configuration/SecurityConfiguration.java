package ru.project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import ru.project.filter.AuthorizationFilter;
import ru.project.service.RequestService;
import ru.project.service.TokenService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private RequestService requestService;
    @Autowired
    private TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new AuthorizationFilter(requestService, tokenService), CorsFilter.class)
                .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
