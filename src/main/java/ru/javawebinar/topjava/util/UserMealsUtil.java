package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 520),

                new UserMeal(LocalDateTime.of(2015, Month.DECEMBER, 30,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.DECEMBER, 30,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.DECEMBER, 30,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filtered = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        filtered.forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();
    }

//    Версия с одним полным проходом по mealList:

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> calPerDayMap = new HashMap<>();

        List<UserMeal> tmpUserMealList = mealList.stream()
                .filter(u -> { calPerDayMap.merge(u.getDateTime().toLocalDate(), u.getCalories(), (i1, i2) -> i1 += i2);
                              LocalTime time = u.getDateTime().toLocalTime();
                              return time.isAfter(startTime) && time.isBefore(endTime);})
                .collect(Collectors.toList());

        return tmpUserMealList.stream().map(u ->
                new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(), calPerDayMap.get(u.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    //Другие версии метода:
//  Версия со стримами и двумя проходами по mealList:

//    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//
//        Map<LocalDate, Integer> calPerDayMap = mealList
//                .stream()
//                .collect(Collectors.toMap(u -> u.getDateTime().toLocalDate(), UserMeal::getCalories, (c1, c2) -> c1 + c2));
//
//        return mealList
//                .stream()
//                .filter(u -> { LocalTime time = u.getDateTime().toLocalTime();
//                               return time.isAfter(startTime) && time.isBefore(endTime);})
//                .map(u -> new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(), calPerDayMap.get(u.getDateTime().toLocalDate()) > caloriesPerDay))
//                .collect(Collectors.toList());
//    }


//    Версия с обычными циклами:

//    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//
//        List<UserMealWithExceed> resultList = new ArrayList<>();
//        Map<Integer, Integer> calPerDayMap = new HashMap<>();
//        List<UserMeal> tmpUserMealList = new ArrayList<>();
//
//        for (UserMeal userMeal : mealList) {
//            LocalDateTime tmpDateTime = userMeal.getDateTime();
//            calPerDayMap.merge(tmpDateTime.getDayOfMonth(), userMeal.getCalories(), (integer, integer2) -> integer += integer2);
//            LocalTime tmpTime = tmpDateTime.toLocalTime();
//            if (tmpTime.isAfter(startTime) && tmpTime.isBefore(endTime))
//                tmpUserMealList.add(userMeal);
//        }
//
//        for (UserMeal userMeal : tmpUserMealList) {
//            UserMealWithExceed mealWithExceed = new UserMealWithExceed(userMeal.getDateTime(),
//                                                                        userMeal.getDescription(),
//                                                                        userMeal.getCalories(),
//                                                                        calPerDayMap.get(userMeal.getDateTime().getDayOfMonth()) > caloriesPerDay);
//            resultList.add(mealWithExceed);
//        }
//
//        return resultList;

//    }


}
