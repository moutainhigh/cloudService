package com.cold.auth2server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		/*设置签名*/
		accessTokenConverter.setSigningKey("iplanguage");
		return accessTokenConverter;
	}

	@Bean
	public ApprovalStore approvalStore() {
		return new JdbcApprovalStore(dataSource);
	}

	//token 的存储
	@Bean
	public TokenStore jwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisConnectionFactory redisConnection;
	

	@Autowired
    private DataSource dataSource;
	
	@Bean
    public JdbcClientDetailsService myClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//super.configure(security);
		security.allowFormAuthenticationForClients()
				.checkTokenAccess("isAuthenticated()")
				.tokenKeyAccess("permitAll()");
		log.info("AuthorizationServerSecurityConfigurer is complete");
	}

	/**
	 * 配置授权、令牌的访问端点和令牌服务
	 * tokenStore：采用redis储存
	 * authenticationManager:身份认证管理器, 用于"password"授权模式
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		/*jwt方式+redis存储token*/
		endpoints.accessTokenConverter(jwtAccessTokenConverter());
		endpoints.authenticationManager(authenticationManager).tokenStore(new RedisTokenStore(redisConnection));
		/*普通*/
//		endpoints.authenticationManager(authenticationManager);
		log.info("AuthorizationServerEndpointsConfigurer is complete.");
	}

	/**
	 * 配置客户端详情信息(Client Details)
	 * clientId：（必须的）用来标识客户的Id。
	 * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
	 * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
	 * authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
	 * authorities：此客户端可以使用的权限（基于Spring Security authorities）。
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(myClientDetailsService());
		log.info("ClientDetailsServiceConfigurer is complete!");
	}


}
