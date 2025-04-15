package com.example.pocprojectbe.config;

import com.example.pocprojectbe.jwt.AuthEntryPoint;
import com.example.pocprojectbe.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// In your SecurityConfig
@EnableMethodSecurity

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthEntryPoint unauthorizedHandler;


    @Bean
    public AuthTokenFilter authJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.cors(cors->{})
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((requests)->{
            requests.requestMatchers("/","/auth/registration").permitAll()
                    .requestMatchers("/auth/login").permitAll()
                    .anyRequest().authenticated();
        });
        http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling((exception)-> exception.authenticationEntryPoint(unauthorizedHandler));

        http.headers((headers)-> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin() ));

        http.addFilterBefore(authJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception{
        return builder.getAuthenticationManager();
    }
}
