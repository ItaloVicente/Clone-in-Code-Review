  protected String quote(final String source) {
    if (quoteMatcher.reset(source).matches()) {
      ParsePosition parsePosition = new ParsePosition(0);
      Number result = numberFormat.parse(source, parsePosition);
      if (parsePosition.getIndex() == source.length()) {
        return result.toString();
