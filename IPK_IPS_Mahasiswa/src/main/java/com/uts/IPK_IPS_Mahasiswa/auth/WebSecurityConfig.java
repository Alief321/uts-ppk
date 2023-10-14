package com.uts.IPK_IPS_Mahasiswa.auth;

import com.uts.IPK_IPS_Mahasiswa.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth
                        -> auth.requestMatchers("/login", "/register").permitAll()
                        //                                Admin
                        .requestMatchers("/periode", "ips/mahasiswa/{id}").hasAnyAuthority("Admin")
                        .requestMatchers(HttpMethod.POST, "/matkul", "/kelas", "/ips").hasAnyAuthority("Admin")
                        .requestMatchers(HttpMethod.PATCH, "/matkul/**", "/kelas/**", "/user/setKelas", "/user/setMatkul", "/user/{id}").hasAnyAuthority("Admin")
                        .requestMatchers(HttpMethod.PUT, "/matkul/**", "/kelas/**").hasAnyAuthority("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/matkul/**", "/kelas/**", "/user/{id}").hasAnyAuthority("Admin")
                        //                                Dosen
                        .requestMatchers(HttpMethod.POST, "/nilai").hasAnyAuthority("Dosen")
                        .requestMatchers(HttpMethod.PATCH, "/nilai/**").hasAnyAuthority("Dosen")
                        .requestMatchers(HttpMethod.DELETE, "/nilai/**").hasAnyAuthority("Dosen")
                        .requestMatchers(HttpMethod.GET, "/nilai/{id}").hasAnyAuthority("Dosen", "Admin")
                        .requestMatchers(HttpMethod.GET, "/ips/{id}", "/ips/mahasiswa/{id}").hasAnyAuthority("Dosen", "Admin")
                        //                                DENYALL
                        .requestMatchers(HttpMethod.DELETE, "/ips").denyAll()
                        .requestMatchers(HttpMethod.PUT, "/ips").denyAll()
                        .requestMatchers(HttpMethod.PATCH, "/ips").denyAll()
                        //                                Semua role
                        .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
