package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealRestControllerTest extends AbstractControllerTest {

    @Test
    void testGet() throws Exception {
        TestUtil.print(
            mockMvc.perform(get(MealRestController.REST_URL + "/" + MealTestData.MEAL1_ID))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(contentJson(MEAL1))
        );
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(MealRestController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1))
        );
    }

    @Test
    void testGetBetween() throws Exception {
        TestUtil.print(
            mockMvc.perform(post(MealRestController.REST_URL + "/filter")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("startDate", "2015-05-30")
                    .param("startTime", "09:00:00")
                    .param("endDate", "2015-05-30")
                    .param("endTime", "14:00:00"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(contentJson(MEAL2, MEAL1))
        );
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(MealRestController.REST_URL + "/" + MealTestData.MEAL1_ID))
               .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    void update() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(MealRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        Meal actual = mealService.get(MEAL1_ID, USER_ID);
        assertMatch(actual, updated);
    }

    @Test
    void create() throws Exception {
        Meal created = getCreated();

        ResultActions action = mockMvc.perform(post(MealRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        Meal returned = readFromJson(action, Meal.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        List<Meal> all = mealService.getAll(USER_ID);
        assertMatch(all, created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }
}
