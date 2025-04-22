  private void fetchNextPage() {
    if (currentState == State.PAGING) {
      if (query.willReduce()) {
        query.setSkip(limit * (currentPage - 1));
      } else {
        query.setStartkeyDocID(nextStartKeyDocID);
        query.setRangeStart(castKey(nextStartKey));
      }
