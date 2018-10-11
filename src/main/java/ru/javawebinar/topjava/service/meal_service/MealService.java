package ru.javawebinar.topjava.service.meal_service;

import ru.javawebinar.topjava.model.meal_model.Meal;
import ru.javawebinar.topjava.model.meal_model.MealWithExceed;

import java.util.List;

public interface MealService {

    List<Meal> findAll();
    List<MealWithExceed> findAllWithExceed(int caloriesPerDay);

    void addMeal(Meal meal);
    void deleteMeal(Long id);
    void updateMeal(Meal meal);

    Meal findMealById(Long id);
}
