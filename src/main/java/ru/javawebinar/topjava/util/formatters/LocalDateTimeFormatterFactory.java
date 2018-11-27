package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;


public class LocalDateTimeFormatterFactory implements AnnotationFormatterFactory<CustomDateTimeFormatter> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(asList(LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(CustomDateTimeFormatter annotation, Class<?> fieldType) {
        if (annotation.type() == DateTimeType.TIME)
            return new LocalTimeFormatter();
        else
            return new LocalDateFormatter();
    }

    @Override
    public Parser<?> getParser(CustomDateTimeFormatter annotation, Class<?> fieldType) {
        if (annotation.type() == DateTimeType.TIME)
            return new LocalTimeFormatter();
        else
            return new LocalDateFormatter();
    }

    public enum DateTimeType {
        DATE,
        TIME
    }

}
