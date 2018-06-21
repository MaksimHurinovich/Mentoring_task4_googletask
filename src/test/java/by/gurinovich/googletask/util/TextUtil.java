package by.gurinovich.googletask.util;

import java.util.Arrays;
import java.util.List;

public class TextUtil {

    public static List<String> textToWords(String text) {
        return Arrays.asList(text.toUpperCase().replaceAll("[@{}\\[\\](),.\"!?<>:;/]", "").split(" "));
    }
}
