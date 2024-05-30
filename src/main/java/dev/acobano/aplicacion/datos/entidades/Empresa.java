package dev.acobano.aplicacion.datos.entidades;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
public class Empresa {

    @Id
    private Long empresaId;

    @NotBlank
    private String nombre;

    @OneToMany(mappedBy = "empresa")
    @Nullable
    private List<Contacto> empleados;

    @Formula("(select count(c.empresa_id) from Contacto c where c.empresa_id = empresa_id)")
    private int numEmpleados;


    /* MÉTODOS GETTERS */
    public String getNombre() {
        return nombre;
    }

    public List<Contacto> getEmpleados() {
        return empleados;
    }

    public int getNumEmpleados() {
        return numEmpleados;
    }


    /* MÉTODOS SETTERS */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmpleados(List<Contacto> empleados) {
        this.empleados = empleados;
    }
}
