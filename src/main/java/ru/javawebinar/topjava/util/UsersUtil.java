package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(null, "Илья", "gekovin@gmail.com", "1111", Role.ROLE_ADMIN),
            new User(null, "Альбина", "albina@gmail.com", "2222", Role.ROLE_USER),
            new User(null, "Иван", "ivan@gmail.com", "3333", Role.ROLE_USER),
            new User(null, "Анна", "anna@gmail.com", "4444", Role.ROLE_USER),
            new User(null, "Павел", "pavel@gmail.com", "5555", Role.ROLE_USER)
    );

    public static List<User> getFilteredByName(List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(AbstractNamedEntity::getName))
                .collect(Collectors.toList());
    }
}
