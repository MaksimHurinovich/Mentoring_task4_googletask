package by.gurinovich.googletask.util;

import java.util.Arrays;
import java.util.List;

public class TextUtil {

    private static final String POSSIBLE_REQUEST_SYMBOLS_REGEX = "[@{}\\[\\](),.\"!?<>:;/]";

    public static List<String> textToWords(String text) {
        return Arrays.asList(text.toUpperCase().replaceAll(POSSIBLE_REQUEST_SYMBOLS_REGEX, "").split(" "));
    }
}
