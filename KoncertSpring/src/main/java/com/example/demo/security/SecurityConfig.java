package com.example.demo.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	
	@Autowired
	@Qualifier("UserDetailProvider")
 	UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	   auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	   http.authorizeRequests().
	   antMatchers("/","/auth/**").permitAll()
	   .antMatchers("/koncerti**/").hasAnyRole("radnik","ROLE_radnik","ROLE_korisnik", "korisnik")
	   .antMatchers("/worker/**","/radnik/**").hasAnyRole("radnik","ROLE_radnik")
	   .antMatchers("/user/**","/korisnik/**").hasAnyRole("korisnik","ROLE_korisnik")
	   .and()
	   .formLogin()
	   .loginPage("/auth/loginPage")
	   .loginProcessingUrl("/login")
	   .usernameParameter("username")
       .passwordParameter("password")
	   .defaultSuccessUrl("/auth/pocetna")
	   .failureUrl("/auth/failLogin")
	   .and().rememberMe().and().csrf().disable();

	}
}
