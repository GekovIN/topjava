package ru.javawebinar.topjava.util;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.repository.datajpa.CrudUserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    @Qualifier("crudUserRepository")
    CrudUserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserTo user = (UserTo) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.userTo.notEmpty.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "validation.userTo.notEmpty.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "validation.userTo.notEmpty.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "caloriesPerDay", "validation.userTo.notEmpty.caloriesPerDay");

        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        Integer calories = user.getCaloriesPerDay();

        if (name.length() < 2 || name.length() > 100) {
            errors.rejectValue("name", "validation.userTo.invalidPattern.name");
        }

        if (password.length() < 5 || password.length() > 32) {
            errors.rejectValue("password", "validation.userTo.invalidPattern.password");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            errors.rejectValue("email", "validation.userTo.invalidPattern.email");
        }

        if (calories < 5 || calories > 10000) {
            errors.rejectValue("caloriesPerDay", "validation.userTo.notEmpty.caloriesPerDay");
        }

        if (SecurityUtil.safeGet() == null) {
            if (userRepository.existsByEmail(email)) {
                errors.rejectValue("email", "validation.userTo.already_exists.email");
            }
        }

    }
}
