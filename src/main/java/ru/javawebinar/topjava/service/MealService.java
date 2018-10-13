package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealService {

    Collection<Meal> getAll(int userId);
    Meal get(int id, int userId);
    Meal create(Meal meal);
    void update(Meal meal, int userId);
    void delete(int id, int userId);


}