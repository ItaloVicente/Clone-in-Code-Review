public abstract class JsonValue {

    public static JsonObject jo() {
        return JsonObject.create();
    }

    public static JsonArray ja() {
        return JsonArray.create();
    }

    protected static boolean checkType(Object item) {
        return item instanceof String
            || item instanceof Integer
            || item instanceof Long
            || item instanceof Double
            || item instanceof Boolean
            || item instanceof JsonObject
            || item instanceof JsonArray;
    }

