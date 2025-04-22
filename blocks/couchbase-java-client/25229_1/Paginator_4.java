  private void fetchNextPage() {
    if (currentState == State.PAGING) {
      if (query.willReduce()) {
        query.setSkip(limit * (currentPage - 1));
      } else {
        query.setStartkeyDocID(nextStartKeyDocID);
        query.setRangeStart(nextStartKey);
      }
    }

    if (totalLimit > 0 && (currentPage * limit) >= totalLimit) {
      int reduceBy = (currentPage * limit) - totalLimit;
      query.setLimit(limit - reduceBy);
