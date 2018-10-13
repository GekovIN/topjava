package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

    List<Meal> getAll(int userId);
    Meal get(int id, int userId);
    Meal create(Meal meal);
    void update(Meal meal, int userId);
    void delete(int id, int userId);


}