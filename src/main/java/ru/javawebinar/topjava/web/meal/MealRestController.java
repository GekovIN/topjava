package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.formatters.CustomDateTimeFormatter;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.formatters.LocalDateTimeFormatterFactory.DateTimeType;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

//    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//                                    produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<MealTo> getBetween(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime startDate,
//                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime endDate) {
//        return super.getBetween(startDate.toLocalDate(), startDate.toLocalTime(),
//                                 endDate.toLocalDate(), endDate.toLocalTime());
//    }

    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@CustomDateTimeFormatter(type = DateTimeType.DATE) @RequestParam LocalDate startDate,
                                   @CustomDateTimeFormatter(type = DateTimeType.TIME) @RequestParam LocalTime startTime,
                                   @CustomDateTimeFormatter(type = DateTimeType.DATE) @RequestParam LocalDate endDate,
                                   @CustomDateTimeFormatter(type = DateTimeType.TIME) @RequestParam LocalTime endTime) {

        if (startDate == null)
            startDate = DateTimeUtil.MIN_DATE;
        if (endDate == null)
            endDate = DateTimeUtil.MAX_DATE;
        if (startTime == null)
            startTime = LocalTime.MIN;
        if (endTime == null)
            endTime = LocalTime.MAX;

        List<MealTo> between = super.getBetween(startDate, startTime, endDate, endTime);
        return between;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal) {
        super.update(meal, meal.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }


}