package dev.acobano.aplicacion.seguridad;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import dev.acobano.aplicacion.list.PanelInicioSesion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class ConfiguracionSeguridad extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers
                        (new AntPathRequestMatcher("/public/**"))
                .permitAll());
        super.configure(http);
        setLoginView(http, PanelInicioSesion.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    //Bean con los datos de los usuarios de prueba, uno raso y otro administrador,
    //que podrán acceder por el formulario de inicio de sesión de Vaadin:
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails usuario = User.withUsername("usuario")
                .password("{noop}prueba")
                .roles("USER")
                .build();

        UserDetails administrador =
                User.withUsername("admin")
                        .password("{noop}admin")
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(usuario, administrador);
    }
}
