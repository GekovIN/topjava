package ru.javawebinar.topjava.repositories;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsRepository {

    List<Meal> findAll();
    void addMeal();
    void deleteMeal();
    void updateMeal();
}
