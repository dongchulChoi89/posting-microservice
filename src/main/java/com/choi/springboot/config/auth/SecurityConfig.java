package com.choi.springboot.config.auth;

import com.choi.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // to enable Spring Security
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // to use h2-console UI
                .headers().frameOptions().disable() // to use h2-console UI
                .and()
                    .authorizeRequests() // starting point to manage authorization of URL ,and to use antMatchers()
                    .antMatchers( // manage authorization of URL, HTTP method
                            "/",
                            "/css/**",
                            "/images/**",
                            "/js/**",
                            "/h2-console/**").permitAll() // give everyone(user, guest) permits to read "/" ... urls
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // give only person who has USER authorization permit(user) to read "/api/v1/**"
                    .anyRequest().authenticated() // anyRequest represent every URLs except URLs set up by antMatchers() // give only users who log in permits to read those URLs
                .and()
                    .logout() // configuration starting point related to functionalities of logout
                        .logoutSuccessUrl("/") // when log out, redirect to "/"
                .and()
                    .oauth2Login() // configuration starting point related to functionalities of OAuth2 login
                        .userInfoEndpoint() // configuration starting point related to functionalities after OAuth2 login is done successfully
                            .userService(customOAuth2UserService); // register class which implements UserService interface when you want to use the user's information from resource servers(google ...) after OAuth2 login is done successfully
    }
}
