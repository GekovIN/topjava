package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (text.length() == 0)
            return null;
        return LocalDate.parse(text);
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        if (date == null)
            return "";
        return date.toString();
    }
}
