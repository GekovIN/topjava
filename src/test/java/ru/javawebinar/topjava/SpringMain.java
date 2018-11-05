package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.JDBC);
            appCtx.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();
            System.out.println("Bean definition names: ");
            for (String bean : appCtx.getBeanDefinitionNames()) {
                System.out.println(bean);
            }
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
//            System.out.println();
//
//            MealRestController mealController = appCtx.getBean(MealRestController.class);
//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExcess.forEach(System.out::println);
        }
    }
}
