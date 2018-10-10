package ru.javawebinar.topjava.service.meal_service;

import ru.javawebinar.topjava.model.meal_model.Meal;

import java.util.List;

public interface MealService {

    List<Meal> findAll();
    void addMeal();
    void deleteMeal();
    void updateMeal();
}
