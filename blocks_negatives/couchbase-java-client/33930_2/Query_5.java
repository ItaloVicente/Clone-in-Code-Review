  @Override
  public String toString() {
    boolean first = true;
    StringBuilder result = new StringBuilder();
    for (Entry<String, Object> arg : args.entrySet()) {
      if (first) {
        result.append('?');
        first = false;
      } else {
        result.append('&');
      }
      String argument;
      try {
        argument = arg.getKey() + '=' + prepareValue(
          arg.getKey(), arg.getValue()
        );
      } catch (Exception ex) {
        throw new RuntimeException("Could not prepare view argument: " + ex);
      }
      result.append(argument);
    }
    return result.toString();
  }

  /**
   * Takes a given object, inspects its type and returns
   * its string representation.
   *
   * This helper method aids the toString() method so that it does
   * not need to transform map entries to their string representations
   * for itself. It also checks for various special cases and makes
   * sure the correct string representation is returned.
   *
   * When no previous match was found, the final try is to cast it to a
   * long value and treat it as a numeric value. If this doesn't succeed
   * either, then it is treated as a string.
   *
   * @param key The key for the corresponding value.
   * @param value The value to prepared.
   * @return The correctly formatted and encoded value.
   */
  private static String prepareValue(String key, Object value)
    throws UnsupportedEncodingException {
    String encoded;

    if (key.equals(STARTKEYDOCID) || key.equals(BBOX)) {
      encoded = (String) value;
    } else if (value instanceof Stale) {
      encoded = value.toString();
    } else if (value instanceof OnError) {
      encoded = value.toString();
    } else if (StringUtils.isJsonObject(value.toString())) {
      encoded = value.toString();
    } else if(value.toString().startsWith("\"")) {
      encoded = value.toString();
    } else {
      ParsePosition pp = new ParsePosition(0);
      NumberFormat numberFormat = NumberFormat.getInstance();
      Number result = numberFormat.parse(value.toString(), pp);
      if (pp.getIndex() == value.toString().length()) {
        encoded = result.toString();
      } else {
        encoded = '"' + value.toString() + '"';
      }
