  private final int hashCode;
  private static final int HIERARICHICAL_FLAG = 0x0100;
  private final String authority;
  private final String fragment;
  private URI cachedTrimFragment;
  private String cachedToString;

  private final String device;
  private static final int ABSOLUTE_PATH_FLAG = 0x0010;
  private final String query;

  private static final URICache uriCache = new URICache();

  private static class URICache extends HashMap<String,WeakReference<URI>>
  {
    private static final long serialVersionUID = 1L;

    static final int MIN_LIMIT = 1000;
    int count;
    int limit = MIN_LIMIT;

    public synchronized URI get(String key)
    {
      WeakReference<URI> reference = super.get(key);
      return reference == null ? null : reference.get();
    }

    public synchronized void put(String key, URI value)
    {
      super.put(key, new WeakReference<URI>(value));
      if (++count > limit)
      {
        cleanGCedValues();
      }
    }

    private void cleanGCedValues()
    {
      for (Iterator<Map.Entry<String,WeakReference<URI>>> i = entrySet().iterator(); i.hasNext(); )
      {
        Map.Entry<String,WeakReference<URI>> entry = i.next();
        WeakReference<URI> reference = entry.getValue();
        if (reference.get() == null)
        {
          i.remove();
        }
      }
      count = 0;
      limit = Math.max(MIN_LIMIT, size() / 2);
    }
  }

  private static final Set<String> archiveSchemes;

  private static final String SCHEME_FILE = "file";
  private static final String SCHEME_JAR = "jar";
  private static final String SCHEME_ZIP = "zip";
  private static final String SCHEME_ARCHIVE = "archive";
  private static final String SCHEME_PLATFORM = "platform";

  private static final String SEGMENT_EMPTY = "";
  private static final String SEGMENT_SELF = ".";
  private static final String SEGMENT_PARENT = "..";
  private static final String[] NO_SEGMENTS = new String[0];

  private static final char SCHEME_SEPARATOR = ':';
  private static final char DEVICE_IDENTIFIER = ':';
  private static final char SEGMENT_SEPARATOR = '/';
  private static final char QUERY_SEPARATOR = '?';
  private static final char FRAGMENT_SEPARATOR = '#';
  private static final char USER_INFO_SEPARATOR = '@';
  private static final char PORT_SEPARATOR = ':';
  private static final char FILE_EXTENSION_SEPARATOR = '.';
  private static final char ARCHIVE_IDENTIFIER = '!';
  private static final String ARCHIVE_SEPARATOR = "!/";

  private static final char ESCAPE = '%';
  private static final char[] HEX_DIGITS = {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  private static final long ALPHA_HI = highBitmask('a', 'z') | highBitmask('A', 'Z');
  private static final long ALPHA_LO = lowBitmask('a', 'z')  | lowBitmask('A', 'Z');
  private static final long DIGIT_HI = highBitmask('0', '9');
  private static final long DIGIT_LO = lowBitmask('0', '9');
  private static final long ALPHANUM_HI = ALPHA_HI | DIGIT_HI;
  private static final long ALPHANUM_LO = ALPHA_LO | DIGIT_LO;
  private static final long HEX_HI = DIGIT_HI | highBitmask('A', 'F') | highBitmask('a', 'f');
  private static final long HEX_LO = DIGIT_LO | lowBitmask('A', 'F')  | lowBitmask('a', 'f');
  private static final long UNRESERVED_HI = ALPHANUM_HI | highBitmask("-_.!~*'()");
  private static final long UNRESERVED_LO = ALPHANUM_LO | lowBitmask("-_.!~*'()");
  private static final long RESERVED_HI = highBitmask(";/?:@&=+$,);
-  private static final long RESERVED_LO = lowBitmask(;/?:@&=+$,);
-  private static final long URIC_HI = RESERVED_HI | UNRESERVED_HI;  // | ucschar | escaped
-  private static final long URIC_LO = RESERVED_LO | UNRESERVED_LO;
-
-  // Additional useful character classes, including characters valid in certain
-  // URI components and separators used in parsing them out of a string.
-  //
-  private static final long SEGMENT_CHAR_HI = UNRESERVED_HI | highBitmask(;:@&=+$,);  // | ucschar | escaped
-  private static final long SEGMENT_CHAR_LO = UNRESERVED_LO | lowBitmask(;:@&=+$,);
-  private static final long PATH_CHAR_HI = SEGMENT_CHAR_HI | highBitmask('/');  // | ucschar | escaped
-  private static final long PATH_CHAR_LO = SEGMENT_CHAR_LO | lowBitmask('/');
+	// Common to all URI types.
+	private final int hashCode;
+	private static final int HIERARICHICAL_FLAG = 0x0100;
+	private final String scheme;  // null -> relative URI reference
+	private final String authority;
+	private final String fragment;
+	private URI cachedTrimFragment;
+	private String cachedToString;
+	//private final boolean iri;
+	//private URI cachedASCIIURI;
+
+	// Applicable only to a hierarchical URI.
+	private final String device;
+	private static final int ABSOLUTE_PATH_FLAG = 0x0010;
+	private final String[] segments; // empty last segment -> trailing separator
+	private final String query;
+
+	// A cache of URIs, keyed by the strings from which they were created.
+	// The fragment of any URI is removed before caching it here, to minimize
+	// the size of the cache in the usual case where most URIs only differ by
+	// the fragment.
+	private static final URICache uriCache = new URICache();
+
+	private static class URICache extends HashMap<String,WeakReference<URI>>
+	{
+		private static final long serialVersionUID = 1L;
+
+		static final int MIN_LIMIT = 1000;
+		int count;
+		int limit = MIN_LIMIT;
+
+		public synchronized URI get(String key)
+		{
+			WeakReference<URI> reference = super.get(key);
+			return reference == null ? null : reference.get();
+		}
+
+		public synchronized void put(String key, URI value)
+		{
+			super.put(key, new WeakReference<URI>(value));
+			if (++count > limit)
+			{
+				cleanGCedValues();
+			}
+		}
+
+		private void cleanGCedValues()
+		{
+			for (Iterator<Map.Entry<String,WeakReference<URI>>> i = entrySet().iterator(); i.hasNext(); )
+			{
+				Map.Entry<String,WeakReference<URI>> entry = i.next();
+				WeakReference<URI> reference = entry.getValue();
+				if (reference.get() == null)
+				{
+					i.remove();
+				}
+			}
+			count = 0;
+			limit = Math.max(MIN_LIMIT, size() / 2);
+		}
+	}
+
+	// The lower-cased schemes that will be used to identify archive URIs.
+	private static final Set<String> archiveSchemes;
+
+	// Identifies a file-type absolute URI.
+	private static final String SCHEME_FILE = file";
