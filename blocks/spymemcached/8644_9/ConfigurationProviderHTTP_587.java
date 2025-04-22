public class ConfigurationProviderHTTP extends SpyObject implements
    ConfigurationProvider {
  private static final String DEFAULT_POOL_NAME = "default";
  private static final String ANONYMOUS_AUTH_BUCKET = "default";
  public static final String CLIENT_SPEC_VER = "1.0";
  private List<URI> baseList;
  private String restUsr;
  private String restPwd;
  private URI loadedBaseUri;
  private Map<String, Bucket> buckets = new ConcurrentHashMap<String, Bucket>();
