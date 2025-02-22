package br.dev.guilhermecordeiro.consulta_cpfcnpj.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class StringMasker {
    public static Object maskObject(Object obj) {
        if (obj == null) {
            return null;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);

                if ("id".equalsIgnoreCase(field.getName()) || value instanceof Number || value instanceof Boolean) {
                    continue;
                }

                if (value instanceof String) {
                    field.set(obj, maskText((String) value));
                } else if (value instanceof List<?>) {
                    ((List<?>) value).forEach(StringMasker::maskObject);
                } else if (value instanceof Map<?, ?>) {
                    ((Map<?, ?>) value).values().forEach(StringMasker::maskObject);
                } else if (value != null) {
                    maskObject(value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    private static String maskText(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        if (text.length() < 3) {
            return text.replaceAll("[^ ]", "*****");
        }

        int visibleChars = 3;
        StringBuilder maskedText = new StringBuilder(text.substring(0, visibleChars));

        for (int i = visibleChars; i < text.length(); i++) {
            maskedText.append(text.charAt(i) == ' ' ? ' ' : '*');
        }

        return maskedText.toString();
    }
}
