package sk.uniza.fri.vaadinapp;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
class MainView extends VerticalLayout {
    MainView() {
        add(new H1("Hello, world!"));
    }
}
