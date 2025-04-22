  public ViewResponse next() {
    if (first) {
      first = false;
      return page;
    }
    if (!done) {
      query.setSkip(0);
      query.setStartkeyDocID(finalRow.iterator().next().getId());
      query.setRangeStart(finalRow.iterator().next().getKey());
      return getNextPage(query);
    } else {
      return null;
