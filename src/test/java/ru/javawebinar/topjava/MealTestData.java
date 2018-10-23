package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID_1 = START_SEQ;
    public static final int MEAL_ID_2 = MEAL_ID_1+1;
    public static final int MEAL_ID_3 = MEAL_ID_2+1;
    public static final int MEAL_ID_4 = MEAL_ID_3+1;
    public static final int MEAL_ID_5 = MEAL_ID_4+1;
    public static final int MEAL_ID_6 = MEAL_ID_5+1;

    public static final Meal USER_MEAL_1 = new Meal (MEAL_ID_1, LocalDateTime.parse("2018-09-10T10:00:00"), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal (MEAL_ID_2, LocalDateTime.parse("2018-09-10T13:00:00"), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal (MEAL_ID_3, LocalDateTime.parse("2018-09-10T19:00:00"), "Ужин", 1500);

    public static final Meal ADMIN_MEAL_1 = new Meal (MEAL_ID_4, LocalDateTime.parse("2017-10-08T09:00:00"), "Завтрак", 1000);
    public static final Meal ADMIN_MEAL_2 = new Meal (MEAL_ID_5, LocalDateTime.parse("2017-10-08T14:00:00"), "Обед", 1350);
    public static final Meal ADMIN_MEAL_3 = new Meal (MEAL_ID_6, LocalDateTime.parse("2017-10-08T20:00:00"), "Ужин", 1750);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
