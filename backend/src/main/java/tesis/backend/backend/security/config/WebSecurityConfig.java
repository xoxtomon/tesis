package tesis.backend.backend.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // Allowed endpoints from spring security to make requests
    private static final String[] WHITE_LIST_URLS = {
            "/api/v1/login",
            "/api/v1/registration",
            "/api/v1/demo/white",
    };
    private static final String[] ADMIN_URLS = {
            "/api/v1/user/all",
            "/api/v1/user/role/**",
            "/api/v1/anteproyecto/**",
            "/api/v1/demo/admin",

    };

    private static final String[] EVALUADOR_URLS = {
            "/api/v1/demo/evaluador",
    };

    // Cannot duplicate in both ADMIN and EVALUADOR because then,
    // only the first authority in the matchers is taken into account
    private static final String[] ADMIN_EVALUADOR_URLS = {
            "/api/v1/demo/adminevaluador",
    };

    private static final String[] ESTUDIANTE_URLS = {
            "/api/v1/demo/estudiante",
    };
    // Configuration of spring security for building and requests actions
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST_URLS)
                .permitAll()
                .requestMatchers(ADMIN_URLS).hasAuthority("ADMIN")
                .requestMatchers(EVALUADOR_URLS).hasAuthority("EVALUADOR")
                .requestMatchers(ESTUDIANTE_URLS).hasAuthority("ESTUDIANTE")
                .requestMatchers(ADMIN_EVALUADOR_URLS).hasAnyAuthority("ADMIN","EVALUADOR" )
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

