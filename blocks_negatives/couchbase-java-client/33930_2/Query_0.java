  private static final Pattern bboxSplit = Pattern.compile(",);
-  private static final String DESCENDING = descending";
  private static final String ENDKEY = "endkey";
  private static final String ENDKEYDOCID = "endkey_docid";
  private static final String GROUP = "group";
  private static final String GROUPLEVEL = "group_level";
  private static final String INCLUSIVEEND = "inclusive_end";
  private static final String KEY = "key";
  private static final String KEYS = "keys";
  private static final String LIMIT = "limit";
  private static final String REDUCE = "reduce";
  private static final String SKIP = "skip";
  private static final String STALE = "stale";
  private static final String STARTKEY = "startkey";
  private static final String STARTKEYDOCID = "startkey_docid";
  private static final String ONERROR = "on_error";
  private static final String BBOX = "bbox";
  private static final String DEBUG = "debug";
  private boolean includedocs;

  private final Map<String, Object> args;

  /**
   * Creates a new Query object with default settings.
