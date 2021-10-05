package com.darjan.quizapp.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Value("${front.end.path}")
	private String reactPath;
	
	@Value("${allowed.cors.origins}")
	private String allowedCorsOrigins;
	
	@Autowired
	private CustomOAuth2UserService oauth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().cors().and().authorizeRequests()
			.antMatchers("/", "/login", "/logout", "/users/authenticated").permitAll()

			.anyRequest().authenticated()
			.and().oauth2Login()
				.userInfoEndpoint()
				.userService(oauth2UserService)
			.and().defaultSuccessUrl(reactPath)
			.and().logout()
				.logoutSuccessUrl(reactPath)
				.logoutUrl("/logout")
				.deleteCookies("JSESSIONID")
				.clearAuthentication(true)
				.invalidateHttpSession(true);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
		logger.info("using custom CORS configuration, allowd origins: " + allowedCorsOrigins);
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedCorsOrigins.split(",")));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
