package bisma.project.nike.auth;

import bisma.project.nike.model.ERole;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
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
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(

                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html"
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->

                        auth
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/swagger.html").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/v1/categories").hasAnyAuthority(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/categories/*").hasAnyAuthority(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/*").hasAnyAuthority(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/api/v1/products").hasAnyAuthority(ERole.SUPER_ADMIN.name(), ERole.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/products/*").hasAnyAuthority(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/*").hasAnyAuthority(ERole.ADMIN.name(), ERole.SUPER_ADMIN.name())

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
