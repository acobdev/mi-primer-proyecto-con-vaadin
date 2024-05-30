package dev.acobano.aplicacion.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import dev.acobano.aplicacion.datos.servicios.CrmServicio;
import jakarta.annotation.security.PermitAll;

@PageTitle("Gráficos | Vaadin CRM")
@Route(value = "grafico-tarta", layout = PanelPrincipal.class)
@PermitAll
public class PanelGraficoTarta extends VerticalLayout {

    private CrmServicio servicio;

    public PanelGraficoTarta(CrmServicio servicio) {
        this.servicio = servicio;
        addClassName("panel-grafico-tarta");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getEstadisticasContactos(), getGraficoEmpresas());
    }

    private Component getEstadisticasContactos() {
        long cantidadContactos = servicio.contarContactos();
        String textoEstadistica;

        if (cantidadContactos == 1)
            textoEstadistica = "1 contacto";
        else
            textoEstadistica = cantidadContactos + " contactos";

        Span estadisticas = new Span(textoEstadistica);
        estadisticas.addClassNames("text-xl", "mt-m");
        return estadisticas;
    }

    private Component getGraficoEmpresas() {
        Chart grafico = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        servicio.mostrarListaEmpresas().forEach(empresa -> {
            dataSeries.add(new DataSeriesItem(empresa.getNombre(), empresa.getNumEmpleados()));
        });

        grafico.getConfiguration().setSeries(dataSeries);
        return grafico;
    }
}
