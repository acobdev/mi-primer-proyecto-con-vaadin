package dev.acobano.aplicacion;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "mi-primer-proyecto-con-vaadin")
@PWA(
        name = "Mi primer proyecto con Vaadin",
        shortName = "Vaadin",
        offlinePath = "offline.html",
        offlineResources = {"icons/logo.png", "images/offline.png"}
)
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
