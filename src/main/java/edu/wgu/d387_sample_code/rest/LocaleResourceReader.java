package edu.wgu.d387_sample_code.rest;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleResourceReader {
    private Locale locale;
    private ResourceBundle resourceBundle;

    public String getWelcomeMessage() {
        return resourceBundle.getString("welcome.message");
    }

    public LocaleResourceReader(String language, String country) {
        locale = new Locale(language, country);
        resourceBundle = ResourceBundle.getBundle("welcome", locale);
    }
}