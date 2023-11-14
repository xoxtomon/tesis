package tesis.backend.backend.security.config;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
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
                        "/api/v1/file/download/**",
        };
        private static final String[] ADMIN_URLS = {
                        "/api/v1/user/all",
                        "/api/v1/user/delete/**",
                        "/api/v1/user/role/**",
                        "/api/v1/anteproyecto",
                        "/api/v1/anteproyecto/add/**",
                        "/api/v1/anteproyecto/delete/**",
                        "/api/v1/proyecto",
                        "/api/v1/proyecto/all",
                        "/api/v1/file",
        };

        private static final String[] EVALUADOR_URLS = {
                        "/api/v1/demo/evaluador",
        };

        private static final String[] ESTUDIANTE_URLS = {
                        "/api/v1/file/upload", 
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
                                .requestMatchers(ADMIN_URLS).hasAuthority("ADMIN")              //GLOBAL LEVEL ACCESS CONFIGURATION
                                .requestMatchers(EVALUADOR_URLS).hasAuthority("EVALUADOR")      //@PreAuthorize() OFFERS MORE GRANULARITY AT METHOD LEVEL FOR FURTHER SPECIFICATION
                                .requestMatchers(ESTUDIANTE_URLS).hasAuthority("ESTUDIANTE")
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

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("*"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

}
