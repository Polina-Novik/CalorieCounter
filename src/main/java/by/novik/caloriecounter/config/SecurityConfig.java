package by.novik.caloriecounter.config;

import by.novik.caloriecounter.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests((auth) -> auth
                        .requestMatchers("/foods/add", "/foods/delete/**", "/foods/update/**",
                                "/activities/add", "/activities/delete/**", "/activities/update/**").hasRole("ADMIN")
                        .requestMatchers("/registration", "/login",
                                "/swagger-ui/**", "/v3/api-docs/**", "/home/**",
                                "/information/**", "/diary/**", "/daily_statistics/**",
                                "/edit_profile/**")
                        .permitAll()
                        .requestMatchers("/calorie_counter/**", "/foods/**",
                                "/activities/**", "/profile/**").authenticated()
                        .anyRequest().authenticated()
                        .and()

                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class));

        return http.build();
    }

}
