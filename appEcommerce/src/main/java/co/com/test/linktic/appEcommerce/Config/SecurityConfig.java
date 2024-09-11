package co.com.test.linktic.appEcommerce.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private JwtRequestFilter jwtRequestFilter;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable()
         .authorizeRequests()
         .antMatchers("/api/user/create", "/api/user/getAll", "/api/auth/login").permitAll() // Permitir acceso sin autenticación a estas rutas
         .anyRequest().authenticated()  // Requiere autenticación para cualquier otra solicitud
         .and()
         .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
         .sessionManagement().disable(); // Deshabilita el manejo de sesiones (para JWT)
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
