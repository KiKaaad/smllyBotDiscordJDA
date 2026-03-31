package com.kika.smllybot.utils;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class I18n {
    private static final Map<String, JsonObject> cache = new HashMap<>();

    public static String get(I18nRequest request) {

        String path = request.getFilePath();

    private static final String BASE_PATH = "language.modules";

        if (root == null) {
            try (var is = I18n.class.getResourceAsStream(path)) {
                if (is == null) return RED + "I18n | File not found: " + path;

    public static String get(String module, String key, String lang) {
        String bundlePath = String.format("%s.%s.%s", BASE_PATH, module, module);
        String cacheKey = bundlePath + "_" + lang;

        ResourceBundle bundle = cache.computeIfAbsent(cacheKey, k ->
                ResourceBundle.getBundle(bundlePath, Locale.of(lang))
        );

        return bundle.getString(key);
    }
}

        // Если все верно - то все верно, иначе в терминале увидишь ключ который мы пытались найти, но не нашли
        return (current != null && current.isJsonPrimitive()) ? current.getAsString() : key;
    }

}
