public class ConfigurationParserJSON extends SpyObject
  implements ConfigurationParser {

  private final ConfigFactory configFactory = new DefaultConfigFactory();

  public Map<String, Pool> parsePools(final String poolsJson)
    throws ParseException {
    final Map<String, Pool> parsedBase = new HashMap<String, Pool>();
    final JSONArray allPools;

