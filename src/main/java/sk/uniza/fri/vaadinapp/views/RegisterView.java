package sk.uniza.fri.vaadinapp.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import sk.uniza.fri.vaadinapp.forms.RegisterForm;
import sk.uniza.fri.vaadinapp.services.UserDetailService;

@Route("register")
@PageTitle("Register | Vaadin CRM")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private final RegisterForm registerForm;

    public RegisterView(UserDetailService userService) {
        registerForm = new RegisterForm(userService);
        registerForm.addBindingAndValidation();

        setHorizontalComponentAlignment(Alignment.CENTER, registerForm);

        add(registerForm);
    }

}
