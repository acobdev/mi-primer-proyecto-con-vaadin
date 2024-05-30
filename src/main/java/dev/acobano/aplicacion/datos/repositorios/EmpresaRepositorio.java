package dev.acobano.aplicacion.datos.repositorios;

import dev.acobano.aplicacion.datos.entidades.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
}
