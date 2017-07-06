package org.ibayer.personal.talktome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("1").password("1").roles("USER");
		auth.inMemoryAuthentication().withUser("2").password("2").roles("USER");
		auth.inMemoryAuthentication().withUser("3").password("3").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable();
		http.authorizeRequests().antMatchers("/swagger-ui.html", "/v2/api-docs/**").permitAll().anyRequest()
				.authenticated();
	}
}
