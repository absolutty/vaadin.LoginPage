package sk.uniza.fri.vaadinapp.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import sk.uniza.fri.vaadinapp.models.Gender;
import sk.uniza.fri.vaadinapp.models.User;
import sk.uniza.fri.vaadinapp.services.UserService;

@Route("/user")
@PageTitle("User | Vaadin CRM")
@PermitAll
public class UserView extends FormLayout {

    private final UserService userService;
    private final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    private final H3 title = new H3("Úprava prihláseného používateľa");
    private final TextField firstName = new TextField("Meno");
    private final TextField lastName = new TextField("Priezvisko");
    private final EmailField email = new EmailField("Email");
    private final ComboBox<Gender> gender = new ComboBox<>("Pohlavie");
    private final DatePicker dateOfBirth = new DatePicker("Zvol dátum narodenia");
    private final PasswordField password = new PasswordField("Heslo");
    private final Span errorMessageField = new Span();
    private final Button submitButton = new Button("Ulož zmeny");

    public UserView(UserService userService) {
        this.userService = userService;

        addFields();

        User currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("There is no current user logged in!");
        } else {
            populateFields(currentUser);
            createFrontEnd();
        }
    }

    private void createFrontEnd() {
        add(title, firstName, lastName, email, gender, dateOfBirth, password, errorMessageField, submitButton);

        gender.setItems(Gender.values());
        gender.setItemLabelGenerator(Gender::getDisplayName);

        setMaxWidth("500px");
        setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);

        getElement().getStyle().set("margin", "auto");
        submitButton.addClickListener(buttonClickEvent -> saveChanges(getCurrentUser()));
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }


    private void addFields() {
        add(firstName, lastName, email);
    }

    private void populateFields(User user) {
        firstName.setValue(user.getFirstName());
        lastName.setValue(user.getLastName());
        email.setValue(user.getEmail());
        dateOfBirth.setValue(user.getDateOfBirth());
        password.setValue(user.getPassword());
    }

    private void saveChanges(User user) {
        try {
            user.setFirstName(firstName.getValue());
            user.setLastName(lastName.getValue());
            user.setEmail(email.getValue());

            userService.tryUpdateUser(user);

            Notification.show("Informácie o používateľovi uložené!");
        } catch (Exception e) {
            Notification.show("Nastala chyba: " + e.getMessage());
        }
    }

    private User getCurrentUser() {
        if (authentication != null && authentication.isAuthenticated()) {
            return userService.loadUserByUsername(authentication.getName());
        } else {
            return null;
        }
    }

}
