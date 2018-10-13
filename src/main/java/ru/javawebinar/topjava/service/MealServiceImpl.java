package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class MealServiceImpl implements MealService {

    private static String NOT_FOUND_MESSAGE = "Meal with id: %d for user with id: %d not found.";

    private MealRepository repository;

    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId)
                .stream()
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        if (meal == null)
            throw new NotFoundException(String.format(NOT_FOUND_MESSAGE, id, userId));
        return meal;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public Meal update(Meal meal, int userId) {
        if (meal.getUserID() != userId)
            throw new NotFoundException(String.format(NOT_FOUND_MESSAGE, meal.getId(), userId));
        return create(meal);
    }

    @Override
    public void delete(int id, int userId) {
        if (!repository.delete(id, userId))
            throw new NotFoundException(String.format(NOT_FOUND_MESSAGE, id, userId));
    }

//    public static void main(String[] args) {
//        MealServiceImpl service = new MealServiceImpl(new InMemoryMealRepositoryImpl());
//
//        service.delete(2, 2);
//    }

}