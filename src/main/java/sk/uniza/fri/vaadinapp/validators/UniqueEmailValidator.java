package sk.uniza.fri.vaadinapp.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import sk.uniza.fri.vaadinapp.services.UserService;

public class UniqueEmailValidator extends AbstractValidator<String> {

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        super("Email already exists in the database");
        this.userService = userService;
    }

    @Override
    public ValidationResult apply(String email, ValueContext context) {
        if (userService.emailDoesntExists(email)) {
            return ValidationResult.ok();
        } else {
            return ValidationResult.error("Tento email už u nás existuje!");
        }
    }

}
