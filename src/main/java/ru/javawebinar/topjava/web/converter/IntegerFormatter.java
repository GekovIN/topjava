package ru.javawebinar.topjava.web.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class IntegerFormatter implements Formatter<Integer> {

    @Override
    public Integer parse(String text, Locale locale) throws ParseException {
        if (text.isBlank() || "\"\"".equals(text))
            return 0;
        return Integer.parseInt(text);
    }

    @Override
    public String print(Integer integer, Locale locale) {
        return String.valueOf(integer);
    }
}
