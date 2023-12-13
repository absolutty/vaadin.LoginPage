package sk.uniza.fri.vaadinapp.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import sk.uniza.fri.vaadinapp.forms.RegisterForm;
import sk.uniza.fri.vaadinapp.services.UserDetailService;

@Route("register")
@PageTitle("Register | Vaadin CRM")
@AnonymousAllowed
public class RegisterView extends HorizontalLayout {

    private final RegisterForm registerForm;

    public RegisterView(UserDetailService userService) {
        addClassName("login-view");
        setSizeFull();

        Component image = new Image("/VAADIN/login-image.png", "Login image");
        image.getElement().getStyle().set("width", "475px");

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        registerForm = new RegisterForm(userService);
        registerForm.addBindingAndValidation();

        add(image, registerForm);
    }

}
