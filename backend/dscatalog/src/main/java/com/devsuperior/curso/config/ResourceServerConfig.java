package com.devsuperior.curso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// Endpoints publicos
	private static final String[] PUBLIC = { "/oauth/token" };
	// Endpoints de operador e admin
	private static final String[] OPERATION_OR_ADMIN = { "/products/**", "/categories/**" };
	// Endpoints de admin
	private static final String[] ADMIN = { "/users/**" };

	@Autowired
	private JwtTokenStore tokenStore;

	// Verifica se o token é válido
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()// Acessar aos endpoints públicos não exigiram login
		.antMatchers(HttpMethod.GET, OPERATION_OR_ADMIN).permitAll() // Libera somente metodo GET para todos
		.antMatchers(OPERATION_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN") //Libera os outros métodos(POST,PUT) para perfis com role de adm e operator
		.antMatchers(ADMIN).hasRole("ADMIN"). 
		anyRequest().authenticated(); //Para acessar qualquer outra rota o usuário precisa estar logado

	}

}
