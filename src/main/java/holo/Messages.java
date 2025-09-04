package holo;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    private static ResourceBundle bundle;

    public static void setLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("Languages.messages", locale);
    }

    public static String get(String key) {
        return bundle.getString(key);
    }
}
