  private static final Pattern decimalPattern = Pattern.compile("^-?\\d+$");

  private static final Matcher decimalMatcher = decimalPattern.matcher("");

  private static final int MAX_KEY_LENGTH = MemcachedClientIF.MAX_KEY_LENGTH;

  private static final IllegalArgumentException KEY_TOO_LONG_EXCEPTION =
    new IllegalArgumentException("Key is too long (maxlen = "
      + MAX_KEY_LENGTH + ")");

  private static final IllegalArgumentException KEY_EMPTY_EXCEPTION =
    new IllegalArgumentException("Key must contain at least one character.");

  static {
    KEY_TOO_LONG_EXCEPTION.setStackTrace(new StackTraceElement[0]);
    KEY_EMPTY_EXCEPTION.setStackTrace(new StackTraceElement[0]);
  }

