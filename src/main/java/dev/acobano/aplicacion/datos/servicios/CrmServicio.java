package dev.acobano.aplicacion.datos.servicios;

import dev.acobano.aplicacion.datos.entidades.Contacto;
import dev.acobano.aplicacion.datos.entidades.Empresa;
import dev.acobano.aplicacion.datos.entidades.Estatus;
import dev.acobano.aplicacion.datos.repositorios.ContactoRepositorio;
import dev.acobano.aplicacion.datos.repositorios.EmpresaRepositorio;
import dev.acobano.aplicacion.datos.repositorios.EstatusRepositorio;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CrmServicio {

    @Autowired
    private ContactoRepositorio contactoRepositorio;

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @Autowired
    private EstatusRepositorio estatusRepositorio;


    public List<Contacto> mostrarListaContactos(String textoFiltro) {
        if (Objects.isNull(textoFiltro) || StringUtils.isEmpty(textoFiltro)) {
            return contactoRepositorio.findAll();
        } else {
            return contactoRepositorio.buscarContacto(textoFiltro);
        }
    }

    public long contarContactos() {
        return contactoRepositorio.count();
    }

    public void eliminarContacto(Contacto contacto) {
        contactoRepositorio.delete(contacto);
    }

    public void guardarContacto(Contacto contacto) {
        if (!Objects.isNull(contacto)) {
            contactoRepositorio.save(contacto);
        }
    }

    public List<Empresa> mostrarListaEmpresas() {
        return empresaRepositorio.findAll();
    }

    public List<Estatus> mostrarListaEstatus() {
        return estatusRepositorio.findAll();
    }
}
