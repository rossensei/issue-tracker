package io.github.rossensei.issuetracker.auth.config

import com.nimbusds.jose.jwk.source.ImmutableSecret
import io.github.rossensei.issuetracker.config.FrontendProperties
import org.springframework.core.convert.converter.Converter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.BadJwtException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.UUID
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class SecurityConfig(
    private val frontendProperties: FrontendProperties,
    private val jwtProperties: JwtProperties,
) {
    private val key: SecretKey by lazy {
        SecretKeySpec(jwtProperties.secret.toByteArray(), "HmacSHA256")
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()

        configuration.allowCredentials = true
        configuration.allowedOrigins = listOf(frontendProperties.baseUrl)

        configuration.allowedMethods = listOf(
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS"
        )

        configuration.allowedHeaders = listOf("*")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http
            .sessionManagement {
                it.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .csrf { csrf ->
                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                csrf.ignoringRequestMatchers("/api/auth/login", "/api/auth/register")
            }
            .authorizeHttpRequests {
                it.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { rs ->
                rs.bearerTokenResolver(cookieTokenResolver())
                rs.jwt { it.jwtAuthenticationConverter(jwtConverter()) }
            }
            .cors { }

        return http.build()
    }

    private fun cookieTokenResolver() = BearerTokenResolver { request ->
        request.cookies?.firstOrNull { it.name == jwtProperties.cookieName }?.value
    }

    private fun jwtConverter() = Converter<Jwt, AbstractAuthenticationToken> { jwt ->
        val claim = jwt.getClaimAsMap("user")
            ?: throw BadJwtException("Missing user claim")

        val user = AuthenticatedUser(
            id = UUID.fromString(claim["id"] as String),
            email = claim["email"] as String,
            username = claim["username"] as String,
        )

        UserAuthenticationToken(user, jwt, listOf(SimpleGrantedAuthority("ROLE_USER")))
    }

    @Bean
    fun jwtDecoder(): JwtDecoder =
        NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build()

    @Bean
    fun jwtEncoder(): JwtEncoder = NimbusJwtEncoder(ImmutableSecret(key))

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun authenticationManager(
        usernameUserDetailsService: UsernameUserDetailsService,
        passwordEncoder: PasswordEncoder,
    ): AuthenticationManager {
        val provider = DaoAuthenticationProvider(usernameUserDetailsService)
        provider.setPasswordEncoder(passwordEncoder)
        return ProviderManager(provider)
    }

}