package dev.acobano.aplicacion.datos.repositorios;

import dev.acobano.aplicacion.datos.entidades.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepositorio extends JpaRepository<Contacto, Long> {

    @Query("select c from Contacto c " +
           "where lower(c.nombre) like lower(concat('%', :textoFiltro, '%')) " +
           "or lower(c.apellidos) like lower(concat('%', :textoFiltro, '%'))")
    List<Contacto> buscarContacto(@Param("textoFiltro") String textoFiltro);
}
