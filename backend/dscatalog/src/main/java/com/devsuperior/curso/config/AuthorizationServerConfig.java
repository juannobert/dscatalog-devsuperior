package com.devsuperior.curso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtAccessTokenConverter tokenConverter;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	//Configuração da aplicação
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("dscatalog") // nome da aplicação
		.secret(passwordEncoder.encode("dscatalog123")) // Senha da aplicação
		.scopes("read","write") // escopo da aplicação
		.authorizedGrantTypes("password") // Autorização por senha
		.accessTokenValiditySeconds(864000); // Token valido por 24 horas
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore)
		.accessTokenConverter(tokenConverter);
	}
	
	
}
