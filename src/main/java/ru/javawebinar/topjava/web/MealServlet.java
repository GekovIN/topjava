package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.meal_model.Meal;
import ru.javawebinar.topjava.model.meal_model.MealWithExceed;
import ru.javawebinar.topjava.service.meal_service.MealService;
import ru.javawebinar.topjava.service.meal_service.MealServiceFabric;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private static int caloriesPerDay = 2000;
    private static String LIST_MEALS = "/meals.jsp";
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private MealService mealService;

    public MealServlet() {
        this.mealService = MealServiceFabric.getMealService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String forward;
        String action = req.getParameter("action");

        if (action == null || action.equalsIgnoreCase("meals")) {
            LOG.debug("Action: meals");

            forward = LIST_MEALS;
            List<MealWithExceed> mealsWithExceeds = mealService.findAllWithExceed(caloriesPerDay);

            LOG.debug("received MealWithExceed list from repository, size: " + mealsWithExceeds.size());
            req.setAttribute("meals", mealsWithExceeds);

            //Добавляем форматер для форматирования даты в jsp:
            req.setAttribute("localDateTimeFormat", formatter);

        } else if (action.equalsIgnoreCase("edit")) {
            LOG.debug("Action: edit");
            forward = INSERT_OR_EDIT;
            Meal meal = mealService.findMealById(Long.parseLong(req.getParameter("id")));
            req.setAttribute("meal", meal);
            req.setAttribute("localDateTimeFormat", formatter);
        } else if (action.equalsIgnoreCase("delete")) {
            LOG.debug("Action: delete");
            forward = LIST_MEALS;
            mealService.deleteMeal(Long.parseLong(req.getParameter("id")));
            List<MealWithExceed> mealsWithExceeds = mealService.findAllWithExceed(caloriesPerDay);
            LOG.debug("received MealWithExceed list from repository, size: " + mealsWithExceeds.size());
            req.setAttribute("meals", mealsWithExceeds);
            req.setAttribute("localDateTimeFormat", formatter);
        } else if (action.equalsIgnoreCase("insert")) {
            LOG.debug("Action: insert");
            req.setAttribute("insert", true);
            forward = INSERT_OR_EDIT;
        } else {
            forward = LIST_MEALS;
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String tmpId = req.getParameter("id");
        if (tmpId == null || tmpId.isEmpty()) {
            mealService.addMeal(new Meal(dateTime, description, calories));
        } else {
            long id = Long.parseLong(tmpId);
            mealService.updateMeal(new Meal(id, dateTime, description, calories));
        }

        List<MealWithExceed> mealsWithExceeds = mealService.findAllWithExceed(caloriesPerDay);
        LOG.debug("received MealWithExceed list from repository, size: " + mealsWithExceeds.size());
        req.setAttribute("meals", mealsWithExceeds);
        req.setAttribute("localDateTimeFormat", formatter);
        req.getRequestDispatcher(LIST_MEALS).forward(req, resp);

    }
}
