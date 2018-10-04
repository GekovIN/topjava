package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repositories.MealsRepository;
import ru.javawebinar.topjava.repositories.memory_repo.MealMemoryRepo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private static int caloriesPerDay = 2000;
    private static String LIST_MEALS = "/meals.jsp";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    private MealsRepository repository;

    public MealServlet() {
        this.repository = new MealMemoryRepo();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("forward to meals list");
        List<Meal> meals = repository.findAll();
        List<MealWithExceed> mealsWithExceeds = MealsUtil.getAllWithExceeded(meals, caloriesPerDay);

        LOG.debug("received MealWithExceed list from repository, size: " + mealsWithExceeds.size());
        //Добавляем форматер для форматирования даты в jsp:
        req.setAttribute("localDateTimeFormat", formatter);

        req.setAttribute("meals", mealsWithExceeds);
        req.getRequestDispatcher(LIST_MEALS).forward(req, resp);
    }

}
