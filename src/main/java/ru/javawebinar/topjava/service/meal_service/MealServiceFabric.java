package ru.javawebinar.topjava.service.meal_service;

import ru.javawebinar.topjava.service.meal_service.memory_meal_service.MemoryDataMealService;

public class MealServiceFabric {

    private static MealService mealService;

    public static MealService getMealService() {
        if (mealService == null) {
            mealService = new MemoryDataMealService();
        }
        return mealService;
    }
}
