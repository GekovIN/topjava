package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        if (text.length() == 0)
            return null;
        return LocalTime.parse(text);
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        if (time == null)
            return "";
        return time.toString();
    }
}
