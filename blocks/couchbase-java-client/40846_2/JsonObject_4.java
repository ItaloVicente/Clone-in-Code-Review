public class JsonObject extends JsonValue {

    private final Map<String, Object> content;

    private JsonObject() {
        content = new HashMap<String, Object>();
    }

    public static JsonObject empty() {
        return new JsonObject();
    }

    public static JsonObject create() {
        return new JsonObject();
    }

    public JsonObject put(final String name, final Object value) {
        if (checkType(value)) {
            content.put(name, value);
        } else {
            throw new IllegalArgumentException("Unsupported type for JsonObject: " + value.getClass());
        }
        return this;
    }

    public Object get(final String name) {
        return content.get(name);
    }

    public JsonObject put(final String name, final String value) {
        content.put(name, value);
        return this;
    }

    public String getString(String name) {
        return (String) content.get(name);
    }

    public JsonObject put(String name, int value) {
        content.put(name, value);
        return this;
    }

    public Integer getInt(String name) {
        return (Integer) content.get(name);
    }

    public JsonObject put(String name, long value) {
        content.put(name, value);
        return this;
    }

    public Long getLong(String name) {
        return (Long) content.get(name);
    }

    public JsonObject put(String name, double value) {
        content.put(name, value);
        return this;
    }

    public Double getDouble(String name) {
        return (Double) content.get(name);
    }

    public JsonObject put(String name, boolean value) {
        content.put(name, value);
        return this;
    }

    public Boolean getBoolean(String name) {
        return (Boolean) content.get(name);
    }

    public JsonObject put(String name, JsonObject value) {
        content.put(name, value);
        return this;
    }

    public JsonObject getObject(String name) {
        return (JsonObject) content.get(name);
    }

    public JsonObject put(String name, JsonArray value) {
        content.put(name, value);
        return this;
    }

    public JsonArray getArray(String name) {
        return (JsonArray) content.get(name);
    }

    public Set<String> getNames() {
        return content.keySet();
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>(content);
    }

    public boolean containsKey(String name) {
        return content.containsKey(name);
    }

    public boolean containsValue(Object value) {
        return content.containsValue(value);
    }

    public int size() {
        return content.size();
    }
