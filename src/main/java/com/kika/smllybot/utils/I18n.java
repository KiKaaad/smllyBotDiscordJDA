package com.kika.smllybot.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.kika.smllybot.utils.colors.RED;

public class I18n {
    private static final Map<String, JsonObject> cache = new HashMap<>();

    public static String get(I18nRequest request) {

        String path = request.getFilePath();

        JsonObject root = cache.get(path);

        if (root == null) {
            try (var is = I18n.class.getResourceAsStream(path)) {
                if (is == null) return RED + "I18n | File not found: " + path;

                root = JsonParser.parseReader(new InputStreamReader(is, StandardCharsets.UTF_8)).getAsJsonObject();
                cache.put(path, root);
            } catch (Exception e) {
                return RED + "I18n | Ошибка: " + e.getMessage();
            }
        }

        String key = request.key();
        String[] parts = key.split("\\.");
        JsonElement current = root;

        for (String part : parts) {
            if (current != null && current.isJsonObject()) {
                current = current.getAsJsonObject().get(part);
            } else {
                return RED + "I18n | Ключ " + key + " не найден в " + path;
            }
        }

        // Если все верно - то все верно, иначе в терминале увидишь ключ который мы пытались найти, но не нашли
        return (current != null && current.isJsonPrimitive()) ? current.getAsString() : key;
    }

}
