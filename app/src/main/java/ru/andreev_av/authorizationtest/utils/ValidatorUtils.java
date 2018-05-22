package ru.andreev_av.authorizationtest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtils {

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})";

    public static boolean checkValidEmail(String email){
        boolean valid;

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        valid = matcher.matches();

        return valid;
    }

    public static boolean checkValidPassword(String password){
        boolean valid;

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        valid = matcher.matches();

        return valid;
    }
}
