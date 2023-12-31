package CenturionAndMystic.util;

import java.lang.reflect.Field;
import java.util.HashMap;

public class KeywordManager {
    public static HashMap<String, String> keywordMap = new HashMap<>();
    public static String CENTURION;
    public static String MYSTIC;
    public static String COIL;
    public static String EXPOSED;
    public static String STAGGER;
    public static String IMBUE;
    public static String CHARGED;
    public static String POISED;
    public static String INFUSE;
    public static String SURVEY;
    public static String VENOM;
    public static String INTIMIDATED;
    public static String HEAVY_PLATING;

    public static String TOXIN;
    public static String NOX;
    public static String DRAIN;
    public static String HEX;
    public static String MIGHT;

    public static String getKeyword(String ID) {
        return keywordMap.getOrDefault(ID, "");
    }

    public static void setupKeyword(String ID, String key) {
        keywordMap.put(ID, key);
        Field[] fields = KeywordManager.class.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().toLowerCase().equals(ID)) {
                try {
                    f.set(null, key);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
