package ru.javawebinar.topjava.service.meal_service.memory_meal_service;

import ru.javawebinar.topjava.model.meal_model.Meal;
import ru.javawebinar.topjava.repositories.memory_repo.MealMemoryRepo;
import ru.javawebinar.topjava.service.meal_service.MealService;

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
    public void addMeal() {
        repository.addMeal();
    }

    @Override
    public void deleteMeal() {
        repository.deleteMeal();
    }

    @Override
    public void updateMeal() {
        repository.updateMeal();
    }
}
