package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        log.info("getAll");
        List<Meal> meals = service.getAll(SecurityUtil.authUserId());
        return MealsUtil.getWithExceeded(meals, SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getAllDateFiltered(String fromDateStr, String toDateStr, String fromTimeStr, String toTimeStr) {

        LocalDate fromDate = DateTimeUtil.localDateParse(fromDateStr);
        LocalDate toDate = DateTimeUtil.localDateParse(toDateStr);
        if (fromDate == null)
            fromDate = LocalDate.MIN;
        if (toDate == null)
            toDate = LocalDate.MAX;

        LocalTime fromTime = DateTimeUtil.localTimeParse(fromTimeStr);
        LocalTime toTime = DateTimeUtil.localTimeParse(toTimeStr);
        if (fromTime == null)
            fromTime = LocalTime.MIN;
        if (toTime == null)
            toTime = LocalTime.MAX;
//      Ошибка - получаем exceeded уже по отфильтрованному списку, соответственно exceed определяется неправильно:
//        List<Meal> meals = service.getAllFilteredByDate(SecurityUtil.authUserId(), fromDate, toDate, fromTime, toTime);
//
//        return MealsUtil.getWithExceeded(meals, SecurityUtil.authUserCaloriesPerDay());

//      Правильно: сначала получаем список Meal отфильтрованный по датам
//                 затем получаем список MealWithExceed, отфильтрованный по времени
        List<Meal> mealsFiltered = service.getAllFilteredByDate(SecurityUtil.authUserId(), fromDate, toDate);
        return MealsUtil.getFilteredWithExceeded(mealsFiltered, SecurityUtil.authUserCaloriesPerDay(), fromTime, toTime);

    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserID(SecurityUtil.authUserId());
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int userId) {
        log.info("update {} with id={}", meal, userId);
        service.update(meal, SecurityUtil.authUserId());
    }

}