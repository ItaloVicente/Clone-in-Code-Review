public class ConfigurationParserJSON extends SpyObject implements ConfigurationParser {
    private static final String NAME_ATTR = "name";
    private static final String URI_ATTR = "uri";
    private static final String STREAMING_URI_ATTR = "streamingUri";
    public Map<String, Pool> parseBase(String base) throws ParseException {
        Map<String, Pool> parsedBase = new HashMap<String, Pool>();
        JSONArray poolsJA = null;
        try {
            JSONObject baseJO = new JSONObject(base);
            poolsJA = baseJO.getJSONArray("pools");
        } catch (JSONException e) {
            throw new ParseException("Can not read base " + base, 0);
