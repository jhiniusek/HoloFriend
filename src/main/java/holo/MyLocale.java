package holo;

import java.util.Locale;

enum MyLocale {
    ENGLISH(Locale.ENGLISH),
    JAPANESE(Locale.JAPANESE);

    private final Locale locale;
    MyLocale(Locale locale) { this.locale = locale; }
    public Locale getLocale() { return locale; }
}