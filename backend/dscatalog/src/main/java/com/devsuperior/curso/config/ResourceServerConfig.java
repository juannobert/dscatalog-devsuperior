package com.devsuperior.curso.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// Endpoints publicos
	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" };
	// Endpoints de operador e admin
	private static final String[] OPERATION_OR_ADMIN = { "/products/**", "/categories/**" };
	// Endpoints de admin
	private static final String[] ADMIN = { "/users/**" };
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JwtTokenStore tokenStore;

	// Verifica se o token é válido
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) { // Se for perfil de teste
			http.headers().frameOptions().disable(); // Liberar H2 console
		}
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()// Acessar aos endpoints públicos não exigiram login
		.antMatchers(HttpMethod.GET, OPERATION_OR_ADMIN).permitAll() // Libera somente metodo GET para todos
		.antMatchers(OPERATION_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN") //Libera os outros métodos(POST,PUT) para perfis com role de adm e operator
		.antMatchers(ADMIN).hasRole("ADMIN"). 
		anyRequest().authenticated(); //Para acessar qualquer outra rota o usuário precisa estar logado
		
		http.cors().configurationSource(corsConfigurationSource());

	}
	
	@Bean
	 CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean
                = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
