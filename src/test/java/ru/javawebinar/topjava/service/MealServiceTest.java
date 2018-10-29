package ru.javawebinar.topjava.service;

import org.hamcrest.core.IsInstanceOf;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.RollbackException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    private static final Logger logger =
            LoggerFactory.getLogger(MealServiceTest.class);

    private static Map<String, Long> allTestTime = new HashMap<>();

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {

        private long start;

        @Override
        protected void starting(Description description) {
            logger.info("Starting test: " + description.getMethodName());
            start = System.currentTimeMillis();
        }

        @Override
        protected void finished(Description description) {
            long end = System.currentTimeMillis();
            allTestTime.put("Test " + description.getMethodName(), (end - start));
            logger.info("Test " + description.getMethodName() + " took " + (end - start) + "ms");
        }

    };

    @ClassRule
    public static TestWatcher allTestWatcher = new TestWatcher() {
        @Override
        protected void finished(Description description) {
            StringBuilder sb = new StringBuilder();
            sb.append("\nAll tests time info:\n");
            for (Map.Entry<String, Long> entry : allTestTime.entrySet()) {
                sb.append(entry.getKey()).append(" took ").append(entry.getValue()).append("ms\n");
            }
            logger.info(sb.toString());
        }
    };

    @Autowired
    private MealService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("Not found entity with id=100002");
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void create() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("Not found entity with id=100002");
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("Not found entity with id=100002");
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void createBlankDescription() {
        expectedException.expectCause(IsInstanceOf.instanceOf(RollbackException.class));
        Meal created = getCreated();
        created.setDescription(" ");
        service.create(created, USER_ID);
    }
}