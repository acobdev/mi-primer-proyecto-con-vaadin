package dev.acobano.aplicacion.datos.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Contacto {

    @Id
    private Long contactoId;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellidos;

    @ManyToOne
    @JoinColumn(name = "empresaId")
    @NotNull
    @JsonIgnoreProperties({"empleados"})
    private Empresa empresa;

    @NotNull
    @JoinColumn(name = "estatusId")
    @ManyToOne
    private Estatus estatus;

    @Email
    @NotEmpty
    private String email;


    /* MÉTODO SOBREESCRITO toString */
    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }

    /* MÉTODOS GETTERS */
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public String getEmail() {
        return email;
    }


    /* MÉTODOS SETTERS */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
