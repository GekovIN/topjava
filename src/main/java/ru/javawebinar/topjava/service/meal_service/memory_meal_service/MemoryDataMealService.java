package ru.javawebinar.topjava.service.meal_service.memory_meal_service;

import ru.javawebinar.topjava.model.meal_model.Meal;
import ru.javawebinar.topjava.model.meal_model.MealWithExceed;
import ru.javawebinar.topjava.repositories.memory_repo.MealMemoryRepo;
import ru.javawebinar.topjava.service.meal_service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MemoryDataMealService implements MealService {

    private final MealMemoryRepo repository;

    public MemoryDataMealService() {
        this.repository = new MealMemoryRepo();
    }

    @Override
    public List<Meal> findAll() {
        return repository.findAll();
    }

    @Override
    public List<MealWithExceed> findAllWithExceed(int caloriesPerDay) {
        return MealsUtil.getAllWithExceeded(findAll(), caloriesPerDay);
    }

    @Override
    public void addMeal(Meal meal) {
        repository.addMeal(meal);
    }

    @Override
    public void deleteMeal(Long id) {
        repository.deleteMeal(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        repository.updateMeal(meal);
    }

    @Override
    public Meal findMealById(Long id) {
        return repository.findMealById(id);
    }
}
