package dev.acobano.aplicacion.list;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import dev.acobano.aplicacion.seguridad.SeguridadServicio;
import org.springframework.beans.factory.annotation.Autowired;


public class PanelPrincipal extends AppLayout {

     @Autowired
     private SeguridadServicio seguridadServicio;

    public PanelPrincipal() {
        inicializarCabecera();
        inicializarBandeja();
    }

    private void inicializarCabecera() {
        H1 logo = new H1("Vaadim CRM");
        //Añadimos estilos CSS: m-m = margin medium
        //https://vaadin.com/docs/latest/styling/lumo/utility-classes
        logo.addClassNames("text-l", "m-m");

        Button btnCerrarSesion = new Button("Cerrar sesión", evento -> seguridadServicio.cerrarSesion());

        HorizontalLayout cabecera = new HorizontalLayout(new DrawerToggle(), logo, btnCerrarSesion);
        cabecera.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        cabecera.expand(logo);
        cabecera.setWidthFull();
        //py-0 = padding 0 en el eje y
        //px-m = padding medium en el eje x
        cabecera.addClassNames("py-0", "px-m");

        addToNavbar(cabecera);
    }

    private void inicializarBandeja() {
        RouterLink listaContactos = new RouterLink("Lista de contactos", ListView.class);
        RouterLink panelGraficos = new RouterLink("Gráfico de empresas", PanelGraficoTarta.class);

        listaContactos.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(
                listaContactos,
                panelGraficos
        ));
    }
}
