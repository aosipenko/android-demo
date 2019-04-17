package com.company.app.tests.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * This class allows to transform numbers from deciman format to #.#M/B/K format:
 * For exmaple, 6341101 would be 6.3M, 2330 would be 2.3K, etc
 */
public class NumberFormatterUtil {
    public static String formatValue(double value) {
        int power;
        String suffix = " kmbt";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int) StrictMath.log10(value);
        value = value / (Math.pow(10, (power / 3) * 3));
        formattedNumber = formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power / 3);
        return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }
}
