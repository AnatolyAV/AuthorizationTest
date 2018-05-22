package ru.andreev_av.authorizationtest.utils;

import java.util.Locale;

public class TemperatureUtils {

    public static String getFormatTemperature(float temperature) {
        return (temperature > 0 ? String.format(Locale.getDefault(), "+%.0f", temperature) : String.format(Locale.getDefault(), "%.0f", temperature));
    }
}
