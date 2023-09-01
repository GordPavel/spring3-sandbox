package com.example.spring3.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Value("\${management.endpoints.web.base-path}")
    private val managementEndpointsBasePath: String,
) {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http
        .csrf { it.disable() }
        .authorizeExchange { exchange ->
            exchange
                .pathMatchers(
                    "${managementEndpointsBasePath}/**",
                )
                .permitAll()
                .anyExchange()
                .authenticated()
        }
        .httpBasic {}
        .build()
}

@Configuration
@Profile("dev")
@EnableConfigurationProperties(
    StaticBasicCredentials::class,
)
class DevelopmentSecurityConfig {
    @Bean
    @Profile("dev")
    fun userDetailsService(
        staticBasicCredentials: StaticBasicCredentials,
    ): ReactiveUserDetailsService = User
        .withDefaultPasswordEncoder()
        .username(staticBasicCredentials.username)
        .password(staticBasicCredentials.password)
        .roles(*staticBasicCredentials.roles.toTypedArray())
        .build()
        .let { MapReactiveUserDetailsService(it) }
}

@ConfigurationProperties(prefix = "static-credentials")
data class StaticBasicCredentials(
    val username: String,
    val password: String,
    val roles: Collection<String>
)
