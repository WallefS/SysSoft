package br.ueg.posse.soft.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.ueg.posse.soft.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/layout/**")
			.antMatchers("/images/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/cidades/nova").hasRole("CADASTRAR_CIDADE")
				.antMatchers("/usuarios/**").hasRole("CADASTRAR_USUARIO")				
				.antMatchers("/medicos/**").hasRole("CADASTRAR_MEDICO")
				.antMatchers("/pacientes/**").hasRole("CADASTRAR_PACIENTE")
				.antMatchers("/exames/**").hasRole("CADASTRAR_EXAME")
				.antMatchers("/empresas/**").hasRole("CADASTRAR_EMPRESA")
				.antMatchers("/agendas/**").hasRole("CADASTRAR_AGENDA")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
				.csrf().disable()
				.exceptionHandling()
				.accessDeniedPage("/403")
				.and()
				.sessionManagement();
				//.invalidSessionUrl("/login");
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}