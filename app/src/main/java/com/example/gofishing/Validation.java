package com.example.gofishing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validation {
    public static boolean Validate(String text, String expression){
        final String regex = expression;
        final String string = text;

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
