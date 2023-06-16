package tesis.backend.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    // Allowed endpoints from spring security to make requests
    private static final String[] WHITE_LIST_URLS = {
            "/api/v1/user/all",
            "/api/v1/login/provisionallogin",
            "/api/v1/registration",
    };

    // Configuration of spring security for building and requests actions
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST_URLS).permitAll()
                .and()
                .headers().frameOptions().sameOrigin();

        return http.build();
    }
}
