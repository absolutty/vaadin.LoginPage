package sk.uniza.fri.vaadinapp.forms;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import sk.uniza.fri.vaadinapp.models.Gender;
import sk.uniza.fri.vaadinapp.models.User;
import sk.uniza.fri.vaadinapp.services.UserService;
import sk.uniza.fri.vaadinapp.validators.UniqueEmailValidator;
import sk.uniza.fri.vaadinapp.views.LoginView;

import java.util.stream.Stream;

public class RegisterForm extends FormLayout {

    private final UserService userService;
    private boolean enablePasswordValidation;

    private final H3 title = new H3("Registrácia");
    private final TextField firstName = new TextField("Meno");
    private final TextField lastName = new TextField("Priezvisko");
    private final EmailField email = new EmailField("Email");
    private final ComboBox<Gender> gender = new ComboBox<>("Pohlavie");
    private final DatePicker dateOfBirth = new DatePicker("Zvol dátum narodenia");
    private final PasswordField password = new PasswordField("Heslo");
    private final PasswordField passwordConfirm = new PasswordField("Zopakuj heslo");
    private final Span errorMessageField = new Span();
    private final Button submitButton = new Button("Registruj");

    public RegisterForm(UserService userService) {
        this.userService = userService;
        this.createFrontEnd();
    }

    private void createFrontEnd() {
        setRequiredIndicatorVisible(firstName, lastName, email, password, passwordConfirm);
        add(title, firstName, lastName, email, gender, dateOfBirth, password, passwordConfirm, errorMessageField, submitButton);

        gender.setItems(Gender.values());
        gender.setItemLabelGenerator(Gender::getDisplayName);

        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP)
        );
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);

        getElement().getStyle().set("margin", "auto");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(this);

        binder.forField(email)
                .withValidator(new UniqueEmailValidator(userService))
                .bind("email");

        binder.forField(password)
                .withValidator(this::passwordValidator)
                .bind("password");

        passwordConfirm.addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(this.errorMessageField);

        submitButton.addClickListener(event -> {
            try {
                User toBeRegistered = new User();
                binder.writeBean(toBeRegistered);
                toBeRegistered.setRole("USER");
                userService.tryRegisterUser(toBeRegistered);

                UI.getCurrent().navigate(LoginView.class);
            } catch (ValidationException ignored) {}
        });
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Heslo musí mať aspoň 8 znakov!");
        }

        if (enablePasswordValidation && !pass1.equals(passwordConfirm.getValue())) {
            return ValidationResult.error("Heslá sa nezhodujú!");
        }

        enablePasswordValidation = true;
        return ValidationResult.ok();
    }

}
