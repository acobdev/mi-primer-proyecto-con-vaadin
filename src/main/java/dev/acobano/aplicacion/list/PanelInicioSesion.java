package dev.acobano.aplicacion.list;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Inicio de sesión | Vaadin")
@AnonymousAllowed
public class PanelInicioSesion extends VerticalLayout implements BeforeEnterListener {

    private LoginForm formularioInicioSesion = new LoginForm();


    public PanelInicioSesion() {
        addClassName("panel-inicio-sesion");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        formularioInicioSesion.setAction("login");

        add(
                new H1("Bienvenido a mi primera prueba con Vaadin"),
                formularioInicioSesion
        );
    }

    //Método sobreescrito de la interfaz BeforeEnterListener que nos otorga un evento
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            formularioInicioSesion.setError(true);
        }
    }
}
