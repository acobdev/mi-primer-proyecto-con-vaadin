package dev.acobano.aplicacion.datos.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Estatus {

    @Id
    private Long estatusId;

    private String nombre;

    public Estatus() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
