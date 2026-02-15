package com.polarbookshop.catalogservice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.DefaultSecurityFilterChain

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class SecurityConfiguration {

	@Bean
	fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain =
		http
			.authorizeHttpRequests {
				it
					.requestMatchers(HttpMethod.GET, "/", "/books/**")
					.permitAll()
					.anyRequest()
					.hasRole("employee")
			}
			.oauth2ResourceServer {
				it.jwt(Customizer.withDefaults())
			}
			.sessionManagement {
				it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			}
			.csrf {
				it.disable()
			}
			.build()

	@Bean
	fun jwtAuthenticationConverter(): JwtAuthenticationConverter =
		JwtAuthenticationConverter().apply {
			setJwtGrantedAuthoritiesConverter(
				JwtGrantedAuthoritiesConverter().apply {
					setAuthorityPrefix("ROLE_")
					setAuthoritiesClaimName("roles")
				}
			)
		}
}
