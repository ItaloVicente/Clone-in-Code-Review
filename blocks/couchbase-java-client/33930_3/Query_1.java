  private final String[] params;

  private boolean includeDocs;

  private static final Pattern quotePattern =
    Pattern.compile("^(\".*|\\{.*|\\[.*|true|false|null|-?\\d+([.Ee]\\d+)?)$");

  private final Matcher quoteMatcher = quotePattern.matcher("");

  public Query() {
    this(new String[NUM_PARAMS * 2]);
