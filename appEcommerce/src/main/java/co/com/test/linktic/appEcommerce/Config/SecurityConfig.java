package co.com.test.linktic.appEcommerce.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
	    public void configure(WebSecurity web) throws Exception {
	        // Permitir el acceso a Swagger UI y otros recursos estáticos
	        web.ignoring().antMatchers("/swagger-ui/index.html","/swagger-ui/**", "/v2/api-docs");
	    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		 http.cors().and().csrf().disable()
         .authorizeRequests()
         .antMatchers("/swagger-ui/index.html", "/v2/api-docs","/**").permitAll() 
         .antMatchers("/api/user/create", "/api/user/getAll", "/api/auth/login","/swagger-ui/index.html").permitAll() 
         .anyRequest().authenticated()  
         .and()
         .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
         .sessionManagement().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
