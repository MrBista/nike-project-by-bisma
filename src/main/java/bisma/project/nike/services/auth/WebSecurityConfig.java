package bisma.project.nike.services.auth;

import bisma.project.nike.model.ERole;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.PrintWriter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPoint authEntryPoint;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessoin -> sessoin.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/api/v1/users/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/categories").hasAnyRole(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/categories/*").hasAnyRole(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/*").hasAnyRole(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/products").hasAnyRole(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/products/*").hasAnyRole(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/*").hasAnyRole(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .anyRequest().authenticated()

                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            PrintWriter out = response.getWriter();
                            out.print("{" +
                                    "\"data\": null," +
                                    "\"message\": \"forbidden\"," +
                                    "\"status\": 403" +
                                    "}");
                            out.flush();
                        })
                        .authenticationEntryPoint(authEntryPoint)
                );
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
