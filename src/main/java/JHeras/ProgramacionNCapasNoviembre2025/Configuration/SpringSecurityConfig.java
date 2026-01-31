package JHeras.ProgramacionNCapasNoviembre2025.Configuration;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IUsuarioJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.Service.CustomFailureHandle;
import JHeras.ProgramacionNCapasNoviembre2025.Service.UserDetailsJPAService;
import jakarta.servlet.http.HttpSession;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SpringSecurityConfig {

    private UserDetailsJPAService userDetailsJPAService;

    public SpringSecurityConfig(UserDetailsJPAService userDetailsJPAService) {
        this.userDetailsJPAService = userDetailsJPAService;
    }

    @Autowired
    private IUsuarioJPARepository iUsuarioJPARepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        CustomFailureHandle failureHandle = new CustomFailureHandle();

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(config -> config
                .requestMatchers("/login", "acceso-denegado", "/login**").permitAll()
                .requestMatchers("/usuario/detail/**", "/usuario/editar", "/usuario/updateImagen",
                        "/api/**", "/usuario/addDireccion/**")
                .hasAnyRole("Admin", "Usuario")
                .requestMatchers("/**").hasRole("Admin")
                .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado")
                )
                .formLogin(form
                        -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            Usuario usuario = iUsuarioJPARepository.findByUsername(authentication.getName());

                            HttpSession session = request.getSession();
                            session.setAttribute("userEstatus", usuario.getEstatus());

                            String targetUrl = determineTargetUrl(authentication);
                            response.sendRedirect(request.getContextPath() + targetUrl);

                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                )
                .userDetailsService(userDetailsJPAService);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Admin"))) {
            return "/usuario";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_Usuario"))) {
            String user = authentication.getName();
            Usuario usuario = iUsuarioJPARepository.findByUsername(user);

            int idUsuario = usuario.getIdUsuario();
            return "/usuario/detail/" + idUsuario;
        } else {
            return "/usuario";
        }
    }

}
