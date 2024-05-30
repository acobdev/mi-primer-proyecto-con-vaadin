package dev.acobano.aplicacion.datos.repositorios;

import dev.acobano.aplicacion.datos.entidades.Estatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstatusRepositorio extends JpaRepository<Estatus, Long> {
}
