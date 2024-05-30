package dev.acobano.aplicacion.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import dev.acobano.aplicacion.datos.entidades.Contacto;
import dev.acobano.aplicacion.datos.entidades.Empresa;
import dev.acobano.aplicacion.datos.entidades.Estatus;

import java.util.List;

public class FormularioContacto extends FormLayout {

    //Los binders de Vaadin enlazan una entidad de la BD con un elemento de la UI:
    Binder<Contacto> contactoBinder = new BeanValidationBinder<>(Contacto.class);

    TextField nombre = new TextField("Nombre");
    TextField apellidos = new TextField("Apellidos");
    EmailField email = new EmailField("Email");
    ComboBox<Estatus> estatus = new ComboBox<>("Estatus");
    ComboBox<Empresa> empresa = new ComboBox<>("Empresa");
    Button btnGuardarContacto = new Button("Guardar");
    Button btnEliminarContacto = new Button("Eliminar");
    Button btnCancelarAccion = new Button("Volver");
    private Contacto contacto;


    /* CONSTRUCTOR */
    public FormularioContacto(List<Empresa> listaEmpresas, List<Estatus> listaEstatus) {
        addClassName("formulario-contactos");

        //Bindeamos los atributos de UI (botones, textFields, comboBoxes...) al atributo de la clase Contacto:
        contactoBinder.bindInstanceFields(this);

        empresa.setItems(listaEmpresas);
        empresa.setItemLabelGenerator(Empresa::getNombre);

        estatus.setItems(listaEstatus);
        estatus.setItemLabelGenerator(Estatus::getNombre);

        add(
                nombre,
                apellidos,
                email,
                empresa,
                estatus,
                inicializarBotonesFormulario()
        );
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
        contactoBinder.readBean(contacto);
    }

    private Component inicializarBotonesFormulario() {
        //Cambiamos la estética de los tres botones del formulario:
        btnGuardarContacto.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnEliminarContacto.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnCancelarAccion.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        //Ponemos los listeners correspondientes a dichos botones:
        btnGuardarContacto.addClickListener(evento -> validarGuardarContacto());
        btnEliminarContacto.addClickListener(evento -> fireEvent(new EventoEliminar(this, contacto)));
        btnCancelarAccion.addClickListener(evento -> fireEvent(new EventoCerrar(this, contacto)));

        //Adicionamos atajos de teclado a los botones más impotantes:
        btnGuardarContacto.addClickShortcut(Key.ENTER);
        btnCancelarAccion.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(
                btnGuardarContacto,
                btnEliminarContacto,
                btnCancelarAccion
        );
    }

    //Métodos de lógica para los eventos de botón:
    private void validarGuardarContacto() {
        try {
            contactoBinder.writeBean(contacto);
            fireEvent(new EventoGuardar(this, contacto));
        }
        catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    //EVENTOS DE BOTÓN:
    public static abstract class FormularioContactoEvento extends ComponentEvent<FormularioContacto> {

        private Contacto contacto;

        protected FormularioContactoEvento(FormularioContacto fuente, Contacto contacto) {
            super(fuente, false);
            this.contacto = contacto;
        }

        public Contacto getContacto() {
            return contacto;
        }
    }

    public static class EventoGuardar extends FormularioContactoEvento {
        EventoGuardar(FormularioContacto fuente, Contacto contacto) {
            super(fuente, contacto);
        }
    }

    public static class EventoEliminar extends FormularioContactoEvento {
        EventoEliminar(FormularioContacto fuente, Contacto contacto) {
            super(fuente, contacto);
        }
    }

    public static class EventoCerrar extends FormularioContactoEvento {
        EventoCerrar(FormularioContacto fuente, Contacto contacto) {
            super(fuente, contacto);
        }
    }

    public <T extends ComponentEvent<?>>Registration insertarListener(Class<T> tipoEvento,
                                                                      ComponentEventListener<T> listener) {
        return getEventBus().addListener(tipoEvento, listener);
    }
}
