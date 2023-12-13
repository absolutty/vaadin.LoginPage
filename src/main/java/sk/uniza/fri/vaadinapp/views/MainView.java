package sk.uniza.fri.vaadinapp.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
class MainView extends VerticalLayout {

    MainView() {
        add(new H1("Hello, world!"));
    }

}
