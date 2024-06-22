package auth.server.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {

    @Autowired
    private JwtConversor conversor;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers(HttpMethod.PUT, "/user/login").permitAll()
                .requestMatchers(HttpMethod.PUT, "/user/changePassword").permitAll()
                .requestMatchers(HttpMethod.GET, "/user").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/blocked").permitAll()
                .requestMatchers(HttpMethod.PUT, "/user/unlock/**").hasRole("ADMIN"))
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(conversor)));

        return http.build();
    }

    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        System.out.println(passwordEncoder.encode("password"));

        UserDetails usuario = User.withUsername("user").password(passwordEncoder.encode("password")).roles("USUARIO").build();

        UserDetails administrador = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USUARIO", "ADMIN").build();

        return new InMemoryUserDetailsManager(usuario, administrador);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
     * @Bean
     * public UserDetailsManager usuarios(DataSource dataSource, PasswordEncoder passwordEncoder) {
     *     UserDetails teste = User.builder().username("teste").password(passwordEncoder.encode("teste")).roles("USUARIO").build();
     *
     *     UserDetails usuario = User.builder().username("user").password(passwordEncoder.encode("password")).roles("USUARIO").build();
     *
     *     UserDetails administrador = User.builder().username("admin").password(passwordEncoder.encode("password")).roles("USUARIO", "ADMIN").build();
     *
     *     JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
     *     usuarios.createUser(usuario);
     *     usuarios.createUser(administrador);
     *     usuarios.createUser(teste);
     *
     *     return usuarios;
     * }
     */
}

