package by.ilmizeroth.birthdaycalendar.security;

import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/register",
                                "/error",
                                "/css/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/birthdays", true)
                )
                .logout((form) -> form
                        .logoutUrl("/logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepository.
                        findByName(username).
                        orElseThrow(() -> new UsernameNotFoundException("User not found"));
                Set<SimpleGrantedAuthority> grantedAuthorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
                return new User(userEntity.getName(), userEntity.getPassword(), grantedAuthorities);

            }
        };
    }
}
