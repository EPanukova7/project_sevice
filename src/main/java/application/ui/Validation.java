package application.ui;

import java.util.regex.Pattern;

public class Validation {

    public static boolean isCorrectEmail(String url){
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return patternMatches(url, pattern);
    }

    public static boolean isCorrectName(String name){
        String pattern = "^[A-Za-z0-9_-]{1,32}$";
        return patternMatches(name, pattern);
    }

    public static boolean isCorrectPassword(String password){
        return 6 <= password.length() && password.length() <= 64;
    }

    public static boolean patternMatches(String string, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(string)
                .matches();
    }
}
