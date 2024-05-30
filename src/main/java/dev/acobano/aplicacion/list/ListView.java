package dev.acobano.aplicacion.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import dev.acobano.aplicacion.datos.entidades.Contacto;
import dev.acobano.aplicacion.datos.servicios.CrmServicio;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Objects;

@PageTitle("Contactos | Vaadin")
@Route(value = "lista-contactos", layout = PanelPrincipal.class)
@RouteAlias(value = "")
@PermitAll
public class ListView extends VerticalLayout {

    Grid<Contacto> contactoGrid = new Grid<>(Contacto.class);
    TextField campoFiltros = new TextField();
    FormularioContacto formContacto;

    @Autowired
    private CrmServicio servicio;

    public ListView(CrmServicio servicio) {
        /*setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("This place intentionally left empty");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
         */

        this.servicio = servicio;
        addClassName("lista-contactos");
        setSizeFull();
        configurarGrid();
        configurarFormulario();

        add(
                inicializarBarraHerramientas(),
                inicializarPanelContenido()
        );

        //Insertamos en el grid todos los elementos existentes en la BD:
        actualizarGridContactos();
        cerrarEditor();
    }

    private void cerrarEditor() {
        formContacto.setContacto(null);
        formContacto.setVisible(false);
        removeClassName("editando");
    }

    private void configurarFormulario() {
        formContacto = new FormularioContacto(servicio.mostrarListaEmpresas(), servicio.mostrarListaEstatus());
        //formContacto.setWidth("5em");
        formContacto.insertarListener(FormularioContacto.EventoGuardar.class, this::guardarContacto);
        formContacto.insertarListener(FormularioContacto.EventoEliminar.class, this::eliminarContacto);
        formContacto.insertarListener(FormularioContacto.EventoCerrar.class, evento -> cerrarEditor());
    }

    private void eliminarContacto(FormularioContacto.EventoEliminar eventoEliminar) {
        servicio.eliminarContacto(eventoEliminar.getContacto());
        actualizarGridContactos();
        cerrarEditor();
    }

    private void guardarContacto(FormularioContacto.EventoGuardar eventoGuardar) {
        servicio.guardarContacto(eventoGuardar.getContacto());
        actualizarGridContactos();
        cerrarEditor();
    }

    private Component inicializarBarraHerramientas() {
        //CAMPO DE TEXTO PARA FILTRAR POR NOMBRE:
        campoFiltros.setPlaceholder("Filtrar por nombre...");
        campoFiltros.setClearButtonVisible(true);

        //Ponemos el modo LAZY para que no busque en la BD cada vez que el usuario presione
        //una tecla en el campo de filtros, sino cuando presione el botón de búsqueda.
        campoFiltros.setValueChangeMode(ValueChangeMode.LAZY);

        //Adicionamos un listener para el filtro:
        campoFiltros.addValueChangeListener(evento -> actualizarGridContactos());

        //BOTÓN PARA CREAR NUEVOS CONTACTOS:
        Button btnCrearContacto = new Button("Nuevo contacto");
        btnCrearContacto.addClickListener(evento -> crearContacto());

        //COMPONENTE UI DE BARRA DE HERRAMIENTAS:
        HorizontalLayout barraHerramientas = new HorizontalLayout(campoFiltros, btnCrearContacto);
        barraHerramientas.addClassName("barra-herramientas");
        return barraHerramientas;
    }

    private void crearContacto() {
        contactoGrid.asSingleSelect().clear();
        editarContacto(new Contacto());
    }

    private Component inicializarPanelContenido() {
        HorizontalLayout panelContenido = new HorizontalLayout(contactoGrid, formContacto);
        panelContenido.setSizeFull();
        panelContenido.setFlexGrow(2, contactoGrid);
        panelContenido.setFlexGrow(1, formContacto);
        panelContenido.addClassName("panel-contenido");
        return panelContenido;
    }


    private void configurarGrid() {
        contactoGrid.addClassName("tabla-contactos");
        contactoGrid.setSizeFull();
        contactoGrid.setColumns("nombre", "apellidos", "email");
        contactoGrid.addColumn(contacto -> contacto.getEmpresa().getNombre()).setHeader("Empresa");
        contactoGrid.addColumn(contacto -> contacto.getEstatus().getNombre()).setHeader("Estatus");
        contactoGrid.getColumns().forEach(columna -> columna.setAutoWidth(true));

        //Añadimos un listener para cuando aparezca el formulario para modificar
        //datos cuando seleccionemos una fila:
        contactoGrid.asSingleSelect().addValueChangeListener(evento -> editarContacto(evento.getValue()));
    }

    private void editarContacto(Contacto contacto) {
        if (Objects.isNull(contacto)) {
            cerrarEditor();
        } else {
            formContacto.setContacto(contacto);
            formContacto.setVisible(true);
            addClassName("editando");
        }
    }

    private void actualizarGridContactos() {
        contactoGrid.setItems(servicio.mostrarListaContactos(campoFiltros.getValue()));
    }
}
