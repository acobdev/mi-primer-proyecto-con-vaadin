package dev.acobano.aplicacion.seguridad;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class SeguridadServicio {

    private static final String URL_CIERRE_SESION_EXITOSO = "/";

    public UserDetails getUsuarioAutenticado() {
        //Sacamos el usuario registrado y su autenticación a partir del contexto:
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();

        //Si es un usuario ya autenticado, devolveremos sus datos:
        if (principal instanceof UserDetails) {
            return (UserDetails) context.getAuthentication().getPrincipal();
        }

        // En caso de que el usuario se anónimo o no autenticado, devolveremos 'null':
        return null;
    }

    public void cerrarSesion() {
        UI.getCurrent().getPage().setLocation(URL_CIERRE_SESION_EXITOSO);
        SecurityContextLogoutHandler manejadorCerrarSesion = new SecurityContextLogoutHandler();
        manejadorCerrarSesion.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
