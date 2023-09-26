package olegivanov.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)
public class SecureConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/hi").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form.permitAll()
                )
                .logout((logout) -> logout.permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("Oleg")
                        .password("1234")
                        .roles("DELETE")
                        .build();
        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("Peter")
                        .password("1234")
                        .roles("READ")
                        .build();
        UserDetails user3 =
                User.withDefaultPasswordEncoder()
                        .username("Vasya")
                        .password("1234")
                        .roles("WRITE")
                        .build();
        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("READ","WRITE", "DELETE")
                        .build();
//        UserDetails admin =  User.withUsername("admin")
//                .password(passwordEncoder.encode("admin"))
//                .roles("USER", "ADMIN")
//                .build();
        return new InMemoryUserDetailsManager(user1,user2, admin);
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return encoder;
//    }
}
