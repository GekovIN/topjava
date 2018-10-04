package ru.javawebinar.topjava.memory_repo;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealMemoryRepo {

    private Map<Long, Meal> meals;

    public Map<Long, Meal> getMeals() {

        if (meals == null)  {
            meals = new ConcurrentHashMap<>();
            meals.put(1L, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            meals.put(2L, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            meals.put(3L, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));

            meals.put(4L, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            meals.put(5L, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            meals.put(6L, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }

        return meals;
    }

}
